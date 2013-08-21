package com.ts.libraries;

public class Mes {
	
	int mes;
	int annno;
	
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnno() {
		return annno;
	}
	public void setAnnno(int annno) {
		this.annno = annno;
	}
	public Mes(int mes, int anno){
		this.annno=anno;
		this.mes=mes;
		
	}
	public Numeric getMesAnno(){
		String temp = ""+ this.mes;
		temp+= this.annno;
		
		return new Numeric(Integer.parseInt(temp));
	}

}
