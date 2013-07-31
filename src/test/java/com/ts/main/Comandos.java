package com.ts.main;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.ts.main.InterpreteMandatos;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Repo;


public class Comandos {

	InterpreteMandatos interpreteMandatos;
	
	
	@BeforeTest
	public void setUp() throws IOException
	{
		System.setOut(new PrintStream(new File(ArchivoLog.LOG_NAME)));
		new File(ArchivoLog.LOG_NAME).delete();
		interpreteMandatos = new InterpreteMandatos(false);	
	}
	
	@Test
	public void agregarCompanniaTest() throws IOException, CommandException
	{
		String comando ="crear_compannia";
		String cedComando= "12,";
		int cedula = Integer.parseInt(cedComando.substring(0, cedComando.length()-1));
		String nombre = " TestingSoftware;";
		comando+=(" "+ cedComando + nombre);
		interpreteMandatos.ejecutaComando(comando);
		Compannia compannia= Repo.getCompannia(cedula);
		
		Assert.assertNotNull(compannia);
	}
	
	@Test
	public void agregarEdificioTest() throws IOException, CommandException
	{
		String comando ="crear_edificio";
		String nombre = "Ts1;";
		comando += " "+nombre;
		interpreteMandatos.ejecutaComando(comando);
		Edificio edificio= Repo.getEdificio(nombre);
		
		Assert.assertNotNull(edificio);
	}
	@AfterTest
	public void end() throws IOException
	{
		interpreteMandatos.ejecutaComando("exit");
		new File(ArchivoLog.LOG_NAME).delete();
		
	}
}
