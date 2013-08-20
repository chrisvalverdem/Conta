package com.ts.main;

import java.util.ArrayList;

import com.ts.libraries.Moneda;
import com.ts.libraries.RangoRenta;

public class Hacienda {
	
	private static Moneda montoConyuge;
	private static Moneda montoHijo;
	public static ArrayList<RangoRenta> intervalosRenta = new ArrayList<RangoRenta>();
	
	public  Hacienda () {
		
	}

	public static Moneda getMontoConyuge() {
		return montoConyuge;
	}

	public static void setMontoConyuge(Moneda montoConyuge) {
		Hacienda.montoConyuge = montoConyuge;
	}

	public static ArrayList<RangoRenta> getIntervalosRenta() {
		return intervalosRenta;
	}

	public static void setIntervalosRenta(ArrayList<RangoRenta> intervalosRenta) {
		Hacienda.intervalosRenta = intervalosRenta;
	}

	public static Moneda getMontoHijo() {
		return montoHijo;
	}

	public static void setMontoHijo(Moneda montoHijo) {
		Hacienda.montoHijo = montoHijo;
	}

}
