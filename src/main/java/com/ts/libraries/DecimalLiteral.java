package com.ts.libraries;

import com.ts.interprete.libraries.Expression;
import com.ts.objects.CommandException;


public class DecimalLiteral extends Objecto {
	public double valor;
	
	public DecimalLiteral(double valor)
	{
		this.valor = valor;
	}
	
	public String show()
	{
		return ""+this.valor;
	}

}
