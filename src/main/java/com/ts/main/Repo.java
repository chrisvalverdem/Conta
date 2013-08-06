package com.ts.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ts.objects.Colaborador;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Moneda;
import com.ts.objects.Objecto;

public class Repo  {
	
	private static HashMap<String, Objecto> tablaDeSimbolos = new HashMap<String, Objecto>();
	
	private static void pullInTOTablaDeSimbolos(String string, Objecto object)
	{
		tablaDeSimbolos.put(string, object);
	}
	
	public static void AgregarCompannia(String instance, String cedulaJuridica,String nombre) throws CommandException {
		boolean revisarSiLaCompanniaYaExiste = getCompannia(cedulaJuridica) != null;
		
		if(revisarSiLaCompanniaYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}				
			Compannia nuevaCompannia = new Compannia(cedulaJuridica,nombre);	
			pullInTOTablaDeSimbolos(instance, nuevaCompannia );
			System.out.println("La Compañia: " + nombre + " se agrego exitosamente");	
	}
	
	public static void AgregarColaborador(String instance, String nombre, String numeroCedula, Date fechaNacimiento,
			Date fechaIngreso, boolean estado, String telefono,  int numeroHijos,
			Moneda salario) throws CommandException{
		
		boolean existeElColaborador = getColaborador(numeroCedula) != null;		
	
			if ( existeElColaborador )
			{
				throw new CommandException("El colaborador " + nombre + ", ya tiene el numero de c�dula "+numeroCedula);
			}
			
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula, fechaNacimiento,fechaIngreso, estado, telefono,  numeroHijos, salario);				
		    pullInTOTablaDeSimbolos(instance, nuevoColaborador );
		    
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");	
	}
	
	public static void aumentar_Salario(String persona, Moneda nuevoSalario) throws CommandException{
		boolean existeAlgo= !tablaDeSimbolos.isEmpty();
		Colaborador colaborador= null;

		  if(existeAlgo){
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) tablaDeSimbolos.get(persona);
				   colaborador.setSalario(nuevoSalario);
				   tablaDeSimbolos.put(persona, colaborador);
				   System.out.println("El Colaborador con identificacion: " + colaborador.getNombre() + " se le aumento exitosamente su salario");
			   }else{
					throw new CommandException("El Colaborador " + persona + " no existe.Imposible modificar Salario");
			   }  
		  }

	}    					
	public static void 	mostrar_Salario(String persona) throws CommandException{

		boolean existeAlgo= ! tablaDeSimbolos.isEmpty();
		Colaborador colaboradorEncontrado= null;

		if(existeAlgo){
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaboradorEncontrado= (Colaborador) tablaDeSimbolos.get(persona);
				   System.out.println("El Colaborador con identificacion: " + colaboradorEncontrado.getNombre() + " posee un salario de " + colaboradorEncontrado.getSalario().getMonto());
			   }else{
				   throw new CommandException("El Colaborador " + persona + " no existe.");
			   }  
		  }				
}
	public static void AgregarEdificio(String instance, String nombre) throws CommandException{			
		boolean existeElEdificio =  getEdificio(nombre) != null;
			
		if (existeElEdificio)
		{
			throw new CommandException("El Edificio: " + nombre+ " ya existe.");
		}
		Edificio edificio = new Edificio(nombre);
		tablaDeSimbolos.put(instance, edificio);
		 System.out.println("El edificio " + nombre + " se le agrego exitosamente.");
	}

	public static Compannia getCompannia(String cedulaJuridica) throws CommandException {
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnaCompannia = value instanceof Compannia; 
		    if(esUnaCompannia)
		    {
		    	Compannia comp = (Compannia)value; 
		    	if(comp.getCedulaJuridica().equals(cedulaJuridica))
		    	{
		    		return comp;
		    	}
		    }
		}	
		return null;			
	}	
	public static Edificio getEdificio(String nombre){			
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
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
	
	public static Colaborador getColaborador(String cedula){			
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnColaborador = value instanceof Colaborador; 
		    if(esUnColaborador)
		    {
		    	Colaborador cola = (Colaborador)value; 
		    	if(cola.getCedula().equals(cedula))
		    	{
		    		return cola;
		    	}
		    }
		}	
		return null;
	}
	
	public static int getTamannoCompannia(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnaCompannia = value instanceof Compannia; 
		    if(esUnaCompannia)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	
	public static int getTamannoEdificio(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnEdificio = value instanceof Edificio; 
		    if(esUnEdificio)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	
	public static int getTamannoColaborador(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnColaborador= value instanceof Colaborador; 
		    if(esUnColaborador)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	
	protected static void validarInstaciaEnLaTablaDeSimbolos(String instance) throws CommandException  {
		
		boolean existeAlgunaInstancea = ! tablaDeSimbolos.isEmpty();
		if(existeAlgunaInstancea){
			if(tablaDeSimbolos.containsKey(instance)){			
				throw new CommandException("La instancia " + instance+ " ya existe, cambiela por una diferente.");				
			}					
		}				
	}	
	public static void limpiaListas() {
		tablaDeSimbolos.clear();	
	}
}//fin clase
