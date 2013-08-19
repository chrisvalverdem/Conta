package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.ts.objects.CommandException;
import com.ts.objects.Moneda;
import com.ts.objects.RangoRenta;

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
      protected Comando interpreteCadena(String cadena) throws CommandException{
    	  
    	  String fecha;
		  String hora="00:00";
		  String instance;
		  String comando;
		  String metodo;
		  String[] parametros=null;		 
		  String[] temp2;		
		  boolean existeMetodo=false;
		  
		  if (cadena.equals("")){
			  throw new CommandException("Comando Invalido. El formato del comando no se reconoce");			  
		  }	
		  
		  Comando.llenarListas();
		  
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
		  if(!cadenaTraeFecha){			  
			  throw new CommandException("Comando Invalido. El formato del comando debe contener fecha y hora ");			  
		  }
		 
		  metodo = temp[1].trim().toUpperCase(); 		
		  temp2 =temp[0].split("\\,");		  
		  String fechaHora=temp2[0];		 
		  
		  temp = temp2[0].split(" ");		  	
		  if(temp.length <= 1){
		  		throw new CommandException("Comando Invalido. El formato del comando debe contener hora ");	
		  }		  
		
		  fecha=temp[0].trim();
		  hora=temp[1].trim();
		  
		  Date fechaParse=Sistema.getParseFechaHora(fechaHora);
		  fecha=Sistema.getFechaConFormato(fechaParse);		  
		  
		  temp=temp2[1].split(" ");		  		  	
		  if(temp.length <= 2){
		  		throw new CommandException("Formato invalido, el comando debe contener un SHOW, WRITE ó un EXECUTE ");	
		  }		
		  comando = temp[1].trim().toUpperCase();
		  instance = temp[2].trim();
	
		switch(comando.toUpperCase()){
		
		case Comando.EXECUTE:
		if(!Comando.getListaParaMetodosExcecute().isEmpty()){
			for (int indice=0; indice < Comando.getListaParaMetodosExcecute().size(); indice++){			
				if(metodo.equals(Comando.getListaParaMetodosExcecute().get(indice))){
					existeMetodo=true;
					break;
					}
				}			
			break;			
		}
		case Comando.WRITE:
			if(!Comando.getListaParaMetodosWrite().isEmpty()){				
				for (int indice=0; indice < Comando.getListaParaMetodosWrite().size(); indice++){					
					if(metodo.equals(Comando.getListaParaMetodosWrite().get(indice))){
						existeMetodo=true;
						break;
						}
					}			
				break;				
			}
		case Comando.SHOW:
			if(!Comando.getListaParaMetodosShow().isEmpty()){
				for (int indice=0; indice < Comando.getListaParaMetodosShow().size(); indice++){			
					if(metodo.equals(Comando.getListaParaMetodosShow().get(indice))){
						existeMetodo=true;
						break;
						}
					}
				break;	
				
			}						
		default:
		   		throw new CommandException("Comando desconocido. Favor introducirlo nuevamente:");   
		
		}
		
		if(!existeMetodo ){
			throw new CommandException("El metodo " + metodo+ " no es valido para el comando "+comando);
		}
		  for(int indice=0; indice < parametros.length; indice++ ){
			   parametros[indice]=parametros[indice].trim();			   
		   }			
		  	return new Comando(fecha, hora, instance, comando, metodo, parametros);
	} 	
	
	public static void main(String[] args) throws IOException {
		new InterpreteMandatos();
	} 	
	
	protected void ejecutaComando(String dato) throws IOException{
        String cadena= dato;       
               
        	 try {
        		 Comando comando=interpreteCadena (cadena);
        		        			 
     			switch(comando.getComando()){	
     			
     			case Comando.EXIT:  
     				System.exit(0);
     		   	break; 	   		 
     			case Comando.WRITE: 
     				
     				switch(comando.getMetodo()){     				
     				case Comando.CREAR_COLABORADOR:     					
     					Moneda salario =null;
         				String isMarried;
         				String nombre=comando.getParametros()[0];
         				String cedula=comando.getParametros()[1];
         				Date fechaNacimiento= Sistema.getParseFecha(comando.getParametros()[2]);
         				Date fechaIngresoEmpresa= Sistema.getParseFecha(comando.getParametros()[3]);
         				String telefono= comando.getParametros()[5];
         				int cantidadHijos = Integer.parseInt(comando.getParametros()[6]);
         				salario= Sistema.getMoneda(comando.getParametros()[7]);
         				
         				if(comando.getParametros()[4].equalsIgnoreCase("true")){
         					isMarried="true";
         				}else if(comando.getParametros()[4].equalsIgnoreCase("false")){
         					isMarried="false";
         				}else{
         					isMarried="N/A";
         				}
         				
         		   		Repo.AgregarColaborador(comando.getInstance(),nombre,cedula,fechaNacimiento, fechaIngresoEmpresa, isMarried, telefono, cantidadHijos, salario);	
         		   	break;
     				case Comando.CREAR_COMPANNIA:
         		   		String nombreC =comando.getParametros()[0];
         		   		String cedulaC= comando.getParametros()[1];
         		   		
         		   		Repo.AgregarCompannia(comando.getInstance(),nombreC,cedulaC);
         		   	break;
     				case Comando.CREAR_EDIFICIO:
     					String nombreE= comando.getParametros()[0];
         		   		
         		   		Repo.AgregarEdificio(comando.getInstance(),nombreE);   		 
         		   	break;
     				default:
      			   		throw new CommandException("El metodo no corresponde al comando:");  
     				}     			  			
     				break;
     			case Comando.EXECUTE:
     				
     				switch(comando.getMetodo()){ 
     				case Comando.AUMENTAR_SALARIO:
         		   		Moneda salarioAumentar = Sistema.getMoneda(comando.getParametros()[0]);
         		   		
         		   		Repo.aumentarSalario( comando.getInstance(), salarioAumentar);
         		   		break;
     				case Comando.TOMAR_VACACIONES:
         		   		Date fechaTomar = Sistema.getParseFecha(comando.getParametros()[0]);
         		   		
         		   		Repo.tomarVacaciones(comando.getInstance(), fechaTomar);
         		   		break;
    				case Comando.CALCULAR_SALARIO_NETO_IQ:
         		   		String respuesta= Repo.calculaSalarioNetoPrimeraQuincena(comando.getInstance());
         	 	   		
         		   		System.out.println(respuesta);
         		   		esCargaDeDatos= true;
         		   		break;
        		   	case Comando.ESTABLECER_RANGO_RENTA:
        		   		RangoRenta rango = Repo.analizaIntervalosRenta(comando.getParametros());  		

        		   		Repo.cambioRangoRenta(comando.getInstance(), rango);
        		   		break;
        		   	case Comando.LIMPIAR_RANGO_RENTA:		

        		   		Repo.limpiaRangoRenta(comando.getInstance());
        		   		break;
        		   	case Comando.ACTUALIZAR_MONTO_CONYUGE_HIJO:
        		   		Moneda montoConyuge= Sistema.getMoneda(comando.getParametros()[0]);
        		   		Moneda montoHijo= Sistema.getMoneda(comando.getParametros()[1]);       		   		
        		   		
        		   		Repo.actualizarMontoConyugeHijo(montoConyuge, montoHijo);
        		   		break;
     				default:
      			   		throw new CommandException("El metodo no corresponde al comando:");  
     				}
     				break;
     			case Comando.SHOW:
     			
     				switch(comando.getMetodo()){
     				case Comando.MOSTRAR_SALARIO:
         		   		String message= Repo.mostrarSalario(comando.getInstance());
         		   		
         		   		System.out.println(message);
         		   		esCargaDeDatos= true;
         		   		break;
     				case Comando.MOSTRAR_VACACIONES:
         		   		String mensaje= Repo.mostrarVacaciones(comando.getInstance());
         		   		
         		   		System.out.println(mensaje);
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
         		   case Comando.MOSTRAR_RETENCIONES_FUENTE:
        		   		Repo.mostrarRetencionesFuente(comando.getInstance(),comando.getFecha(),comando.getParametros()[0]);
        		   		esCargaDeDatos= true;
        		   		break;
         		   default:
     			   		throw new CommandException("El metodo no corresponde al comando:");  
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
