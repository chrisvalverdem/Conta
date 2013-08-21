package com.ts.interprete.libraries;

import com.ts.libraries.Hilera;
public class CreateComando extends Comando{
	
	private Hilera nombreInstancia;

	public CreateComando( Hilera nombreInstancia, Expression expression)
	{
		this.nombreInstancia = nombreInstancia;
		this.expression = expression;
	}

	public Hilera getNombreInstancia() {
		return nombreInstancia;
	}

	public void setNombreInstancia(Hilera nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}
	
	@Override
	public void execute()
	{
		expression.getObjecto().save(nombreInstancia);
	}
	
	
}
