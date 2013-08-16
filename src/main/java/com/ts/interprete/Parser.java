package com.ts.interprete;

import com.ts.interprete.Token.TokenType;
import com.ts.interprete.libraries.Colon;
import com.ts.interprete.libraries.DecimalLiteral;
import com.ts.interprete.libraries.Dolar;
import com.ts.interprete.libraries.Expression;
import com.ts.interprete.libraries.Fecha;
import com.ts.interprete.libraries.Literal;
import com.ts.interprete.libraries.Moneda;
import com.ts.interprete.libraries.NumericLiteral;
import com.ts.interprete.libraries.Parameters;
import com.ts.interprete.libraries.ShowComando;
import com.ts.interprete.libraries.Variable;
import com.ts.main.Comando;
import com.ts.objects.CommandException;


public class Parser {

	private Lexer lexer;
	
	public Parser(String input)
	{
		lexer = new Lexer(input);
	}
	
	public Expression process()
	{
		Expression result = subProcess();
		return result;
	}
	
	private Expression subProcess()
	{
		boolean esUnComandoShow = lexer.getToken().getType() == TokenType.show;
		boolean esUnaVariableSeguidaDeIgual= lexer.getToken().getType() == TokenType.variable && lexer.sgtToken().getType() == TokenType.igual ;
		boolean sonParametros= lexer.getToken().getType() == TokenType.lParentesis;
		boolean esUnNumero = lexer.getToken().getType() == TokenType.numero;
		boolean esUnDecimal = lexer.getToken().getType() == TokenType.decimal;
		boolean esUnLiteral = lexer.getToken().getType() == TokenType.literal;
		boolean esUnMonto = lexer.getToken().getType() == TokenType.monto;
		boolean esUnaFecha = lexer.getToken().getType() == TokenType.fecha;
		
		if( esUnComandoShow )
		{
			lexer.accept();
			Expression content = subProcess();
			return new ShowComando(content);
		}
		else if( esUnaFecha )
		{
			String[] tiempo = lexer.getToken().getValor().split("/");
			boolean tieneHora = lexer.sgtToken() != null && lexer.sgtToken().getType() == TokenType.hora;
			
			lexer.accept();
			int dia = Integer.parseInt(tiempo[0]);
			int mes = Integer.parseInt(tiempo[1]);
			int anno = Integer.parseInt(tiempo[2]);
			int hora = 0;
			int minuto = 0;
			int segundo = 0;
			
			if(tieneHora)
			{
				tiempo = lexer.getToken().getValor().split(":");
				hora = Integer.parseInt(tiempo[0]);
				minuto = Integer.parseInt(tiempo[1]);
				segundo = Integer.parseInt(tiempo[2]);
				lexer.accept();
			}
			
			return new Fecha(dia, mes, anno, hora, minuto, segundo);
			
		}
		else if( esUnMonto )
		{
			String economia = lexer.getToken().getValor();
			lexer.accept();
			Double cantidad = Double.parseDouble(lexer.getToken().getValor());
			Moneda moneda;
			
			if(Moneda.isColon(economia))
			{
				moneda = new Colon(cantidad);
			}
			else if(Moneda.isDolar(economia))
			{
				moneda = new Dolar(cantidad);
			}
			else
			{
				throw new CommandException("Economia no soportada;");
			}
			
			return moneda;
		}
		else if( esUnaVariableSeguidaDeIgual )
		{
			String nombreVarible = lexer.getToken().getValor();
			lexer.accept();
			lexer.accept(TokenType.igual);
			String comando = lexer.getToken().getValor();
			lexer.accept();
			Parameters params = (Parameters)subProcess();
			return new Variable(nombreVarible, comando, params);
		}
		else if( sonParametros ) 
		{
			lexer.accept(TokenType.lParentesis);
			Parameters params = new Parameters();
			
			while(lexer.sgtToken().getType() == TokenType.coma)
			{
				Expression content = subProcess();
				params.add(content);
				lexer.accept();
				lexer.accept(TokenType.coma);
			}
			Expression content = subProcess();
			params.add(content);
			lexer.accept();
			lexer.accept(TokenType.rParentesis);
			return params;
		}
		else if( esUnNumero )
		{
			return new NumericLiteral(Long.parseLong(lexer.getToken().getValor()));
		}
		else if( esUnDecimal )
		{
			return new DecimalLiteral(Double.parseDouble(lexer.getToken().getValor()));
		}
		else if( esUnLiteral )
		{
			return  new Literal(lexer.getToken().getValor());
		}
		else
		{
			throw new CommandException("El comando no existe.");
		}
	}
}
