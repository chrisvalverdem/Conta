package com.ts.main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ts.main.InterpreteMandatos;
import com.ts.objects.Colaborador;
import com.ts.objects.Colon;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Dolar;
import com.ts.objects.Edificio;
import com.ts.objects.Moneda;
import com.ts.objects.RangoRenta;



public class Comandos  extends TestCase{
	InterpreteMandatos interpreteMandatos;
		
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
		setErrorsFileOutput("agregarCompanniaTestErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarCompanniaTest.txt");
		
		String comando ="01/07/2013 10:53, Write t.CREAR_COMPANNIA(j456, ts)";
		interpreteMandatos.ejecutaComando(comando);
		Compannia compannia= Repo.getCompannia("j456");
		
		Assert.assertNotNull(compannia);
		Assert.assertEquals(Repo.getTamannoCompannia(),1);
	}
	
	@Test
	public void agregarEdificioTest() throws IOException, CommandException
	{
		setErrorsFileOutput("agregarEdificioTestErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarEdificioTest.txt");
		
		String comando ="02/07/2013 11:23, Write elguarco.CREAR_EDIFICIO(ts5)";
		interpreteMandatos.ejecutaComando(comando);
		Edificio edificio= Repo.getEdificio("ts5");
		
		Assert.assertNotNull(edificio);
		Assert.assertEquals(edificio.getNombre(), "ts5");
		Assert.assertEquals(Repo.getTamannoEdificio(), 1);
		
	}
	
	@Test
	public void agregarColaboradorTest() throws IOException, CommandException
	{
		setErrorsFileOutput("agregarColaboradorTestErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"agregarColaboradorTest.txt");
		
		String comando ="03/07/2013 20:53, Write jahzeel= CREAR_COLABORADOR(Jahzeel, 1-1111-1111, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $1000)";
		interpreteMandatos.ejecutaComando(comando);
		Colaborador colaborador= Repo.getColaborador("1-1111-1111");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getNombre(), "Jahzeel");
		Assert.assertEquals(Repo.getTamannoColaborador(), 1);
		
		comando ="04/07/2013 22:14, Write Cguillen= CREAR_COLABORADOR(Cristian Guillen, 1-2222-1111, 15/12/1988, 08/07/2013, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);
		colaborador= Repo.getColaborador("1-2222-1111");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getNombre(), "Cristian Guillen");
		Assert.assertEquals(Repo.getTamannoColaborador(), 2);	
	}
	
	@Test
	public void pruebaValidarInstacias() throws IOException {
		setErrorsFileOutput("pruebaValidarInstaciasErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"validarInstaciaEnLaTablaDeSimbolos.txt");
		try{
			Repo.validarInstaciaEnLaTablaDeSimbolos("ts4");
		}catch (CommandException commandException){					
			Assert.fail("ts4 nunca deberia estar duplicado");			
		}
		
		interpreteMandatos.ejecutaComando("01/08/2013 08:17, Write ts4=CREAR_COMPANNIA(123HY4567,cecropia2)");
		try{
			Repo.validarInstaciaEnLaTablaDeSimbolos("ts4");
		}catch (CommandException commandException){					
			Assert.assertEquals(commandException.getMessage(),"La instancia ts4 ya existe, cambiela por una diferente.");	
		}
		
	}	
	
	@Test
	public void pruebaAgregaVacaciones() throws IOException {
		setErrorsFileOutput("pruebaAgregaVacacionesErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaAgregaVacacionesTest.txt");
		Colaborador colaborador;
		Date fecha=null;
		
		String result1 = "06/08/2013";
		String result2 = "";
		
		String comando1 ="02/08/2013 15:17, Write jlopez= CREAR_COLABORADOR(gerardo, 1-1111-2223, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando2 ="02/08/2013 19:17, Execute jlopez.TOMAR_VACACIONES("+result1+")";
		
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		colaborador=Repo.getColaborador("1-1111-2223");	
		fecha= colaborador.getVacaciones().get(0);
		result2=Sistema.getFechaConFormato(fecha);
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(result1, result2);
		Assert.assertTrue(colaborador.getEstadoCivil());
		Assert.assertEquals(Repo.getTamannoColaborador(), 3);
		
		interpreteMandatos.ejecutaComando("03/08/2013 18:15, Execute jlopez.TOMAR_VACACIONES(07/08/2013)");
		interpreteMandatos.ejecutaComando("03/08/2013 19:15, Execute jlopez.TOMAR_VACACIONES(08/08/2013)");
		interpreteMandatos.ejecutaComando("03/08/2013 20:15, Execute jlopez.TOMAR_VACACIONES(09/08/2013)");
		interpreteMandatos.ejecutaComando("03/08/2013 21:15, Execute jlopez.TOMAR_VACACIONES(10/08/2013)");
		interpreteMandatos.ejecutaComando("03/08/2013 22:15, Execute jlopez.TOMAR_VACACIONES(11/08/2013)");
		
		colaborador=Repo.getColaborador("1-1111-2223");
		Assert.assertEquals(""+colaborador.getVacaciones().size(), "6", "El colaboradores deberia tener 6 dias de vacaciones. " );
		Assert.assertEquals(Sistema.getFechaConFormato(colaborador.getVacaciones().get(0)), result1, "El primer dia de vacaciones deberia ser "+result1+". " );
		Assert.assertEquals(Sistema.getFechaConFormato(colaborador.getVacaciones().get(1)), "07/08/2013", "El segundo dia de vacaciones deberia ser 07/08/2013. " );
		Assert.assertEquals(Sistema.getFechaConFormato(colaborador.getVacaciones().get(2)), "08/08/2013", "El tercer dia de vacaciones deberia ser 08/08/2013. " );
		Assert.assertEquals(Sistema.getFechaConFormato(colaborador.getVacaciones().get(3)), "09/08/2013", "El cuarto dia de vacaciones deberia ser 09/08/2013. " );
		Assert.assertEquals(Sistema.getFechaConFormato(colaborador.getVacaciones().get(4)), "10/08/2013", "El quinto dia de vacaciones deberia ser 10/08/2013. " );
		Assert.assertEquals(Sistema.getFechaConFormato(colaborador.getVacaciones().get(5)), "11/08/2013", "El sexto dia de vacaciones deberia ser 11/08/2013. " );
				
	}

	@Test
	public void pruebaMostrarVacaciones() throws IOException{
		setErrorsFileOutput("pruebaAgregaVacacionesErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"MostrarVacaciones.txt");
		Colaborador colaborador;
		Date fecha;
		String result="";
		String comando12 ="04/08/2013 10:30, Write fchinchilla= CREAR_COLABORADOR(Fernanda Chinchilla, 1-4444-4444, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando21 ="04/08/2013 15:30, Execute fchinchilla.TOMAR_VACACIONES(07/08/2013)";
		
		interpreteMandatos.ejecutaComando(comando12);
		interpreteMandatos.ejecutaComando(comando21);
		
		colaborador= Repo.getColaborador("1-4444-4444");
		fecha= colaborador.getVacaciones().get(0);
		result= Sistema.getFechaConFormato(fecha);
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(result, "07/08/2013");
		Assert.assertEquals(colaborador.getVacaciones().size(), 1);
	}
	
	@Test
	public void pruebaAumentarSalario() throws IOException {
		setErrorsFileOutput("pruebaAumentarSalarioErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaAumentarSalarioTest.txt");
		Colaborador colaborador;
		
		String comando1 ="05/08/2013 11:11, Write marias= CREAR_COLABORADOR(Maria Arias, 2-2222-2223, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		String comando2 ="05/08/2013 13:22, Execute marias.AUMENTAR_SALARIO($2000)";

		
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		
		colaborador=Repo.getColaborador("2-2222-2223");	

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getClass(),Dolar.class);
		Assert.assertEquals(colaborador.getSalario().getMonto(),2000.0);
		Assert.assertEquals(colaborador.getNombre(),"Maria Arias");
		Assert.assertEquals(Repo.getTamannoColaborador(),4);
		
		comando1 ="06/08/2013 14:40, Write cguillen= CREAR_COLABORADOR(Cristan, 2-2222-55, 15/12/1988, 08/07/1988, true, 8445-1544, 0, ¢1000)";
		comando2 ="06/08/2013 14:50, Execute cguillen.AUMENTAR_SALARIO( ¢2000)";
		String comando3 ="06/08/2013 14:55, Execute cguillen.AUMENTAR_SALARIO(¢3000)";

		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		interpreteMandatos.ejecutaComando(comando3);
		
		colaborador=Repo.getColaborador("2-2222-55");	

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getClass(),Colon.class);
		Assert.assertEquals(colaborador.getSalario().getMonto(),3000.0);
		Assert.assertEquals(colaborador.getNombre(),"Cristan");
		Assert.assertEquals(Repo.getTamannoColaborador(),5);
	}
	
	@Test
	public void pruebaMostrarSalario() throws IOException{
		setErrorsFileOutput("pruebaMostrarSalarioTestErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaMostrarSalarioTest.txt");
		Colaborador colaborador;

		String comando1 ="07/08/2013 15:30, Write jguerrero= CREAR_COLABORADOR(jose guerrero, 3-2222-2233, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		
		interpreteMandatos.ejecutaComando(comando1);

		colaborador= Repo.getColaborador("3-2222-2233");

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getMonto(), 1000.0);	
	}
	
	@Test
	public void pruebaCalculaSalarioNetoIQuincena() throws IOException, CommandException{
		setErrorsFileOutput("pruebaCalculaSalarioNetoIQTestErrors.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaCalculaSalarioNetoIQTest.txt");
		Colaborador colaborador;
		String info;
		
		String comando1 ="08/08/2013 12:00, Write garias= CREAR_COLABORADOR(Gabriel Arias, 3-6666-6666, 15/12/1988, 08/07/1988, true, 8445-1544, 0, $1000)";
		
		interpreteMandatos.ejecutaComando(comando1);
		
		colaborador= Repo.getColaborador("3-6666-6666");
		info= Repo.calculaSalarioNetoPrimeraQuincena("garias");

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(colaborador.getSalario().getMonto(), 1000.0);
		Assert.assertTrue(info.contains("$1000.0"));
		Assert.assertTrue(info.contains("$91.7"));
		Assert.assertTrue(info.contains("$908.3"));
	}
	
	@Test
	public void pruebaCantidadVacacionesDisponibles() throws IOException, CommandException {
		setErrorsFileOutput("pruebacantidadVacacionesDisponiblesTestErrors.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebacantidadVacacionesDisponiblesTest.txt");
		Colaborador colaborador;
		
		String comando ="08/08/2013 14:20, Write Svillegas= CREAR_COLABORADOR(Sandra Villegas, 8-5555-1111, 15/12/1988, 08/04/2013, true, 8445-1544, 0, $2000)";		
		interpreteMandatos.ejecutaComando(comando);		
		colaborador=Repo.getColaborador("8-5555-1111");	

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("Svillegas", "07/08/2013"),"Cantidad de vacaciones disponibles para Svillegas son: 3");
		Assert.assertEquals(Repo.getTamannoColaborador(),7);
		
		comando ="08/08/2013 14:40, Execute Svillegas.TOMAR_VACACIONES(06/05/2013)";		
		interpreteMandatos.ejecutaComando(comando);		
		comando ="08/08/2013 15:10, Execute Svillegas.TOMAR_VACACIONES(07/05/2013)";		
		interpreteMandatos.ejecutaComando(comando);		
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("Svillegas", "07/08/2013"),"Cantidad de vacaciones disponibles para Svillegas son: 1");
		
		comando ="08/08/2013 15:30, Execute Svillegas.TOMAR_VACACIONES(08/05/2013)";		
		interpreteMandatos.ejecutaComando(comando);
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("Svillegas", "07/08/2013"),"El colaborador Svillegas no tiene dias de vacaciones disponibles.");
		
		comando ="08/08/2013 15:40, Execute Svillegas.TOMAR_VACACIONES(09/05/2013)";		
		interpreteMandatos.ejecutaComando(comando);
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("Svillegas", "07/08/2013"),"El colaborador Svillegas no tiene dias de vacaciones disponibles, por el contrario debe la siguiente cantidad de vacaciones: 1");
		
		comando ="08/08/2013 16:08, Write ydesanti= CREAR_COLABORADOR(Yohan Desanti, 8-5555-4444, 15/10/1984, 10/06/2012, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);
		colaborador= Repo.getColaborador("8-5555-4444");
		
		Assert.assertNotNull(colaborador);
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("ydesanti", "07/08/2013"),"Cantidad de vacaciones disponibles para ydesanti son: 12");
		Assert.assertEquals(Repo.getTamannoColaborador(),8);
		
		comando="08/08/2013 16:20, Execute ydesanti.TOMAR_VACACIONES(04/03/2013)";
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("ydesanti", "07/08/2013"),"Cantidad de vacaciones disponibles para ydesanti son: 11");
		
		comando="08/08/2013 09:11, Write clopez= CREAR_COLABORADOR(Cesar Lopez, 9-1234-6666, 15/12/1990, 01/01/2011, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("clopez", "16/12/2011"),"Cantidad de vacaciones disponibles para clopez son: 10");
		
		comando="08/08/2013 09:11, Write cjovane= CREAR_COLABORADOR(Celia Jovane, 8-1234-6666, 15/12/1998, 01/01/2012, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("cjovane", "15/12/2012"),"Cantidad de vacaciones disponibles para cjovane son: 10");
		
		comando="08/08/2013 09:11, Write naltamirano= CREAR_COLABORADOR(Noelia Altamirano, 5-1239-6666, 23/03/1985, 31/03/2013, false, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("naltamirano", "30/04/2013"),"El colaborador naltamirano no tiene dias de vacaciones disponibles.");
		
		comando="08/08/2013 09:11, Write ojimenez= CREAR_COLABORADOR(Olga Jimenez, 4-1289-6666, 23/03/1985, 01/02/2013, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesDisponibles("ojimenez", "01/03/2013"),"El colaborador ojimenez no tiene dias de vacaciones disponibles.");
	
	}
	
	@Test
	public void pruebaCantidadVacacionesLiquidacion() throws IOException, CommandException {
		setErrorsFileOutput("pruebaCantidadVacacionesLiquidacionTestErrors.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaCantidadVacacionesLiquidacionTest.txt");
		Colaborador colaborador;
		
		String comando ="09/08/2013 08:10, Write asanchez= CREAR_COLABORADOR(Ana Sanchez, 8-5555-6666, 15/12/1988, 25/03/2013, true, 8445-1544, 0, $2000)";		
		interpreteMandatos.ejecutaComando(comando);		
		colaborador=Repo.getColaborador("8-5555-6666");	

		Assert.assertNotNull(colaborador);
		Assert.assertEquals(Repo.cantidadVacacionesLiquidacion("asanchez", "07/08/2013"),"Cantidad de vacaciones disponibles para asanchez son: 5");
		Assert.assertEquals(Repo.getTamannoColaborador(),13);
		
		comando ="09/08/2013 08:20, Execute asanchez.TOMAR_VACACIONES(17/06/2013)";		
		interpreteMandatos.ejecutaComando(comando);		
		comando ="09/08/2013 08:40, Execute asanchez.TOMAR_VACACIONES(18/06/2013)";		
		interpreteMandatos.ejecutaComando(comando);	
		comando ="09/08/2013 08:40, Execute asanchez.TOMAR_VACACIONES(19/06/2013)";		
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesLiquidacion("asanchez", "07/08/2013"),"Cantidad de vacaciones disponibles para asanchez son: 2");
		
		comando ="09/08/2013 09:00, Execute asanchez.TOMAR_VACACIONES(20/06/2013)";		
		interpreteMandatos.ejecutaComando(comando);
		comando ="09/08/2013 09:08, Execute asanchez.TOMAR_VACACIONES(01/07/2013)";		
		interpreteMandatos.ejecutaComando(comando);
		
		Assert.assertEquals(Repo.cantidadVacacionesLiquidacion("asanchez", "07/08/2013"),"El colaborador asanchez no tiene dias de vacaciones disponibles.");
		
		comando ="09/08/2013 09:15, Execute asanchez.TOMAR_VACACIONES(02/07/2013)";		
		interpreteMandatos.ejecutaComando(comando);
		
		Assert.assertEquals(Repo.cantidadVacacionesLiquidacion("asanchez", "07/08/2013"),"El colaborador asanchez no tiene dias de vacaciones disponibles, por el contrario debe la siguiente cantidad de vacaciones: 1");	
		
		comando="08/08/2013 09:11, Write csoto= CREAR_COLABORADOR(Carlos Soto, 7-1234-6666, 15/12/1968, 01/01/2012, true, 8445-1544, 0, $2000)";
		interpreteMandatos.ejecutaComando(comando);	
		
		Assert.assertEquals(Repo.cantidadVacacionesLiquidacion("csoto", "01/11/2012"),"Cantidad de vacaciones disponibles para csoto son: 10");			
	}
	
	@Test
	public void pruebaCambiarRangoRenta() throws IOException, CommandException{
		setErrorsFileOutput("pruebaCambiarRangoRentaErrores.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaCambiarRangoRentaTest.txt");
		ArrayList<RangoRenta> interval = new ArrayList<RangoRenta>();

		String comando1 ="01/08/2013 08:17, Write ts=CREAR_COMPANNIA(31014659,cecropia2)";
		String comando2 = "01/08/2013 08:17, Execute ts.ESTABLECER_RANGO_RENTA(¢1,¢714000,0)";
		String comando3 = "01/08/2013 08:18, Execute ts.ESTABLECER_RANGO_RENTA(¢714000,¢1085000,10)";
		String comando4 = "01/08/2013 08:19, Execute ts.ESTABLECER_RANGO_RENTA(¢1085000,¢9999999,15)";
		
		interpreteMandatos.ejecutaComando(comando1);
		interpreteMandatos.ejecutaComando(comando2);
		interpreteMandatos.ejecutaComando(comando3);
		interpreteMandatos.ejecutaComando(comando4);
		
		interval= Compannia.intervalosRenta;
		
		Assert.assertTrue(!(interval.isEmpty()));
		Assert.assertEquals(interval.get(0).getIntervaloInferior().getMonto(), 1.0);
		Assert.assertEquals(interval.get(0).getIntervaloSuperior().getMonto(), 714000.0);
		Assert.assertEquals(interval.get(0).getIntervaloPorciento(), 0.0);
		Assert.assertEquals(interval.get(0).getIdentificador(), "case0");
		Assert.assertEquals(interval.get(0).getIntervaloInferior().getSign(), "¢");
		
		Assert.assertEquals(interval.get(1).getIntervaloInferior().getMonto(), 714000.0);
		Assert.assertEquals(interval.get(1).getIntervaloSuperior().getMonto(), 1085000.0);
		Assert.assertEquals(interval.get(1).getIntervaloPorciento(), 10.0);
		Assert.assertEquals(interval.get(1).getIdentificador(), "case1");
		Assert.assertEquals(interval.get(1).getIntervaloInferior().getSign(), "¢");
		
		Assert.assertEquals(interval.get(2).getIntervaloInferior().getMonto(), 1085000.0);
		Assert.assertEquals(interval.get(2).getIntervaloSuperior().getMonto(), 9999999.0);
		Assert.assertEquals(interval.get(2).getIntervaloPorciento(), 15.0);
		Assert.assertEquals(interval.get(2).getIdentificador(), "case2");
		Assert.assertEquals(interval.get(2).getIntervaloInferior().getSign(), "¢");	
	}
	
	@Test
	public void pruebaActualizarMontoConyugeHijo() throws IOException {
		setErrorsFileOutput("pruebaActualizarMontoConyugeHijooErrors.txt");
		interpreteMandatos = new InterpreteMandatos(false, outTestDirectory+"pruebaActualizarMontoConyugeHijoTest.txt");			
		
		String comando2="01/08/2013 08:17, Execute tsa=ACTUALIZAR_MONTO_CONYUGE_HIJO(¢2000,¢1340)";
		interpreteMandatos.ejecutaComando(comando2);	
		
		Assert.assertEquals(Hacienda.getMontoConyuge().getMonto(),2000.0);
		Assert.assertEquals(Hacienda.getMontoHijo().getMonto(),1340.0);					
	}	
	
}
	

