package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ts.objects.Repo;



public class InterpreteMandatos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ArchivoLog archivo = new ArchivoLog();
		
		   	InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        String dato="";
	        String comando="";
	        System.out.println("Introducir un Comando:");
	        String[] prueba = {"axel", "206530838"};
	            
	 do{
		 try {
	        	dato = br.readLine();
	            comando= dato.toLowerCase().toString();
	   		 	System.out.println("El comando introducido fue: "+ comando);
	            
	   		 switch(comando){
	   		 
	   		 case "exit": 
	   			 ArchivoLog.crearLog(comando);
	   			 System.exit(0);
	   		 break; 
	   		 
	   		 case "crear_colaborador": 
	   			System.err.print("Comando real:" + comando + "\n");
	   			ArchivoLog.crearLog(comando + " " + prueba[0].toString() + " " + prueba[1].toString());
	   			
	   		 break;
	   		 
	   		 case "crear_edificio": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "crear_proyecto": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "crear_compañia": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "crear_activo":   System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
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
