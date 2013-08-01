package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.ts.objects.CommandException;
import com.ts.objects.Repo;

public class InterpreteMandatos {	
		
	 	private ArchivoLog log = new ArchivoLog();
        private boolean esCargaDeDatos= false; 
     
	 	public InterpreteMandatos() throws IOException
	 	{
	 		new InterpreteMandatos(true);

	 	}	 	
	 	public InterpreteMandatos(boolean listenTheConsole) throws IOException
	 	{
	 		if(log.existeUnLogPrevio())
			{
				esCargaDeDatos=true;
				ArrayList<String> comandos = log.getAllCommandsFromLog();
	            for(String comando : comandos)
	            {   		
	            	ejecutaComando(comando);
	            }
	            esCargaDeDatos=false;
				System.out.println("Se realizo el proceso de carga exitosamente");
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

        String cadena= dato.toLowerCase().toString();
        Comando comando=interpreteCadena (cadena);        
     
        try
		{
			switch(comando.getMetodo()){	   		 
				case "exit": 
					System.exit(0);
			   	break; 	   		 
				case "crear_colaborador":   
			   		Repo.AgregarColaborador(comando.getInstance(),comando.getParametros()[0], Integer.parseInt(comando.getParametros()[1]));	   
			   	break;	   		 
			   	case "crear_edificio": 	   			 
			   		Repo.AgregarEdificio(comando.getInstance(),comando.getParametros()[0]);   		 
			   	break;	   		 
			   	case "crear_proyecto": 
			   		 Repo.AgregarProyecto(comando.getInstance(),comando.getParametros()[0]);	
			   	break;	   		 
			   	case "crear_compannia":
			   		Repo.AgregarCompannia(comando.getInstance(),comando.getParametros()[0],comando.getParametros()[1]);
			   		System.err.println("Cantidad de Compannias : "+ Repo.getTamannoCompannia() + "\n");
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
		catch(CommandException commandException)
		{
			System.err.println(commandException.getMessage()); 
		}		
		
	}			
	
}
