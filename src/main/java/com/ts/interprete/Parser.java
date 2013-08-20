package com.ts.interprete;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.ts.db.Repo;
import com.ts.interprete.Token.TokenType;
import com.ts.interprete.libraries.CreateComando;
import com.ts.interprete.libraries.Expression;
import com.ts.interprete.libraries.ShowComando;
import com.ts.libraries.Boolean;
import com.ts.libraries.Colon;
import com.ts.libraries.CommandException;
import com.ts.libraries.Decimal;
import com.ts.libraries.Dolar;
import com.ts.libraries.Fecha;
import com.ts.libraries.FechaHora;
import com.ts.libraries.Hilera;
import com.ts.libraries.Moneda;
import com.ts.libraries.Numeric;
import com.ts.libraries.Objecto;
import com.ts.interprete.libraries.Comando;


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
		FechaHora fecha = paserFechaHora();
		
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
		ArrayList<Objecto> params = parseParametros();
	
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
		
		Constructor constructor =/* clase.getConstructors()[0];*/clase.getConstructor(firmaConstructor);
		Objecto objecto = (Objecto)constructor.newInstance(firmaConstructorValores);
		Expression exp = new Expression(objecto);
		
		return new CreateComando(new Hilera(nombreVarible), exp);
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
		lexer.accept(TokenType.variable);	
		Method method ;
		Objecto resultado = null; 
		
		while(lexer.getToken().getType() == TokenType.punto)
		{
			lexer.accept(TokenType.punto);
			String methodName = lexer.getToken().getValor();
			lexer.accept(TokenType.variable);
			boolean tieneParametros = lexer.sgtToken().getType() != TokenType.rParentesis;	
						
			if( tieneParametros )
			{
				ArrayList<Objecto> params = parseParametros();

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

	private ArrayList<Objecto> parseParametros() throws Exception {
		ArrayList<Objecto> params = new ArrayList<Objecto>();
		Objecto content;
		lexer.accept(TokenType.lParentesis);
		
		while(lexer.sgtToken().getType() == TokenType.coma)
		{
			content = parseLiteral();
			params.add(content);
			lexer.accept(TokenType.coma);

		}
		content = parseLiteral();
		params.add(content);
		lexer.accept(TokenType.rParentesis);
		return params;
	}

	
	private Fecha paserFecha() {
		if(lexer.getToken().getType() != TokenType.fecha)
		{
			throw new CommandException("Se esperaba una fecha.");
		}
		
		String[] tiempo = lexer.getToken().getValor().split("/");
				
		int dia = Integer.parseInt(tiempo[0]);
		int mes = Integer.parseInt(tiempo[1]);
		int anno = Integer.parseInt(tiempo[2]);
		
		//TODO validaciones de fecha. validar dia, mes anno biciesto.
	
		lexer.accept(TokenType.fecha);
		
		return new Fecha(dia, mes, anno);
	}
	
	private FechaHora paserFechaHora() {
		Fecha fecha = paserFecha();
				
		int dia = fecha.getDia();
		int mes = fecha.getMes();
		int anno = fecha.getAnno();
		int hora = 0;
		int minuto = 0;
		int segundo = 0;
		
		boolean tieneHora = ! (lexer.getToken() != null && lexer.getToken().getType() == TokenType.hora);
				
		if(tieneHora)
		{
			throw new CommandException("Se esperaba una hora.");
		}
		
		String []tiempo = lexer.getToken().getValor().split(":");
		hora = Integer.parseInt(tiempo[0]);
		minuto = Integer.parseInt(tiempo[1]);
		segundo = Integer.parseInt(tiempo[2]);
		lexer.accept(TokenType.hora);
		//TODO validaciones de fecha. validar dia, mes anno biciesto.
	
		return new FechaHora(dia, mes, anno, hora, minuto, segundo);
	}

	private Objecto parseLiteral() throws Exception
	{
		boolean sonParametros= lexer.getToken().getType() == TokenType.lParentesis;
		boolean esUnNumero = lexer.getToken().getType() == TokenType.numero;
		boolean esUnDecimal = lexer.getToken().getType() == TokenType.decimal;
		boolean esUnLiteral = lexer.getToken().getType() == TokenType.literal;
		boolean esUnMonto = lexer.getToken().getType() == TokenType.monto;
		boolean esUnaFecha = lexer.getToken().getType() == TokenType.fecha;
		boolean esUnaFechaHora = esUnaFecha && lexer.sgtToken().getType() == TokenType.hora;
		boolean esUnBoolean = lexer.getToken().getType() == TokenType.boolFalse || lexer.getToken().getType() == TokenType.boolTrue;
		
		if( esUnaFechaHora )
		{
			return paserFechaHora();		
		}
		else if( esUnaFecha )
		{
			return paserFecha();		
		}
		else if( esUnMonto )
		{
			return parserMonto();
		}
		else if( esUnNumero )
		{
			return parserNumero();
		}
		else if( esUnDecimal )
		{
			return parserDecimal();
		}
		else if( esUnLiteral )
		{
			return  parserLiteral();
		}
		else if( esUnBoolean )
		{
			return  parserBoolean();
		}
		else
		{
			throw new CommandException("El tipo de dato no existe.");
		}
	}

	private Boolean parserBoolean() {
		Boolean literal = new Boolean(java.lang.Boolean.parseBoolean(lexer.getToken().getValor()));
		lexer.accept();
		return literal;
	}

	private Hilera parserLiteral() {
		Hilera literal = new Hilera(lexer.getToken().getValor());
		lexer.accept();
		return literal;
	}

	private Decimal parserDecimal() {
		Decimal decimalLiteral = new Decimal(Double.parseDouble(lexer.getToken().getValor()));
		lexer.accept();
		return decimalLiteral;
	}

	private Numeric parserNumero() {
		Numeric numericLiteral = new Numeric(Integer.parseInt(lexer.getToken().getValor()));
		lexer.accept();
		return numericLiteral;
	}

	private Moneda parserMonto() {
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
		lexer.accept();
		return moneda;
	}
}
