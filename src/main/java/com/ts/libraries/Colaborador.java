package com.ts.libraries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	Moneda salarioBruto = null;
	
	private final static Double porcientoDeduccion= 0.0917;
	public ArrayList<Fecha> vacaciones = new ArrayList<Fecha>();
	public HashMap<Mes, Moneda> retencionesFuentes = new HashMap<Mes, Moneda>();
	public HashMap<Mes, Moneda> salariosNetosPrimeraQuincena = new HashMap<Mes, Moneda>();
	public HashMap<Mes, Moneda> salariosNetosSegundaQuincena = new HashMap<Mes, Moneda>();
	
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
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData().get(key);

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
	
	public static Colaborador getColaboradorEnCompannia(Compania comp, Hilera instanciaColaborador) throws CommandException{
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

	public void calculaRetencionFuente(Hilera instancia, Mes fecha) throws CommandException{

		Colaborador colaborador;
		Compania compannia;
		RangoRenta case0;
		RangoRenta case1;
		RangoRenta case2;
		Moneda montoConyugue=null;
		Moneda montoHijo=null;
		Moneda retencion;
		Moneda salario;
		Numeric cantidadHijos;
		Boolean estadoCivil;
		double montoRetencion=0.0;
		double porcientoAplicado;
		double excedente;
		Hilera instanciaColaborador= instancia;
		
		if(Compania.getMontoConyuge().show() != "" && Compania.getMontoHijo().show() != ""){
			montoConyugue= Compania.getMontoConyuge();
			montoHijo= Compania.getMontoHijo();
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
			colaborador= (Colaborador) Repo.getData(instancia);
			compannia= getCommpanniaConBaseEnColaborador(colaborador);		
			salario= colaborador.getSalario();
			cantidadHijos= colaborador.getCantidadHijos();
			estadoCivil= colaborador.getEstadoCivil();
			
			if(compannia.getTipoMoneda().getValor().equalsIgnoreCase("colon")){
				if(Moneda.isDolar(new Hilera(compannia.getTipoMoneda().getValor()))){
					Decimal operacion= new Decimal(salario.getMonto().getValor() * Sistema.tipoCambio);
					salarioBruto.setMonto(operacion);
				}else{
					salarioBruto= salario;
				}
				if(salarioBruto.getMonto().getValor() > case0.getIntervaloInferior().getMonto().getValor()  && salarioBruto.getMonto().getValor()  < case0.getIntervaloSuperior().getMonto().getValor() ){
					porcientoAplicado=0.00;
					System.out.println("Porciento Aplicado: "+porcientoAplicado);   
					if(porcientoAplicado==0.0){
						montoRetencion = 0.0;  
					}else{
						montoRetencion = salarioBruto.getMonto().getValor() * porcientoAplicado;
					}			   
						System.out.println("La retencion del colaborador " + colaborador.getNombre() +" es de: "+salarioBruto.getSing().toString()+ montoRetencion);
						retencion = Sistema.getMoneda(new Hilera(salarioBruto.getSing().toString()+ montoRetencion));
						colaborador.retencionesFuentes.put(fecha, retencion);
						Repo.save(instanciaColaborador, colaborador);
						
						System.out.println("Se calculo la retencion de manera exitosa");   					
					}else if(salarioBruto.getMonto().getValor() > case1.getIntervaloInferior().getMonto().getValor()  && salarioBruto.getMonto().getValor()  < case1.getIntervaloSuperior().getMonto().getValor() ){
						double temp1 = case1.getIntervaloPorciento().getValor();
						double temp2 = salarioBruto.getMonto().getValor();
						
						porcientoAplicado=(temp1/100);
						System.out.println("Porciento Aplicado: "+porcientoAplicado); 
						temp1=case1.getIntervaloInferior().getMonto().getValor();	
						excedente = temp2- temp1;					   
						montoRetencion = excedente*porcientoAplicado;						   
						if(estadoCivil.esIgual(new Boolean(true))){
							montoRetencion=  montoRetencion - montoConyugue.getMonto().getValor();
							  if(montoRetencion < 0){	  
								  montoRetencion =0.0;
							  }
							System.out.println("Se aplica deduccion por estar casado: " + montoConyugue.getMonto());
						}else{
							System.out.println("El colaborador " + colaborador.getNombre() + " no registra conyugue");
						}
						if(cantidadHijos.getValor()>0){
							montoRetencion= montoRetencion - (cantidadHijos.getValor() * montoHijo.getMonto().getValor());
							System.out.println("Se aplica deduccion por poseer "+cantidadHijos + " hijo(s): " + (cantidadHijos.getValor() * montoHijo.getMonto().getValor()));
						}
						System.out.println("La retencion del colaborador " + colaborador.getNombre() +" es de: "+salarioBruto.getSing().toString()+ montoRetencion);
						retencion = Sistema.getMoneda(new Hilera(salarioBruto.getSing().toString()+ montoRetencion));
						colaborador.retencionesFuentes.put(fecha, retencion);
						Repo.save(instanciaColaborador, colaborador);

						
						System.out.println("Se calculo la retencion de manera exitosa"); 
					   }else if(salarioBruto.getMonto().getValor() > case2.getIntervaloInferior().getMonto().getValor() && salarioBruto.getMonto().getValor() < case2.getIntervaloSuperior().getMonto().getValor()){
						   double temp3 = case2.getIntervaloPorciento().getValor();
						   double temp4 = salarioBruto.getMonto().getValor();
						   double temp5 = case2.getIntervaloInferior().getMonto().getValor();
						   
						   porcientoAplicado= (temp3/100);	
						   System.out.println("Porciento Aplicado: "+porcientoAplicado); 
						   excedente = temp4 - temp5;
						   montoRetencion = excedente*porcientoAplicado;
						   if(estadoCivil.esIgual(new Boolean(true))){
							  montoRetencion=  montoRetencion - montoConyugue.getMonto().getValor(); 
							  if(montoRetencion < 0){	  
								  montoRetencion =0.0;
							  }
							  System.out.println("Se aplica deduccion por estar casado: " + montoConyugue.getMonto());
						   }else{
								System.out.println("El colaborador " + colaborador.getNombre() + " no registra conyugue");
							}
						   if(cantidadHijos.getValor()>0){
							   montoRetencion= montoRetencion - (cantidadHijos.getValor() * montoHijo.getMonto().getValor());
								  if(montoRetencion < 0){	  
									  montoRetencion =0.0;
								  }
								  System.out.println("Se aplica deduccion por poseer "+cantidadHijos + " hijo(s): " + (cantidadHijos.getValor() * montoHijo.getMonto().getValor()));
						   }
						   System.out.println("La retencion del colaborador " + colaborador.getNombre() +" es de: "+salarioBruto.getSing().toString()+ montoRetencion);
						   retencion = Sistema.getMoneda(new Hilera(salarioBruto.getSing().toString()+ montoRetencion));
						   colaborador.retencionesFuentes.put(fecha, retencion);
						   Repo.save(instanciaColaborador, colaborador);
						   
						   System.out.println("Se calculo la retencion de manera exitosa");   
					   }else{
						   throw new CommandException("Salario del colaborador no aplica a ningun rango de renta");
					   }	
			}else if(compannia.getTipoMoneda().getValor().equalsIgnoreCase("dolar")){
				throw new CommandException("No se contempla la funcion de calcular retenciones a empresa con tipo de moneda en $"); 
			}	
	}
	
	public static Colaborador getColaborador(Hilera cedula){			
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnColaborador = value instanceof Colaborador; 
		    if(esUnColaborador)
		    {
		    	Colaborador cola = (Colaborador)value; 
		    	if(cola.getCedula().esIgual(cedula))
		    	{
		    		return cola;
		    	}
		    }
		}	
		return null;
	}
	

	public static Colaborador getColaboradorPorInstancea(Hilera instanciaColaborador) throws CommandException{		
		String key;
		Objecto value;
		boolean esxisteInstancea=false;
		Colaborador cola=null;
		
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnColaborador = value instanceof Colaborador; 
		    
		    if(esUnColaborador)
		    {		    	
		    	if(key.equals(instanciaColaborador))
		    	{
		    		esxisteInstancea=true;
		    		cola = (Colaborador)value; 
		    	}
		    }
		}
		if(!esxisteInstancea){
			throw new CommandException("La instancia " + instanciaColaborador+ " no existe.");			
		}
		return cola;
	}
	
	public int getTamannoColaborador(){
		int total = 0;
		String key;
		Objecto value;
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData(key);
		    boolean esUnColaborador= value instanceof Colaborador; 
		    if(esUnColaborador)
		    {
		    	total++;
		    }
		}	
		return total;
	}
	
	public Moneda mostrarRetencionesFuente (Hilera instancea, Fecha mesAnnioActualP, Mes mesAnnio) throws CommandException {			
		double monto=0.0;
		Moneda montoRetencionFuente=Sistema.getMoneda(new Hilera("¢"+monto));
		boolean montoExiste=false;		
		Mes key;
		Objecto value;
		Hilera mesAnnioActual;
		
		mesAnnioActual = new Hilera(""+mesAnnioActualP.getMes()+mesAnnioActualP.getAnno());
		Numeric valorMesAnnoActual = new Numeric(Integer.parseInt(mesAnnioActual.getValor()));
		
		Numeric valorMesAnnioRetencionFuente = mesAnnio.getMesAnno();
		
		if (valorMesAnnioRetencionFuente.getValor() > valorMesAnnoActual.getValor()){
			throw new CommandException("El periodo consultado : " + mesAnnio.getMes() + "/"+ mesAnnio.getAnno() +" es superior a la fecha actual.");
		}	
		
		if (valorMesAnnioRetencionFuente == valorMesAnnoActual){			
			throw new CommandException("No se pude brindar el dato ya que el periodo consultado es el actual : " +mesAnnio.getMes() + "/"+ mesAnnio.getAnno() +" y el mismo no ha finalizado.");
		}
		
		if (valorMesAnnioRetencionFuente.getValor()  < valorMesAnnoActual.getValor()){			
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
			    	if(key.getMesAnno()== valorMesAnnioRetencionFuente)
			    	{
			    		montoRetencionFuente = (Moneda)value; 
			    		montoExiste=true;	
			    	}
			    }
			}		
		}
		if(!montoExiste){
			throw new CommandException("El colaborador : " +instancea+ " no posee retenciones fuente, para el periodo: "+ mesAnnio.getMes() + "/"+ mesAnnio.getAnno() );	
		}
		System.out.println("El monto de la retencion fuente para el colaborador "+instancea +" es de: "+montoRetencionFuente.getMonto());
		return montoRetencionFuente;
	}
		
	public String mostrarSalario(String persona) throws CommandException{
	Colaborador colaboradorEncontrado= null;
	
	   if(Repo.getData().containsKey(persona)){   
		   colaboradorEncontrado= (Colaborador) Repo.getData(persona);
		   return("El Colaborador: " + colaboradorEncontrado.getNombre() + " posee un salario de " + colaboradorEncontrado.getSalario().getSing() +colaboradorEncontrado.getSalario().getMonto());
	   }else{
		   throw new CommandException("El Colaborador " + persona + " no existe.");
	   }				
}
	public void aumentarSalario(Hilera variableInstancia, Moneda nuevoSalario) throws CommandException{
		Colaborador colaborador= (Colaborador) Repo.getData(variableInstancia);
		colaborador.setSalario(nuevoSalario);
		Repo.save(variableInstancia, colaborador);
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
	
	public void agregarVacaciones(Fecha fecha){
			vacaciones.add(fecha);
	}
	
	public Hilera mostrarVacaciones(Hilera variableInstancia){
		Colaborador colaboradorEncontrado= null;
		String mens = "El colaborador presenta las siguientes fechas de vacaciones :" + "\n";
		ArrayList<Fecha> fechasAlmacenadas;
		int cont =1;  
			colaboradorEncontrado= (Colaborador) Repo.getData(variableInstancia);
			fechasAlmacenadas = colaboradorEncontrado.getVacaciones();
			if(fechasAlmacenadas.size()<=0){
				throw new CommandException("El Colaborador " + variableInstancia + " no posee dias de vacaciones.");
			}else{
				for( Fecha fecha : fechasAlmacenadas){
					mens += "\t" +cont+". "+ fecha.show() + "\n"; 
					cont++;
				}		   
			}
		return new Hilera(mens);	

}
	public void calcularSalarioNetoPrimeraQuincena(Hilera instancia, Mes mesAnnio) throws CommandException {		
		
		Colaborador colaborador=null;
		Moneda salarioInicial;
		Double deducciones;
		Double monto;
		Double neto;
		
		colaborador= Colaborador.getColaboradorPorInstancea(instancia);
		salarioInicial= colaborador.getSalario();
		monto= salarioInicial.getMonto().getValor();
		monto=monto/2;
		deducciones = monto * porcientoDeduccion;
		neto= monto-deducciones;
				 				   
		Moneda salarioNetoIQuincena=Sistema.getMoneda(new Hilera(salarioInicial.getSing().toString()+neto));				   			   
		colaborador.salariosNetosPrimeraQuincena.put(mesAnnio,salarioNetoIQuincena);		
		Repo.save(instancia, colaborador);
		System.out.println("El salario neto de la primera quincena para el periodo : "+mesAnnio.getMes()+"/"+mesAnnio.getAnno()+ " del colaborador: " + instancia + " se agrego exitosamente.");
	}		
	public Hilera  mostrarSalarioNetoPrimeraQuincena(Hilera instanciaColaborador, Mes mesAnnio) throws CommandException {		
	
	 Moneda salarioNetoIQuincena;
	 Hilera mensaje=new Hilera("");
	 Mes key;
	 Objecto value;	 	 
	 Colaborador colaborador = Colaborador.getColaboradorPorInstancea(instanciaColaborador);
	 boolean existeAlgunSalario = ! colaborador.salariosNetosPrimeraQuincena.isEmpty();
		 
		 if(!existeAlgunSalario){				 
			 throw new CommandException("El Colaborador " + instanciaColaborador + " no registra salario.Imposible mostrar salario alguno.");						 
		   }
		 	Iterator<Mes> iterator = colaborador.salariosNetosPrimeraQuincena.keySet().iterator();
		 	while (iterator.hasNext()) {
			    key = iterator.next();
			    value = colaborador.salariosNetosPrimeraQuincena.get(key);
			    boolean esUnaMoneda = value instanceof Moneda; 
			    if(esUnaMoneda)
			    {
			    	if(key.getMesAnno() == mesAnnio.getMesAnno())
			    	{			    		
			    		salarioNetoIQuincena = (Moneda)value; 
				    	mensaje.setValor("El colaborador : "+instanciaColaborador+ " para la primera quiencena del periodo : "+mesAnnio.getMes()+"/"+mesAnnio.getAnno()+ " posee el siguiente salario neto : "+salarioNetoIQuincena.getMonto());		    			    		
			    	}		    	
			    }
			}		   
	 return mensaje;
	}	
	
	public void calcularSalarioNetoSegundaQuincena(Hilera instanciaColaborador, Mes mesAnnio) throws CommandException {
		
		Colaborador colaborador= null;
		Moneda salarioInicial;
		Double deducciones;
		Double monto;
		Double neto;
		Mes key;
		Objecto value;
			
			colaborador= Colaborador.getColaboradorPorInstancea(instanciaColaborador);			
			salarioInicial= colaborador.getSalario();
			
			 monto= salarioInicial.getMonto().getValor();
			 monto=monto/2;
			 deducciones = monto * porcientoDeduccion;
			 
			 boolean existeAlgunaRetencionFuente = ! colaborador.retencionesFuentes.isEmpty();
			 
			 if(!existeAlgunaRetencionFuente){
					throw new CommandException("El calculo de la retencion fuente para el periodo : "+mesAnnio.getMes()+"/"+mesAnnio.getAnno()+ " del Colaborador " + instanciaColaborador + " no existe, por favor realizar dicho calculo antes del calculo para el salario neto. ");
				}	
			 Iterator<Mes> iterator = colaborador.retencionesFuentes.keySet().iterator();
			 
			 while (iterator.hasNext()) {
				 
				 key = iterator.next();
				 value = colaborador.retencionesFuentes.get(key);
				 boolean esUnaMoneda = value instanceof Moneda;
				 
				 if(esUnaMoneda) {
					 			   
				    if(key.getMesAnno() == mesAnnio.getMesAnno())
				    	{
				    		Moneda montoRF = (Moneda)value; 
				    		Double montoRetencionFuente=montoRF.getMonto().getValor();
				    		neto= monto-deducciones;
							neto=neto-montoRetencionFuente;
			 				   
							 Moneda salarioNetoIIQuincena=Sistema.getMoneda(new Hilera(salarioInicial.getSing().toString()+neto));
							 colaborador.salariosNetosSegundaQuincena.put(mesAnnio,salarioNetoIIQuincena);	
							 Repo.save(instanciaColaborador, colaborador);
							 System.out.println("El salario neto de la segunda quincena para el periodo : "+mesAnnio.getMes()+"/"+mesAnnio.getAnno()+ " del colaborador: " + instanciaColaborador + " se agrego exitosamente.");								    		
				    	}				    	
				    }
				}			 	
	}	
	
	public Hilera  mostrarSalarioNetoSegundaQuincena(Hilera instanciaColaborador, Mes mesAnnio) throws CommandException {		
		
		Moneda salarioNetoIIQuincena;
		Hilera mensaje= new Hilera("");
		Mes key;
		Objecto value;		
		
		Colaborador colaborador = Colaborador.getColaboradorPorInstancea(instanciaColaborador);
		boolean existeAlgunSalario = ! colaborador.salariosNetosSegundaQuincena.isEmpty();
			   
		if(!existeAlgunSalario){				 
			throw new CommandException("El Colaborador " + instanciaColaborador + " no registra salario.Imposible mostrar salario alguno.");						 
		}			  
			Iterator<Mes> iterator = colaborador.salariosNetosSegundaQuincena.keySet().iterator();
			 	while (iterator.hasNext()) {
				    key = iterator.next();
				    value = colaborador.salariosNetosSegundaQuincena.get(key);
				    boolean esUnaMoneda = value instanceof Moneda; 
				    if(esUnaMoneda)
				    {
				    	if(key.getMesAnno() == mesAnnio.getMesAnno())
				    	{
				    		salarioNetoIIQuincena = (Moneda)value; 
					    	mensaje.setValor("El colaborador : "+instanciaColaborador+ " para la segunda quiencena del periodo : "+mesAnnio.getMes()+"/"+mesAnnio.getAnno()+ " posee el siguiente salario neto : "+salarioNetoIIQuincena.getMonto());		    		
				    	}			    	
				    }
				}		 
		 return mensaje;
	}	
	
	public Hilera cantidadVacacionesDisponibles(Hilera instancia, Fecha fecha){
		  
		  Numeric vacacionesDisponibles= new Numeric(0);
		  Numeric cantidadVacacionesTomados= new Numeric(0);
		  Hilera mensaje= new Hilera("");  
		  Date fechaIngreso=null;
		  Date fechaFin=null;
		  
		  Calendar cal1 = new GregorianCalendar();
		  Calendar cal2 = new GregorianCalendar();
		    
		   Colaborador cola=(Colaborador) Repo.getData(instancia);
		   Fecha fechaIngTemp=cola.getFechaIngresoEmpresa();
		   
		   fechaIngreso=fecha.covertFechaADate(fechaIngTemp);     
		   cal1.setTime(fechaIngreso); 
		  
		   fechaFin=fecha.covertFechaADate(fecha);   
		   cal2.setTime(fechaFin); 
		   
		   
		   Numeric cantidadDiasLaborados= fecha.cantidadDiasEntreFechas(cal1.getTime(), cal2.getTime()) ;
		   
		   float diviEntresiete=cantidadDiasLaborados.getValor()/7;  
		   float diviEntreCincuenta=diviEntresiete/50;   
		   float  multEntrediez=diviEntreCincuenta * 10 ;  
		   
		  vacacionesDisponibles.setValor((int)multEntrediez);
		    
		  if (!cola.getVacaciones().isEmpty()){
		   cantidadVacacionesTomados.setValor(cola.getVacaciones().size()); 
		   vacacionesDisponibles.setValor(vacacionesDisponibles.getValor() - cantidadVacacionesTomados.getValor()); 
		  }  
		  
		  System.out.println("cantidad de vacaciones  : " + vacacionesDisponibles.getValor());
		  if(vacacionesDisponibles.getValor() > 0 )
		   mensaje.setValor("Cantidad de vacaciones disponibles para "+instancia+" son: " +vacacionesDisponibles);
		  else{
		   if(vacacionesDisponibles.getValor() < 0 )    
		    mensaje.setValor("El colaborador "+instancia+" no tiene dias de vacaciones disponibles, por el contrario debe la siguiente cantidad de vacaciones: "+ vacacionesDisponibles.getValor() * -1);   
		   else
		    mensaje.setValor("El colaborador "+instancia+" no tiene dias de vacaciones disponibles.");
		  }  
		  System.out.println("Fecha  : " + mensaje+ " \n");
		  return  mensaje;
		  }
	
	public  Hilera cantidadVacacionesLiquidacion(Hilera instancia, Fecha fecha) throws CommandException{
		  
		  Numeric vacacionesDisponiblesLiquidacion= new Numeric(0);
		  Numeric cantidadVacacionesTomadosLiquidacion= new Numeric(0);
		  Hilera mensajeliquidacion= new Hilera(""); 
		  
		  Colaborador cola=(Colaborador) Repo.getData(instancia);
		  Fecha fechaIngTem1=cola.getFechaIngresoEmpresa();  
		    
		  Numeric valorRepresentativoFechaInicio=  new Numeric(fechaIngTem1.getAnno() * 12 + fechaIngTem1.getMes());
		  Numeric ValorRepresentativoFechaFin=  new Numeric( fecha.getAnno() * 12 + fecha.getMes());  

		  vacacionesDisponiblesLiquidacion.setValor(ValorRepresentativoFechaFin.getValor() - (valorRepresentativoFechaInicio.getValor() + 1)+1);

		  if (!cola.getVacaciones().isEmpty()){
		   cantidadVacacionesTomadosLiquidacion.setValor(cola.getVacaciones().size()); 
		    vacacionesDisponiblesLiquidacion.setValor(vacacionesDisponiblesLiquidacion.getValor() - cantidadVacacionesTomadosLiquidacion.getValor()); 
		  }      
		  
		  if(vacacionesDisponiblesLiquidacion.getValor() > 0 )
		   mensajeliquidacion.setValor("Cantidad de vacaciones disponibles para "+instancia+" son: " +vacacionesDisponiblesLiquidacion);
		  else
		   if(vacacionesDisponiblesLiquidacion.getValor() < 0 )    
		    mensajeliquidacion.setValor("El colaborador "+instancia+" no tiene dias de vacaciones disponibles, por el contrario debe la siguiente cantidad de vacaciones: "+ vacacionesDisponiblesLiquidacion.getValor() * -1);   
		   else
		    mensajeliquidacion.setValor("El colaborador "+instancia+" no tiene dias de vacaciones disponibles.");
		  System.out.println(" : " + mensajeliquidacion);
		  return  mensajeliquidacion;
		  }
	
	public int getCantidadHijos(String cantidad) throws CommandException {
		if(cantidad.contains("/") || cantidad.contains("$") || cantidad.contains("¢") || cantidad.equalsIgnoreCase("")){
			throw new CommandException("No se reconocen caracteres especiales en el monto");
		}	
		int canti = Integer.parseInt(cantidad);	
		if(canti < 0){
			throw new CommandException("No se reconoce una cantidad valida para los hijos del colaborador");				
		}
		return canti;
	}
	
	public void actualizarCantidadHijos(Hilera instanciaColaborador, Numeric cantidad){
		Colaborador cola = getColaboradorPorInstancea(instanciaColaborador);
		boolean existeColaborador = cola != null;
		if(existeColaborador){
			cola.setCantidadHijos(cantidad);
			System.out.println("Se actualizo la cantidad de hijos del colaborador " + cola.getNombre() + " a " + cantidad + " hijos.");
		}
	}
	
	public void actualizarEstadoCivil(Hilera instanciaColaborador, Boolean estado){
		Colaborador cola = getColaboradorPorInstancea(instanciaColaborador);		
		boolean existeColaborador = cola != null;
		if(existeColaborador){
			cola.setEstadoCivil(estado);
			System.out.println("Se actualizo el estado civil del colaborador " + cola.getNombre() + " a " + estado);
		}	
	}
	
	public void agregaColaborardorCompannia(Hilera variableInstancia, Hilera instanciaColaborador) throws CommandException{
		Compania compania = Compania.getCompanniaPorInstancea(variableInstancia);
		Colaborador colaborador = Colaborador.getColaboradorEnCompannia(compania, instanciaColaborador);
		
		boolean revisarSiElColaboradorYaSeAgrego = colaborador != null;
		if(revisarSiElColaboradorYaSeAgrego){
			Compania.listaColaboradores.add(colaborador);	
			System.out.println("La Colaborador: " + colaborador.getNombre() + " se agrego exitosamente a la campañia " + compania.getNombre());	
		}else{
			throw new CommandException("El colaborador ya se agrego a esta compañia");
		}				
	}
}//fin clase

