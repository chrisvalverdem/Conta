package com.ts.libraries;


public class Numeric extends Objecto {
	
	public int valor;
	
	public Numeric(int valor)
	{
		this.valor = valor;
	}
	
	@Override
	public String show()
	{
		return ""+this.valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
