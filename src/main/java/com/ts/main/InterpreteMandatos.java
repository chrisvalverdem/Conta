package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class InterpreteMandatos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      	InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        String comando= "";
	        String dato="";
	        System.out.println("Introducir un Comando:");
	            
	 do{
		 try {
	        	dato = br.readLine();
	            System.out.println("El comando introducido fue: "+ dato);
	            comando= dato.toLowerCase().toString();
	   		 		   		 
	   		 switch(comando){
	   		 
	   		 case "exit": System.exit(0);
	   		 break; 
	   		 
	   		 case "crear_colaborador": 
	   			 System.err.print("Comando real:" + comando + "\n");
	   			 			 
	   		 break;
	   		 
	   		 case "crear_edificio": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "crear_proyecto": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "crear_compañia": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "crear_activo":   System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "aumenta_salario": System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
	   		 break;
	   		 
	   		 case "calcular_salario":
	   			   			 
	   		 break;
	   		 
	   		 case "calcula_retenciones_fuente":	System.out.println("El comando introducido no se encuentra implementado aun: "+ comando +"\n");
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
