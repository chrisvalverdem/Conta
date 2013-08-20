package com.ts.libraries;

public final class Colon extends Moneda{

	
	public Colon(double monto) {
		super(monto);

	}
	
	@Override
	public String show()
	{
		return "¢"+monto;
	}	
	@Override
	public String getSing() {
		return "¢";
	}
}
