package com.ts.objects;


import java.util.concurrent.CopyOnWriteArrayList;

public class Compañia {
	
	String nombreCompania;
	String direccionCompania;
	int	telefonoCompania;
    int faxCompañia;
    
      
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
	
}//fin de la clase