package com.ts.libraries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import com.ts.db.Repo;
import com.ts.main.Hacienda;
import com.ts.main.Sistema;

public class Colaborador extends Objecto {
	Hilera nombre;
	Hilera cedula;
	Fecha fechaNacimiento;
	Fecha fechaIngresoEmpresa;
	Boolean estadoCivil;
	Hilera telefono;
	Numeric cantidadHijos;
	Moneda salario;	
	
	private final static Double porcientoDeduccion= 0.0917;
	public ArrayList<Fecha> vacaciones = new ArrayList<Fecha>();
	public HashMap<Mes, Moneda> retencionesFuentes = new HashMap<Mes, Moneda>();
	
	public Colaborador(Hilera nombre, Hilera cedula, Fecha fechaNacimiento, Fecha fechaIngreso, Boolean estado, Hilera telefono,  Numeric numeroHijos, Dolar salario) {			
		this.nombre = nombre;
		this.cedula = cedula;
		this.fechaNacimiento= fechaNacimiento;
		this.fechaIngresoEmpresa= fechaIngreso;
		this.estadoCivil= estado;
		this.telefono= telefono;
		this.cantidadHijos= numeroHijos;
		this.salario = salario;		
		
	}

	public ArrayList<Fecha> getVacaciones() {
		return vacaciones;
	}

	public void setVacaciones(Fecha fecha) {
		vacaciones.add(fecha);
	}

	public Hilera getNombre() {
		return nombre;
	}

	public void setNombre(Hilera nombre) {
		this.nombre = nombre;
	}

	public Hilera getCedula() {
		return cedula;
	}

	public void setCedula(Hilera cedula) {
		this.cedula = cedula;
	}

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Fecha getFechaIngresoEmpresa() {
		return fechaIngresoEmpresa;
	}

	public void setFechaIngresoEmpresa(Fecha fechaIngresoEmpresa) {
		this.fechaIngresoEmpresa = fechaIngresoEmpresa;
	}

