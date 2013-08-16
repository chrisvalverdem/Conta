package com.ts.interprete.libraries;

import com.ts.objects.CommandException;

public class Expression {

	public String toShow() {
		throw new CommandException("El metodo toShow no a sido implementado para "+this.getClass().getName());
	}

}
