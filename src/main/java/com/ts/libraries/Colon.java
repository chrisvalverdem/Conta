package com.ts.libraries;

public final class Colon extends Moneda{

	
	public Colon(double monto) {
		super(monto);

	}
	
	public String show()
	{
		return "¢"+monto;
	}

}
