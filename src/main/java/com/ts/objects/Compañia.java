package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Compañia {
	
	String nombreCompania;
	String direccionCompania;
	int	telefonoCompania;
    int faxCompañia;
    CopyOnWriteArrayList listEdificios = null; 
	CopyOnWriteArrayList listProyectos = null;
    
    public Compañia() {
		
	}
     
	public Compañia(String nombreCompania, String direccionCompania,
			int telefonoCompania, int faxCompañia) {		
		this.nombreCompania = nombreCompania;
		this.direccionCompania = direccionCompania;
		this.telefonoCompania = telefonoCompania;
		this.faxCompañia = faxCompañia;
	}
	
	public String getNombreCompania() {
		return nombreCompania;
	}
	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}
	public String getDireccionCompania() {
		return direccionCompania;
	}
	public void setDireccionCompania(String direccionCompania) {
		this.direccionCompania = direccionCompania;
	}
	public int getTelefonoCompania() {
		return telefonoCompania;
	}
	public void setTelefonoCompania(int telefonoCompania) {
		this.telefonoCompania = telefonoCompania;
	}
	public int getFaxCompañia() {
		return faxCompañia;
	}
	public void setFaxCompañia(int faxCompañia) {
		this.faxCompañia = faxCompañia;
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