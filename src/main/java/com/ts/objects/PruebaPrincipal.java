package com.ts.objects;

public class PruebaPrincipal {
	
	public static void main(String[] args){	
		
		Colaborador colaborador1 = new Colaborador();
		colaborador1.setNombreCompleto("Laura Quesada B.");
		colaborador1.setNumeroCedula(111880703);	
		//
		Colaborador colaborador2 = new Colaborador("Esteban Irias Q.",222222222);	
		
		Edificio edificio=new Edificio ();
		edificio.crearListas(1);
		edificio.agregarColaboradorList(colaborador1);
		edificio.agregarColaboradorList(colaborador2);		
		
		System.out.println(" Lista: "+edificio.listColaboradores); 
		
		//for (int contador=0; contador < edificio.listColaboradores.size(); contador++)
			
			//System.out.println(edificio.listColaboradores.get(contador));
	}

}