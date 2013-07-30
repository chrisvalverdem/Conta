package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.ts.objects.CommandException;
import com.ts.objects.Repo;

public class InterpreteMandatos {
	
		private String comando="";
	 	private ArchivoLog log = new ArchivoLog();
        
	 	public InterpreteMandatos() throws IOException
	 	{
	 		if(log.existeUnLogPrevio())
			{
				ArrayList<String> comandos = log.getAllCommandsFromLog();
	            for(String comando : comandos)
	            {
	            	ejecutaComando(comando);
	            }
				
				System.out.println("Se realizo el proceso de carga exitosamente");
			}
	   
	 		InputStreamReader isr = new InputStreamReader(System.in);
		 	BufferedReader br = new BufferedReader(isr);
			do{
				System.out.println("Introducir un Comando:"); 
	        	String dato = br.readLine(); 
	        	ejecutaComando(dato);
	    	}
			while(true);
	 	}
	 		
	private String[] interpretarCadena(String cadena){
		int contador=0; 				
		StringTokenizer tokens=new StringTokenizer(cadena);
		int cantidadP = tokens.countTokens();
		String[] parametros = new String[cantidadP];
		comando= tokens.nextToken();		
				
		while(tokens.hasMoreTokens()){		
			String valor= tokens.nextToken().toString();
			parametros[contador]=valor.substring(0, valor.length()-1);							
			contador++; 
		}
		return parametros;
	} 	
	
	public static void main(String[] args) throws IOException {
		new InterpreteMandatos();
	} 	

	private void ejecutaComando(String dato) throws IOException{

        String cadena= dato.toLowerCase().toString();
        String[] parametros = interpretarCadena(cadena);
		
        try
		{
			switch(comando){	   		 
				case "exit": 
					System.exit(0);
			   	break; 	   		 
			   	case "crear_colaborador":   			
			   		Repo.AgregarColaborador(parametros[0], Integer.parseInt(parametros[1].toString()));	   
			   	break;	   		 
			   	case "crear_edificio": 	   			 
			   		Repo.AgregarEdificio(parametros[0]); 
			   	break;	   		 
			   	case "crear_proyecto": 
			   		 Repo.AgregarProyecto(parametros[0]);	
			   	break;	   		 
			   	case "crear_compannia":
			   		Repo.AgregarCompannia(Integer.parseInt(parametros[0].toString()),parametros[1]);
			   	break;	   		 
			   	case "crear_activo":   
			   		Repo.AgregarActivo(parametros[0], Integer.parseInt(parametros[1].toString()));
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
			log.crearRegistroLog(cadena);
		}
		catch(CommandException commandException)
		{
			System.err.println(commandException.getMessage()); 
		}
		
		
	}	
} 
