package com.ts.main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ts.main.InterpreteMandatos;
import com.ts.objects.Colaborador;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;


public class Comandos {
	InterpreteMandatos interpreteMandatos;
	File err;
	String outTestDirectory;
	
	@BeforeClass
	public void setUp() throws FileNotFoundException
	{
		outTestDirectory = "test-output"+File.separator+getClass().getSimpleName()+"_logs"+File.separator;
		File outTestDirectoryFile = new File(outTestDirectory);
		File[] listOfFiles = outTestDirectoryFile.listFiles();
		
		outTestDirectoryFile.mkdirs();
		if(listOfFiles != null )
		{
			for(File file : listOfFiles )
			{
				file.delete();
			}
		}

		
		err = new File(outTestDirectory+"errors.txt");
		
		PrintStream printStream = new PrintStream(new FileOutputStream(err));
		System.setErr(printStream);
		
	}
	
	@BeforeTest
	public void setUpTestCase()
	{
		Repo.limpiaListas();
	}
	
	@AfterTest
	public void ends()
	{
		boolean isThereErrorsInTheExecution = 0 != err.length();
		if(isThereErrorsInTheExecution)
		{
			Assert.fail("Existen errores en la ejecucion de los comandos.");
		}
	}
	
	@Test
	public void agregarCompanniaTest() throws IOException, CommandException
	{
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarCompanniaTest.txt");
		
		String comando ="t.CREAR_COMPANNIA(j456, ts)";
		interpreteMandatos.ejecutaComando(comando);
		Compannia compannia= Repo.getCompannia("j456");
		
		Assert.assertNotNull(compannia);
		Assert.assertEquals(Repo.getTamannoCompannia(),1);
	}
	
	@Test
	public void agregarEdificioTest() throws IOException, CommandException
	{
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarEdificioTest.txt");
		
		String comando ="elguarco.CREAR_EDIFICIO(ts5)";
		interpreteMandatos.ejecutaComando(comando);
		Edificio edificio= Repo.getEdificio("ts5");
		
		Assert.assertNotNull(edificio);
		Assert.assertEquals(edificio.getNombre(), "ts5");
		Assert.assertEquals(Repo.getTamannoEdificio(), 1);
		
	}
	
	@Test
	public void agregarColaboradorTest() throws IOException, CommandException
	{
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarColaboradorTest.txt");
		
		String comando ="Cguillen=CREAR_COLABORADOR(jahzeel, 1-1111-1111, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		Colaborador colaborador= Repo.getColaborador("1-1111-1111");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getNombre(), "jahzeel");
		Assert.assertEquals(Repo.getTamannoColaborador(), 1);
		
	}
	
	
	@Test
	public void pruebaValidarInstacias() throws IOException {
		
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"validarInstaciaEnLaTablaDeSimbolos.txt");
		try{
			Repo.validarInstaciaEnLaTablaDeSimbolos("ts4");
		}catch (CommandException commandException){			
			
			Assert.fail("ts4 nunca deberia estar duplicado");	
			
		}
		
		interpreteMandatos.ejecutaComando("ts4=CREAR_COMPANNIA(123HY4567,cecropia2)");
		try{
			Repo.validarInstaciaEnLaTablaDeSimbolos("ts4");
		}catch (CommandException commandException){			
			
			Assert.assertEquals(commandException.getMessage(),"La instancia ts4 ya existe, cambiela por una diferente.");	
			
		}
		
	}	

}
