package com.ts.interprete.libraries;

public class ShowComando extends Comando{
		
	public ShowComando(Expression expression) {
		super(expression);
	}

	@Override
	public void execute()
	{
		System.out.println(expression.getObjecto().show());
	}

}
