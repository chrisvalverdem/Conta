package com.ts.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class ArchivoLog {
   
	static FileWriter archivo; //nuestro archivo log

    public static void crearRegistroLog(String operacion) throws IOException {

            Calendar fechaActual = Calendar.getInstance();
            
          //Empieza a escribir en el archivo
            archivo.write(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
                    +"/"+String.valueOf(fechaActual.get(Calendar.MONTH)+1)
                    +"/"+String.valueOf(fechaActual.get(Calendar.YEAR))
                    +" "+String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
                    +":"+String.valueOf(fechaActual.get(Calendar.MINUTE))
                    +":"+String.valueOf(fechaActual.get(Calendar.SECOND))+ " " + operacion+ "\r\n");
            archivo.close(); //Se cierra el archivo
    }//Fin del metodo crearLog
   
    public static boolean lecturaLog(){

        try{
            // Abrimos el archivo
            FileInputStream fstream = new FileInputStream("log.txt");
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String linea;
            // Leer el archivo linea por linea
            while ((linea = buffer.readLine()) != null)   {
                int tamaño= linea.length();
                InterpreteMandatos.ejecutaComando(linea.substring(19, tamaño));
                }
            // Cerramos el archivo
            entrada.close();
            return true;
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error a la hora de cargar los datos: " + e.getMessage());
        }
        return false;
    }

    
    public static boolean gestionArchivo() throws IOException {
        //Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
        if (new File("log.txt").exists()== false){
     	   	   archivo=new FileWriter(new File("log.txt"),false);
     	   	   return true;
     	   }else{
     		   archivo = new FileWriter(new File("log.txt"), true);  
     		   return false;
     	   }
 	
    }
    
}//Fin clase
