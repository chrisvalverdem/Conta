package com.ts.main;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Repo;

public class InterpreteComandos {
	
	@BeforeTest
	public void setUp() throws IOException
	{
		//System.setOut(new PrintStream(new File("PRUEBAS.LOG")));
		new File(ArchivoLog.LOG_NAME).delete();
			
	}
	
	@Test
	public void pruebaCargaArchivo() throws IOException, CommandException{

		InterpreteMandatos interprete = new InterpreteMandatos(false);
		interprete.ejecutaComando("ts.crear_edificio(occidente)");
		interprete.ejecutaComando("cecropia=crear_compannia(123JK456, ts)");
		interprete.ejecutaComando("ts.crear_compannia(789, cecropia)");
		
		new InterpreteMandatos(false);
		
		Compannia compannia= Repo.getCompannia("123JK456");
		Edificio edificio= Repo.getEdificio("occidente");
		
		Assert.assertNotNull(compannia);
		Assert.assertEquals(compannia.getNombre(),"ts");
		Assert.assertEquals(Repo.getTamannoCompannia(), 2);
		
		Assert.assertNotNull(edificio);
		Assert.assertEquals(edificio.getNombre(),"occidente");
		Assert.assertEquals(Repo.getTamannoEdificio(), 1);
	
	}
	
	@Test
	public void pruebaParseComandos() throws IOException, CommandException{

		InterpreteMandatos interprete = new InterpreteMandatos(false);
		Comando comando =interprete.interpreteCadena("ts=crear_compannia(123456789, cecropia)");
		Comando comando2 =interprete.interpreteCadena("juan.crear_colaborador(juan, 123456)");
		
		Assert.assertEquals(comando.getMetodo(),"crear_compannia");		
		Assert.assertEquals(comando.getInstance(),"ts");
		Assert.assertEquals(comando.getParametros()[0],"123456789");
		Assert.assertEquals(comando.getParametros()[1],"cecropia");
		
		
		Assert.assertEquals(comando2.getMetodo(),"crear_colaborador");		
		Assert.assertEquals(comando2.getInstance(),"juan");
		Assert.assertEquals(comando2.getParametros()[0],"juan");
		Assert.assertEquals(comando2.getParametros()[1],"123456");
			
		
		
	}
	@Test
	public void pruebaParseEspacios() throws IOException, CommandException{

		InterpreteMandatos interprete = new InterpreteMandatos(false);
		Comando comando =interprete.interpreteCadena("ts  =  crear_compannia  (  123456789  ,   cecropia   )");	
		
		Assert.assertEquals(comando.getMetodo(),"crear_compannia");		
		Assert.assertEquals(comando.getInstance(),"ts");
		Assert.assertEquals(comando.getParametros()[0],"123456789");
		Assert.assertEquals(comando.getParametros()[1],"cecropia");
		
		
		
	}
	
}
