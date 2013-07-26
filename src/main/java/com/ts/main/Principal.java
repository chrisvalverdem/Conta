package com.ts.main;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ts.comands.calculaSalario;
import com.ts.objects.Colaborador;

public class Principal {

	@SuppressWarnings("rawtypes")
	CopyOnWriteArrayList colaboradores = new CopyOnWriteArrayList();
	static Colaborador selec= null;
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public Principal(){
				

		Colaborador colaborador1 = new Colaborador("axel", "asd", "asd", 0, 0, 0, 0, null, null, null, 0, 10);
		colaboradores.add(colaborador1);
		

		Colaborador colaborador2 = new Colaborador("adadad", "ad", "ad", 0, 0, 0, 0, null, null, null, 0, 10);
		colaboradores.add(colaborador2);
		
		this.selec= (Colaborador) colaboradores.get(0);

		
		calculaSalario.asigna(selec);
		
		Colaborador colaModi= calculaSalario.retorna();
		colaboradores.add(0, colaModi);
		
		if(calculaSalario.calcular()){
			System.err.print("\n" + "YES.");
		}else{
			System.err.print("\n" + "NO.");
			}
	}

	public static void main(String[] args) {
		new Principal();
		
	
	}

}
