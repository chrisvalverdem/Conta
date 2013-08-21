package com.ts.libraries;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Fecha extends Objecto{
	
	private int dia;
	private int mes;
	private int anno;
	
	public Fecha(int dia, int mes, int anno)
	{
		this.dia = dia;
		this.mes = mes;
		this.anno = anno;
	}
	
	public Fecha(int dia, int mes, int anno, int hora, int minutos, int segundos)
	{
		this.dia = dia;
		this.mes = mes;
		this.anno = anno;
	}
	
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String show()
	{
		String result = String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+anno;
		return result;
	}
	
	 public Numeric cantidadDiasEntreFechas(Date d1, Date d2){
	     int result=(int)(d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
	     return new Numeric(result+1);           
	    }
	 
	 public Date covertFechaADate(Fecha fecha){
	    
	    Date fechaTipoDate=null;
	    
	    int diaTemp=fecha.getDia();
	    int mesTemp=fecha.getMes();
	    int anioTemp=fecha.getAnno();
	     
	    String fechaTemp=diaTemp+"/"+mesTemp+"/"+anioTemp;
	    SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
	     
	    try {
	     fechaTipoDate = formatFecha.parse(fechaTemp);
	    }
	    catch (ParseException e){
	     throw new CommandException("Formato de fecha invalido, el formato debe ser: dd/mm/yyyy");
	    }
	    return fechaTipoDate;
	    
	   }
}
