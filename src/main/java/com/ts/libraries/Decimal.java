package com.ts.libraries;


public class Decimal extends Objecto {
	public double valor;
	
	public Decimal(double valor)
	{
		this.valor = valor;
	}
	
	@Override
	public String show()
	{
		return ""+this.valor;
	}

}
