package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.ts.objects.Repo;



public class InterpreteMandatos {
	 	static String comando="";
	 	static String[] parametros;
	 	
	public static void asignaComando(String cadena){
		int contador=0; 
				
		StringTokenizer tokens=new StringTokenizer(cadena);
		int cantidadP = tokens.countTokens();
		parametros = new String[cantidadP];
		comando= tokens.nextToken();	
		while(tokens.hasMoreTokens()){
			parametros[contador]= tokens.nextToken().toString();
			System.out.println(contador + " " + parametros[contador]);
				 
			contador++; 
			 }//while
			 }//asigna	 	
	 	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ArchivoLog archivo = new ArchivoLog();		
		   	InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        String dato="";
	        String cadena="";
	        System.out.println("Introducir un Comando:");    
	            
	 do{
		 try {
	        	dato = br.readLine();
	            cadena= dato.toLowerCase().toString();
	   		 	System.out.println("La cadena introducida fue: "+ cadena + "\n");
	   		 	asignaComando(cadena);
	            
	   		 switch(comando){	   		 
	   		 case "exit": 
	   			 ArchivoLog.crearLog(comando);
	   			 System.exit(0);
	   		 break; 	   		 
	   		 case "crear_colaborador": 
	   			System.out.print("Comando real:" + comando + "\n");	   			
	   			Repo.AgregarColaborador(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
	   			ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());	   			
	   		 break;	   		 
	   		 case "crear_edificio": 
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");	   			 
	   			 Repo.AgregarEdificio(parametros[0]);	   			
	   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString());	   		 	   		 
	   		 break;	   		 
	   		 case "crear_proyecto": 
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   			 Repo.AgregarProyecto(parametros[0]);	   			
	   			 ArchivoLog.crearLog(comando + " " + parametros[0].toString());	 
	   		 break;	   		 
	   		 case "crear_compañia":
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   			Repo.AgregarCompania(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
	   			ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());
	   		 break;	   		 
	   		 case "crear_activo":   
	   			 System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   			Repo.AgregarActivo(parametros[0], Integer.parseInt(parametros[1].toString()));	   			
	   			ArchivoLog.crearLog(comando + " " + parametros[0].toString() + " " + parametros[1].toString());
	   		 break;
	   		 
	   		 case "aumentar_salario": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "calcular_salario":
	   			   			 
	   		 break;
	   		 
	   		 case "calcular_retenciones_fuente":	System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "mostrar_retenciones_fuente":	System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "desvincular_colaborador_proyecto":	System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "vincular_colaborador_proyecto":	System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		
	   		 
	   		 default:System.err.print("\n" + "Comando desconocido. Favor introducirlo nuevamente:" + "\n"); break;
	   		
	   		 }//switch
	        } catch (IOException e) {
	            e.printStackTrace();
	        } //catch
		 
	 }while(true);
			
	
	}//main
}//class
