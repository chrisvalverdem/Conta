package com.ts.main;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.ts.objects.Colaborador;
import com.ts.objects.Colon;
import com.ts.objects.CommandException;

public class ComandosNegativos extends TestCase{
	InterpreteMandatos interpreteMandatos;
	
	@BeforeTest
	public void setUpTestCase() throws IOException
	{
		Repo.limpiaListas();
	}
		
	@Test
	public void agregarColaboradorNegativeTest() throws IOException
	{
		setErrorsFileOutput("agregarColaboradorNegativeTestErrors.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarColaboradorNegativeTest.txt");
		
		//wrong month
		String comando ="jahzeel= CREAR_COLABORADOR(Jahzeel, 1-1111-1111, 15/1988/1998, 08/07/2013, true, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		Colaborador colaborador= Repo.getColaborador("1-1111-1111");
		Assert.assertNull(colaborador);
		
		//wrong year
		comando ="jahzeel= CREAR_COLABORADOR(Jahzeel, 1-1111-1111, 15/11/12, 08/07/12, true, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		colaborador= Repo.getColaborador("1-1111-1111");
		Assert.assertNull(colaborador);
		
		//wrong monto
		comando ="jahzeel= CREAR_COLABORADOR(Jahzeel, 1-1111-1111, 15/11/1988, 08/07/1988, true, 8445-1544, 0, 1000)";
		interpreteMandatos.ejecutaComando(comando);
		colaborador= Repo.getColaborador("1-1111-1111");
		Assert.assertNull(colaborador);
		
		//wrong estado civil
		comando ="jahzeel= CREAR_COLABORADOR(Jahzeel, 1-1111-1111, 15/11/1988, 08/07/1988, casado, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		colaborador= Repo.getColaborador("1-1111-1111");
		Assert.assertNull(colaborador);
		
		Assert.assertEquals(""+getErrors().size(), "4", "Deberian existir error en agregarColaboradorNegativeTest.");
	}
	
	@Test
	public void aumentaSalarioNegativeTestCase() throws IOException
	{
		setErrorsFileOutput("aumentaSalarioNegativeTestCaseError.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"aumentaSalarioNegativeTestCaseError.txt");
		
		//wrong moneda.
		String comando1 ="marias= CREAR_COLABORADOR(Maria Arias, 2-2222-2223, 15/12/1988, 08/07/1988, true, 8445-1544, 0, ¢1000)";
		String comando2 ="marias.AUMENTAR_SALARIO($2000)";
		
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		Colaborador colaborador=Repo.getColaborador("2-2222-2223");	

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getClass(),Colon.class);
		Assert.assertEquals(colaborador.getSalario().getMonto(),1000.0);
		Assert.assertEquals(colaborador.getNombre(),"Maria Arias");
		Assert.assertEquals(""+getErrors().size(), "1", "Deberian existir solo un error en aumentaSalarioNegativeTestCases, por el cambio de moneda.");
				
		//aumento sin usuario existente.
		String comando ="marias.AUMENTAR_SALARIO($2000)";
		interpreteMandatos.ejecutaComando(comando);
		Assert.assertEquals(""+getErrors().size(), "2", "Deberian existir solo un error en aumentaSalarioNegativeTestCases, por que el usuario no existe");
		
		//wrong monto.
		 comando ="marias.AUMENTAR_SALARIO(2000)";
		interpreteMandatos.ejecutaComando(comando);
		Assert.assertEquals(""+getErrors().size(), "3", "Deberian existir solo un error en aumentaSalarioNegativeTestCases, por un monto incorrecto");
	

	}
	
	@Test
	public void pruebaAgregaVacacionesNegativeTest() throws IOException {
		setErrorsFileOutput("pruebaAgregaVacacionesNegativeTest.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaAgregaVacacionesNegativeTest.txt");
		
		interpreteMandatos.ejecutaComando("jlopez.TOMAR_VACACIONES(08/08/2013)");
		Assert.assertEquals(""+getErrors().size(), "1", "Deberia existir un error por que la variable de instancia no existe.");
		
		interpreteMandatos.ejecutaComando("jlopez= CREAR_COLABORADOR(gerardo, 1-1111-2223, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)");
		interpreteMandatos.ejecutaComando("jlopez.TOMAR_VACACIONES(08/2013/2013)");
		interpreteMandatos.ejecutaComando("jlopez.TOMAR_VACACIONES(09/08/13)");
		interpreteMandatos.ejecutaComando("jlopez.TOMAR_VACACIONES(32/08/2013)");
		Assert.assertEquals(""+getErrors().size(), "4", "Deberia existir 4 errores por paseo incorrecto de la fecha.");			
	}
	
	@Test
	public void pruebaCalculaSalarioNetoIQNegativeTest() throws IOException{
		setErrorsFileOutput("pruebaCalculaSalarioNetoIQNegativeTest.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaCalculaSalarioNetoIQNegativeTest.txt");
		Colaborador colaborador;
		String info;
		
		//no existe

		String comando= "garias.calculaSalarioNetoPrimeraQuincena()";
		interpreteMandatos.ejecutaComando(comando);
		Assert.assertEquals(""+getErrors().size(), "1", "Deberian existir solo un error, por un colaborador que no existe");

		String comando1 ="garias= CREAR_COLABORADOR(Gabriel Arias, 3-6666-6666, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando2 ="garias.AUMENTAR_SALARIO($2000)";
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		colaborador= Repo.getColaborador("3-6666-6666");

			try {
				info= Repo.calculaSalarioNetoPrimeraQuincena("garias");
				Assert.assertNotNull(colaborador);
				Assert.assertEquals(colaborador.getSalario().getMonto(), 2000.0);
				Assert.assertTrue(info.contains("$2000.0"));
				Assert.assertTrue(info.contains("$183.4"));
				Assert.assertTrue(info.contains("$1816.6"));	
			} catch (CommandException e) {

			}
	}	
	

}
