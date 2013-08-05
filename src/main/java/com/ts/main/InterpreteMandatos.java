package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ts.objects.CommandException;

public class InterpreteMandatos {	
		
	 	private ArchivoLog log;
        private boolean esCargaDeDatos= false; 
        private final SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
	 	
        public InterpreteMandatos() throws IOException
	 	{
	 		new InterpreteMandatos(true, ArchivoLog.LOG_NAME);

	 	}	 	
	 	public InterpreteMandatos(boolean listenTheConsole, String filePath) throws IOException
	 	{
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
	            	}
	            }
			}
	 		if(listenTheConsole)
	 		{			
				InputStreamReader isr = new InputStreamReader(System.in);
	 			BufferedReader br = new BufferedReader(isr);
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
		  
		  String[] temp = cadena.split("\\(");
		  temp[1] = temp[1].substring(0, temp[1].length()-1);
		  parametros = temp[1].split("\\,"); 
		  	
		  boolean esUnaAsinacion = temp[0].indexOf("=") > -1;
		  if(esUnaAsinacion)
		  {
		   temp = temp[0].split("\\=");
		  }
		  else
		  {
		   temp = temp[0].split("\\.");		   		  
		  }	
		  instance = temp[0].trim();
		   metodo = temp[1].trim();	
		   for(int indice=0; indice < parametros.length; indice++ ){
			   parametros[indice]=parametros[indice].trim();			   
		   }
		   return new Comando(instance, metodo, parametros); 
	} 	
	
	public static void main(String[] args) throws IOException {
		new InterpreteMandatos();
	} 	

	protected void ejecutaComando(String dato) throws IOException{
		boolean temporal;
        String cadena= dato.toLowerCase().toString();
        Comando comando=interpreteCadena (cadena);        
     
        try
		{
			switch(comando.getMetodo()){	   		 
				case "exit": 
					System.exit(0);
			   	break; 	   		 
				case "crear_colaborador":   
					if(comando.getParametros()[4].equalsIgnoreCase("true")){
						temporal= true;
					}else{
						temporal=false;
					}		
			   		Repo.AgregarColaborador(comando.getParametros()[0], comando.getParametros()[1],formatFecha.parse(comando.getParametros()[2].toString()), formatFecha.parse(comando.getParametros()[3].toString()), temporal, comando.getParametros()[5], Integer.parseInt(comando.getParametros()[6].toString()), Double.parseDouble(comando.getParametros()[7].toString()) );	
			   	break;	   		 
			   	case "crear_edificio": 	   			 
			   		Repo.AgregarEdificio(comando.getInstance(),comando.getParametros()[0]);   		 
			   	break;	   		 
			   	case "crear_proyecto": 
			   		 Repo.AgregarProyecto(comando.getInstance(),comando.getParametros()[0]);	
			   	break;	   		 
			   	case "crear_compannia":
			   		Repo.AgregarCompannia(comando.getInstance(),comando.getParametros()[0],comando.getParametros()[1]);
			   	break;	   		 
			   	case "crear_activo":   
			   		Repo.AgregarActivo(comando.getInstance(),comando.getParametros()[0], Integer.parseInt(comando.getParametros()[1]));
			   	break;
			    case "cargar_log":	
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
		catch(CommandException | NumberFormatException | ParseException commandException)
		{
			System.err.println(commandException.getMessage()); 
		}		
		
	}			
	
}
