package com.ts.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.ts.libraries.CommandException;
import com.ts.libraries.Compania;
import com.ts.libraries.Hilera;
import com.ts.libraries.Moneda;
import com.ts.libraries.Objecto;
import com.ts.libraries.RangoRenta;
import com.ts.main.Hacienda;
import com.ts.main.Sistema;

public class Repo  {
	
	private static HashMap<String, Objecto> tablaDeSimbolos = new HashMap<String, Objecto>();

	public static void save(Hilera string, Objecto object)
	{
		boolean existeAlgunaInstancea = ! tablaDeSimbolos.isEmpty();
		if(existeAlgunaInstancea){
			if(tablaDeSimbolos.containsKey(string)){			
				throw new CommandException("La instancia " + string+ " ya existe, cambiela por una diferente.");				
			}					
		}	
		
		tablaDeSimbolos.put(string.valor, object);
	}

	public static Objecto getData(Hilera key)
	{
		return tablaDeSimbolos.get(key.valor);
	}
	public static Objecto getData(String key)
	{
		return getData(new Hilera(key));
	}
	public static HashMap<String, Objecto> getData()
	{
		return tablaDeSimbolos;
	}
	public static void clean() {
		tablaDeSimbolos.clear();	
	}
	public static ArrayList<Object> get(Class<?> clase)
	{			
		String key;
		Object value;
		Iterator<String> iterator = Repo.getData().keySet().iterator();
		ArrayList<Object> resultado = new ArrayList<Object>();
		while (iterator.hasNext()) {
		    key = iterator.next();
		    value = Repo.getData().get(key);
		    boolean esLaClaseEsperada = value.getClass().equals(clase); 
		    if(esLaClaseEsperada)
		    {
		    	resultado.add(value);
		    }
		}	
		return resultado;
	}
	
	public static void limpiaListas() {
		tablaDeSimbolos.clear();	
	}

}//fin clase
