package com.ts.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.ts.libraries.CommandException;
import com.ts.libraries.Compania;
import com.ts.libraries.Hilera;
import com.ts.libraries.Moneda;
import com.ts.libraries.Objecto;
import com.ts.libraries.RangoRenta;
import com.ts.main.Hacienda;
import com.ts.main.Sistema;

public class Repo  {
	
	public static HashMap<Hilera, Objecto> tablaDeSimbolos = new HashMap<Hilera, Objecto>();
	public static ArrayList<Double> intervalosRenta = new ArrayList<Double>();
	

	public static void save(Hilera string, Objecto object)
	{
		boolean existeAlgunaInstancea = ! tablaDeSimbolos.isEmpty();
		if(existeAlgunaInstancea){
			if(tablaDeSimbolos.containsKey(string)){			
				throw new CommandException("La instancia " + string+ " ya existe, cambiela por una diferente.");				
			}					
		}	
		
		tablaDeSimbolos.put(string, object);
	}

	public static Objecto getData(String key)
	{
		return tablaDeSimbolos.get(key);
	}
	public static HashMap<Hilera, Objecto> getData()
	{
		return tablaDeSimbolos;
	}
	public static void clean() {
		tablaDeSimbolos.clear();	
	}
	public static ArrayList<Object> get(Class<?> clase)
	{			
		Hilera key;
		Object value;
		Iterator<Hilera> iterator = Repo.getData().keySet().iterator();
		ArrayList<Object> resultado = new ArrayList<Object>();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData().get(key);
		    boolean esLaClaseEsperada = value.getClass().equals(clase); 
		    if(esLaClaseEsperada)
		    {
		    	resultado.add(value);
		    }
		}	
		return resultado;
	}
	public static RangoRenta analizaIntervalosRenta(String[] parametros) throws CommandException {
		Double porciento=0.0;
		Moneda intervaloMenor;
		Moneda intervaloMayor;
		String identificador="";	
   		String param1;
   		String param2;
   		String param3;	

		if(parametros.length > 3 ){
			throw new CommandException("No se reconoce "+ "," + " como separador de digitos favor ingresar monto correctamente el monto ¢714000");
		}else{
	   		param1 = parametros[0];
	   		param2 = parametros[1];
	   		param3 = parametros[2];	
			
		}
		if(param1.contains(".") || param2.contains(".")){
			throw new CommandException("No se reconoce "+ "." + " como separador de digitos favor ingresar monto correctamente el monto ¢714000");
		}
		if(param3.contains("$") || param3.contains("¢")){
			throw new CommandException("No se reconocen el monto donde se espera un porciento");
		}
		if(param3.contains("%")){
			throw new CommandException("No se reconocen caracter % en los porcientos, estos se dan en valor entero o decimales %10= 10 = 0.10");
		}
		if(!(param1.contains("$") || param1.contains("¢") && param2.contains("¢") || param2.contains("$"))){
			throw new CommandException("No se reconocen montos, estos deben contener tipo de moneda $ o ¢");
		}
		if(param1.contains("$") && param2.contains("¢") || param1.contains("¢") && param2.contains("$")){
			throw new CommandException("No se reconocen montos, estos deben contener el mismo tipo de moneda");
		}

		intervaloMenor = Sistema.getMoneda(param1);
		intervaloMayor = Sistema.getMoneda(param2);
		porciento= Double.parseDouble(param3);	

		 if(Hacienda.intervalosRenta.isEmpty()){
		    if(intervaloMenor.getMonto() == 1.0){
		    	identificador= "case0";
		    }else{
		    	throw new CommandException("Favor ingresar en el orden debido los intervalos, de menor cuantia a mayor");
		    }	
		 }else if(Hacienda.intervalosRenta.size()==1){
		    if(intervaloMenor.getMonto()== 1.0){
		    	identificador= "case0";
		    }else{
		    	RangoRenta case0 = Hacienda.intervalosRenta.get(0);
		    	
		    	if(case0.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
		    		identificador= "case1";
		    	}else{
		    		throw new CommandException("Los intervalos del case 0 y case 1 no concuerdad favor verificar");
		    	}
		    }  
		  }	else if(Hacienda.intervalosRenta.size()== 2){
			  if(intervaloMenor.getMonto()== 1.0){
			    	identificador= "case0";
			    }else{
			    	RangoRenta case0 = Hacienda.intervalosRenta.get(0);
			    	RangoRenta case1 = Hacienda.intervalosRenta.get(1);
			    	
			    	if(case0.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
			    		identificador= "case1";
			    	}else if(case1.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
			    		identificador= "case2";
			    	}else{
			    		throw new CommandException("Los intervalos no concuerdad favor verificar");
			    	}
			    }     
		  }else if(Hacienda.intervalosRenta.size()== 3){
			  if(intervaloMenor.getMonto()== 1.0){
			    	identificador= "case0";
			    }else{
			    	RangoRenta case0 = Hacienda.intervalosRenta.get(0);
			    	RangoRenta case1 = Hacienda.intervalosRenta.get(1);
			    	RangoRenta case2 = Hacienda.intervalosRenta.get(2);
			    	
			    	if(case0.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto() && case2.getIntervaloInferior().getMonto()== intervaloMayor.getMonto()){
			    		identificador= "case1";
			    	}else{
			    		throw new CommandException("Los intervalos no concuerdan con los del case o y el case 2. Para modificar favor limpiar rangos");
			    	}
			    	if(case1.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
			    		identificador= "case2";
			    	}else{
			    		throw new CommandException("Los intervalos del case 1 y case 2 no concuerdad favor verificar. Para modificar favor limpiar rangos");
			    	}
			    }    
		  }	
		return new RangoRenta(intervaloMenor, intervaloMayor, porciento, identificador);
	}
 
	public static void cambioRangoRenta(Hilera instancia, RangoRenta rango) throws CommandException {
		Hilera key;
		Objecto value;
		Iterator<Hilera> iterator = tablaDeSimbolos.keySet().iterator();	
		while (iterator.hasNext()){
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnaCompannia = value instanceof Compania; 
		    if(esUnaCompannia){
		    	if(key.esIgual(instancia)){	  
			    	switch(rango.getIdentificador().toLowerCase()){
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
		    }
		}
	}
	
	public static void actualizarMontoConyugeHijo (Moneda montoConyuge, Moneda montoHijo) {
	Hacienda.setMontoConyuge(montoConyuge);
	Hacienda.setMontoHijo(montoHijo);
	System.out.println("Los montos para conyuge e hijo se actualizaron exitosamente");		
	}	
	public static void limpiaRangoRenta(){
	    if(!(Hacienda.intervalosRenta.isEmpty())){
	    	ArrayList<RangoRenta> array = Hacienda.getIntervalosRenta();
	    	array.clear();
	    	Hacienda.setIntervalosRenta(array);
	    	System.out.println("Se limpiaron todos los registros de los rangos de renta exitosamente");
	    }
	}
	public static void validarInstaciaEnLaTablaDeSimbolos(String instancia) throws CommandException  {
		
		boolean existeAlgunaInstancea = ! tablaDeSimbolos.isEmpty();
		if(existeAlgunaInstancea){
			if(tablaDeSimbolos.containsKey(instancia)){			
				throw new CommandException("La instancia " + instancia+ " ya existe, cambiela por una diferente.");				
			}					
		}				
	}
	
	public static void limpiaListas() {
	tablaDeSimbolos.clear();	
	}

}//fin clase
