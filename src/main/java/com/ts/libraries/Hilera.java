package com.ts.libraries;


public class Hilera extends Objecto{

	public String valor;
	
	public Hilera(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String show()
	{
		return this.valor;
	}

	public boolean esIgual(Hilera literal) {
		return valor.equals(literal.valor);
	}
	
	@Override
	public String toString() {
		return valor;
	}
	
	public boolean equals(Hilera literal) {
		return literal.esIgual(literal);
	}
}
