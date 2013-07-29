package com.ts.objects;

public class Edificio {
	
	String nombre;
	String direccion;
	public static String comandosValidos[] ={"nuevoEdificio,agregarActivo"};

	public Edificio(String nombre) {
		this.nombre = nombre;
	}
	
	public Edificio(String nombre, String direccion) {		
		this.nombre = nombre;
		this.direccion = direccion;		
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
}//fin clase
