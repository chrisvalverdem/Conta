package com.ts.interprete.libraries;


public class Literal extends Expression {

	private String valor;
	
	public Literal(String valor) {
		this.valor = valor;
	}

	public String toShow()
	{
		return this.valor;
	}
}
