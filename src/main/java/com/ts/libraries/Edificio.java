package com.ts.libraries;

import java.util.ArrayList;
import java.util.Iterator;
import com.ts.db.Repo;

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
	@Override
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
	public static int getTamannoEdificio(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
		    boolean esUnEdificio = value instanceof Edificio; 
		    if(esUnEdificio)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	public static Edificio getEdificio(String nombre){			
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
		    boolean esUnEdificio = value instanceof Edificio; 
		    if(esUnEdificio)
		    {
		    	Edificio edi = (Edificio)value; 
		    	if(edi.getNombre().equals(nombre))
		    	{
		    		return edi;
		    	}
		    }
		}	
		return null;
	}
	public static void AgregarEdificio(String instance, String nombre, String direccion) throws CommandException{			
		boolean existeElEdificio =  getEdificio(nombre) != null;
			
		if (existeElEdificio)
		{
			throw new CommandException("El Edificio: " + nombre+ " ya existe.");
		}
		Edificio edificio = new Edificio(nombre,direccion);
		Repo.tablaDeSimbolos.put(instance, edificio);
		 System.out.println("El edificio " + nombre + " se le agrego exitosamente.");
	}	
}
