package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ts.objects.Colon;
import com.ts.objects.CommandException;
import com.ts.objects.Dolar;
import com.ts.objects.Moneda;

public class InterpreteMandatos {	
		
	 	private ArchivoLog log;
        private boolean esCargaDeDatos= false; 
        private BufferedReader br;
        private boolean listenTheConsole;
	 	
        private InterpreteMandatos() throws IOException
	 	{
	 		new InterpreteMandatos(true, ArchivoLog.LOG_NAME);

	 	}	 	
	 	public InterpreteMandatos(boolean listenTheConsole, String filePath) throws IOException
	 	{
	 		this.listenTheConsole=listenTheConsole;
	 		if(listenTheConsole){
	 			
	 			InputStreamReader isr = new InputStreamReader(System.in);
	 			 br = new BufferedReader(isr);
	 		}
	 		
	 		log = new ArchivoLog(filePath);
	 		if(log.existeUnLogPrevio()){
				esCargaDeDatos=true;
				int contador=0;
				ArrayList<String> comandos = log.getAllCommandsFromLog();
	            for(String comando : comandos)
	            {   	
	            	ejecutaComando(comando);
	            	contador++;
	            	if(contador==comandos.size()){
	            		System.out.println("Se realizo el proceso de carga exitosamente");
	            		esCargaDeDatos=false;
	            	}
	            }
			}	 		
			pedirComando ();
	 	}
	 
	 	public Moneda getSalario(String dato) throws CommandException{
	 		Moneda salario=null;
	 		double monto;
	 		
	 		monto= Double.parseDouble(dato.substring(1, dato.length()));
	 		
			if(dato.contains("¢")){
				salario= new Colon(monto);
			}else if(dato.contains("$")){
				salario= new Dolar(monto);
			}else{
				throw new CommandException("Solo se aceptan montos en $ o ¢, Favor revisarlo monto digitado");
			}
	 		return salario;
	 	}
	 	
	 public Date getFecha(String dato) throws CommandException{
		 Date fecha;		 
		  try {
				 SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
				 String[] fechasTemp = dato.split("/");
				 int dia = Integer.parseInt(fechasTemp[0]);
				 int mes = Integer.parseInt(fechasTemp[1]);
				 int annio = Integer.parseInt(fechasTemp[2]);
				 
				 if(dia >31 || dia < 1){
					 throw new CommandException("El rango de los dias esta definido entre 1-31");
				 }else if(mes>12 || mes< 1){
					 throw new CommandException("El rango de los meses esta definido entre 1-12");
				 }else if(annio > 10000 || annio < 1000){
					 throw new CommandException("El rango de los años solo acepta valores de 4 digitos");
				 }
			   fecha = formatFecha.parse(dato);
			  } catch (ParseException e) {
			   throw new CommandException("El formato de la fecha es dd/MM/yyyy. Favor actualizar");
			  } 
		 return fecha;
	 }	
	
	 public static String getFechaConFormato(Date fecha){
		 SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
		 String result = "";
		 result= formatFecha.format(fecha);
		 return result;
	}	
      private void pedirComando () throws IOException {
			 
	     	if(listenTheConsole)
	 		{					
				do{
					 System.out.println("Introducir un Comando:");
					 String dato = br.readLine(); 
			     	 ejecutaComando(dato);
		    	}
				while(true);	
	 		} 	 
	     	 
		 }	
      protected Comando interpreteCadena(String cadena){
    	  
		  String instance;
		  String metodo;
		  String[] parametros=null;
		  String fecha;
		  String hora="00:00";
		  String[] temp2;
				  
		  String[] temp = cadena.split("\\(");
		  temp[1] = temp[1].substring(0, temp[1].length()-1);  
		  
		  parametros = temp[1].split("\\,");		 
		  
		  boolean esUnaAsignacion = temp[0].indexOf("=") > -1;
		  if(esUnaAsignacion)
		  {
		   temp = temp[0].split("\\=");
		  }
		  else
		  {
		   temp = temp[0].split("\\.");		   		  
		  } 		 
		  boolean cadenaTraeFecha = temp[0].indexOf("/") > -1;
		  if(cadenaTraeFecha){
			  
			  temp2 =temp[0].split("\\,");		  
			  String fechaHora=temp2[0]; 
			  instance = temp2[1].trim();
			  metodo = temp[1].trim().toUpperCase(); 
			  
			  for(int indice=0; indice < parametros.length; indice++ ){
				   parametros[indice]=parametros[indice].trim();			   
			   }		  
			  	temp2 = temp2[0].split(" ");
			  	fecha=temp2[0].trim();
			  	if(temp2.length>1){	
				  	hora=temp2[1].substring(0, temp2[1].length()-1).trim();	
					  try{
						  Date p=Sistema.getParseFechaHora(fechaHora);
						  fecha=Sistema.getFechaConFormato(p);	
					  }
						catch(CommandException commandException)
						{				 
							System.err.println(commandException.getMessage());
						}   	  
					   return new Comando(instance, metodo, parametros,fecha, hora);
			  	}
			  	else{
			  		   return null;
			  	}
			 
		  }else{
			  return null;
			 
		  }		 
	} 	
	
