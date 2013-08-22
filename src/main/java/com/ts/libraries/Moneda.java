package com.ts.libraries;

public abstract class Moneda extends Objecto{

	public Decimal monto;
	
	public Moneda(Decimal monto){
		
		this.monto= monto;
	}

	public Decimal getMonto() {
		return monto;
	}

	public void setMonto(Decimal monto) {
		this.monto = monto;
	}

	public static boolean isColon(Hilera economia)
	{
		return "¢".equals(economia.getValor());
	}
	
	public static boolean isDolar(Hilera economia)
	{
		return "$".equals(economia.getValor());
	}
	public abstract String getSing();
}
