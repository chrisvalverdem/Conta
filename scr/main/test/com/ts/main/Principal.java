package com.ts.main;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ts.comands.CalculaSalario;
import com.ts.objects.Colaborador;
import com.ts.objects.Edificio;
import com.ts.objects.Repo;

public class Principal {

	
	CopyOnWriteArrayList<Colaborador> colaboradores = new CopyOnWriteArrayList();
	static Colaborador selec= null;
	
	public Principal(){
				

		Colaborador colaborador1 = new Colaborador("axel", "asd", "asd", 0, 0, 0, 0, null, null, null, 0, 10);
		colaboradores.add(colaborador1);
		

		Colaborador colaborador2 = new Colaborador("adadad", "ad", "ad", 0, 0, 0, 0, null, null, null, 0, 10);
		colaboradores.add(colaborador2);
		
		this.selec= (Colaborador) colaboradores.get(0);

		
		CalculaSalario.asigna(selec);
		
		Colaborador colaModi= CalculaSalario.retorna();
		colaboradores.add(0, colaModi);
		
		if(CalculaSalario.calcular()){
			System.err.print("\n" + "YES.");
		}else{
			System.err.print("\n" + "NO.");
			}
	}

	public static void main(String[] args) {
		new Principal();	
	}
}
