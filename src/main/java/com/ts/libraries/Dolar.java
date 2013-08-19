package com.ts.libraries;

public final class Dolar extends Moneda{

	public Dolar(double monto) {
		super(monto);		
	}
	
	public String show()
	{
		return "$"+monto;
	}
}