	public static void main(String[] args) throws IOException {
		new InterpreteMandatos();
	} 	
	
	protected void ejecutaComando(String dato) throws IOException{
        String cadena= dato;
        Comando comando;
        
        
        	 try {
        		 comando=interpreteCadena (cadena);
        		 
        		 if(comando == null)
        		 {
        			 throw new CommandException("Comando Invalido. El formato del comando debe contener fecha y hora ");
        		 }
        			 
     			switch(comando.getMetodo()){	   		 
     			case Comando.EXIT:  
     				System.exit(0);
     		   	break; 	   		 
     			case Comando.CREAR_COLABORADOR: 
     				Moneda salario =null;
     				String isMarried;
     				String nombre=comando.getParametros()[0];
     				String cedula=comando.getParametros()[1];
     				Date fechaNacimiento= getFecha(comando.getParametros()[2]);
     				Date fechaIngresoEmpresa= getFecha(comando.getParametros()[3]);
     				String telefono= comando.getParametros()[5];
     				int cantidadHijos = Integer.parseInt(comando.getParametros()[6]);
     				salario= getSalario(comando.getParametros()[7]);
     				
     				if(comando.getParametros()[4].equalsIgnoreCase("true")){
     					isMarried="true";
     				}else if(comando.getParametros()[4].equalsIgnoreCase("false")){
     					isMarried="false";
     				}else{
     					isMarried="N/A";
     				}
     	
     		   		Repo.AgregarColaborador(comando.getInstance(),nombre,cedula,fechaNacimiento, fechaIngresoEmpresa, isMarried, telefono, cantidadHijos, salario);	
     		   	break;	   		 
     			case Comando.CREAR_EDIFICIO: 
     		   		String nombreE= comando.getParametros()[0];
     		   		
     		   		Repo.AgregarEdificio(comando.getInstance(),nombreE);   		 
     		   	break;	   		   		 
     			case Comando.CREAR_COMPANNIA:
     		   		String nombreC =comando.getParametros()[0];
     		   		String cedulaC= comando.getParametros()[1];
     		   		
     		   		Repo.AgregarCompannia(comando.getInstance(),nombreC,cedulaC);
     		   	break;	   		 
     		   	case Comando.AUMENTAR_SALARIO:
     		   		Moneda salarioAumentar = getSalario(comando.getParametros()[0]);
     		   		
     		   		Repo.aumentarSalario( comando.getInstance(), salarioAumentar);
     		   		break;
     		   	case Comando.MOSTRAR_SALARIO:
     		   		String message= Repo.mostrarSalario(comando.getInstance());
     		   		
     		   		System.out.println(message);
     		   		esCargaDeDatos= true;
     		   		break;
     		   	case Comando.TOMAR_VACACIONES:
     		   		Date fechaTomar = getFecha(comando.getParametros()[0]);
     		   		
     		   		Repo.tomarVacaciones(comando.getInstance(), fechaTomar);
     		   		break;
     		   	case Comando.MOSTRAR_VACACIONES:
     		   		String mensaje= Repo.mostrarVacaciones(comando.getInstance());
     		   		
     		   		System.out.println(mensaje);
     		   		esCargaDeDatos= true;
     		   		break;
     		   	case Comando.CALCULAR_SALARIO_NETO_IQ:
     		   		String respuesta= Repo.calculaSalarioNetoPrimeraQuincena(comando.getInstance());
     		   		
     		   		System.out.println(respuesta);
     		   		esCargaDeDatos= true;
     		   		break;
     		   	case Comando.MOSTRAR_VACACIONES_DISPONIBLES:
     		   		Repo.cantidadVacacionesDisponibles(comando.getInstance(),comando.getFecha());
     		   		esCargaDeDatos= true;
     		   		break;
     		   	case Comando.MOSTRAR_VACACIONES_LIQUIDACION:
     		   		Repo.cantidadVacacionesLiquidacion(comando.getInstance(),comando.getFecha());
     		   		esCargaDeDatos= true;
     		   		break;		   		
     		   	case Comando.CARGAR_LOG:	
     			    	if(log.existeUnLogPrevio())
     			    	{
     			    		System.out.println("Se realizo el proceso de carga exitosamente");
     			   		}else{
     			   			System.out.println("No se realizo el proceso de carga de forma debida");	
     			   		}
     			   	break;
     			   	default:
     			   		throw new CommandException("Comando desconocido. Favor introducirlo nuevamente:");   		
     			}
     			if(!esCargaDeDatos){
     				log.crearRegistroLog(cadena);
     			}
     		}
     		catch(CommandException commandException)
     		{
     			System.err.println(commandException.getMessage());
     			pedirComando ();
     		}				
	}				
}
