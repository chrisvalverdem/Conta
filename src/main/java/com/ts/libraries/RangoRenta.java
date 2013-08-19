package com.ts.libraries;

import com.ts.interprete.libraries.Expression;

public class RangoRenta extends Object {

	Moneda intervaloInferior;
	Moneda intervaloSuperior;
	double intervaloPorciento;
	String identificador;
	
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public RangoRenta(Moneda inf, Moneda sup, double porcent, String ident){
		
		this.intervaloInferior= inf;
		this.intervaloSuperior= sup;
		this.intervaloPorciento= porcent;
		this.identificador= ident;
	}

	public Moneda getIntervaloInferior() {
		return intervaloInferior;
	}

	public void setIntervaloInferior(Moneda intervaloInferior) {
		this.intervaloInferior = intervaloInferior;
	}

	public Moneda getIntervaloSuperior() {
		return intervaloSuperior;
	}

	public void setIntervaloSuperior(Moneda intervaloSuperior) {
		this.intervaloSuperior = intervaloSuperior;
	}

	public double getIntervaloPorciento() {
		return intervaloPorciento;
	}

	public void setIntervaloPorciento(double intervaloPorciento) {
		this.intervaloPorciento = intervaloPorciento;
	}
	
	
}
