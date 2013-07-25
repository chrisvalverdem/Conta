package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Edificio {
	
	String nombre;
	String direccion;
	
	 // CopyOnWriteArrayList listColaboradores = null;  
	CopyOnWriteArrayList listColaboradores = new CopyOnWriteArrayList ();		  
	  
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
	
	public void setListColabotadores(Object colaborador) {			
		this.listColaboradores.add (colaborador);		
	}	
	public CopyOnWriteArrayList getListColaboradores( ) {
		
		return  listColaboradores;	
	}
		
}//fin de la clase
