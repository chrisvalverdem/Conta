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
				 }else if(minutos > 60 || minutos < 0){
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
	 
	 public static Moneda getMoneda(String dato) throws CommandException{
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
	 
	  public static String getFechaFormatoRetencion(String dato) throws CommandException{
		   SimpleDateFormat formatFecha = new SimpleDateFormat("MM/yyyy");
		   String result = "";
		   String[] fechasTemp;
		   Date fecha;
		   int mes;
		   int annio;

		   if(dato.length()>7 && dato.length()< 7){
		    throw new CommandException("Fecha invalidad debe poseer el siguiente formato: MM/yyyy");
		   }else{
		    fechasTemp = dato.split("/");
		    mes = Integer.parseInt(fechasTemp[0]);
		    annio = Integer.parseInt(fechasTemp[1]);  
		    if(mes > 12 || mes < 1){
		     throw new CommandException("El rango de los meses esta definido entre 1-12"); 
		    }
		    if(annio > 10000 || annio < 1000){
		     throw new CommandException("El rango de los aÃ±os solo acepta valores de 4 digitos");
		    }
		    try {
		    fecha= formatFecha.parse(dato);
		    result= formatFecha.format(fecha); 
		   } catch (ParseException e) {

		    e.printStackTrace();
		   } 
		   } 
		   return result;
		 }	  
}
