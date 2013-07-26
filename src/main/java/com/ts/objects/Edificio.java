package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Edificio {
	
	String nombre;
	String direccion;
	CopyOnWriteArrayList listColaboradores = null; 
	CopyOnWriteArrayList listActivos = null;
	
	public Edificio() {
		
	}
	
	public Edificio(String nombre, String direccion,
			CopyOnWriteArrayList listColaboradores, CopyOnWriteArrayList listActivos) {		
		this.nombre = nombre;
		this.direccion = direccion;
		this.listColaboradores = listColaboradores;
		this.listColaboradores = listActivos;
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
	
	public void setListColabotadores(CopyOnWriteArrayList listColaboradores) {			
		this.listColaboradores=listColaboradores;		
	}	
	public CopyOnWriteArrayList getListColaboradores( ) {		
		return  listColaboradores;	
	}
	public void agregarColaboradorList(Object colaborador) {		
		this.listColaboradores.add (colaborador);		
	}	
	public void crearListas(int opcion){	
		switch (opcion){		
		case 1:
			listColaboradores=new CopyOnWriteArrayList ();
		case 2:				
			listActivos=new CopyOnWriteArrayList ();
		}		
	}		
	public void setListActivos(CopyOnWriteArrayList listActivos) {			
		this.listActivos=listActivos;		
	}	
	public CopyOnWriteArrayList getListActivos( ) {		
		return  listActivos;	
	}
	public void agregarActivosList(Object activo) {			
		this.listActivos.add (activo);		
	}
		
}//fin clase
