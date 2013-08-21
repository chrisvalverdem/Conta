package com.ts.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.ts.interprete.Parser;
import com.ts.libraries.CommandException;

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
