package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Repo  {
	
	public static CopyOnWriteArrayList<Colaborador> listColaboradores =new CopyOnWriteArrayList<Colaborador> ();
	public static CopyOnWriteArrayList<Edificio> listEdificios=new CopyOnWriteArrayList<Edificio> ();
	public static CopyOnWriteArrayList<Proyecto> listProyectos =new CopyOnWriteArrayList<Proyecto> ();
	public static CopyOnWriteArrayList<Activo> listActivos =new CopyOnWriteArrayList<Activo> ();
	public static CopyOnWriteArrayList<Compania> listCompania =new CopyOnWriteArrayList<Compania> ();
		
	public static void AgregarColaborador(String nombre, int numeroCedula ){
		boolean indicador;			
		if(!listColaboradores.isEmpty()){			
			for (Colaborador colaborador : listColaboradores){		
				if (colaborador.getNumeroCedula() == numeroCedula)
				 indicador=true;
			}
		}
		if(indicador=false){
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula);	
		    listColaboradores.add(nuevoColaborador);			
		}					
	}
	public static void AgregarEdificio(String nombre){			
		boolean indicador;
		if(!listEdificios.isEmpty()){
			for (Edificio edificio : listEdificios){						
				if (edificio.getNombre().equalsIgnoreCase(nombre))
					 indicador=true;						
			}			
		}			
		if(indicador=false){
			Edificio nuevoEdificio = new Edificio(nombre);
		    listEdificios.add(nuevoEdificio);			
		}					
	}
	public static void AgregarProyecto(String nombre){				
		boolean indicador;
		if(!listProyectos.isEmpty()){
			for (Proyecto proyecto : listProyectos){							
				if (proyecto.getNombre().equalsIgnoreCase(nombre))
					 indicador=true;						
			}			
		}				
		if(indicador=false){
			Proyecto nuevoProyecto = new Proyecto(nombre);
			listProyectos.add(nuevoProyecto);			
		}					
	}
	public static void AgregarActivo(String nombre, int numeroPlaca){				
		boolean indicador;
		if(!listProyectos.isEmpty()){
			for (Activo activo : listActivos){							
				if ( activo.getNumeroPlaca() == numeroPlaca )
					 indicador=true;		
			}			
		}				
		if(indicador=false){
			Activo nuevoActivo = new Activo(nombre,numeroPlaca);
			listActivos.add(nuevoActivo);			
		}	
	}
	public static void AgregarCompania(String nombre,  int cedulaJuridica){				
		boolean indicador;
		if(!listCompania.isEmpty()){
			for (Compania compania: listCompania){							
				if (compania.getCedulaJuridica() == cedulaJuridica )
					 indicador=true;		
			}			
		}				
		if(indicador=false){
			Compania nuevaCompania = new Compania(nombre,cedulaJuridica);
			listCompania.add(nuevaCompania);			
		}	
	}
}//fin clase
