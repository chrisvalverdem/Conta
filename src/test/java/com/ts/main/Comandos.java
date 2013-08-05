package com.ts.main;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ts.main.InterpreteMandatos;
import com.ts.objects.Colaborador;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;


public class Comandos {

	InterpreteMandatos interpreteMandatos;
	
	
	@BeforeTest
	public void setUp() throws IOException
	{
		System.setOut(new PrintStream(new File("logTestCases.txt")));
		new File("logTestCases.txt").delete();
		interpreteMandatos = new InterpreteMandatos(false);	
	}
	
	@Test
	public void agregarCompanniaTest() throws IOException, CommandException
	{
		String comando ="t.crear_compannia(j456, ts)";
		interpreteMandatos.ejecutaComando(comando);
		Compannia compannia= Repo.getCompannia("j456");
		
		Assert.assertNotNull(compannia);
		Assert.assertEquals(Repo.getTamannoCompannia(),1);
	}
	
	@Test
	public void agregarEdificioTest() throws IOException, CommandException
	{
		String comando ="elguarco.crear_edificio(ts2)";
		interpreteMandatos.ejecutaComando(comando);
		Edificio edificio= Repo.getEdificio("ts2");
		
		Assert.assertNotNull(edificio);
		Assert.assertEquals(edificio.getNombre(), "ts2");
		
	}
	
	@Test
	public void agregarColaboradorTest() throws IOException, CommandException
	{
		String comando ="Cguillen= crear_Colaborador(Jahzeel, 1-1111-1112, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		Colaborador colaborador= Repo.getColaborador("1-1111-1112");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getNombre(), "jahzeel");
		
	}
	
	@Test
	public void aumentarSalario() throws IOException, CommandException
	{
		String comando1 ="Cguillen= crear_Colaborador(Jahzeel, 1-1111-1111, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $1000)";
		String comando2 ="Cguillen.aumentar_salario($2000)";
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		Colaborador colaborador= Repo.getColaborador("1-1111-1111");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario(), "$2000");
		
		
		
		
	}
}
