package com.ts.main;

import java.util.ArrayList;
import com.ts.libraries.RangoRenta;

public class Hacienda {
	

	public static ArrayList<RangoRenta> intervalosRenta = new ArrayList<RangoRenta>();
	
	public  Hacienda () {
		
	}
	public static ArrayList<RangoRenta> getIntervalosRenta() {
		return intervalosRenta;
	}

	public static void setIntervalosRenta(ArrayList<RangoRenta> intervalosRenta) {
		Hacienda.intervalosRenta = intervalosRenta;
	}
}
