package com.ts.libraries;

import java.util.ArrayList;
import java.util.Iterator;
import com.ts.db.Repo;
import com.ts.main.Hacienda;
import com.ts.main.Sistema;

public class Compania extends Objecto{
	
	Hilera nombre;
	Hilera cedulaJuridica;
	Hilera tipoMoneda;  
	private static Moneda montoConyuge;
	private static Moneda montoHijo;

	public static ArrayList<Colaborador> listaColaboradores= new ArrayList<Colaborador>();
    public static ArrayList<RangoRenta> intervalosRenta = new ArrayList<RangoRenta>();
  
    public Compania(Hilera cedulaJuridica, Hilera nombre, Hilera tipoMoneda) {
    	this.cedulaJuridica=cedulaJuridica;  
    	this.nombre = nombre;    
    	this.tipoMoneda = tipoMoneda;
	}  
    
    public static Moneda getMontoConyuge() {
		return montoConyuge;
	}

	public static void setMontoConyuge(Moneda montoConyuge) {
		Compania.montoConyuge = montoConyuge;
	}

	public static Moneda getMontoHijo() {
		return montoHijo;
	}

	public static void setMontoHijo(Moneda montoHijo) {
		Compania.montoHijo = montoHijo;
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
	
	public static Compania get(Hilera cedula)
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
	
	public static Compania getCompanniaPorInstancea(Hilera instancia){		
		String key;
		Objecto value;
		boolean esxisteInstancea=false;
		Compania compa =null;

		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData().get(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {		    	
		    	if(key.equals(instancia))
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
		    value =  Repo.getData().get(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	
	public RangoRenta analizaIntervalosRenta(String[] parametros) throws CommandException {
		Decimal porciento= new Decimal(0.0);
		Moneda intervaloMenor;
		Moneda intervaloMayor;
		Hilera identificador= new Hilera("");	
		Hilera param1;
		Hilera param2;
		Hilera param3;	

		if(parametros.length > 3 ){
			throw new CommandException("No se reconoce "+ "," + " como separador de digitos favor ingresar monto correctamente el monto ¢714000");
		}else{
	   		param1 = new Hilera(parametros[0]);
	   		param2 = new Hilera(parametros[1]);
	   		param3 = new Hilera(parametros[2]);		
		}
		intervaloMenor = Sistema.getMoneda(param1);
		intervaloMayor = Sistema.getMoneda(param2);
		porciento.setValor(Double.parseDouble(param3.getValor()));	

		 if(Hacienda.intervalosRenta.isEmpty()){
		    if(intervaloMenor.getMonto().getValor() == 1.0){
		    	identificador= new Hilera("case0");
		    }else{
		    	throw new CommandException("Favor ingresar en el orden debido los intervalos, de menor cuantia a mayor");
		    }	
		 }else if(Hacienda.intervalosRenta.size()==1){
		    if(intervaloMenor.getMonto().getValor()== 1.0){
		    	identificador= new Hilera("case0");
		    }else{
		    	RangoRenta case0 = Hacienda.intervalosRenta.get(0);
		    	
		    	if(case0.getIntervaloSuperior().getMonto().getValor()== intervaloMenor.getMonto().getValor()){
		    		identificador= new Hilera("case1");
		    	}else{
		    		throw new CommandException("Los intervalos del case 0 y case 1 no concuerdad favor verificar");
		    	}
		    }  
		  }	else if(Hacienda.intervalosRenta.size()== 2){
			  if(intervaloMenor.getMonto().getValor()== 1.0){
			    	identificador= new Hilera("case0");
			    }else{
			    	RangoRenta case0 = Hacienda.intervalosRenta.get(0);
			    	RangoRenta case1 = Hacienda.intervalosRenta.get(1);
			    	
			    	if(case0.getIntervaloSuperior().getMonto().getValor()== intervaloMenor.getMonto().getValor()){
			    		identificador= new Hilera("case1");
			    	}else if(case1.getIntervaloSuperior().getMonto().getValor()== intervaloMenor.getMonto().getValor()){
			    		identificador= new Hilera("case2");
			    	}else{
			    		throw new CommandException("Los intervalos no concuerdad favor verificar");
			    	}
			    }     
		  }else if(Hacienda.intervalosRenta.size()== 3){
			  if(intervaloMenor.getMonto().getValor()== 1.0){
			    	identificador= new Hilera("case0");
			    }else{
			    	RangoRenta case0 = Hacienda.intervalosRenta.get(0);
			    	RangoRenta case1 = Hacienda.intervalosRenta.get(1);
			    	RangoRenta case2 = Hacienda.intervalosRenta.get(2);
			    	
			    	if(case0.getIntervaloSuperior().getMonto().getValor()== intervaloMenor.getMonto().getValor() && case2.getIntervaloInferior().getMonto().getValor()== intervaloMayor.getMonto().getValor()){
			    		identificador= new Hilera("case1");
			    	}else{
			    		throw new CommandException("Los intervalos no concuerdan con los del case o y el case 2. Para modificar favor limpiar rangos");
			    	}
			    	if(case1.getIntervaloSuperior().getMonto().getValor()== intervaloMenor.getMonto().getValor()){
			    		identificador= new Hilera("case2");
			    	}else{
			    		throw new CommandException("Los intervalos del case 1 y case 2 no concuerdad favor verificar. Para modificar favor limpiar rangos");
			    	}
			    }    
		  }	
		return new RangoRenta(intervaloMenor, intervaloMayor, porciento, identificador);
	}
 
	public void cambioRangoRenta(RangoRenta rango) throws CommandException {  
			    	switch(rango.getIdentificador().getValor()){
			    	case "case0":
			    		Hacienda.intervalosRenta.add(0, rango);
			    		System.out.println("El intervalo se agrego exitosamente");
			    		break;
			    	case "case1":
			    		if(Hacienda.intervalosRenta.get(0).getIntervaloInferior().getSing().equalsIgnoreCase(rango.getIntervaloInferior().getSing())){
			    			Hacienda.intervalosRenta.add(1, rango);
				    		System.out.println("El intervalo se agrego exitosamente");
			    		}else{
			    		throw new CommandException("Imposible incluir rangos de diferente tipo de moneda");	
			    		}
			    		break;
			    	case "case2":
			    		if(Hacienda.intervalosRenta.get(1).getIntervaloInferior().getSing().equalsIgnoreCase(rango.getIntervaloInferior().getSing())){
			    			Hacienda.intervalosRenta.add(2, rango);
				    		System.out.println("El intervalo se agrego exitosamente");
			    		}else{
			    		throw new CommandException("Imposible incluir rangos de diferente tipo de moneda");	
			    		}
			    		break;
			    	}
	}
	
	public void actualizarMontoConyuge (Moneda montoConyuge) {	
			setMontoConyuge(montoConyuge);
			System.out.println("El monto para conyuge se actualizaro exitosamente");
	}	
	public void actualizarMontoHijo (Moneda montoHijo) {	
			Compania.setMontoHijo(montoHijo);
			System.out.println("El monto para hijo se actualizo exitosamente");	
	}
	
	public static void limpiaRangoRenta(){
	    if(!(Hacienda.intervalosRenta.isEmpty())){
	    	ArrayList<RangoRenta> array = Hacienda.getIntervalosRenta();
	    	array.clear();
	    	Hacienda.setIntervalosRenta(array);
	    	System.out.println("Se limpiaron todos los registros de los rangos de renta exitosamente");
	    }
	}

}