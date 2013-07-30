package com.ts.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class ArchivoLog {
   
	private FileWriter archivo; 
	public final static String LOG_NAME="log.txt";

    public void crearRegistroLog(String operacion) throws IOException {

    	creaArchivoDeLogParaComandosEjecutadosSiNoExiste();
    	Calendar fechaActual = Calendar.getInstance();
        
        archivo.write(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
                +"/"+String.valueOf(fechaActual.get(Calendar.MONTH)+1)
                +"/"+String.valueOf(fechaActual.get(Calendar.YEAR))
                +" "+String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
                +":"+String.valueOf(fechaActual.get(Calendar.MINUTE))
                +":"+String.valueOf(fechaActual.get(Calendar.SECOND))+ " " + operacion+ "\r\n");
        archivo.close(); 
    }
   
    public ArrayList<String> getAllCommandsFromLog() throws IOException{
    	
    	FileInputStream fstream = new FileInputStream(LOG_NAME);
        DataInputStream entrada = new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
        String linea;
        ArrayList<String> resultado = new ArrayList<String>();

        while ((linea = buffer.readLine()) != null)
        {
            int tamanno= linea.length();
            resultado.add(linea.substring(19, tamanno));
        }

        entrada.close();
        return resultado;
    }

    
    private void creaArchivoDeLogParaComandosEjecutadosSiNoExiste() throws IOException 
    {
    	boolean hagaAppendDeLosComandosEjecutados = true;
        File logDeComandosEjecutados = new File(LOG_NAME);
        archivo = new FileWriter(logDeComandosEjecutados, hagaAppendDeLosComandosEjecutados);
    }
    
    public boolean existeUnLogPrevio() throws IOException 
    {
    	return new File(LOG_NAME).exists();
    }
    
}
