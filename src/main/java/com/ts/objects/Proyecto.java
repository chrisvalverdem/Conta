package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Proyecto {
	
	String nombre;	
	int fechainicio;
	int fechaFin;
	CopyOnWriteArrayList listEquipo = null;
	
	public Proyecto(String nombre) {
		this.nombre= nombre;		
	}
	
	public Proyecto(String nombre, int fechainicio, int fechaFin,
			CopyOnWriteArrayList listEquipo) {
	
		this.nombre= nombre;
		this.fechainicio = fechainicio;
		this.fechaFin = fechaFin;
		this.listEquipo = listEquipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre= nombre;
	}
	
	public int getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(int fechainicio) {
		this.fechainicio = fechainicio;
	}

	public int getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(int fechaFin) {
		this.fechaFin = fechaFin;
	}

	public CopyOnWriteArrayList getListEquipo() {
		return listEquipo;
	}

	public void setListEquipo(CopyOnWriteArrayList listEquipo) {
		this.listEquipo = listEquipo;
	}
	
	public void agregarColaboradorList(Object colaborador) {		
		
		this.listEquipo.add (colaborador);		
	}	
}//fin clase

