package com.ts.libraries;


public class NumericLiteral extends Objecto {
	
	public int valor;
	
	public NumericLiteral(int valor)
	{
		this.valor = valor;
	}
	
	@Override
	public String show()
	{
		return ""+this.valor;
	}

}
