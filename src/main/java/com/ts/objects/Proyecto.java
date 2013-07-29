package com.ts.objects;

public class Proyecto {
	
	String nombre;	
	int fechainicio;
	int fechaFin;
	public static String comandosValidos[] ={"nuevoProyecto,agregarColaborador"};

	public Proyecto(String nombre) {
		this.nombre= nombre;		
	}
	
	public Proyecto(String nombre, int fechainicio, int fechaFin) {
	
		this.nombre= nombre;
		this.fechainicio = fechainicio;
		this.fechaFin = fechaFin;		
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
}//fin clase

