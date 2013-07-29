package com.ts.objects;

import java.util.Date;

public class Activo {
	
	String nombre;
	String estado;
	String ubicacion;
	int numeroPlaca;
	Date fechaIngreso;
	Date fechaSalida;
	public static String comandosValidos[] ={"nuevoActivo"};	

	public Activo(String nombre,int numeroPlaca) {
		this.nombre = nombre;
		this.numeroPlaca = numeroPlaca;
	}
	
	public Activo(String nombre, int numeroPlaca, String estado, String ubicacion,
			Date fechaIngreso, Date fechaSalida) {		
		this.nombre = nombre;
		this.numeroPlaca = numeroPlaca;
		this.estado = estado;
		this.ubicacion = ubicacion;		
		this.fechaIngreso=fechaIngreso;
		this.fechaSalida=fechaSalida;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getNumeroPlaca() {
		return numeroPlaca;
	}
	public void setNumeroPlaca(int numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}	
}//fin de la clase