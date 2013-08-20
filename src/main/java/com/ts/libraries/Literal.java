package com.ts.libraries;


public class Literal extends Objecto{

	public String valor;
	
	public Literal(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String show()
	{
		return this.valor;
	}

	public boolean esIgual(Literal literal) {
		return valor.equals(literal.valor);
	}
	
	@Override
	public String toString() {
		return valor;
	}
	
	public boolean equals(Literal literal) {
		return literal.esIgual(literal);
	}
}
