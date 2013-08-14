package com.ts.main;

import com.ts.objects.Moneda;

public class Hacienda {
	
	private static Moneda montoConyuge;
	private static Moneda montoHijo;
	
	public  Hacienda () {
		
	}

	public static Moneda getMontoConyuge() {
		return montoConyuge;
	}

	public static void setMontoConyuge(Moneda montoConyuge) {
		Hacienda.montoConyuge = montoConyuge;
	}

	public static Moneda getMontoHijo() {
		return montoHijo;
	}

	public static void setMontoHijo(Moneda montoHijo) {
		Hacienda.montoHijo = montoHijo;
	}

}
