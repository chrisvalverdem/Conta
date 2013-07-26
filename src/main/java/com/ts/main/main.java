package com.ts.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	 
	        System.out.println("Introducir un numero: ");
	        String comando;
	 
	        try {
	 
	        	comando = br.readLine();
	            System.out.println("El numero introducido fue: "+comando);
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		
	        
	        
	}

}
