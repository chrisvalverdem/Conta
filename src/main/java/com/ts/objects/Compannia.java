package com.ts.objects;

import java.util.Date;

public class Compannia extends Object{
	
	String nombre;
	int cedulaJuridica;
	Date fechaCreacion;
	String direccion;	
	int	telefono;
    int fax;
    public static String comandosValidos[] ={"nuevaCompañia,gregarColaborador,agregarEdificio"};
    
  
    public Compannia(int cedulaJuridica, String nombre) {
    	this.cedulaJuridica=cedulaJuridica;  
    	this.nombre = nombre;    	  	
	} 
    public Compannia(String nombre, String direccion, int cedulaJuridica,
			Date fechaCreacion, int telefono, int fax) {		
		this.nombre = nombre;
		this.cedulaJuridica=cedulaJuridica;
		this.fechaCreacion=fechaCreacion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fax = fax;
	}	    
   
    public String getNombre() {
		return nombre;
	}
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	public int getCedulaJuridica() {
		return cedulaJuridica;
	}
	public void setCedulaJuridica(int cedulaJuridica) {
		this.cedulaJuridica = cedulaJuridica;
	}	   
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono= telefono;
	}
	public int getFax() {
		return fax;
	}
	public void setFax(int fax) {
		this.fax = fax;
	}	
	
}//fin de la clase