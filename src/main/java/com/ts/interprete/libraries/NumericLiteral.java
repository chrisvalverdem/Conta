package com.ts.interprete.libraries;


public class NumericLiteral extends Expression {
	
	private long valor;
	
	public NumericLiteral(long valor)
	{
		this.valor = valor;
	}
	
	public String toShow()
	{
		return ""+this.valor;
	}

}
