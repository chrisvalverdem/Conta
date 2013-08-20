package com.ts.libraries;

import com.ts.interprete.libraries.Expression;

public class FechaHora extends Objecto{
	
	private int dia;
	private int mes;
	private int anno;
	private int hora;
	private int minutos;
	private int segundos;
	
	public FechaHora(int dia, int mes, int anno)
	{
		this.dia = dia;
		this.mes = mes;
		this.anno = anno;
	}
	
	public FechaHora(int dia, int mes, int anno, int hora, int minutos, int segundos)
	{
		this.hora = hora;
		this.minutos = minutos;
		this.segundos = segundos;
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
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getSegundos() {
		return segundos;
	}
	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}
	
	public String show()
	{
		String result = String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+anno;
		
		if(hora != 0 && minutos != 0 && segundos != 0)
		{
			result += " "+String.format("%02d", hora)+":"+String.format("%02d", minutos)+":"+String.format("%02d", segundos);
		}
		
		return result;
	}
}
