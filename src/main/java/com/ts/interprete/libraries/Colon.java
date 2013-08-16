package com.ts.interprete.libraries;

public final class Colon extends Moneda{

	
	public Colon(double monto) {
		super(monto);

	}
	
	public String toShow()
	{
		return "¢"+monto;
	}

}
