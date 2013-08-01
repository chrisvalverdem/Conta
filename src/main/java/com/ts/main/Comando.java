package com.ts.main;

public class Comando {
	
	private String instance;
	private String metodo;
	private String [] parametros;		
	
	public Comando(String instance, String metodo, String[] parametros) {
		this.instance = instance;
		this.metodo = metodo;
		this.parametros = parametros;		
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String[] getParametros() {
		return parametros;
	}

	public void setParametros(String[] parametros) {
		this.parametros = parametros;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	

}
