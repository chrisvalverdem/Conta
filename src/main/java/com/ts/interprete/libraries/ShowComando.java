package com.ts.interprete.libraries;

public class ShowComando extends Expression{
	
	private Expression expression;
	
	public ShowComando(Expression expression)
	{
		this.expression = expression;
	}
	
	public String toShow()
	{
		return expression.toShow();
	}

}
