package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ts.main.InterpreteMandatos;

public class Repo  {
	
	public static CopyOnWriteArrayList<Colaborador> listColaboradores =new CopyOnWriteArrayList<Colaborador> ();
	public static CopyOnWriteArrayList<Edificio> listEdificios=new CopyOnWriteArrayList<Edificio> ();
	public static CopyOnWriteArrayList<Proyecto> listProyectos =new CopyOnWriteArrayList<Proyecto> ();
	public static CopyOnWriteArrayList<Activo> listActivos =new CopyOnWriteArrayList<Activo> ();
	public static CopyOnWriteArrayList<Compañia> listCompania =new CopyOnWriteArrayList<Compañia> ();
	
	
	
	public static void AgregarColaborador(String nombre, int numeroCedula ){
		boolean indicador=false;		
		
		if(!listColaboradores.isEmpty()){
			System.err.print("Cantidad en la lista:" + listColaboradores.size() +"\n");
			for (Colaborador colaborador : listColaboradores){		
				if (colaborador.getNumeroCedula() == numeroCedula)
				 indicador=true;
				 InterpreteMandatos.estadoFuncion=false;
			}
		}
		if(indicador==false){
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula);	
		    listColaboradores.add(nuevoColaborador);
		    System.out.print("El Colaborador: " + nombre + " se agrego exitosamente" + "\n");
		    InterpreteMandatos.estadoFuncion=true;
		}
		
	}
	public static void AgregarEdificio(String nombre){			
		boolean indicador= false;
		if(!listEdificios.isEmpty()){
			for (Edificio edificio : listEdificios){						
				if (edificio.getNombre().equalsIgnoreCase(nombre))
					 indicador=true;
					 InterpreteMandatos.estadoFuncion=false;
			}			
		}			
		if(indicador==false){
			Edificio nuevoEdificio = new Edificio(nombre);
		    listEdificios.add(nuevoEdificio);	
		    System.out.print("El Edificio: " + nombre + " se agrego exitosamente");
		    InterpreteMandatos.estadoFuncion=true;
		}					
	}
	public static void AgregarProyecto(String nombre){				
		boolean indicador = false;
		if(!listProyectos.isEmpty()){
			for (Proyecto proyecto : listProyectos){							
				if (proyecto.getNombre().equalsIgnoreCase(nombre))
					 indicador=true;
					 InterpreteMandatos.estadoFuncion=false;
			}			
		}				
		if(indicador==false){
			Proyecto nuevoProyecto = new Proyecto(nombre);
			listProyectos.add(nuevoProyecto);	
			System.out.print("El Proyecto: " + nombre + " se agrego exitosamente");
			InterpreteMandatos.estadoFuncion=true;
		}					
	}
	public static void AgregarActivo(String nombre, int numeroPlaca){				
		boolean indicador=false;
		if(!listProyectos.isEmpty()){
			for (Activo activo : listActivos){							
				if ( activo.getNumeroPlaca() == numeroPlaca )
					 indicador=true;
					 InterpreteMandatos.estadoFuncion=false;
			}			
		}				
		if(indicador==false){
			Activo nuevoActivo = new Activo(nombre,numeroPlaca);
			listActivos.add(nuevoActivo);	
			InterpreteMandatos.estadoFuncion=true;
		}	
	}
	public static void AgregarCompania(int cedulaJuridica,String nombre){				
		boolean indicador=false;
		if(!listCompania.isEmpty()){
			for (Compañia compañia: listCompania){							
				if (compañia.getCedulaJuridica() == cedulaJuridica )
					 indicador=true;
					 InterpreteMandatos.estadoFuncion=false;
			}			
		}				
		if(indicador==false){
			Compañia nuevaCompañia = new Compañia(cedulaJuridica,nombre);
			listCompania.add(nuevaCompañia);
			System.out.print("La Compañia: " + nombre + " se agrego exitosamente");
			InterpreteMandatos.estadoFuncion=true;
		}	
	}
}//fin clase
