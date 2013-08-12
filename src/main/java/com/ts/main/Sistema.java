package com.ts.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ts.objects.Colon;
import com.ts.objects.CommandException;
import com.ts.objects.Dolar;
import com.ts.objects.Moneda;

public class Sistema {
	
	private static Calendar fechaActual;
	private static String dia;
	private static String mes;
	private static String annio;
	private static String hora;
	private static String minutos;
	private static String segundos;
	private static String fechaSistema;
	private static String horaSistema;
	
	public Sistema (){
		
	}	
	
	public static Calendar getFechaActual() {
		return fechaActual;
	}

	public static void setFechaActual(Calendar fechaActual) {
		Sistema.fechaActual = fechaActual;
	}

	public static String getDia() {
		return dia;
	}


	public static void setDia(String dia) {
		Sistema.dia = dia;
	}


	public static String getMes() {
		return mes;
	}


	public static void setMes(String mes) {
		Sistema.mes = mes;
	}


	public static String getAnnio() {
		return annio;
	}


	public static void setAnnio(String annio) {
		Sistema.annio = annio;
	}


	public static String getHora() {
		return hora;
	}


	public static void setHora(String hora) {
		Sistema.hora = hora;
	}


	public static String getMinutos() {
		return minutos;
	}


	public static void setMinutos(String minutos) {
		Sistema.minutos = minutos;
	}


	public static String getSegundos() {
		return segundos;
	}


	public static void setSegundos(String segundos) {
		Sistema.segundos = segundos;
	}


	public static String getFechaSistema() {
		return fechaSistema;
	}


	public static void setFechaSistema(String fechaSistema) {
		Sistema.fechaSistema = fechaSistema;
	}


	public static String getHoraSistema() {
		return horaSistema;
	}


	public static void setHoraSistema(String horaSistema) {
		Sistema.horaSistema = horaSistema;
	}


