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
	private String filePath;

	public ArchivoLog(String filePath)
	{
		this.setFilePath(filePath);
	}

    public void crearRegistroLog(String operacion) throws IOException {
    	String dia= "0";
		String mes= "0";
    	creaArchivoDeLogParaComandosEjecutadosSiNoExiste();
    	Calendar fechaActual = Calendar.getInstance();
        
    	if(fechaActual.get(Calendar.DAY_OF_MONTH)< 10){
    		dia+=String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH));
    	}else{
    		dia=String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH));
    	}
    	if(fechaActual.get(Calendar.MONTH)+1 < 10){
    		mes+=String.valueOf(fechaActual.get(Calendar.MONTH)+1);
    	}else{
    		mes= String.valueOf(fechaActual.get(Calendar.MONTH)+1);
    	}
    	
        archivo.write(dia +"/"+ mes +"/"+String.valueOf(fechaActual.get(Calendar.YEAR))
                +" "+String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
                +":"+String.valueOf(fechaActual.get(Calendar.MINUTE))
                +":"+String.valueOf(fechaActual.get(Calendar.SECOND))+ " " + operacion+ "\r\n");
        archivo.close(); 
    }
   
    public ArrayList<String> getAllCommandsFromLog() throws IOException{
    	
    	FileInputStream fstream = new FileInputStream(filePath);
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
        File logDeComandosEjecutados = new File(getFilePath());
        archivo = new FileWriter(logDeComandosEjecutados, hagaAppendDeLosComandosEjecutados);
    }
    
    public boolean existeUnLogPrevio() throws IOException 
    {
    	return new File(getFilePath()).exists();
    }

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
    
}
