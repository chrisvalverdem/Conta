package com.ts.interprete.libraries;

import com.ts.objects.CommandException;


public class DecimalLiteral extends Expression {
	private double valor;
	
	public DecimalLiteral(double valor)
	{
		this.valor = valor;
	}
	
	public String toShow()
	{
		return ""+this.valor;
	}

}
