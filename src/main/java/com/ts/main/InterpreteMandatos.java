package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.ts.objects.Repo;



public class InterpreteMandatos {
	
	 	static String comando="";
	 	static String valor="";
	 	static String[] parametros;
	 	public static boolean estadoFuncion= false;	
	 	static ArchivoLog archivo = null;
	 	
	 	public static void interpretarCadena(String cadena){
			int contador=0; 				
			StringTokenizer tokens=new StringTokenizer(cadena);
			int cantidadP = tokens.countTokens();
			parametros = new String[cantidadP];
			comando= tokens.nextToken();		
				
			while(tokens.hasMoreTokens()){		
				valor= tokens.nextToken().toString();
				parametros[contador]=valor.substring(0, valor.length()-1);							
				System.out.println("Token " + contador + " valor: " + parametros[contador]);
				contador++; 
				}//while
			}//interpreta 	
	 	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			archivo = new ArchivoLog();		
		   	InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        String dato="";
	        String cadena="";
	        System.out.println("Introducir un Comando:");    
	            
	 do{
		 try {
	        	dato = br.readLine();
	            cadena= dato.toLowerCase().toString();
	   		 	System.out.println("La cadena introducida fue: "+ cadena);
	   		    interpretarCadena(cadena);
	   		 	System.out.print("Comando Real:" + comando + "\n");	
	   		 
	   		 switch(comando){	   		 
	   		 case "exit": 
	   			 ArchivoLog.crearLog(comando);
	   			 System.exit(0);
	   		 break; 	   		 
	   		 case "crear_colaborador":   			
	   			Repo.AgregarColaborador(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
	   		
	   		//Se guarda en archivo solo en su ejecucion exitosa
	   			if(estadoFuncion){
		   		 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			}else{
	   			 System.out.print("El Colaborador no se agrego " + estadoFuncion + "\n");	
	   			}   		
	   		 break;	   		 
	   		 case "crear_edificio": 	   			 
	   			 Repo.AgregarEdificio(parametros[0]);	   			
	   		
	   		//Se guarda en archivo solo en su ejecucion exitosa
	   			if(estadoFuncion){
		   		 ArchivoLog.crearLog(comando + " " + parametros[0].toString());	   		 	   		 
		   		}else{
		   		 System.out.print("El Edificio no se agrego" );	
		   		} 
	   		 break;	   		 
	   		 case "crear_proyecto": 
	   			 Repo.AgregarProyecto(parametros[0]);	   			
	   			
	   		//Se guarda en archivo solo en su ejecucion exitosa
	   			if(estadoFuncion){
	   			ArchivoLog.crearLog(comando + " " + parametros[0].toString());	   		 	   		 
			   	}else{
			   	System.out.print("El Proyecto no se agrego");	
			   	}   
	   		 break;	   		 
	   		 case "crear_compañia":
	   			Repo.AgregarCompania(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
	   			
	   			//Se guarda en archivo solo en su ejecucion exitosa
	   			if(estadoFuncion){
	   			ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());		   		 	   		 
				}else{
				System.out.print("El Proyecto no se agrego");	
				}
	   		 break;	   		 
	   		 case "crear_activo":   
	   			 Repo.AgregarActivo(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
	   		
	   		//Se guarda en archivo solo en su ejecucion exitosa
	   			if(estadoFuncion){
	   			ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());
	   			}else{
				System.out.print("El Activo no se agrego");	
				}
	   		 break;
	   		 
	   		 case "aumentar_salario": 
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   			
	   		 
	   		 //Se guarda en archivo solo en su ejecucion exitosa
	   			 if(estadoFuncion){
		   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			 }else{
	   				System.out.print("No se realizo el proceso");	
	   			 }
	   		 break;
	   		 
	   		 case "calcular_salario":
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   			
	   		 //Se guarda en archivo solo en su ejecucion exitosa	 
	   			 if(estadoFuncion){
		   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			 }else{
	   				System.out.print("No se realizo el proceso");	
	   			 }	   		
	   		 break;
	   		 
	   		 case "calcular_retenciones_fuente":	
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		
	   		 
	   		 //Se guarda en archivo solo en su ejecucion exitosa
	   			 if(estadoFuncion){
		   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			 }else{
	   				System.out.print("No se realizo el proceso");	
	   			 }	   		 
	   		 break;
	   		 
	   		 case "mostrar_retenciones_fuente":	
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   			
	   		 //Se guarda en archivo solo en su ejecucion exitosa
	   			 if(estadoFuncion){
		   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			 }else{
	   				System.out.print("No se realizo el proceso");	
	   			 }	   		 
	   		break;
	   		 
	   		 case "desvincular_colaborador_proyecto":	
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		
	   		 //Se guarda en archivo solo en su ejecucion exitosa
	   			 if(estadoFuncion){
		   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			 }else{
	   				System.out.print("No se realizo el proceso");	
	   			 }	   		
	   		break;
	   		 
	   		 case "vincular_colaborador_proyecto":	
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 
	   		 //Se guarda en archivo solo en su ejecucion exitosa
	   			 if(estadoFuncion){
		   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   			 }else{
	   				System.out.print("No se realizo el proceso");	
	   			 }
	   		break;
	   		
	   		 
	   		 default:System.err.print("\n" + "Comando desconocido. Favor introducirlo nuevamente:" + "\n"); break;
	   		
	   		 }//switch
	        } catch (IOException e) {
	            e.printStackTrace();
	        } //catch
		 
	 }while(true);			
	
	}//main
}//class
