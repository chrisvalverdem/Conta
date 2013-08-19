package com.ts.libraries;

import com.ts.interprete.libraries.Expression;


public class Literal extends Objecto{

	public String valor;
	
	public Literal(String valor) {
		this.valor = valor;
	}
	
	public String show()
	{
		return this.valor;
	}

	public boolean esIgual(Literal literal) {
		return valor.equals(literal.valor);
	}
	
	public String toString() {
		return valor;
	}
	
	public boolean equals(Literal literal) {
		return literal.esIgual(literal);
	}
}
