package com.ts.libraries;

public class Boolean extends Objecto{
	boolean valor;
	
	public Boolean(boolean valor)
	{
		this.valor = valor;
	}
	
	public boolean esIgual(Objecto bool)
	{
		return this.valor == ((Boolean)bool).valor;
	}
	
}
