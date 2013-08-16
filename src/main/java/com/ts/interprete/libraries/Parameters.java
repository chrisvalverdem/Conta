package com.ts.interprete.libraries;

import java.util.ArrayList;

public class Parameters  extends Expression
{
	private ArrayList<Expression> params = new ArrayList<Expression>();
	
	public void add(Expression param)
	{
		this.params.add(param);
	}
	
	public Expression get(int index)
	{
		return this.params.get(index);
	}
	
	public int size()
	{
		return this.params.size();
	}
}
