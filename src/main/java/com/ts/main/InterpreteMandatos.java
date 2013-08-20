package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.ts.db.Repo;
import com.ts.interprete.Parser;
import com.ts.libraries.Colaborador;
import com.ts.libraries.CommandException;
import com.ts.libraries.Compania;
import com.ts.libraries.Edificio;
import com.ts.libraries.Mes;
import com.ts.libraries.Moneda;
import com.ts.libraries.RangoRenta;


public class InterpreteMandatos {	
		
	 	private ArchivoLog log;
        private boolean esCargaDeDatos= false; 
        private BufferedReader br;
        private boolean listenTheConsole;
	 	
        private InterpreteMandatos() throws Exception
	 	{
	 		new InterpreteMandatos(true, ArchivoLog.LOG_NAME);

	 	}	 	
	 	public InterpreteMandatos(boolean listenTheConsole, String filePath) throws Exception
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

      private void pedirComando () throws Exception {
			 
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
	
	public static void main(String[] args) throws Exception {
		new InterpreteMandatos();
	} 	
	
	protected void ejecutaComando(String dato) throws Exception{
        String cadena= dato; 
        Parser paser;
               
        	 try {        		        			 
        		 paser = new Parser(dato);
        		 paser.process();
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
