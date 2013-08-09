package com.ts.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ArchivoLog {
   
	private FileWriter archivo; 
	public final static String LOG_NAME="log.txt";
	private String filePath;

	public ArchivoLog(String filePath)
	{
		this.setFilePath(filePath);
	}

	public void crearRegistroLog(String operacion) throws IOException {
    	
    	creaArchivoDeLogParaComandosEjecutadosSiNoExiste();
    	
        archivo.write(operacion+ "\r\n");
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
            resultado.add(linea);
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
