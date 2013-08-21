package com.ts.libraries;

public class Boolean extends Objecto{
	boolean valor;
	
	public Boolean(boolean valor)
	{
		this.valor = valor;
	}
	
	public boolean esIgual(Objecto bool)
	{
		return bool instanceof Boolean && this.valor == ((Boolean)bool).valor;
	}
	
	public boolean esIgual(boolean valor) {
		if(valor == this.valor){
			return true;
		}else{
			return false;
		}
	}
}
