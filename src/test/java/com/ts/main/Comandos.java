package com.ts.main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
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
		
		String comando ="jahzeel= CREAR_COLABORADOR(Jahzeel, 1-1111-1111, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		Colaborador colaborador= Repo.getColaborador("1-1111-1111");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getNombre(), "Jahzeel");
		Assert.assertEquals(Repo.getTamannoColaborador(), 1);
		
		comando ="Cguillen= CREAR_COLABORADOR(Cristian Guillen, 1-2222-1111, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);
		colaborador= Repo.getColaborador("1-2222-1111");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getNombre(), "Cristian Guillen");
		Assert.assertEquals(Repo.getTamannoColaborador(), 2);	
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
	
	@Test
	public void pruebaAgregaVacaciones() throws IOException {
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"AgregaVacacionesColaborador.txt");
		Colaborador colaborador;
		Date fecha=null;
		
		String comando1 ="jlopez= CREAR_COLABORADOR(gerardo, 1-1111-2223, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando2 ="jlopez.TOMAR_VACACIONES(06/08/2013)";
		
		String result1 = "06/08/2013";
		String result2="";
		
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		colaborador=Repo.getColaborador("1-1111-2223");	
		fecha= colaborador.getVacaciones().get(0);
		result2=InterpreteMandatos.getFechaConFormato(fecha);
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(result1, result2);
		Assert.assertTrue(colaborador.getEstadoCivil());
		Assert.assertEquals(Repo.getTamannoColaborador(), 3);
	}

	@Test
	public void pruebaMostrarVacaciones() throws IOException{
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"MostrarVacaciones.txt");
		Colaborador colaborador;
		Date fecha;
		String result="";
		String comando12 ="fchinchilla= CREAR_COLABORADOR(Fernanda Chinchilla, 1-4444-4444, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando21 ="fchinchilla.TOMAR_VACACIONES(07/08/2013)";
		
		interpreteMandatos.ejecutaComando(comando12);
		interpreteMandatos.ejecutaComando(comando21);
		
		colaborador= Repo.getColaborador("1-4444-4444");
		fecha= colaborador.getVacaciones().get(0);
		result= InterpreteMandatos.getFechaConFormato(fecha);
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(result, "07/08/2013");
		Assert.assertEquals(colaborador.getVacaciones().size(), 1);
	}
	
	@Test
	public void pruebaAumentarSalario() throws IOException {
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"AumentarSalario.txt");
		Colaborador colaborador;
		
		String comando1 ="marias= CREAR_COLABORADOR(Maria Arias, 2-2222-2223, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando2 ="marias.AUMENTAR_SALARIO($2000)";
		
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		colaborador=Repo.getColaborador("2-2222-2223");	

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getMonto(),2000.0);
		Assert.assertEquals(colaborador.getNombre(),"Maria Arias");
	}
	
	@Test
	public void pruebaMostrarSalario() throws IOException{
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"MostrarSalario.txt");
		Colaborador colaborador;

		String comando1 ="jguerrero= CREAR_COLABORADOR(jose guerrero, 3-2222-2233, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		
		interpreteMandatos.ejecutaComando(comando1);

		colaborador= Repo.getColaborador("3-2222-2233");

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getMonto(), 1000.0);	
	}
	
}
