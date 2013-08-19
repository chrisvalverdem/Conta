package com.ts.interprete.libraries;

import com.ts.libraries.Objecto;

public class CreateComando extends Comando{
	
	private String nombreInstancia;

	public CreateComando( String nombreInstancia, Expression expression)
	{
		this.nombreInstancia = nombreInstancia;
		this.expression = expression;
	}

	public String getNombreInstancia() {
		return nombreInstancia;
	}

	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}
	
	public void execute()
	{
		expression.objecto.save(nombreInstancia);
	}
	
	
}
