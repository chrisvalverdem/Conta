package com.ts.interprete.libraries;

import java.util.ArrayList;


public class Variable extends Expression {

	private String nombreInstancia;
	private String comando;
	private Parameters params = new Parameters();
	
	public Variable(String nombreInstancia, String comando, Parameters params) {
		this.nombreInstancia = nombreInstancia;
		this.comando =comando;
		this.params =params;
	}

	
	
	public String getNombreInstancia() {
		return nombreInstancia;
	}



	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}



	public String getComando() {
		return comando;
	}



	public void setComando(String comando) {
		this.comando = comando;
	}



	public Parameters getParams() {
		return params;
	}



	public void setParams(Parameters params) {
		this.params = params;
	}

}
