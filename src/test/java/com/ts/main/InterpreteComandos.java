package com.ts.main;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.ts.db.Repo;
import com.ts.libraries.Compania;
import com.ts.libraries.Edificio;
import com.ts.libraries.Hilera;


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
	public void pruebaCargaArchivo() throws Exception{
		setErrorsFileOutput("pruebaCargaArchivoErrores.txt");
		InterpreteMandatos interprete = new InterpreteMandatos(false, outTestDirectory+"pruebaCargaArchivo.txt");
		interprete.ejecutaComando("22/07/2013 08:20, Write tsoccidente.CREAR_EDIFICIO(occidente)");
		interprete.ejecutaComando("22/07/2013 09:40, Write cecropia=CREAR_COMPANNIA(123JK456, ts)");
		interprete.ejecutaComando("22/07/2013 11:10, Write ts.CREAR_COMPANNIA(789, testing)");
		Repo.limpiaListas();
		new InterpreteMandatos(false, outTestDirectory+"pruebaCargaArchivo.txt");
		
		Compania compannia= Compania.get(new Hilera("123JK456"));
		Edificio edificio= Edificio.getEdificio(new Hilera("occidente"));
		
		Assert.assertNotNull(compannia);
		Assert.assertEquals(compannia.getNombre(),"ts");
		Assert.assertEquals(Compania.getTamannoCompannia(), 2);
		
		Assert.assertNotNull(edificio);
		Assert.assertEquals(edificio.getNombre(),"occidente");
		Assert.assertEquals(Edificio.getTamannoEdificio(), 1);
	}
}
