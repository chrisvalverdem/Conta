package com.ts.libraries;

import java.util.ArrayList;
import java.util.Iterator;

import com.ts.db.Repo;
import com.ts.interprete.libraries.Expression;
import com.ts.objects.CommandException;

public class Edificio extends Objecto{
	
	String nombre;
	String direccion;

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
	public void save(String variableInstancia)
	{
			boolean existeElEdificio =  Edificio.get(nombre) != null;
				
			if (existeElEdificio)
			{
				throw new CommandException("El Edificio: " + nombre+ " ya existe.");
			}
			Repo.save(variableInstancia, this);
			System.out.println("El edificio " + nombre + " se le agrego exitosamente.");
	}
	public static Edificio get(String nombre)
	{			
		ArrayList<Object> lista = Repo.get(Edificio.class);
		for(Object exp : lista)
		{
			Edificio comp = (Edificio)exp;
			if(comp.getNombre().equals(nombre))
	    	{
	    		return comp;
	    	}
		}
		
		return null;
	}
}
