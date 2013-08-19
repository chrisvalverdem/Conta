package com.ts.interprete;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.ts.db.Repo;
import com.ts.interprete.Token.TokenType;
import com.ts.interprete.libraries.CreateComando;
import com.ts.interprete.libraries.Expression;
import com.ts.interprete.libraries.ShowComando;
import com.ts.libraries.Colon;
import com.ts.libraries.DecimalLiteral;
import com.ts.libraries.Dolar;
import com.ts.libraries.Fecha;
import com.ts.libraries.Literal;
import com.ts.libraries.Moneda;
import com.ts.libraries.NumericLiteral;
import com.ts.libraries.Objecto;
import com.ts.interprete.libraries.Comando;
import com.ts.objects.CommandException;


public class Parser {

	private Lexer lexer;
	
	public Parser(String input)
	{
		lexer = new Lexer(input);
	}
	
	public void process() throws Exception
	{
		Comando result = comandoProcess();
		result.execute();
	}
	
	protected Comando comandoProcess() throws Exception
	{
		//TODO retorna un comando que no hereda de expression.
		Fecha fecha = paserFechaHora();
		
		boolean esUnComandoShow = lexer.getToken().getType() == TokenType.show;
		boolean esUnComandoCreate= lexer.getToken().getType() == TokenType.variable && lexer.sgtToken().getType() == TokenType.igual ;
		
		if( esUnComandoShow )
		{
			return paserComandoShow();
		}
		else if( esUnComandoCreate )
		{
			return parseCommandCreate();
		}
		else
		{
			throw new CommandException("El comando no existe.");
		}
	}
	
	private CreateComando parseCommandCreate() throws Exception {
		String nombreVarible = lexer.getToken().getValor();
		lexer.accept();
		lexer.accept(TokenType.igual);
		String claseVarible = lexer.getToken().getValor();
		lexer.accept();
		ArrayList<Objecto> params = parseparametros();
	
		Class clase = Class.forName(Objecto.class.getPackage().getName()+"."+claseVarible);

		Class[] firmaConstructor = new Class[params.size()];
		Object[] firmaConstructorValores = new Object[params.size()];
		
		int index =0;
		for( Objecto parameter: params )
		{
			firmaConstructor[index] = parameter.getClass();
			firmaConstructorValores[index] = parameter;
			System.out.println(firmaConstructor[index].getName()+"   "+firmaConstructorValores[index].getClass());
			index++;
		}
		
		Constructor constructor = clase.getConstructors()[0];/*clase.getConstructor(firmaConstructor)*/;
		Objecto objecto = (Objecto)constructor.newInstance(firmaConstructorValores);
		Expression exp = new Expression(objecto);
		
		return new CreateComando(nombreVarible, exp);
	}

	private ShowComando paserComandoShow() throws Exception {
		if(lexer.getToken().getType() != TokenType.show)
		{
			throw new CommandException("Se esperaba un show.");
		}
		lexer.accept();		
		Objecto objecto = paserVariable();
		Expression exp = new Expression(objecto);
		return new ShowComando(exp);
	}
	private Objecto paserVariable() throws Exception {
		
		if(lexer.getToken().getType() != TokenType.variable)
		{
			throw new CommandException("Se esperaba un nombre de variable.");
		}
		if(lexer.sgtToken().getType() != TokenType.punto)
		{
			throw new CommandException("Se esperaba un metodo.");
		}
		String key = lexer.getToken().getValor();
		Objecto objecto = Repo.getData(key);
		lexer.accept();	
		Method method ;
		Objecto resultado = null; 
		
		while(lexer.sgtToken().getType() == TokenType.punto)
		{
			lexer.accept(TokenType.punto);	
			String methodName = lexer.getToken().getValor();
			lexer.accept();
			boolean tieneParametros = lexer.sgtToken().getType() != TokenType.rParentesis;
			lexer.accept(TokenType.lParentesis);
						
			if( tieneParametros )
			{
				ArrayList<Objecto> params = parseparametros();

				Class[] firma = new Class[params.size()];
				Object[] firmaValores = new Objecto[params.size()];
				int index =0;
				for( Objecto parameter: params )
				{
					firma[index] = parameter.getClass();
					firmaValores[index] = parameter;
					System.out.println(firma[index].getName()+"   "+firmaValores[index].getClass());
					index++;
				}
				
				method = objecto.getClass().getMethod (methodName, firma);
				resultado = (Objecto) method.invoke(objecto, firmaValores);
			}
			else
			{
				method = objecto.getClass().getMethod(methodName);
				resultado = (Objecto) method.invoke(objecto);
			}
		}

		return resultado;
	}

	private ArrayList<Objecto> parseparametros() throws Exception {
		ArrayList<Objecto> params = new ArrayList<Objecto>();
		Objecto content;
		while(lexer.sgtToken().getType() == TokenType.coma)
		{
			content = subProcess();
			params.add(content);
			lexer.accept();
			lexer.accept(TokenType.coma);

		}
		content = subProcess();
		params.add(content);
		lexer.accept(TokenType.lParentesis);
		return params;
	}

	private Fecha paserFechaHora() {
		if(lexer.getToken().getType() != TokenType.fecha)
		{
			throw new CommandException("Se esperaba una fecha.");
		}
		
		String[] tiempo = lexer.getToken().getValor().split("/");
				
		int dia = Integer.parseInt(tiempo[0]);
		int mes = Integer.parseInt(tiempo[1]);
		int anno = Integer.parseInt(tiempo[2]);
		int hora = 0;
		int minuto = 0;
		int segundo = 0;
		
		//TODO validaciones de fecha. validar dia, mes anno biciesto.
		lexer.accept(TokenType.fecha);
		boolean tieneHora = ! (lexer.sgtToken() != null && lexer.sgtToken().getType() == TokenType.hora);
		
		if(tieneHora)
		{
			throw new CommandException("Se esperaba una hora.");
		}
		
		lexer.accept();
		tiempo = lexer.getToken().getValor().split(":");
		hora = Integer.parseInt(tiempo[0]);
		minuto = Integer.parseInt(tiempo[1]);
		segundo = Integer.parseInt(tiempo[2]);
		lexer.accept(TokenType.hora);
		//TODO validaciones de fecha. validar dia, mes anno biciesto.
	
		return new Fecha(dia, mes, anno, hora, minuto, segundo);
	}

	private Objecto subProcess() throws Exception
	{
		boolean sonParametros= lexer.getToken().getType() == TokenType.lParentesis;
		boolean esUnNumero = lexer.getToken().getType() == TokenType.numero;
		boolean esUnDecimal = lexer.getToken().getType() == TokenType.decimal;
		boolean esUnLiteral = lexer.getToken().getType() == TokenType.literal;
		boolean esUnMonto = lexer.getToken().getType() == TokenType.monto;
		boolean esUnaFecha = lexer.getToken().getType() == TokenType.fecha;
		
		if( esUnaFecha )
		{
			return paserFechaHora();
			
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
		else if( esUnNumero )
		{
			return new NumericLiteral(Integer.parseInt(lexer.getToken().getValor()));
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
