package com.ts.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import com.ts.objects.Colaborador;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Moneda;
import com.ts.objects.Objecto;
import com.ts.objects.RangoRenta;

public class Repo  {
	
	private static HashMap<String, Objecto> tablaDeSimbolos = new HashMap<String, Objecto>();
	private final static Double porcientoDeduccion= 0.0917;
	public static ArrayList<Double> intervalosRenta = new ArrayList<Double>();
	
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
						   mens+= "\t" +cont+". "+ Sistema.getFechaConFormato(fecha) + "\n"; 
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
	
	protected static void validarInstaciaEnLaTablaDeSimbolos(String instancia) throws CommandException  {
		
		boolean existeAlgunaInstancea = ! tablaDeSimbolos.isEmpty();
		if(existeAlgunaInstancea){
			if(tablaDeSimbolos.containsKey(instancia)){			
				throw new CommandException("La instancia " + instancia+ " ya existe, cambiela por una diferente.");				
			}					
		}				
	}
	public static Colaborador getColaboradorPorInstancea(String instancia){			
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
		    	if(key.equalsIgnoreCase(instancia))
		    	{
		    		return cola;
		    	}
		    }
		}	
		return null;
	}	

	public static String cantidadVacacionesDisponibles(String instancia, String fecha){
		
		int vacacionesDisponibles=0;
		int cantidadVacacionesTomados;
		String mensaje;	
		Date fechacomando=null;
		
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
				
		 Colaborador cola=getColaboradorPorInstancea(instancia);
		 Date fechaIngTem1=cola.getFechaIngresoEmpresa();
		 cal1.setTime(fechaIngTem1);		 
		 
		try {
			fechacomando = Sistema.getParseFecha(fecha);
		}
		catch(CommandException commandException)
		{
			commandException.setMessage("Formato de fecha invalido, el formato debe ser: dd/mm/yyyy");
			System.err.println(commandException.getMessage());
			
		}		
		 cal2.setTime(fechacomando);
		 
		 int cantidadDiasLaborados= cantidadDiasEntreFechas(cal1.getTime(), cal2.getTime())+1;		 
		 float diviEntresiete=cantidadDiasLaborados/7;		
		 float diviEntreCincuenta=diviEntresiete/50;		 
		 float  multEntrediez=diviEntreCincuenta * 10 ;		
		
		 //float vacaciones= (((cantidadDiasLaborados/7)/50)*10);		
		vacacionesDisponibles= (int) multEntrediez;
				
		if (!cola.getVacaciones().isEmpty()){
		 cantidadVacacionesTomados=cola.getVacaciones().size();	
		 vacacionesDisponibles=vacacionesDisponibles - cantidadVacacionesTomados;	
		}		
		
		System.out.println("cantidad de vacaciones  : " + vacacionesDisponibles);
		if(vacacionesDisponibles > 0 )
			mensaje = "Cantidad de vacaciones disponibles para "+instancia+" son: " +vacacionesDisponibles;
		else{
			if(vacacionesDisponibles < 0 )				
				mensaje = "El colaborador "+instancia+" no tiene dias de vacaciones disponibles, por el contrario debe la siguiente cantidad de vacaciones: "+ vacacionesDisponibles * -1;			
			else
				mensaje = "El colaborador "+instancia+" no tiene dias de vacaciones disponibles.";
		}		
		System.out.println("Fecha  : " + mensaje+ " \n");
		return  mensaje;
		}
	
	 public static int cantidadDiasEntreFechas(Date d1, Date d2){
         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	 }	
	public static String cantidadVacacionesLiquidacion(String instancia, String fecha){
		
		int vacacionesDisponiblesLiquidacion=0;
		int cantidadVacacionesTomadosLiquidacion=0;
		String mensajeliquidacion="";
		
		String [] parseFecha=fecha.split("\\/");		
		int mesComando=(Integer.parseInt(parseFecha [1]));
		int annioComando=(Integer.parseInt(parseFecha [2]));			
		
		Colaborador cola=getColaboradorPorInstancea(instancia);
		Date fechaIngTem1=cola.getFechaIngresoEmpresa();		
		String fechaIngreso=Sistema.getFechaConFormato(fechaIngTem1);							
		String[] temp = fechaIngreso.split("\\/"); 
		
		int annioIngreso=Integer.parseInt(temp[2]);
		int mesIngreso=Integer.parseInt( temp[1]);
		int diaIngreso=Integer.parseInt( temp[0]);
		System.out.println("Fecha de ingreso : " + diaIngreso +""+mesIngreso+""+annioIngreso+ " \n");
		
		int x= annioIngreso * 12 + mesIngreso;
		int y= annioComando * 12 + mesComando;		
		
		vacacionesDisponiblesLiquidacion= y - (x + 1)+1;

		if (!cola.getVacaciones().isEmpty()){
			cantidadVacacionesTomadosLiquidacion=cola.getVacaciones().size();	
			 vacacionesDisponiblesLiquidacion=vacacionesDisponiblesLiquidacion - cantidadVacacionesTomadosLiquidacion;	
		}						
		
		if(vacacionesDisponiblesLiquidacion > 0 )
			mensajeliquidacion = "Cantidad de vacaciones disponibles para "+instancia+" son: " +vacacionesDisponiblesLiquidacion;
		else
			if(vacacionesDisponiblesLiquidacion < 0 )				
				mensajeliquidacion = "El colaborador "+instancia+" no tiene dias de vacaciones disponibles, por el contrario debe la siguiente cantidad de vacaciones: "+ vacacionesDisponiblesLiquidacion * -1;			
			else
				mensajeliquidacion = "El colaborador "+instancia+" no tiene dias de vacaciones disponibles.";
		System.out.println(" : " + mensajeliquidacion);
		return  mensajeliquidacion;
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

			intervaloMenor = Sistema.getSalario(param1);
			intervaloMayor = Sistema.getSalario(param2);
			porciento= Double.parseDouble(param3);	

			System.out.println("Tamaño :"+Compannia.intervalosRenta.size() );
			 if(Compannia.intervalosRenta.isEmpty()){
			    if(intervaloMenor.getMonto() == 1.0){
			    	identificador= "case0";
			    }else{
			    	throw new CommandException("Favor ingresar en el orden debido los intervalos, de menor cuantia a mayor");
			    }	
			 }else if(Compannia.intervalosRenta.size()==1){
			    if(intervaloMenor.getMonto()== 1.0){
			    	identificador= "case0";
			    }else{
			    	RangoRenta case0 = Compannia.intervalosRenta.get(0);
			    	
			    	if(case0.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
			    		identificador= "case1";
			    	}else{
			    		throw new CommandException("Los intervalos del case 0 y case 1 no concuerdad favor verificar");
			    	}
			    }  
			  }	else if(Compannia.intervalosRenta.size()== 2){
				  if(intervaloMenor.getMonto()== 1.0){
				    	identificador= "case0";
				    }else{
				    	RangoRenta case0 = Compannia.intervalosRenta.get(0);
				    	RangoRenta case1 = Compannia.intervalosRenta.get(1);
				    	
				    	if(case0.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
				    		identificador= "case1";
				    	}else if(case1.getIntervaloSuperior().getMonto()== intervaloMenor.getMonto()){
				    		identificador= "case2";
				    	}else{
				    		throw new CommandException("Los intervalos no concuerdad favor verificar");
				    	}
				    }     
			  }else if(Compannia.intervalosRenta.size()== 3){
				  if(intervaloMenor.getMonto()== 1.0){
				    	identificador= "case0";
				    }else{
				    	RangoRenta case0 = Compannia.intervalosRenta.get(0);
				    	RangoRenta case1 = Compannia.intervalosRenta.get(1);
				    	RangoRenta case2 = Compannia.intervalosRenta.get(2);
				    	
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
	 
	public static void cambioRangoRenta(String instancia, RangoRenta rango) throws CommandException {
			String key;
			Objecto value;
			Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();	
			while (iterator.hasNext()){
			    key = iterator.next();
			    value = tablaDeSimbolos.get(key);
			    boolean esUnaCompannia = value instanceof Compannia; 
			    if(esUnaCompannia){
			    	if(key.equalsIgnoreCase(instancia)){	  
				    	switch(rango.getIdentificador().toLowerCase()){
				    	case "case0":
				    		Compannia.intervalosRenta.add(0, rango);
				    		System.out.println("El intervalo se agrego exitosamente");
				    		break;
				    	case "case1":
				    		if(Compannia.intervalosRenta.get(0).getIntervaloInferior().getSign().equalsIgnoreCase(rango.getIntervaloInferior().getSign())){
					    		Compannia.intervalosRenta.add(1, rango);
					    		System.out.println("El intervalo se agrego exitosamente");
				    		}else{
				    		throw new CommandException("Imposible incluir rangos de diferente tipo de moneda");	
				    		}
				    		break;
				    	case "case2":
				    		if(Compannia.intervalosRenta.get(1).getIntervaloInferior().getSign().equalsIgnoreCase(rango.getIntervaloInferior().getSign())){
					    		Compannia.intervalosRenta.add(2, rango);
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
	public static void limpiaRangoRenta(String instancia){
		String key;
		Objecto value;
		Iterator<String> iterator = tablaDeSimbolos.keySet().iterator();
		ArrayList<RangoRenta> array = new ArrayList<RangoRenta>();
		while (iterator.hasNext()){
		    key = iterator.next();
		    value = tablaDeSimbolos.get(key);
		    boolean esUnaCompannia = value instanceof Compannia; 
		    if(esUnaCompannia){
		    	if(key.equalsIgnoreCase(instancia)){
		    		Compannia comp = (Compannia)value;
		    		array = comp.getIntervalosRenta();
		    		array.clear();
		    		comp.setIntervalosRenta(array);
		    		System.out.println("Se limpiaron todos los registros de los rangos de renta exitosamente");
		    		}
		    	}
		}
	}
	public static void cambioMontoConyugeHijo (String instancia, int montoConyuge, int montoHijo) {
		
		//Colaborador cola=getColaboradorPorInstancea(instancia);
			
	}	
	
	public static void limpiaListas() {
		tablaDeSimbolos.clear();	
	}
}//fin clase
