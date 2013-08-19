package com.ts.libraries;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ts.db.Repo;
import com.ts.interprete.libraries.Expression;
import com.ts.objects.CommandException;

public class Compania extends Objecto{
	
	String nombre;
	String cedulaJuridica;
	  
    public static ArrayList<RangoRenta> intervalosRenta = new ArrayList<RangoRenta>();
  
    public Compania(String cedulaJuridica, String nombre) {
    	this.cedulaJuridica=cedulaJuridica;  
    	this.nombre = nombre;    	  	
	}    
   
    public String getNombre() {
		return nombre;
	}
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	public String getCedulaJuridica() {
		return cedulaJuridica;
	}
	public void setCedulaJuridica(String cedulaJuridica) {
		this.cedulaJuridica = cedulaJuridica;
	}	   
	public ArrayList<RangoRenta> getIntervalosRenta() {
		return intervalosRenta;
	}
	public void setIntervalosRenta(ArrayList<RangoRenta> intervalosRenta) {
		Compania.intervalosRenta = intervalosRenta;
	}
	
	public void save(String variableInstancia)
	{
		boolean revisarSiLaCompanniaYaExiste = get(cedulaJuridica) != null;
		
		if(revisarSiLaCompanniaYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}	
		
		Repo.save(variableInstancia, this );
		System.out.println("La Compannia: " + nombre + " se agrego exitosamente");
	}
	
	public static Compania get(String cedula)
	{			
		ArrayList<Object> lista = Repo.get(Compania.class);
		for(Object exp : lista)
		{
			Compania comp = (Compania)exp;
			if(comp.getCedulaJuridica().equals(cedula))
	    	{
	    		return comp;
	    	}
		}
		
		return null;
	}
}