	public Boolean getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(Boolean estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Hilera getTelefono() {
		return telefono;
	}

	public void setTelefono(Hilera telefono) {
		this.telefono = telefono;
	}

	public Numeric getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(Numeric cantidadHijos) {
		this.cantidadHijos = cantidadHijos;
	}

	public Moneda getSalario() {
		return salario;
	}

	public void setSalario(Moneda nuevoSalario) {
		this.salario = nuevoSalario;
	}
	

	public static Colaborador get(Hilera cedula)
	{			
		ArrayList<Object> lista = Repo.get(Colaborador.class);
		for(Object exp : lista)
		{
			Colaborador comp = (Colaborador)exp;
			if(comp.getCedula().equals(cedula))
	    	{
	    		return comp;
	    	}
		}
		
		return null;
	}
	
	private static Compania getCommpanniaConBaseEnColaborador(Colaborador cola) throws CommandException{
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);

		    boolean esUnaCompannia = value instanceof Compania;
 
		    if(esUnaCompannia)
		    {
			    Compania comp = (Compania)value;
		    		for(Colaborador colabora : Compania.getListaColaboradores()){
		    			if(cola.getCedula().esIgual(colabora.getCedula())){
		    				return comp;
		    			}
		    		}
		    }
		}	
		return null;		
	}
	
	public static Colaborador getColaboradorEnCompannia(Compania comp, String instanciaColaborador) throws CommandException{
		Colaborador colaborador = getColaboradorPorInstancea(instanciaColaborador);	
		if(Compania.listaColaboradores.isEmpty()){
			return colaborador;
		}
		for(Colaborador cola : Compania.listaColaboradores){
			if(cola.getCedula().esIgual(colaborador.getCedula())){
				throw new CommandException("Colaborador ya se encuentra registrado en la compañia");
			}	
		}
		return colaborador;
	}

	public static void calculaRetencionFuente(String instancia, Mes fecha) throws CommandException{
		Colaborador colaborador;
		Compania compannia;
		RangoRenta case0;
		RangoRenta case1;
		RangoRenta case2;
		Moneda salarioBruto = null;
		Moneda montoConyugue=null;
		Moneda montoHijo=null;
		Moneda retencion;
		Moneda salario;
		Numeric cantidadHijos;
		Boolean estadoCivil;
		double montoRetencion=0.0;
		double porcientoAplicado;
		double excedente;
		String instanciaColaborador= instancia.toLowerCase();
		
		if(Hacienda.getMontoConyuge().show() != "" && Hacienda.getMontoHijo().show() != ""){
			montoConyugue= Hacienda.getMontoConyuge();
			montoHijo= Hacienda.getMontoHijo();
		}else{
			throw new CommandException("No se reconocen montos de retencion para hijos y conyugue favor ingresarlos primero");
		}
		if(Hacienda.intervalosRenta.size()==3){
			case0= Hacienda.intervalosRenta.get(0);
			case1= Hacienda.intervalosRenta.get(1);
			case2= Hacienda.intervalosRenta.get(2);
		}else{
			throw new CommandException("No se reconocen montos de los intervalos de renta favor ingresarlos primero");
		}
		if(Repo.tablaDeSimbolos.containsKey(instanciaColaborador)){ 
			
			colaborador= (Colaborador) Repo.tablaDeSimbolos.get(instanciaColaborador);
			compannia= getCommpanniaConBaseEnColaborador(colaborador);		
			salario= colaborador.getSalario();
			cantidadHijos= colaborador.getCantidadHijos();
			estadoCivil= colaborador.getEstadoCivil();
			
			if(compannia.getTipoMoneda().valor.equalsIgnoreCase("colon")){
				if(Moneda.isDolar(compannia.getTipoMoneda().valor)){
					double operacion= (salario.getMonto()* Sistema.tipoCambio);
					salarioBruto.setMonto(operacion);
				}else{
					salarioBruto= salario;
				}
				if(salarioBruto.getMonto()> case0.getIntervaloInferior().getMonto() && salarioBruto.getMonto() < case0.getIntervaloSuperior().getMonto()){
					porcientoAplicado=0.00;
					System.out.println("Porciento Aplicado: "+porcientoAplicado);   
					if(porcientoAplicado==0.0){
						montoRetencion = 0.0;  
					}else{
						montoRetencion = salarioBruto.getMonto()*porcientoAplicado;
					}			   
						System.out.println("La retencion del colaborador " + colaborador.getNombre() +" es de: "+salarioBruto.getSing().toString()+ montoRetencion);
						retencion = Sistema.getMoneda(salarioBruto.getSing().toString()+ montoRetencion);
						colaborador.retencionesFuentes.put(fecha, retencion);
						Repo.tablaDeSimbolos.put(instanciaColaborador, colaborador);
						
						System.out.println("Se calculo la retencion de manera exitosa");   					
					}else if(salarioBruto.getMonto()> case1.getIntervaloInferior().getMonto() && salarioBruto.getMonto() < case1.getIntervaloSuperior().getMonto()){
						double temp1 = case1.getIntervaloPorciento();
						double temp2 = salarioBruto.getMonto();
						
						porcientoAplicado=(temp1/100);
						System.out.println("Porciento Aplicado: "+porcientoAplicado); 
						temp1=case1.getIntervaloInferior().getMonto();	
						excedente = temp2- temp1;					   
						montoRetencion = excedente*porcientoAplicado;						   
						if(estadoCivil.esIgual(new Boolean(true))){
							montoRetencion=  montoRetencion - montoConyugue.getMonto();
							  if(montoRetencion < 0){	  
								  montoRetencion =0.0;
							  }
							System.out.println("Se aplica deduccion por estar casado: " + montoConyugue.getMonto());
						}else{
							System.out.println("El colaborador " + colaborador.getNombre() + " no registra conyugue");
						}
						if(cantidadHijos.valor>0){
							montoRetencion= montoRetencion - (cantidadHijos.valor * montoHijo.getMonto());
							System.out.println("Se aplica deduccion por poseer "+cantidadHijos + " hijo(s): " + (cantidadHijos.valor * montoHijo.getMonto()));
						}
						System.out.println("La retencion del colaborador " + colaborador.getNombre() +" es de: "+salarioBruto.getSing().toString()+ montoRetencion);
						retencion = Sistema.getMoneda(salarioBruto.getSing().toString()+ montoRetencion);
						colaborador.retencionesFuentes.put(fecha, retencion);
						Repo.tablaDeSimbolos.put(instanciaColaborador, colaborador);
						
						System.out.println("Se calculo la retencion de manera exitosa"); 
					   }else if(salarioBruto.getMonto()> case2.getIntervaloInferior().getMonto() && salarioBruto.getMonto() < case2.getIntervaloSuperior().getMonto()){
						   double temp3 = case2.getIntervaloPorciento();
						   double temp4 = salarioBruto.getMonto();
						   double temp5 = case2.getIntervaloInferior().getMonto();
						   
						   porcientoAplicado= (temp3/100);	
						   System.out.println("Porciento Aplicado: "+porcientoAplicado); 
						   excedente = temp4 - temp5;
						   montoRetencion = excedente*porcientoAplicado;
						   if(estadoCivil.esIgual(new Boolean(true))){
							  montoRetencion=  montoRetencion - montoConyugue.getMonto(); 
							  if(montoRetencion < 0){	  
								  montoRetencion =0.0;
							  }
							  System.out.println("Se aplica deduccion por estar casado: " + montoConyugue.getMonto());
						   }else{
								System.out.println("El colaborador " + colaborador.getNombre() + " no registra conyugue");
							}
						   if(cantidadHijos.valor>0){
							   montoRetencion= montoRetencion - (cantidadHijos.valor * montoHijo.getMonto());
								  if(montoRetencion < 0){	  
									  montoRetencion =0.0;
								  }
								  System.out.println("Se aplica deduccion por poseer "+cantidadHijos + " hijo(s): " + (cantidadHijos.valor * montoHijo.getMonto()));
						   }
						   System.out.println("La retencion del colaborador " + colaborador.getNombre() +" es de: "+salarioBruto.getSing().toString()+ montoRetencion);
						   retencion = Sistema.getMoneda(salarioBruto.getSing().toString()+ montoRetencion);
						   colaborador.retencionesFuentes.put(fecha, retencion);
						   Repo.tablaDeSimbolos.put(instanciaColaborador, colaborador);
						   
						   System.out.println("Se calculo la retencion de manera exitosa");   
					   }else{
						   throw new CommandException("Salario del colaborador no aplica a ningun rango de renta");
					   }	
			}else if(compannia.getTipoMoneda().valor.equalsIgnoreCase("dolar")){
				throw new CommandException("No se contempla la funcion de calcular retenciones a empresa con tipo de moneda en $"); 
						}
			   }else{
				   throw new CommandException("No se reconoce el colaborador. Imposible calcular retenciones");
			   }	
	}
	
	public static Colaborador getColaborador(String cedula){			
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
		    boolean esUnColaborador = value instanceof Colaborador; 
		    if(esUnColaborador)
		    {
		    	Colaborador cola = (Colaborador)value; 
		    	if(cola.getCedula().esIgual(new Hilera(cedula)))
		    	{
		    		return cola;
		    	}
		    }
		}	
		return null;
	}
	
	public static Colaborador getColaboradorPorInstancea(String instancia) throws CommandException{		
		String key;
		Objecto value;
		boolean esxisteInstancea=false;
		Colaborador cola=null;
		
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
		    boolean esUnColaborador = value instanceof Colaborador; 
		    
		    if(esUnColaborador)
		    {		    	
		    	if(key.equalsIgnoreCase(instancia))
		    	{
		    		esxisteInstancea=true;
		    		cola = (Colaborador)value; 
		    	}
		    }
		}
		if(!esxisteInstancea){
			throw new CommandException("La instancia " + instancia+ " no existe.");			
		}
		return cola;
	}
	
	public static int getTamannoColaborador(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.tablaDeSimbolos.keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.tablaDeSimbolos.get(key);
		    boolean esUnColaborador= value instanceof Colaborador; 
		    if(esUnColaborador)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	
	public static Moneda mostrarRetencionesFuente (String instancea, String mesAnnioActual, String mesAnnio) throws CommandException {		
		
		double monto=0.0;
		Moneda montoRetencionFuente=Sistema.getMoneda("¢"+monto);
		boolean montoExiste=false;		
		Mes key;
		Objecto value;
		
		String temp []=mesAnnioActual.split("/");
		mesAnnioActual=temp [1];
		mesAnnioActual+=temp[2];		
		
		String mesAnnioParam=Sistema.getFechaFormatoRetencion(mesAnnio);
		
		temp=mesAnnioParam.split("/");		
		String mesAnnioRetencionFuente=temp[0];
		mesAnnioRetencionFuente+=temp[1];		
			
		if (Integer.parseInt(mesAnnioRetencionFuente) > Integer.parseInt(mesAnnioActual)){
			throw new CommandException("El periodo consultado : " +mesAnnioParam+ " es superior a la fecha actual.");
		}	
		
		if (mesAnnioRetencionFuente.equals(mesAnnioActual)){			
			throw new CommandException("No se pude brindar el dato ya que el periodo consultado es el actual : " +mesAnnio +" y el mismo no ha finalizado.");
		}
		
		if (Integer.parseInt(mesAnnioRetencionFuente) < Integer.parseInt(mesAnnioActual)){			
			Colaborador cola = getColaboradorPorInstancea(instancea);			
			boolean existeAlgunaRetencionFuente = ! cola.retencionesFuentes.isEmpty();		
			
			if(!existeAlgunaRetencionFuente){
				throw new CommandException("El colaborador : " +instancea+ " no posee retenciones fuente.");
			}	
			Iterator<Mes> iterator = cola.retencionesFuentes.keySet().iterator();

			while (iterator.hasNext()) {
			    key = iterator.next();
			    value = cola.retencionesFuentes.get(key);
			    boolean esUnaMoneda = value instanceof Moneda; 
			    if(esUnaMoneda)
			    {
			    	if(key.getMesAnno().equalsIgnoreCase(""+Integer.parseInt(mesAnnioRetencionFuente)))
			    	{
			    		montoRetencionFuente = (Moneda)value; 
			    		montoExiste=true;	
			    	}
			    }
			}		
		}
		if(!montoExiste){
			throw new CommandException("El colaborador : " +instancea+ " no posee retenciones fuente, para el periodo: "+mesAnnioParam);	
		}
		System.out.println("El monto de la retencion fuente para el colaborador "+instancea +" es de: "+montoRetencionFuente.getMonto());
		return montoRetencionFuente;
	}
		
	public static String mostrarSalario(String persona) throws CommandException{
	Colaborador colaboradorEncontrado= null;
	
	   if(Repo.tablaDeSimbolos.containsKey(persona)){   
		   colaboradorEncontrado= (Colaborador) Repo.tablaDeSimbolos.get(persona);
		   return("El Colaborador: " + colaboradorEncontrado.getNombre() + " posee un salario de " + colaboradorEncontrado.getSalario().getSing() +colaboradorEncontrado.getSalario().getMonto());
	   }else{
		   throw new CommandException("El Colaborador " + persona + " no existe.");
	   }				
}
	
	public static void aumentarSalario(String persona, Moneda nuevoSalario) throws CommandException{
		Colaborador colaborador= null;
			   if(Repo.tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) Repo.tablaDeSimbolos.get(persona);
		
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
		   Repo.tablaDeSimbolos.put(persona, colaborador);
		   System.out.println("El Colaborador: " + colaborador.getNombre() + " se le aumento exitosamente su salario");
	} 
	
	public void save(Hilera variableInstancia){
		
		boolean existeElColaborador = get(this.getCedula()) != null;		

			if ( existeElColaborador )
			{
				throw new CommandException("El colaborador " + nombre + ", ya tiene el numero de cedula "+this.getCedula());
			}else if(estadoCivil.esIgual(new Hilera("N/A"))){
				throw new CommandException("No se reconoce el estado civil del colaborador, digite true si esta casado, false en caso contrario");
			}else if(salario == null){
				throw new CommandException("No se reconoce tipo de moneda del salario favor digitar $ o ¢ segun corresponda");
			}
						
		    Repo.save(variableInstancia, this);
		    
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");	
	}
	
	public static void tomarVacaciones(String persona, Fecha fecha) throws CommandException{
		Colaborador colaborador= null;
			   if(Repo.tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) Repo.tablaDeSimbolos.get(persona);		    
				   colaborador.setVacaciones(fecha);
				   Repo.tablaDeSimbolos.put(persona, colaborador);
				   System.out.println("El Colaborador: " + colaborador.getNombre() + " se le agrego un dia de vacaciones");
			   }else{
					throw new CommandException("El Colaborador " + persona + " no existe.Imposible modificar sus vacaciones");
			   }  
	}
	
	public static String mostrarVacaciones(String persona) throws CommandException{
		Colaborador colaboradorEncontrado= null;
		String mens ="El colaborador presenta las siguientes fechas de vacaciones :" + "\n";
		ArrayList<Fecha> fechasAlmacenadas;
		int cont =1;
			   if(Repo.tablaDeSimbolos.containsKey(persona)){   
				   colaboradorEncontrado= (Colaborador) Repo.tablaDeSimbolos.get(persona);
				   fechasAlmacenadas = colaboradorEncontrado.getVacaciones();
				  if(fechasAlmacenadas.size()<=0){
					  throw new CommandException("El Colaborador " + persona + " no posee dias de vacaciones.");
				  }else{
					   for( Fecha fecha : fechasAlmacenadas){
						   mens+= "\t" +cont+". "+ fecha.show() + "\n"; 
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
			   if(Repo.tablaDeSimbolos.containsKey(persona)){   
				   colaborador= (Colaborador) Repo.tablaDeSimbolos.get(persona);
				   salarioInicial= colaborador.getSalario();
				   monto= salarioInicial.getMonto();
				   deducciones = monto * porcientoDeduccion;
				   neto= monto-deducciones; 

				   salario += colaborador.getSalario().getSing();
				   salario += monto;
				   deduc += colaborador.getSalario().getSing();
				   deduc+= deducciones;
				   net += colaborador.getSalario().getSing();
				   net+= neto;
			   
			   }else{
				   throw new CommandException("El Colaborador " + persona + " no existe.Imposible Calcular su primera quincena");
			   }       
		   return("El Colaborador: " + colaborador.getNombre() + " presenta el siguiente salario por concepto de primera quincena:" 
			   + "\n" + "\t" + "1.Salario Inicial: " + salario 
			   + "\n" + "\t" + "2.Deducciones: " + deduc
			   + "\n" + "\t" + "3.Salario Neto: " + net);
	}
	
	protected static void validarInstaciaEnLaTablaDeSimbolos(String instancia) throws CommandException  {
		
		boolean existeAlgunaInstancea = ! Repo.tablaDeSimbolos.isEmpty();
		if(existeAlgunaInstancea){
			if(Repo.tablaDeSimbolos.containsKey(instancia)){			
				throw new CommandException("La instancia " + instancia+ " ya existe, cambiela por una diferente.");				
			}					
		}				
	}	
	
	public static String cantidadVacacionesDisponibles(Hilera instancia, Fecha fecha) throws CommandException{
		
		int vacacionesDisponibles=0;
		int cantidadVacacionesTomados;
		String mensaje;	
		Fecha fechacomando=null;
		
				
		 Colaborador cola=get(instancia);
		 Fecha fechaIngTem1=cola.getFechaIngresoEmpresa();
				 
		 int cantidadDiasLaborados= Fecha.cantidadDiasEntreFechas(fechaIngTem1, fechacomando)+1;		 
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
	
	public static String cantidadVacacionesLiquidacion(Hilera instancia, Fecha fecha) throws CommandException{
		
		int vacacionesDisponiblesLiquidacion=0;
		int cantidadVacacionesTomadosLiquidacion=0;
		String mensajeliquidacion="";	
		
		Colaborador cola=get(instancia);
		Fecha fechaIngTem1=cola.getFechaIngresoEmpresa();		
		
		
		int x= fechaIngTem1.getAnno() * 12 + fechaIngTem1.getMes();
		int y= fecha.getAnno() * 12 + fecha.getMes();		
		
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
	
	
}//fin clase

