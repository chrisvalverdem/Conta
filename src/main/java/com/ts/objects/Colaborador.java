package com.ts.objects;

import java.util.ArrayList;
import java.util.Date;

public class Colaborador extends Objecto {
	String nombre;
	String cedula;
	Date fechaNacimiento;
	Date fechaIngresoEmpresa;
	Boolean estadoCivil;
	String telefono;
	int cantidadHijos;
	Moneda salario;
	
	public static String comandosValidos[] ={"CREAR_COLABORADOR"};
	public ArrayList<Date> vacaciones = new ArrayList<Date>();

	public Colaborador(String nombre, String cedula){
		this.nombre = nombre;
		this.cedula = cedula;
	}
	
	public Colaborador(String nombre, String cedula,Date fechaNacimiento,
			Date fechaIngreso, boolean estado, String telefono,  int numeroHijos,
			Moneda salario) {			
		this.nombre = nombre;
		this.cedula = cedula;
		this.fechaNacimiento= fechaNacimiento;
		this.fechaIngresoEmpresa= fechaIngreso;
		this.estadoCivil= estado;
		this.telefono= telefono;
		this.cantidadHijos= numeroHijos;
		this.salario = salario;
		
	}

	public ArrayList<Date> getVacaciones() {
		return vacaciones;
	}

	public void setVacaciones(Date fecha) {
		vacaciones.add(fecha);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaIngresoEmpresa() {
		return fechaIngresoEmpresa;
	}

	public void setFechaIngresoEmpresa(Date fechaIngresoEmpresa) {
		this.fechaIngresoEmpresa = fechaIngresoEmpresa;
	}

	public Boolean getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(Boolean estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(int cantidadHijos) {
		this.cantidadHijos = cantidadHijos;
	}

	public Moneda getSalario() {
		return salario;
	}

	public void setSalario(Moneda nuevoSalario) {
		this.salario = nuevoSalario;
	}

	public static String[] getComandosValidos() {
		return comandosValidos;
	}

	public static void setComandosValidos(String[] comandosValidos) {
		Colaborador.comandosValidos = comandosValidos;
	}
	
}//fin clase

