package com.ts.libraries;

import java.io.ObjectInputStream.GetField;


public class Fecha extends Objecto{
	
	private int dia;
	private int mes;
	private int anno;
	
	public Fecha(int dia, int mes, int anno)
	{
		this.dia = dia;
		this.mes = mes;
		this.anno = anno;
	}
	
	public Fecha(int dia, int mes, int anno, int hora, int minutos, int segundos)
	{
		this.dia = dia;
		this.mes = mes;
		this.anno = anno;
	}
	
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String show()
	{
		String result = String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+anno;
		return result;
	}

	public static int cantidadDiasEntreFechas(Fecha fechaIngTem1, Fecha fechacomando) {
		//(int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))
		throw new CommandException("El metodo cantidadDiasEntreFechas no se a implementado para Fecha.");

	}
}
