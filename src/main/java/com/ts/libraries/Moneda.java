package com.ts.libraries;

import com.ts.interprete.libraries.Expression;

public abstract class Moneda extends Objecto{

	public double monto;
	
	public Moneda(double monto){
		
		this.monto= monto;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public static boolean isColon(String economia)
	{
		return "¢".equals(economia);
	}
	
	public static boolean isDolar(String economia)
	{
		return "$".equals(economia);
	}
}
