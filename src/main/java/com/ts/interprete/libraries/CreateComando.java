package com.ts.interprete.libraries;

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
	
	@Override
	public void execute()
	{
		expression.objecto.save(nombreInstancia);
	}
	
	
}
