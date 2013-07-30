package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Repo  {
	
	public static CopyOnWriteArrayList<Colaborador> listColaboradores =new CopyOnWriteArrayList<Colaborador> ();
	public static CopyOnWriteArrayList<Edificio> listEdificios=new CopyOnWriteArrayList<Edificio> ();
	public static CopyOnWriteArrayList<Proyecto> listProyectos =new CopyOnWriteArrayList<Proyecto> ();
	public static CopyOnWriteArrayList<Activo> listActivos =new CopyOnWriteArrayList<Activo> ();
	public static CopyOnWriteArrayList<Compannia> listCompania =new CopyOnWriteArrayList<Compannia> ();
	
	
	
	public static void AgregarColaborador(String nombre, int numeroCedula ) throws CommandException{
		
		boolean revisarSiElColaboradorYaExiste = !listColaboradores.isEmpty();		
		
		if( revisarSiElColaboradorYaExiste )
		{
			for (Colaborador colaborador : listColaboradores)
			{		
				if (colaborador.getNumeroCedula() == numeroCedula)
				{
					throw new CommandException("El colaborador " + colaborador.getNombre() + ", ya tiene el numero de cédula "+numeroCedula);
				}
			}
		}
		else
		{
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula);	
		    listColaboradores.add(nuevoColaborador);
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");
		}
		
	}
	public static void AgregarEdificio(String nombre) throws CommandException{			
		boolean revisarSiElEdificioYaExiste= ! listEdificios.isEmpty();
		
		if(revisarSiElEdificioYaExiste)
		{
			for (Edificio edificio : listEdificios){						
				if (edificio.getNombre().equalsIgnoreCase(nombre))
				{
					throw new CommandException("El edificio " + nombre+ " ya existe.");
				}
			}			
		}			
		else
		{
			Edificio nuevoEdificio = new Edificio(nombre);
		    listEdificios.add(nuevoEdificio);	
		    System.out.println("El Edificio: " + nombre + " se agrego exitosamente.");
		}					
	}
	public static void AgregarProyecto(String nombre) throws CommandException{	
		boolean revisarSiElproyectoYaExiste= ! listProyectos.isEmpty();

		if( revisarSiElproyectoYaExiste ){
			for (Proyecto proyecto : listProyectos){							
				if (proyecto.getNombre().equalsIgnoreCase(nombre))
				{
					throw new CommandException("El proyecto " + nombre+ " ya existe.");
				}
			}			
		}				
		else
		{
			Proyecto nuevoProyecto = new Proyecto(nombre);
			listProyectos.add(nuevoProyecto);	
			System.out.println("El Proyecto: " + nombre + " se agrego exitosamente");
		}					
	}
	public static void AgregarActivo(String nombre, int numeroPlaca) throws CommandException
	{				
		boolean revisarSiElActivoYaExiste = ! listProyectos.isEmpty();
		
		if(revisarSiElActivoYaExiste){
			for (Activo activo : listActivos){							
				if ( activo.getNumeroPlaca() == numeroPlaca )
				{
					throw new CommandException("El Activo " + nombre+ " ya existe.");
				}
			}			
		}				
		else
		{
			Activo nuevoActivo = new Activo(nombre,numeroPlaca);
			listActivos.add(nuevoActivo);	
		}	
	}

	public static void AgregarCompannia(int cedulaJuridica,String nombre) throws CommandException {
		
		if(!listCompania.isEmpty()){
			for (Compannia compannia: listCompania){							
				if (compannia.getCedulaJuridica() == cedulaJuridica )
				{
					throw new CommandException("La Compannia " + compannia.getNombre()+ " tiene el mismo numero de cedula juridica.");
				}
			}			
		}				
		else
		{
			Compannia nuevaCompannia = new Compannia(cedulaJuridica,nombre);
			listCompania.add(nuevaCompannia);
			System.out.print("La CompaÃ±ia: " + nombre + " se agrego exitosamente");
		}	
		
	}
}//fin clase
