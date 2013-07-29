package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.ts.objects.Repo;



public class InterpreteMandatos {
		static String dato="";
	 	static String comando="";
	 	static String valor="";
	 	static String[] parametros;
	 	public static boolean estadoFuncion= false;	
	 	static ArchivoLog archivo = new ArchivoLog();
	 	static InputStreamReader isr = new InputStreamReader(System.in);
	 	static BufferedReader br = new BufferedReader(isr);
        
	 	public static void interpretarCadena(String cadena){
			int contador=0; 				
			StringTokenizer tokens=new StringTokenizer(cadena);
			int cantidadP = tokens.countTokens();
			parametros = new String[cantidadP];
			comando= tokens.nextToken();		
				
			while(tokens.hasMoreTokens()){		
				valor= tokens.nextToken().toString();
				parametros[contador]=valor.substring(0, valor.length()-1);							
				//System.out.println("Token " + contador + " valor: " + parametros[contador]);
				contador++; 
				}//while
			}//interpreta 	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        try {
			if(ArchivoLog.gestionArchivo()){
		        System.err.println("Archivo creado recientemente");    

			}else{
				System.err.println("Archivo almacenado con anterioridad"); 
				 if(ArchivoLog.lecturaLog()){
		   				System.out.print("Se realizo el proceso de carga exitosamente" + "\n");
		   			 }else{
		   				System.out.print("No se realizo el proceso de carga de forma debida" + "\n");	
		   			 }
			}		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
          do{
        	  try{
        		  System.out.println("Introducir un Comando:"); 
        		  dato = br.readLine(); 
        		  ejecutaComando(dato);
        	   } catch (IOException e) {
		            e.printStackTrace();
		        } //catch
			}while(true);
        	  
            			
	}//main
	
	public static void ejecutaComando(String dato) throws IOException{

        String cadena= dato.toLowerCase().toString();
		System.out.println("La cadena introducida fue: "+ cadena);
		interpretarCadena(cadena);
		System.out.print("Comando Real:" + comando + "\n");	
		   		 
		   		 switch(comando){	   		 
		   		 case "exit": 
		   			 System.exit(0);
		   		 break; 	   		 
		   		 case "crear_colaborador":   			
		   			Repo.AgregarColaborador(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
		   		
		   		//Se guarda en archivo solo en su ejecucion exitosa
		   			if(estadoFuncion){
			   		 ArchivoLog.crearRegistroLog(cadena);	   			
		   			}else{
		   			 System.out.print("El Colaborador no se agrego " + estadoFuncion + "\n");	
		   			}   		
		   		 break;	   		 
		   		 case "crear_edificio": 	   			 
		   			 Repo.AgregarEdificio(parametros[0]);	   			
		   		
		   		//Se guarda en archivo solo en su ejecucion exitosa
		   			if(estadoFuncion){
			   		 ArchivoLog.crearRegistroLog(cadena);   		 	   		 
			   		}else{
			   		 System.out.print("El Edificio no se agrego" );	
			   		} 
		   		 break;	   		 
		   		 case "crear_proyecto": 
		   			 Repo.AgregarProyecto(parametros[0]);	   			
		   			
		   		//Se guarda en archivo solo en su ejecucion exitosa
		   			if(estadoFuncion){
		   			ArchivoLog.crearRegistroLog(cadena);	   		 	   		 
				   	}else{
				   	System.out.print("El Proyecto no se agrego");	
				   	}   
		   		 break;	   		 
		   		 case "crear_compañia":
		   			Repo.AgregarCompania(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
		   			
		   			//Se guarda en archivo solo en su ejecucion exitosa
		   			if(estadoFuncion){
		   			ArchivoLog.crearRegistroLog(cadena);		   		 	   		 
					}else{
					System.out.print("El Proyecto no se agrego");	
					}
		   		 break;	   		 
		   		 case "crear_activo":   
		   			 Repo.AgregarActivo(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
		   		
		   		//Se guarda en archivo solo en su ejecucion exitosa
		   			if(estadoFuncion){
		   			ArchivoLog.crearRegistroLog(cadena);
		   			}else{
					System.out.print("El Activo no se agrego");	
					}
		   		 break;
		   		 
		   		 case "aumentar_salario": 
		   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
		   			
		   		 
		   		 //Se guarda en archivo solo en su ejecucion exitosa
		   			 if(estadoFuncion){
			   			 ArchivoLog.crearRegistroLog(cadena);	   			
		   			 }else{
		   				System.out.print("No se realizo el proceso");	
		   			 }
		   		 break;
		   		 
		   		 case "calcular_salario":
		   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
		   			
		   		 //Se guarda en archivo solo en su ejecucion exitosa	 
		   			 if(estadoFuncion){
			   			 ArchivoLog.crearRegistroLog(cadena);	   			
		   			 }else{
		   				System.out.print("No se realizo el proceso");	
		   			 }	   		
		   		 break;
		   		 
		   		 case "calcular_retenciones_fuente":	
		   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
		   		
		   		 
		   		 //Se guarda en archivo solo en su ejecucion exitosa
		   			 if(estadoFuncion){
			   			 ArchivoLog.crearRegistroLog(cadena);	   			
		   			 }else{
		   				System.out.print("No se realizo el proceso");	
		   			 }	   		 
		   		 break;
		   		 
		   		 case "mostrar_retenciones_fuente":	
		   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
		   			
		   		 //Se guarda en archivo solo en su ejecucion exitosa
		   			 if(estadoFuncion){
			   			 ArchivoLog.crearRegistroLog(cadena);	   			
		   			 }else{
		   				System.out.print("No se realizo el proceso");	
		   			 }	   		 
		   		break;
		   		 
		   		 case "desvincular_colaborador_proyecto":	
		   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
		   		
		   		 //Se guarda en archivo solo en su ejecucion exitosa
		   			 if(estadoFuncion){
			   			 ArchivoLog.crearRegistroLog(cadena);	   			
		   			 }else{
		   				System.out.print("No se realizo el proceso");	
		   			 }	   		
		   		break;
		   		 
		   		 case "vincular_colaborador_proyecto":	
		   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
		   		 
		   		 //Se guarda en archivo solo en su ejecucion exitosa
		   			 if(estadoFuncion){
			   			 ArchivoLog.crearRegistroLog(cadena);	   			
		   			 }else{
		   				System.out.print("No se realizo el proceso");	
		   			 }
		   		break;
		   		
		   		 case "cargar_log":	
		   		 
		   		 //Se guarda en archivo solo en su ejecucion exitosa
		   			 if(ArchivoLog.lecturaLog()){
		   				System.out.print("Se realizo el proceso de carga exitosamente" + "\n");
		   			 }else{
		   				System.out.print("No se realizo el proceso de carga de forma debida" + "\n");	
		   			 }
		   		break;
		   		 
		   		 default:System.err.print("\n" + "Comando desconocido. Favor introducirlo nuevamente:" + "\n"); break;
		   		
		   		 }//switch	  		
	}//ejecutaComando	
}//class
