package com.ts.objects;

public abstract class Moneda {

	public static final String COLON = "colon" ;
	public static final String DOLAR = "dolar";
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
	
}
