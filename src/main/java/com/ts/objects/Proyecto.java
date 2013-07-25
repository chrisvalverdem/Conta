package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Proyecto {
	
	String nombreProyecto;	
	int fechainicio;
	int fechaFin;
	CopyOnWriteArrayList listEquipo = null;
	
	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
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
	
	public void setListEmpleados(Object colaborador) {		
		
		this.listEquipo.add (colaborador);		
	}			

}//fin de la clase

