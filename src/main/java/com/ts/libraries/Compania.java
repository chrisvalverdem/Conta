package com.ts.libraries;

import java.util.ArrayList;
import java.util.Iterator;
import com.ts.db.Repo;

public class Compania extends Objecto{
	
	Hilera nombre;
	Hilera cedulaJuridica;
	Hilera tipoMoneda;  
    public static ArrayList<Colaborador> listaColaboradores= new ArrayList<Colaborador>();
    public static ArrayList<RangoRenta> intervalosRenta = new ArrayList<RangoRenta>();
  
    public Compania(Hilera cedulaJuridica, Hilera nombre, Hilera tipoMoneda) {
    	this.cedulaJuridica=cedulaJuridica;  
    	this.nombre = nombre;    
    	this.tipoMoneda = tipoMoneda;
	}    
   
    public Hilera getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(Hilera tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

    public Hilera getNombre() {
		return nombre;
	}
    public void setNombre(Hilera nombre) {
		this.nombre = nombre;
	}	
	public Hilera getCedulaJuridica() {
		return cedulaJuridica;
	}
	public void setCedulaJuridica(Hilera cedulaJuridica) {
		this.cedulaJuridica = cedulaJuridica;
	}	   
	public ArrayList<RangoRenta> getIntervalosRenta() {
		return intervalosRenta;
	}
	public void setIntervalosRenta(ArrayList<RangoRenta> intervalosRenta) {
		Compania.intervalosRenta = intervalosRenta;
	}
	
	public Compania get(Hilera cedula)
	{			
		ArrayList<Object> lista = Repo.get(Compania.class);
		for(Object exp : lista)
		{
			Compania comp = (Compania)exp;
			if(comp.getCedulaJuridica().esIgual(cedula))
	    	{
	    		return comp;
	    	}
		}	
		return null;
	}
	public void save(Hilera instance) throws CommandException {
		boolean revisarSiLaCompanniaYaExiste = get(cedulaJuridica) != null;
		
		if(revisarSiLaCompanniaYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}				
			Compania nuevaCompannia = new Compania(cedulaJuridica,nombre, tipoMoneda);	
			Repo.save(instance, nuevaCompannia );
			System.out.println("La Compañia: " + nombre + " se agrego exitosamente");	
	}
	public static Compania getCompanniaPorInstancea(Hilera instancia) throws CommandException{		
		String key;
		Objecto value;
		boolean esxisteInstancea=false;
		Compania compa =null;

		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {		    	
		    	if(key.equals((instancia.valor)))
		    	{
		    		esxisteInstancea=true;
		    		compa = (Compania)value;
		    	}
		    }
		}
		if(!esxisteInstancea){
			throw new CommandException("La instancia " + instancia+ " no existe.");			
		}
		return compa;
	}

	public static ArrayList<Colaborador> getListaColaboradores() {
		return listaColaboradores;
	}

	public static void setListaColaboradores(ArrayList<Colaborador> listaColaboradores) {
		Compania.listaColaboradores = listaColaboradores;
	}
	public static int getTamannoCompannia(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value =  Repo.getData(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	public static Compania getCompannia(Hilera cedulaJuridica) throws CommandException {
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {
		    	Compania comp = (Compania)value; 
		    	if(comp.getCedulaJuridica().equals(cedulaJuridica))
		    	{
		    		return comp;
		    	}
		    }
		}	
		return null;			
	}
	public static void agregaColaborardorCompannia(Hilera instancia, Hilera persona) throws CommandException{
		Compania compania = getCompanniaPorInstancea(instancia);
		Colaborador colaborador = Colaborador.getColaboradorEnCompannia(compania, persona);
		
		boolean revisarSiElColaboradorYaSeAgrego = colaborador != null;
		if(revisarSiElColaboradorYaSeAgrego){
			Compania.listaColaboradores.add(colaborador);	
			System.out.println("La Colaborador: " + colaborador.getNombre() + " se agrego exitosamente a la campañia " + compania.getNombre());	
		}else{
			throw new CommandException("El colaborador ya se agrego a esta compañia");
		}				
	}
}