package com.ts.libraries;

import java.util.ArrayList;
import java.util.Iterator;
import com.ts.db.Repo;

public class Edificio extends Objecto{
	
	Hilera nombre;
	Hilera direccion;

	public Edificio(Hilera nombre, Hilera direccion) {		
		this.nombre = nombre;
		this.direccion = direccion;		
	}
	
	public Hilera getNombre() {
		return nombre;
	}
	public void setNombre(Hilera nombre) {
		this.nombre = nombre;
	}
	public Hilera getDireccion() {
		return direccion;
	}
	public void setDireccion(Hilera direccion) {
		this.direccion = direccion;
	}		

	public void save(Hilera variableInstancia)
	{
			boolean existeElEdificio =  Edificio.get(nombre) != null;
				
			if (existeElEdificio)
			{
				throw new CommandException("El Edificio: " + nombre+ " ya existe.");
			}
			Repo.save(variableInstancia, this);
			System.out.println("El edificio " + nombre + " se le agrego exitosamente.");
	}
	public static Edificio get(Hilera nombre)
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
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnEdificio = value instanceof Edificio; 
		    if(esUnEdificio)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	public static Edificio getEdificio(Hilera nombre){			
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnEdificio = value.getClass()== Edificio.class; 
		    if(esUnEdificio)
		    {
		    	Edificio edi = (Edificio)value; 
		    	if(edi.getNombre().esIgual(nombre))
		    	{
		    		return edi;
		    	}
		    }
		}	
		return null;
	}
	public void AgregarEdificio(Hilera instance) throws CommandException{			
		boolean existeElEdificio =  getEdificio(nombre) != null;
			
		if (existeElEdificio)
		{
			throw new CommandException("El Edificio: " + nombre+ " ya existe.");
		}
		Edificio edificio = new Edificio(nombre,direccion);
		Repo.save(instance, this);
		 System.out.println("El edificio " + nombre + " se le agrego exitosamente.");
	}	
}
