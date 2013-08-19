package com.ts.libraries;

import com.ts.interprete.libraries.Expression;


public class NumericLiteral extends Objecto {
	
	public int valor;
	
	public NumericLiteral(int valor)
	{
		this.valor = valor;
	}
	
	public String show()
	{
		return ""+this.valor;
	}

}
