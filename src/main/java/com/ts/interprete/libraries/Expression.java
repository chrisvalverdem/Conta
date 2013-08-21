package com.ts.interprete.libraries;

import com.ts.libraries.Objecto;

public class Expression {

	private Objecto objecto;

	public Expression(Objecto objecto) {
		this.objecto = objecto;
	}
	
	public Expression() {
		this.objecto = null;
	}

	public Objecto getObjecto() {
		return objecto;
	}

	public void setObjecto(Objecto objecto) {
		this.objecto = objecto;
	}
}
