package com.ts.objects;

import java.util.Date;

public class Colaborador {
	
	String nombreCompleto;
	String direccion;
	String estadoCivil;
	int numeroCedula;
	int codigo;
	int telefonoCelular;
	int telefonoCasa;	
	Date fechaIngreso;
	Date fechaSalida;
	Date FechaNacimiento;
	int numeroHijos;
	int salarioInicial;
	
	public Colaborador() {
		
	}		
	public Colaborador(String nombreCompleto, String direccion,
			String estadoCivil, int numeroCedula, int codigo,
			int telefonoCelular, int telefonoCasa, Date fechaIngreso,
			Date fechaSalida, Date fechaNacimiento, int numeroHijos,
			int salarioInicial) {
		
		this.nombreCompleto = nombreCompleto;
		this.direccion = direccion;
		this.estadoCivil = estadoCivil;
		this.numeroCedula = numeroCedula;
		this.codigo = codigo;
		this.telefonoCelular = telefonoCelular;
		this.telefonoCasa = telefonoCasa;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.FechaNacimiento = fechaNacimiento;
		this.numeroHijos = numeroHijos;
		this.salarioInicial = salarioInicial;
	}
	
	public Colaborador(String nombreCompleto, int numeroCedula) {		
		this.nombreCompleto = nombreCompleto;		
		this.numeroCedula = numeroCedula;		
	}	
		
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public int getNumeroCedula() {
		return numeroCedula;
	}
	public void setNumeroCedula(int numeroCedula) {
		this.numeroCedula = numeroCedula;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getTelefonoCelular() {
		return telefonoCelular;
	}
	public void setTelefonoCelular(int telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}
	public int getTelefonoCasa() {
		return telefonoCasa;
	}
	public void setTelefonoCasa(int telefonoCasa) {
		this.telefonoCasa = telefonoCasa;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Date getFechaNacimiento() {
		return FechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}
	public int getNumeroHijos() {
		return numeroHijos;
	}
	public void setNumeroHijos(int numeroHijos) {
		this.numeroHijos = numeroHijos;
	}
	public int getSalarioInicial() {
		return salarioInicial;
	}
	public void setSalarioInicial(int salarioInicial) {
		this.salarioInicial = salarioInicial;
	}	
	
}//fin clase

