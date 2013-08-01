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
	
}
