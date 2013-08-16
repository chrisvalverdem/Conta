package com.ts.interprete;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ts.interprete.Token.TokenType;

public class LexerTest {
	Lexer lexer;
	
	@Test
	public void testFechaLexer()
	{
		lexer = new Lexer("show 11/01/2013");
		Assert.assertEquals(TokenType.show.name(), lexer.getToken().getType().name());
		Assert.assertEquals("show", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals(TokenType.fecha.name(), lexer.getToken().getType().name());
		Assert.assertEquals("11/01/2013", lexer.getToken().getValor());
		lexer.accept();
	}

	@Test
	public void testMontoLexer()
	{
		lexer = new Lexer("show $5000");
		Assert.assertEquals(TokenType.show.name(), lexer.getToken().getType().name());
		Assert.assertEquals("show", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals(TokenType.monto.name(), lexer.getToken().getType().name());
		Assert.assertEquals("$", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals(TokenType.numero.name(), lexer.getToken().getType().name());
		Assert.assertEquals("5000", lexer.getToken().getValor());
		lexer.accept();
		lexer = new Lexer("show ¢120.01");
		Assert.assertEquals(TokenType.show.name(), lexer.getToken().getType().name());
		Assert.assertEquals("show", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals(TokenType.monto.name(), lexer.getToken().getType().name());
		Assert.assertEquals("¢", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals(TokenType.decimal.name(), lexer.getToken().getType().name());
		Assert.assertEquals("120.01", lexer.getToken().getValor());
		lexer.accept();
	}
	
	@Test
	public void testOperadoresLexer()
	{
		lexer = new Lexer("show 1000 + 1.5 - 5 / 10.5 * 10.5;");
		Assert.assertEquals(TokenType.show.name(), lexer.getToken().getType().name());
		Assert.assertEquals("show", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals(TokenType.numero.name(), lexer.getToken().getType().name());
		Assert.assertEquals("1000", lexer.getToken().getValor());
		lexer.accept();
		Assert.assertEquals("+", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.suma.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("1.5", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.decimal.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("-", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.resta.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("5", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.numero.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("/", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.division.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("10.5", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.decimal.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("*", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.multiplicacion.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("10.5", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.decimal.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(";", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.puntoComa.name(), lexer.getToken().getType().name());
		lexer.accept();
	}
	
	@Test
	public void testVariablesLexer()
	{
		lexer = new Lexer("cguillen.getNombre();");
		Assert.assertEquals("cguillen", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.variable.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(".", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.punto.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("getNombre", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.variable.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("(", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.lParentesis.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(")", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.rParentesis.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(";", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.puntoComa.name(), lexer.getToken().getType().name());
		lexer.accept();
	}
	
	@Test
	public void testLiteralesLexer()
	{
		lexer = new Lexer("\"Hola Mundo\";");
		Assert.assertEquals("Hola Mundo", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.literal.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(";", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.puntoComa.name(), lexer.getToken().getType().name());
		lexer.accept();
		
		lexer = new Lexer("'Hola Mundo';");
		Assert.assertEquals("Hola Mundo", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.literal.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(";", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.puntoComa.name(), lexer.getToken().getType().name());
		lexer.accept();
	}
	
	@Test
	public void testDeclaracionesLexer()
	{
		lexer = new Lexer("cguillen = colaborador(\"cristian\", 12);");
		Assert.assertEquals("cguillen", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.variable.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("=", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.igual.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("colaborador", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.variable.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("(", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.lParentesis.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("cristian", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.literal.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(",", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.coma.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals("12", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.numero.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(")", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.rParentesis.name(), lexer.getToken().getType().name());
		lexer.accept();
		Assert.assertEquals(";", lexer.getToken().getValor());
		Assert.assertEquals(TokenType.puntoComa.name(), lexer.getToken().getType().name());	
	}

}
