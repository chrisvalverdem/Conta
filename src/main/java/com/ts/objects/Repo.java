package com.ts.objects;

import java.util.concurrent.CopyOnWriteArrayList;

public class Repo  {
	
	private static CopyOnWriteArrayList<Colaborador> listColaboradores =new CopyOnWriteArrayList<Colaborador> ();
	private static CopyOnWriteArrayList<Edificio> listEdificios=new CopyOnWriteArrayList<Edificio> ();
	private static CopyOnWriteArrayList<Proyecto> listProyectos =new CopyOnWriteArrayList<Proyecto> ();
	private static CopyOnWriteArrayList<Activo> listActivos =new CopyOnWriteArrayList<Activo> ();
	private static CopyOnWriteArrayList<Compannia> listCompania =new CopyOnWriteArrayList<Compannia> ();
	
	
	public static void AgregarColaborador(String nombre, int numeroCedula ) throws CommandException{
		
		boolean revisarSiElColaboradorYaExiste = !listColaboradores.isEmpty();		
		
		if( revisarSiElColaboradorYaExiste )
		{
			for (Colaborador colaborador : listColaboradores)
			{		
				if (colaborador.getNumeroCedula() == numeroCedula)
				{
					throw new CommandException("El colaborador " + colaborador.getNombre() + ", ya tiene el numero de c�dula "+numeroCedula);
				}
			}
		}
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula);	
		    listColaboradores.add(nuevoColaborador);
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");	
	}
	public static void AgregarEdificio(String nombre) throws CommandException{			
		boolean revisarSiElEdificioYaExiste= getEdificio(nombre) != null;
			
		if(revisarSiElEdificioYaExiste)
		{
			throw new CommandException("El edificio " + nombre+ " ya existe.");		
		}			
			Edificio nuevoEdificio = new Edificio(nombre);
		    listEdificios.add(nuevoEdificio);	
		    System.out.println("El Edificio: " + nombre + " se agrego exitosamente.");					
	}
	public static Edificio getEdificio(String nombre) throws CommandException{			
		boolean existeAlgunEdificio = ! listEdificios.isEmpty();
		
		if(existeAlgunEdificio)
		{
			for (Edificio edificio : listEdificios){						
				if (edificio.getNombre().equalsIgnoreCase(nombre))
				{
					return edificio;
				}
			}			
		}			

		return null;
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
			Proyecto nuevoProyecto = new Proyecto(nombre);
			listProyectos.add(nuevoProyecto);	
			System.out.println("El Proyecto: " + nombre + " se agrego exitosamente");						
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
			Activo nuevoActivo = new Activo(nombre,numeroPlaca);
			listActivos.add(nuevoActivo);		
	}

	public static void AgregarCompannia(int cedulaJuridica,String nombre) throws CommandException {
		boolean revisarSiElActivoYaExiste = getCompannia(cedulaJuridica) != null;
		
		if(revisarSiElActivoYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}				
			Compannia nuevaCompannia = new Compannia(cedulaJuridica,nombre);
			listCompania.add(nuevaCompannia);
			System.out.println("La Compañia: " + nombre + " se agrego exitosamente");	
	}
	
	public static Compannia getCompannia(int cedulaJuridica) throws CommandException {
		
		boolean existeAlgunaCompannia = ! listCompania.isEmpty();
		
		if(existeAlgunaCompannia)
		{
			for (Compannia compannia: listCompania){							
				if (compannia.getCedulaJuridica() == cedulaJuridica )
				{
					return compannia;
				}
			}			
		}			

		return null;
	}
}//fin clase
