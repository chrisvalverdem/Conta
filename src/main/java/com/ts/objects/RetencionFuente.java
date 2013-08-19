package com.ts.objects;

public class RetencionFuente {

	private static String fecha;
	private static Moneda monto;
	
	public  RetencionFuente (String fecha, Moneda monto ) {
		
		RetencionFuente.fecha=fecha;
		RetencionFuente.monto=monto;		
	}
	
	public static String getFecha() {
		return fecha;
	}
	public static void setFecha(String fecha) {
		RetencionFuente.fecha = fecha;
	}
	public static Moneda getMonto() {
		return monto;
	}
	public static void setMonto(Moneda monto) {
		RetencionFuente.monto = monto;
	}	
	
}
