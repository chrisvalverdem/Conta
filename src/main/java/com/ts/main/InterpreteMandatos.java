package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ts.objects.Colon;
import com.ts.objects.CommandException;
import com.ts.objects.Dolar;
import com.ts.objects.Moneda;

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
		  
		  temp[1]=temp[1].replaceAll("\\¢", Moneda.COLON + ",");
		  temp[1]=temp[1].replaceAll("\\$", Moneda.DOLAR + ",");
		  
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
				Moneda salario =null;
				boolean isMarried;
				double monto;
				
				isMarried= Boolean.parseBoolean(comando.getParametros()[4]);
				monto= Double.parseDouble(comando.getParametros()[8]);
				if(comando.getParametros()[7].equalsIgnoreCase(Moneda.COLON)){
					salario= new Colon(monto);	
				}else if(comando.getParametros()[7].equalsIgnoreCase(Moneda.DOLAR)){
					salario= new Dolar(monto);		
				}
				
		   		Repo.AgregarColaborador(comando.getInstance(), comando.getParametros()[0], comando.getParametros()[1],formatFecha.parse(comando.getParametros()[2].toString()), formatFecha.parse(comando.getParametros()[3].toString()), isMarried, comando.getParametros()[5], Integer.parseInt(comando.getParametros()[6].toString()), salario);	
		   	break;	   		 
		   	case "crear_edificio": 
		   		String nombre= comando.getParametros()[0];
		   		
		   		Repo.AgregarEdificio(comando.getInstance(),nombre);   		 
		   	break;	   		   		 
		   	case "crear_compannia":
		   		String nombreC =comando.getParametros()[0];
		   		String cedula= comando.getParametros()[1];
		   		
		   		Repo.AgregarCompannia(comando.getInstance(),nombreC,cedula);
		   	break;	   		 
		   	case "aumentar_salario":
		   		Double montoR= Double.parseDouble(comando.getParametros()[1]);
		   		Moneda salarioAumentar = null;
		   		
				if(comando.getParametros()[0].equalsIgnoreCase(Moneda.COLON)){
					salarioAumentar= new Colon(montoR);	
				}else if(comando.getParametros()[0].equalsIgnoreCase(Moneda.DOLAR)){
					salarioAumentar= new Dolar(montoR);		
				}
		   		
		   		Repo.aumentar_Salario( comando.getInstance(), salarioAumentar); 
		   		break;
		   	case "mostrar_salario":
		   		Repo.mostrar_Salario(comando.getInstance()); 
		   		esCargaDeDatos= true;
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
