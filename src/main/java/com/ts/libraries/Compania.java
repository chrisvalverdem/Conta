package com.ts.libraries;

import java.util.ArrayList;
import java.util.Iterator;
import com.ts.db.Repo;

public class Compania extends Objecto{
	
	String nombre;
	String cedulaJuridica;
    String tipoMoneda;  
    public static ArrayList<Colaborador> listaColaboradores= new ArrayList<Colaborador>();
  
    public Compania(String cedulaJuridica, String nombre,String tipoMoneda) {
    	this.cedulaJuridica=cedulaJuridica;  
    	this.nombre = nombre;    
    	this.tipoMoneda = tipoMoneda;
	}    
   
    public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
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
	@Override
	public void save(String variableInstancia)
	{
		boolean revisarSiLaCompanniaYaExiste = get(cedulaJuridica) != null;
		
		if(revisarSiLaCompanniaYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}	
		
		Repo.save(variableInstancia, this );
		System.out.println("La Compannia: " + nombre + " se agrego exitosamente");
	}
	
	public static Compania get(String cedula)	{			
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
	public static void AgregarCompannia(String instance, String cedulaJuridica,String nombre, String tipoMoneda) throws CommandException {
		boolean revisarSiLaCompanniaYaExiste = getCompannia(cedulaJuridica) != null;
		
		if(revisarSiLaCompanniaYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}				
			Compania nuevaCompannia = new Compania(cedulaJuridica,nombre, tipoMoneda);	
			Repo.save(instance, nuevaCompannia );
			System.out.println("La Compañia: " + nombre + " se agrego exitosamente");	
	}
	public static Compania getCompanniaPorInstancea(String instancia) throws CommandException{		
		String key;
		Objecto value;
		boolean esxisteInstancea=false;
		Compania compa =null;

		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {		    	
		    	if(key.equalsIgnoreCase(instancia))
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
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value =  Repo.tablaDeSimbolos.get(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	public static Compania getCompannia(String cedulaJuridica) throws CommandException {
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
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
	public static void agregaColaborardorCompannia(String instancia, String persona) throws CommandException{
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