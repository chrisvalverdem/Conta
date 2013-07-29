package com.ts.objects;

public class Compania {
	
	String nombre;
	int cedulaJuridica;
	String direccion;
	int	telefono;
    int fax;
    
    public Compania(String nombre,int cedulaJuridica) {		
		this.nombre = nombre;
		this.cedulaJuridica=cedulaJuridica;		
	}
    public Compania(String nombre, String direccion, int cedulaJuridica,
			int telefono, int fax) {		
		this.nombre = nombre;
		this.cedulaJuridica=cedulaJuridica;		
		this.direccion = direccion;
		this.telefono = telefono;
		this.fax = fax;
	}
	
	public int getCedulaJuridica() {
		return cedulaJuridica;
	}
	public void setCedulaJuridica(int cedulaJuridica) {
		this.cedulaJuridica = cedulaJuridica;
	}		
    
    public Compania(String nombre) {
    	this.nombre = nombre;		
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