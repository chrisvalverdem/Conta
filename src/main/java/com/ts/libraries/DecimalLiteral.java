package com.ts.libraries;


public class DecimalLiteral extends Objecto {
	public double valor;
	
	public DecimalLiteral(double valor)
	{
		this.valor = valor;
	}
	
	@Override
	public String show()
	{
		return ""+this.valor;
	}

}
