package com.ts.libraries;

import java.util.ArrayList;
import java.util.Date;

import com.ts.db.Repo;
import com.ts.interprete.libraries.Expression;
import com.ts.objects.CommandException;

public class Colaborador extends Objecto {
	String nombre;
	String cedula;
	Fecha fechaNacimiento;
	Fecha fechaIngresoEmpresa;
	String estadoCivil;
	String telefono;
	int cantidadHijos;
	Moneda salario;	
	
	public ArrayList<Date> vacaciones = new ArrayList<Date>();
	
	public Colaborador(String nombre, String cedula, Fecha fechaNacimiento, Fecha fechaIngreso, String estado, String telefono,  int numeroHijos, Dolar salario) {			
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

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Fecha getFechaIngresoEmpresa() {
		return fechaIngresoEmpresa;
	}

	public void setFechaIngresoEmpresa(Fecha fechaIngresoEmpresa) {
		this.fechaIngresoEmpresa = fechaIngresoEmpresa;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
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
	
	public void save(String variableInstancia)
	{
			boolean existeElColaborador =  Colaborador.get(cedula) != null;
				
			if (existeElColaborador)
			{
				throw new CommandException("El Colaborador: " + nombre+ " tiene el mismo numero de cedula.");
			}
			Repo.save(variableInstancia, this);
			System.out.println("El Colaborador " + nombre + " se le agrego exitosamente.");
	}
	
	public static Colaborador get(String cedula)
	{			
		ArrayList<Object> lista = Repo.get(Colaborador.class);
		for(Object exp : lista)
		{
			Colaborador comp = (Colaborador)exp;
			if(comp.getCedula().equals(cedula))
	    	{
	    		return comp;
	    	}
		}
		
		return null;
	}
	
	
}//fin clase

