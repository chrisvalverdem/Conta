package com.ts.objects;

public class Compañia {
	
	String nombre;
	String direccion;
	int	telefono;
    int fax;	
    
    public Compañia(String nombre) {
    	this.nombre = nombre;		
	}     
	public Compañia(String nombre, String direccion,
			int telefono, int fax) {		
		this.nombre = nombre;
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