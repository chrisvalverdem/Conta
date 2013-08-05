package com.ts.objects;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Repo  {
	
	private static CopyOnWriteArrayList<Colaborador> listColaboradores =new CopyOnWriteArrayList<Colaborador> ();
	private static CopyOnWriteArrayList<Edificio> listEdificios=new CopyOnWriteArrayList<Edificio> ();
	private static CopyOnWriteArrayList<Proyecto> listProyectos =new CopyOnWriteArrayList<Proyecto> ();
	private static CopyOnWriteArrayList<Activo> listActivos =new CopyOnWriteArrayList<Activo> ();
	private static CopyOnWriteArrayList<Compannia> listCompania =new CopyOnWriteArrayList<Compannia> ();
	private static HashMap<String, Object> tablaDeSimbolos = new HashMap<String, Object>();
	
	private static void pullInTOTablaDeSimbolos(String string, Object object)
	{
		//TODO validar que el string ya no exista....
		tablaDeSimbolos.put(string, object);
	}
	
	public static void AgregarCompannia(String instance, String cedulaJuridica,String nombre) throws CommandException {
		boolean revisarSiLaCompanniaYaExiste = getCompannia(cedulaJuridica) != null;
		
		if(revisarSiLaCompanniaYaExiste){
			throw new CommandException("La Compannia " + nombre+ " tiene el mismo numero de cedula juridica.");		
		}				
			Compannia nuevaCompannia = new Compannia(cedulaJuridica,nombre);
			//System.err.println("Compannia tiene: "+ nuevaCompannia.getCedulaJuridica() + "\n");
			//System.err.println("Compannia tiene: "+ nuevaCompannia.getNombre() + "\n");			
			listCompania.add(nuevaCompannia);
			//System.err.println("Cantidad de Compannias : "+ listCompania.size() + "\n");	
			 pullInTOTablaDeSimbolos(instance, nuevaCompannia );
			System.out.println("La Compañia: " + nombre + " se agrego exitosamente");	
	}
	
	public static void AgregarColaborador(String instance, String nombre, String numeroCedula ) throws CommandException{
		
		boolean revisarSiElColaboradorYaExiste = !listColaboradores.isEmpty();		
		
		if( revisarSiElColaboradorYaExiste )
		{
			for (Colaborador colaborador : listColaboradores)
			{		
				if (numeroCedula.equalsIgnoreCase(colaborador.getCedula()))
				{
					throw new CommandException("El colaborador " + colaborador.getNombre() + ", ya tiene el numero de c�dula "+numeroCedula);
				}
			}
		}
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula);			
		    listColaboradores.add(nuevoColaborador);
		    pullInTOTablaDeSimbolos(instance, nuevoColaborador );
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");	
	}
	public static void AgregarEdificio(String instance, String nombre) throws CommandException{			
		boolean revisarSiElEdificioYaExiste=  ! listEdificios.isEmpty();
			
		if(revisarSiElEdificioYaExiste)
		{
			for (Edificio edifico : listEdificios){							
				if (edifico.getNombre().equalsIgnoreCase(nombre))
				{
					throw new CommandException("El Edificio: " + nombre+ " ya existe.");
				}
			}	
		}			
			Edificio nuevoEdificio = new Edificio(nombre);
		    listEdificios.add(nuevoEdificio);
		    pullInTOTablaDeSimbolos(instance, nuevoEdificio );
		    System.out.println("El Edificio: " + nombre + " se agrego exitosamente.");					
	}
	public static void AgregarProyecto(String instance, String nombre) throws CommandException{	
		boolean revisarSiElProyectoYaExiste= ! listProyectos.isEmpty();

		if( revisarSiElProyectoYaExiste ){
			for (Proyecto proyecto : listProyectos){							
				if (proyecto.getNombre().equalsIgnoreCase(nombre))
				{
					throw new CommandException("El proyecto " + nombre+ " ya existe.");
				}
			}			
		}				
			Proyecto nuevoProyecto = new Proyecto(nombre);
			listProyectos.add(nuevoProyecto);
			 pullInTOTablaDeSimbolos(instance, nuevoProyecto );
			System.out.println("El Proyecto: " + nombre + " se agrego exitosamente");						
	}
	public static void AgregarActivo(String instance, String nombre, int numeroPlaca) throws CommandException
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

	public static Compannia getCompannia(String cedulaJuridica) throws CommandException {
		
		boolean existeAlgunaCompannia = ! listCompania.isEmpty();
		
		if(existeAlgunaCompannia)
		{
			for (Compannia compannia: listCompania){							
				if (compannia.getCedulaJuridica().toString().trim().equalsIgnoreCase(cedulaJuridica))
				{
					return compannia;
				}
			}			
		}			

		return null;
	}	
	public static Edificio getEdificio(String nombre) throws CommandException{			
		boolean existeAlgunEdificio = ! listEdificios.isEmpty();
		
		if(existeAlgunEdificio)
		{
			for (Edificio edificio : listEdificios){	
				if (edificio.getNombre().toString().trim().equalsIgnoreCase(nombre))
				{
					return edificio;
				}
			}			
		}			

		return null;
	}
	public static Activo getActivo(int placa) throws CommandException {
		
		boolean existeAlgunActivo = ! listActivos.isEmpty();
		
		if(existeAlgunActivo)
		{
			for (Activo activo: listActivos){							
				if (activo.getNumeroPlaca()== placa )
				{
					return activo;
				}
			}			
		}			

		return null;
	}

	public static int getTamannoCompannia(){
		return listCompania.size();
			}
	public static int getTamannoEdificio(){
		return listEdificios.size();
			}
	public static int getTamannoColaborador(){
		return listColaboradores.size();
			}
}//fin clase
