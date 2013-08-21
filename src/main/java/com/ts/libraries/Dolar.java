package com.ts.libraries;

public final class Dolar extends Moneda{

	public Dolar(Decimal monto) {
		super(monto);		
	}
	
	@Override
	public String show()
	{
		return "$"+monto;
	}

	@Override
	public String getSing() {
		return "$";
	}
}
