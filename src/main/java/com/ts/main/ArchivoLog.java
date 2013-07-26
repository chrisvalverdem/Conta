package com.ts.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class ArchivoLog {
   
	static FileWriter archivo; //nuestro archivo log

    public static void crearLog(String operacion) throws IOException {

       //Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
       if (new File("log.txt").exists()==false){
    	   archivo=new FileWriter(new File("log.txt"),false);
    	   }
            archivo = new FileWriter(new File("log.txt"), true);
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
   
    //Como ejemplo ponemos el caso que se este agregando nombres de personas a un vector
    //y queremos guardar en el Log cada vez que ocurre el evento, seria así:

}
