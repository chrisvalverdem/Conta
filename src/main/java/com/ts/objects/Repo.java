package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Repo  {
	
	public static CopyOnWriteArrayList<Colaborador> listColaboradores =new CopyOnWriteArrayList<Colaborador> ();
	public static CopyOnWriteArrayList<Edificio> listEdificios=new CopyOnWriteArrayList<Edificio> ();
	public static CopyOnWriteArrayList<Proyecto> listProyectos =new CopyOnWriteArrayList<Proyecto> ();
	public static CopyOnWriteArrayList<Activo> listActivos =new CopyOnWriteArrayList<Activo> ();
	public static CopyOnWriteArrayList<Compania> listCompania =new CopyOnWriteArrayList<Compania> ();
		
	public static void AgregarColaborador(String nombre, int numeroCedula ){
		Colaborador nuevoColaborador=null;
		@SuppressWarnings("unused")
		boolean indicador;		
		if(!listColaboradores.isEmpty()){					
			for (int contador=0; contador <= listColaboradores.size(); contador++){				
				Colaborador colaborador= listColaboradores.get(contador);			
				if (colaborador.getNumeroCedula() == numeroCedula )
					 indicador=true;
				}
			}
		if(indicador=false){
			nuevoColaborador = new Colaborador(nombre, numeroCedula);
		    listColaboradores.add(nuevoColaborador);			
		}					
	}
	public static void AgregarEdificio(String nombre){		
		Edificio nuevoEdificio=null;
		@SuppressWarnings("unused")
		boolean indicador;
		if(!listEdificios.isEmpty()){
			for (int contador=0; contador <=listEdificios.size(); contador++){				
				Edificio edificio= listEdificios.get(contador);			
				if (edificio.getNombre().equalsIgnoreCase(nombre))
					 indicador=true;						
			}			
		}			
		if(indicador=false){
			nuevoEdificio = new Edificio(nombre);
		    listEdificios.add(nuevoEdificio);			
		}					
	}
	public static void AgregarProyecto(String nombre){		
		Proyecto nuevoProyecto=null;
		@SuppressWarnings("unused")
		boolean indicador;
		if(!listProyectos.isEmpty()){
			for (int contador=0; contador <=listProyectos.size(); contador++){				
				Proyecto proyecto= listProyectos.get(contador);			
				if (proyecto.getNombre().equalsIgnoreCase(nombre))
					 indicador=true;							
			}			
		}				
		if(indicador=false){
			nuevoProyecto = new Proyecto(nombre);
			listProyectos.add(nuevoProyecto);			
		}					
	}
	public static void AgregarActivo(String nombre, int numeroPlaca){		
		Activo nuevoActivo=null;	
		@SuppressWarnings("unused")
		boolean indicador;
		if(!listProyectos.isEmpty()){
			for (int contador=0; contador <=listActivos.size(); contador++){				
				Activo activo= listActivos.get(contador);			
				if ( activo.getNumeroPlaca() == numeroPlaca )
					 indicador=true;		
			}			
		}				
		if(indicador=false){
			nuevoActivo = new Activo(nombre,numeroPlaca);
			listActivos.add(nuevoActivo);			
		}	
	}
	public static void AgregarCompania(String nombre,  int cedulaJuridica){		
		Compania nuevaCompania=null;	
		@SuppressWarnings("unused")
		boolean indicador;
		if(!listCompania.isEmpty()){
			for (int contador=0; contador <=listCompania.size(); contador++){				
				Compania compania= listCompania.get(contador);			
				if (compania.getCedulaJuridica() == cedulaJuridica )
					 indicador=true;		
			}			
		}				
		if(indicador=false){
			nuevaCompania = new Compania(nombre,cedulaJuridica);
			listCompania.add(nuevaCompania);			
		}	
	}
}//fin clase
