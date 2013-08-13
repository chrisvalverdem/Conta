package com.ts.main;


import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;

public class InterpreteComandos extends TestCase {
	
	@BeforeTest
	public void setupTest(){
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
	public void pruebaCargaArchivo() throws IOException, CommandException{
		setErrorsFileOutput("pruebaCargaArchivoErrores.txt");
		InterpreteMandatos interprete = new InterpreteMandatos(false, outTestDirectory+"pruebaCargaArchivo.txt");
		interprete.ejecutaComando("22/07/2013 08:20, Write tsoccidente.CREAR_EDIFICIO(occidente)");
		interprete.ejecutaComando("22/07/2013 09:40, Write cecropia=CREAR_COMPANNIA(123JK456, ts)");
		interprete.ejecutaComando("22/07/2013 11:10, Write ts.CREAR_COMPANNIA(789, testing)");
		Repo.limpiaListas();
		new InterpreteMandatos(false, outTestDirectory+"pruebaCargaArchivo.txt");
		
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
		setErrorsFileOutput("pruebaParseComandosErrores.txt");
		InterpreteMandatos interprete = new InterpreteMandatos(false, outTestDirectory+"pruebaParseComandos.txt");
		Comando comando =interprete.interpreteCadena("23/07/2013 13:20, Write ts1=CREAR_COMPANNIA(123456789, cecropia)");
		Comando comando2 =interprete.interpreteCadena("23/07/2013 13:30, Write juan.CREAR_COLABORADOR(juan, 123456)");
		
		Assert.assertEquals(comando.getMetodo(),"CREAR_COMPANNIA");		
		Assert.assertEquals(comando.getInstance(),"ts1");
		Assert.assertEquals(comando.getParametros()[0],"123456789");
		Assert.assertEquals(comando.getParametros()[1],"cecropia");
		
		
		Assert.assertEquals(comando2.getMetodo(),"CREAR_COLABORADOR");		
		Assert.assertEquals(comando2.getInstance(),"juan");
		Assert.assertEquals(comando2.getParametros()[0],"juan");
		Assert.assertEquals(comando2.getParametros()[1],"123456");
			
		
		
	}
	@Test
	public void pruebaParseEspacios() throws IOException, CommandException{
		setErrorsFileOutput("pruebaParseEspaciosErrores.txt");
		InterpreteMandatos interprete = new InterpreteMandatos(false, outTestDirectory+"pruebaParseEspacios.txt");
		Comando comando =interprete.interpreteCadena("24/07/2013 14:30, Write ts2  =  CREAR_COMPANNIA  (  987654321  ,   solutions   )");	
		
		Assert.assertEquals(comando.getMetodo(),"CREAR_COMPANNIA");		
		Assert.assertEquals(comando.getInstance(),"ts2");
		Assert.assertEquals(comando.getParametros()[0],"987654321");
		Assert.assertEquals(comando.getParametros()[1],"solutions");
		
	}
	
	
}
