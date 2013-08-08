package com.ts.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.ts.objects.Colaborador;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Moneda;
import com.ts.objects.Objecto;

public class Repo  {
	
	private static HashMap<String, Objecto> tablaDeSimbolos = new HashMap<String, Objecto>();
	private final static Double porcientoDeduccion= 0.0917;
	
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
			Date fechaIngreso, String estadoCivil, String telefono,  int numeroHijos,
			Moneda salario) throws CommandException{
		
		boolean existeElColaborador = getColaborador(numeroCedula) != null;		
		boolean estado;
			if ( existeElColaborador )
			{
				throw new CommandException("El colaborador " + nombre + ", ya tiene el numero de cedula "+numeroCedula);
			}else if(estadoCivil.equalsIgnoreCase("N/A")){
				throw new CommandException("No se reconoce el estado civil del colaborador, digite true si esta casado, false en caso contrario");
			}else if(salario == null){
				throw new CommandException("No se reconoce tipo de moneda del salario favor digitar $ o ¢ segun corresponda");
			}
			estado= Boolean.parseBoolean(estadoCivil);
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula, fechaNacimiento,fechaIngreso, estado, telefono,  numeroHijos, salario);				
		    pullInTOTablaDeSimbolos(instance, nuevoColaborador );
		    
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");	
	}
	public static void tomarVacaciones(String persona, Date fecha) throws CommandException{
		Colaborador colaborador= null;
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) tablaDeSimbolos.get(persona);		    
				   colaborador.setVacaciones(fecha);
				   tablaDeSimbolos.put(persona, colaborador);
				   System.out.println("El Colaborador: " + colaborador.getNombre() + " se le agrego un dia de vacaciones");
			   }else{
					throw new CommandException("El Colaborador " + persona + " no existe.Imposible modificar sus vacaciones");
			   }  
	}
	
	public static String mostrarVacaciones(String persona) throws CommandException{
		Colaborador colaboradorEncontrado= null;
		String mens ="El colaborador presenta las siguientes fechas de vacaciones :" + "\n";
		ArrayList<Date> fechasAlmacenadas;
		int cont =1;
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaboradorEncontrado= (Colaborador) tablaDeSimbolos.get(persona);
				   fechasAlmacenadas = colaboradorEncontrado.getVacaciones();
				  if(fechasAlmacenadas.size()<=0){
					  throw new CommandException("El Colaborador " + persona + " no posee dias de vacaciones.");
				  }else{
					   for( Date fecha : fechasAlmacenadas){
						   mens+= "\t" +cont+". "+ InterpreteMandatos.getFechaConFormato(fecha) + "\n"; 
						   cont++;
					   }		   
				  }
				   return mens;
			   }else{
				   throw new CommandException("El Colaborador " + persona + " no existe.");
			   }  		
}
	
	public static String calculaSalarioNetoPrimeraQuincena(String persona) throws CommandException{
		Colaborador colaborador= null;
		Moneda salarioInicial;
		Double deducciones;
		Double monto;
		Double neto;
		String salario="";
		String deduc="";
		String net="";
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) tablaDeSimbolos.get(persona);
				   salarioInicial= colaborador.getSalario();
				   monto= salarioInicial.getMonto();
				   deducciones = monto * porcientoDeduccion;
				   neto= monto-deducciones; 

				   salario += colaborador.getSalario().getSign();
				   salario += monto;
				   deduc += colaborador.getSalario().getSign();
				   deduc+= deducciones;
				   net += colaborador.getSalario().getSign();
				   net+= neto;
			   
			   }else{
				   throw new CommandException("El Colaborador " + persona + " no existe.Imposible Calcular su primera quincena");
			   }       
		   return("El Colaborador: " + colaborador.getNombre() + " presenta el siguiente salario por concepto de primera quincena:" 
			   + "\n" + "\t" + "1.Salario Inicial: " + salario 
			   + "\n" + "\t" + "2.Deducciones: " + deduc
			   + "\n" + "\t" + "3.Salario Neto: " + net);
	}
	
	public static void aumentarSalario(String persona, Moneda nuevoSalario) throws CommandException{
		Colaborador colaborador= null;
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) tablaDeSimbolos.get(persona);
		
				   if(!(colaborador.getSalario().getClass().equals(nuevoSalario.getClass()))){
						   throw new CommandException("No se reconocen operaciones entre montos de diferente tipo de moneda");  
					   }
				    if(!(colaborador.getSalario().getMonto()< nuevoSalario.getMonto())){
						   throw new CommandException("Imposible ingresar un monto de igual o menor cuantia");    
					   }	  
			   }else{
				   throw new CommandException("El Colaborador " + persona + " no existe.Imposible modificar Salario");
			   } 
		   colaborador.setSalario(nuevoSalario);
		   tablaDeSimbolos.put(persona, colaborador);
		   System.out.println("El Colaborador: " + colaborador.getNombre() + " se le aumento exitosamente su salario");
	}   
	
	public static String mostrarSalario(String persona) throws CommandException{
		Colaborador colaboradorEncontrado= null;
		
		   if(tablaDeSimbolos.containsKey(persona)){   
			   colaboradorEncontrado= (Colaborador) tablaDeSimbolos.get(persona);
			   return("El Colaborador: " + colaboradorEncontrado.getNombre() + " posee un salario de " + colaboradorEncontrado.getSalario().getSign() +colaboradorEncontrado.getSalario().getMonto());
		   }else{
			   throw new CommandException("El Colaborador " + persona + " no existe.");
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
		    	if(cola.getCedula().equalsIgnoreCase(cedula))
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