	public static String obtenerFechaSistema (){		
		
		 fechaActual = Calendar.getInstance();
    	
    	if(fechaActual.get(Calendar.DAY_OF_MONTH)< 10){
    		dia="0";
    		dia+=String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH));
    	}else{
    		dia=String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH));
    	}
    	if(fechaActual.get(Calendar.MONTH)+1 < 10){
    		mes="0";
    		mes+=String.valueOf(fechaActual.get(Calendar.MONTH)+1);
    	}else{
    		mes= String.valueOf(fechaActual.get(Calendar.MONTH)+1);
    	}
    	annio=String.valueOf(fechaActual.get(Calendar.YEAR));
    	
    	return fechaSistema= dia+"/"+mes+"/"+annio;
    	
	}
	
	public static String obtenerHoraSistema (){
		
		 fechaActual = Calendar.getInstance();
		 
		 hora=String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY));
         minutos=String.valueOf(fechaActual.get(Calendar.MINUTE));
         segundos=String.valueOf(fechaActual.get(Calendar.SECOND));
         
        return horaSistema=hora+":"+minutos+":"+segundos;
	}
	
	 public static Date getParseFecha(String dato) throws CommandException{
		 Date fecha;		 
		  try {
				 SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
				 String[] fechasTemp = dato.split("/");
				 int dia = Integer.parseInt(fechasTemp[0]);
				 int mes = Integer.parseInt(fechasTemp[1]);
				 int annio = Integer.parseInt(fechasTemp[2]);
				 
				 if(dia >31 || dia < 1){
					 throw new CommandException("El rango de los dias esta definido entre 1-31");
				 }else if(mes>12 || mes< 1){
					 throw new CommandException("El rango de los meses esta definido entre 1-12");
				 }else if(annio > 10000 || annio < 1000){
					 throw new CommandException("El rango de los años solo acepta valores de 4 digitos");
				 }
			   fecha = formatFecha.parse(dato);
			  } catch (ParseException e) {
			   throw new CommandException("El formato de la fecha es dd/MM/yyyy. Favor actualizar");
			  } 
		 return fecha;
	 }	
	
	 public static String getFechaConFormato(Date fecha){
		 SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
		 String result = "";
		 result= formatFecha.format(fecha);
		 return result;
	}
	 public static Date getParseFechaHora(String dato) throws CommandException{
		 Date fecha;
	  try {
		  
		  SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		  System.out.println(dato.length());
		  
		  if(dato.length() > 10){
			 
			  String[] temp = dato.split(" ");
			    
			     String[] fechasTemp = temp [0].split("/");
				 int dia = Integer.parseInt(fechasTemp[0]);
				 int mes = Integer.parseInt(fechasTemp[1]);
				 int annio = Integer.parseInt(fechasTemp[2]);
			    	  
			    String[] tempHora = temp [1].split(":");				
				int horas= Integer.parseInt(tempHora[0]);
				int minutos= Integer.parseInt(tempHora[1]);	
				
				 if(dia >31 || dia < 1){
					 throw new CommandException("El rango de los dias esta definido entre 1-31");
				 }else if(mes>12 || mes< 1){
					 throw new CommandException("El rango de los meses esta definido entre 1-12");
				 }else if(annio > 10000 || annio < 1000){
					 throw new CommandException("El rango de los años solo acepta valores de 4 digitos");
				 }else if(horas > 24 || horas < 1){
					 throw new CommandException("El rango de la hora esta definido entre 1-24");
				 }else if(minutos > 60 || minutos < 1){
					 throw new CommandException("El rango de los minutos esta definido entre 1-60");
				 }else if(tempHora.length > 3){
					 throw new CommandException("Favor actualizar, el formato de la hora es hh:mm");
				 }				 
				 fecha = formatFecha.parse(dato); 
		  }else{
			  throw new CommandException("El formato de la fecha debe contener hora ejemplo: dd/MM/yyyy hh:mm");
		  }  	    			 
			  } catch (ParseException e) {
			   throw new CommandException("El formato de la fecha y hora es dd/MM/yyyy hh:mm. Favor actualizar");
			  } 
		 return fecha;
	 }
	 
	 public static Moneda getSalario(String dato) throws CommandException{
	 		Moneda salario=null;
	 		double monto;
	 		
	 		monto= Double.parseDouble(dato.substring(1, dato.length()));
	 		
			if(dato.contains("¢")){
				salario= new Colon(monto);
			}else if(dato.contains("$")){
				salario= new Dolar(monto);
			}else{
				throw new CommandException("Solo se aceptan montos en $ o ¢, Favor revisarlo monto digitado");
			}

	 		return salario;
	 	}
	 public static double[] analizaIntervalosRenta(String param1, String param2, String param3) throws CommandException {
			double[] intervalos = new double[9];	
			
			if(!(param1.contains("[") && param1.contains("]"))){
				throw new CommandException("No se reconocen "+ "," + " como separador de digitos favor ingresar monto correctamente 714000");
			}
			if(!(param2.contains("[") && param2.contains("]"))){
				throw new CommandException("No se reconocen "+ "," + " como separador de digitos favor ingresar monto correctamente 714000");
			}
			if(!(param3.contains("[") && param3.contains("]"))){
				throw new CommandException("No se reconocen "+ "," + " como separador de digitos favor ingresar monto correctamente 714000");
			}
			if(param1.contains(".") || param2.contains(".") || param3.contains(".")){
				throw new CommandException("No se reconocen "+ "," + " como separador de digitos favor ingresar monto correctamente 714000");
			}
			if(param1.contains("$") || param2.contains("$") || param3.contains("$")){
				throw new CommandException("No se reconocen el tipo de moneda "+ "$" + "  favor ingresar el monto correctamente 714000");
			}
			if(param1.contains("¢") || param2.contains("¢") || param3.contains("¢")){
				throw new CommandException("No se reconocen el tipo de moneda "+ "¢" + "  favor ingresar el monto correctamente 714000");
			}
			if(param1.contains("%") || param2.contains("%") || param3.contains("%")){
				throw new CommandException("No se reconocen caracter % en los porcientos");
			}
			
			//Case °0
			String temp1 = param1.substring(1, param1.length());
			int catidadIntervaloMenor0= temp1.indexOf("-");
			String intervaloMenor0= param1.substring(1, catidadIntervaloMenor0 + 1);
			
			String temp2 = param1.substring(catidadIntervaloMenor0 + 2, param1.length());
			int catidadIntervaloMayor0= temp2.indexOf("]");
			String intervaloMayor0= temp2.substring(0, catidadIntervaloMayor0);
			String porcientoCase0 = temp2.substring(catidadIntervaloMayor0 + 1, temp2.length());
			
			//Case °1
			String temp3 = param2.substring(1, param2.length());
			int catidadIntervaloMenor1= temp3.indexOf("-");
			String intervaloMenor1= temp3.substring(0, catidadIntervaloMenor1);

			String temp4 = param2.substring(catidadIntervaloMenor1 + 2, param2.length());
			int catidadIntervaloMayor1= temp4.indexOf("]");
			String intervaloMayor1= temp4.substring(0, catidadIntervaloMayor1);
			String porcientoCase1 = temp4.substring(catidadIntervaloMayor1 + 1, temp4.length());
			
			//Case °2
			String temp5 = param3.substring(1, param3.length());
			int catidadIntervaloMenor2= temp5.indexOf("-");
			String intervaloMenor2= temp5.substring(0, catidadIntervaloMenor2);
			
			String temp6 = param3.substring(catidadIntervaloMenor2 + 2, param3.length());
			int catidadIntervalo6= temp6.indexOf("]");
			String intervaloMayor2= temp6.substring(0, catidadIntervalo6);
			String porcientoCase2 = temp6.substring(catidadIntervalo6 + 1, temp6.length());
		
			if(!(intervaloMayor0.equalsIgnoreCase(intervaloMenor1))){
				throw new CommandException("Error, los intervalos no concuerdan verifcar Mayor del case 0 y menor del case 1");
			}
			if(!(intervaloMayor1.equalsIgnoreCase(intervaloMenor2))){
				throw new CommandException("Error, los intervalos no concuerdan verifcar Mayor del case 1 y menor del case 2");
			}
			if(!(intervaloMayor2.equalsIgnoreCase("x"))){
				throw new CommandException("Error con el tercer intervalo, no se encuentrael valor esperado (X) ");
			}

			double valor1= Double.parseDouble(intervaloMenor0);
			double valor2= Double.parseDouble(intervaloMayor0);
			double porcientoIntervalo1= Double.parseDouble(porcientoCase0);

			double valor3= Double.parseDouble(intervaloMenor1);
			double valor4= Double.parseDouble(intervaloMayor1);
			double porcientoIntervalo2= Double.parseDouble(porcientoCase1);
			
			double valor5= Integer.parseInt(intervaloMenor2);
			double valor6= 0.0;
			double porcientoIntervalo3= Double.parseDouble(porcientoCase2);	

			intervalos[0]= valor1;
			intervalos[1]= valor2;
			intervalos[2]= porcientoIntervalo1;
			
			intervalos[3]= valor3;
			intervalos[4]= valor4;
			intervalos[5]= porcientoIntervalo2;
			
			intervalos[6]= valor5;
			intervalos[7]= valor6;
			intervalos[8]= porcientoIntervalo3;
			
			return intervalos;
		}
}
