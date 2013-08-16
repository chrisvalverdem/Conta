package com.ts.interprete.libraries;

public abstract class Moneda extends Expression {

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
