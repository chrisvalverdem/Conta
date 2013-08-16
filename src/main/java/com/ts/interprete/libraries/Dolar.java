package com.ts.interprete.libraries;

public final class Dolar extends Moneda{

	public Dolar(double monto) {
		super(monto);		
	}
	
	public String toShow()
	{
		return "$"+monto;
	}
}
