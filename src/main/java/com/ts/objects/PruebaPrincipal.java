package com.ts.objects;

public class PruebaPrincipal {
	
	Colaborador colaborador = null;
	Edificio edificio= null;
			
	public void crearColaborador () {		
		colaborador = new Colaborador();
		colaborador.setNombreCompleto("Laura Quesada B.");
		colaborador.setNumeroCedula(111880703);		
		
	}
	
	public void crearEdificio () {
		edificio = new Edificio ();
		edificio.setNombre("San Ramon");
		edificio.setDireccion("San Ramon,Costa Rica");
		edificio.setListColabotadores(colaborador);
				
	}
	public void mostrarDatos () {
		
		System.out.println(" Lista " + edificio.listColaboradores.size() + 
				" Colaboradores"); 
		
		//edificio.listColaboradores.get(1);
		
	}
	
	public static void main(String[] args){
		
		 
	}

}