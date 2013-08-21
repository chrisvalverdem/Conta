package com.ts.libraries;

public class RangoRenta extends Object {

	Moneda intervaloInferior;
	Moneda intervaloSuperior;
	Decimal intervaloPorciento;
	Hilera identificador;
	
	public RangoRenta(Moneda inf, Moneda sup, Decimal porcent, Hilera ident){
		
		this.intervaloInferior= inf;
		this.intervaloSuperior= sup;
		this.intervaloPorciento= porcent;
		this.identificador= ident;
	}
	
	public Hilera getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Hilera identificador) {
		this.identificador = identificador;
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

	public Decimal getIntervaloPorciento() {
		return intervaloPorciento;
	}

	public void setIntervaloPorciento(Decimal intervaloPorciento) {
		this.intervaloPorciento = intervaloPorciento;
	}
	
	
}
