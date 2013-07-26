package com.ts.objects;

public class Activo {
	
	String nombre;
	String estado;
	String ubicacion;
	int numeroPlaca;
	
	public Activo() {
	
	}
	
	public Activo(String nombre, String estado, String ubicacion,
			int numeroPlaca) {
		
		this.nombre = nombre;
		this.estado = estado;
		this.ubicacion = ubicacion;
		this.numeroPlaca = numeroPlaca;
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