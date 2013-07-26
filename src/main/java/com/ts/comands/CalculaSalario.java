package com.ts.comands;

import com.ts.objects.Colaborador;

public class CalculaSalario extends ComandoImp {

	static double horasLaboradas;
	static double horasExtras;
	static double valorHora;
	static double salarioDevengado;
	static double salarioNeto;
	static double deducciones;
	static Colaborador colaborador;

	public void execute(){
		
	
				
	}//execute
	
	public static void asigna(Colaborador colabora){
	colaborador=colabora;
	}
	
	public static boolean calcular(){
		
		double horasTotales;
		double valorHoraEx;
		double salarioExtra = 0 ;
		double salarioLaborado =0;
		
		horasExtras= colaborador.getHorasExtra();
		horasTotales= colaborador.getHorasLaboradas();
		horasLaboradas= horasTotales - horasExtras;
		valorHora= colaborador.getValorHora();
		valorHoraEx= valorHora * 1.5;
		salarioExtra= horasExtras * valorHoraEx;
		salarioLaborado= horasLaboradas * valorHora;
		salarioDevengado= salarioExtra + salarioLaborado;
		
		calculaDeducciones();
		
		salarioNeto= salarioDevengado - deducciones;
		
		colaborador.setSalarioInicial(salarioNeto);
		System.err.print("Devengado. " + salarioDevengado + "\t"  + "Deducciones. " + deducciones+ "\t" + "Neto. " + salarioNeto + "\n");
		
		if(colaborador.getSalarioInicial()!= 0){
			
			return true;
		}else{
			
			return false;
		}
		
	}
	
	public static Colaborador retorna(){
	return colaborador;
	}
	public static void calculaDeducciones(){
		double porciento = 0.0975;
		deducciones = salarioDevengado * porciento;
		
	}
	

}
