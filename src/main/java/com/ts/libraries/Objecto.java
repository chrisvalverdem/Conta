package com.ts.libraries;

import com.ts.interprete.libraries.Expression;
import com.ts.objects.CommandException;

public abstract class Objecto {

	public String show() {
		throw new CommandException("El metodo show no a sido implementado para "+this.getClass().getName());
	}
	
	public void save(String variableInstancia) {
		throw new CommandException("El metodo save no a sido implementado para "+this.getClass().getName());
	}
	
	public boolean esIgual(Expression expression) {
		throw new CommandException("El metodo esIgual no a sido implementado para "+this.getClass().getName());
	}
}
