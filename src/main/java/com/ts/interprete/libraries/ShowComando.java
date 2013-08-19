package com.ts.interprete.libraries;

public class ShowComando extends Comando{
		
	public ShowComando(Expression expression) {
		super(expression);
	}

	public void execute()
	{
		System.out.println(expression.objecto.show());
	}

}
