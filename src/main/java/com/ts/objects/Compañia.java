package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Compañia {
	
	String nombre;
	String direccion;
	int	telefono;
    int fax;
    @SuppressWarnings("rawtypes")
	CopyOnWriteArrayList listEdificios = null; 
	@SuppressWarnings("rawtypes")
	CopyOnWriteArrayList listProyectos = null;
    
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
	public void setListEdificios(CopyOnWriteArrayList listEdificios) {			
		this.listEdificios=listEdificios;		
	}	
	public CopyOnWriteArrayList getListEdificios( ) {		
		return  listEdificios;	
	}
	public void agregarEdificioList(Object edificio) {			
		this.listEdificios.add (edificio);		
	}	
	public void setListProyectos(CopyOnWriteArrayList listProyectos) {			
		this.listProyectos=listProyectos;		
	}	
	public CopyOnWriteArrayList getListProyectos( ) {		
		return  listProyectos;	
	}
	public void agregarProyectosList(Object proyecto) {			
		this.listProyectos.add (proyecto);		
	}	
}//fin de la clase