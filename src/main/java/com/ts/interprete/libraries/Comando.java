package com.ts.interprete.libraries;

public abstract class Comando{
	
	protected Expression expression;
	
	public Comando(Expression expression)
	{
		this.expression = expression;
	}
	
	protected Comando()
	{

	}
	
	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public abstract void execute();

}
