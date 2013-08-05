package com.ts.main;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ts.objects.Activo;
import com.ts.objects.Colaborador;
import com.ts.objects.CommandException;
import com.ts.objects.Compannia;
import com.ts.objects.Edificio;
import com.ts.objects.Proyecto;

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
			listCompania.add(nuevaCompannia);	
			pullInTOTablaDeSimbolos(instance, nuevaCompannia );
			System.out.println("La Compañia: " + nombre + " se agrego exitosamente");	
	}
	
	public static void AgregarColaborador(String instance, String nombre, String numeroCedula, Date fechaNacimiento,
			Date fechaIngreso, boolean estado, String telefono,  int numeroHijos,
			String salario ) throws CommandException{
		
		boolean revisarSiElColaboradorYaExiste = !listColaboradores.isEmpty();		
		
		if( revisarSiElColaboradorYaExiste )
		{
			for (Colaborador colaborador : listColaboradores)
			{		
				if (colaborador.getCedula().equalsIgnoreCase(numeroCedula))
				{
					throw new CommandException("El colaborador " + colaborador.getNombre() + ", ya tiene el numero de c�dula "+numeroCedula);
				}
				
			}
		}
		if(salario.contains("¢") || salario.contains("$")){
			Colaborador nuevoColaborador = new Colaborador(nombre, numeroCedula, fechaNacimiento,fechaIngreso, estado, telefono,  numeroHijos, salario);	
		    listColaboradores.add(nuevoColaborador);
		    pullInTOTablaDeSimbolos(instance, nuevoColaborador );
		    System.out.println("El Colaborador: " + nombre + " se agrego exitosamente.");			
		}else{
			throw new CommandException("Favor Ingresar tipo de moneda apropiado en el salario del colaborador");
		}

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
			System.out.println("El Activo con placa: " + numeroPlaca + " se agrego exitosamente");
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

	public static Colaborador getColaborador(String cedula) throws CommandException{			
		boolean existeAlgunColaborador= ! listColaboradores.isEmpty();
		
		if(existeAlgunColaborador)
		{
			for (Colaborador colaborador : listColaboradores){	
				if (colaborador.getCedula().toString().trim().equalsIgnoreCase(cedula))
				{
					return colaborador;
				}
			}			
		}			

		return null;
	}
	public static void amentar_Salario(String persona, String nuevoSalario) throws CommandException{
			boolean existeAlgo= !tablaDeSimbolos.isEmpty();
			Colaborador colaborador= null;
			double salarioViejo=0;
			double salarioNuevo=0;

			  if(existeAlgo){
				   if(tablaDeSimbolos.containsKey(persona)){   
					   colaborador= (Colaborador) tablaDeSimbolos.get(persona);
					   
					   if(colaborador.getSalario().contains("$") && nuevoSalario.contains("$")){
						   salarioViejo= Integer.parseInt(colaborador.getSalario().substring(1, colaborador.getSalario().length()));
						   salarioNuevo= Integer.parseInt(nuevoSalario.substring(1, nuevoSalario.length()));
						   if(salarioNuevo >= salarioViejo){
							   colaborador.setSalario(nuevoSalario);
							   tablaDeSimbolos.put(persona, colaborador);  
							   System.out.println("El Colaborador: " + colaborador.getNombre() + " se modifico su salario exitosamente.");
						   }else{
							   throw new CommandException("Imposible aumentar el salario a un valor menor");
						   }  
					   }else{
						   if(colaborador.getSalario().contains("¢") && nuevoSalario.contains("¢")){
							   salarioViejo= Integer.parseInt(colaborador.getSalario().substring(1, colaborador.getSalario().length()));
							   salarioNuevo= Integer.parseInt(nuevoSalario.substring(1, nuevoSalario.length()));
							   if(salarioNuevo >= salarioViejo){
								   colaborador.setSalario(nuevoSalario);
								   tablaDeSimbolos.put(persona, colaborador);  
								   System.out.println("El Colaborador: " + colaborador.getNombre() + " se modifico su salario exitosamente.");
							   }else{
								   throw new CommandException("Imposible aumentar el salario a un valor menor");
							   }  
						   }else{
							   if(nuevoSalario.contains("¢") || nuevoSalario.contains("$")){
								   throw new CommandException("Imposible realizar operaciones de diferente moneda");   
							   }else{
								   throw new CommandException("No se reconoce el tipo de moneda, favor agregar $ o ¢ al monto");
							   }   	   
						   }
					   }  
				   }else{
						throw new CommandException("El Colaborador " + persona + " no existe.Imposible modificar Salario");
				   }  
			  }

	}    					
	public static void 	mostrar_Salario(String persona) throws CommandException{

		boolean existeAlgo= ! tablaDeSimbolos.isEmpty();
		Colaborador colaboradorEncontrado= null;
		
		if(existeAlgo){
			   if(tablaDeSimbolos.containsKey(persona)){   
				   colaboradorEncontrado= (Colaborador) tablaDeSimbolos.get(persona);
				   System.out.println("El Colaborador con identificacion: " + colaboradorEncontrado.getNombre() + " posee un salario de " + colaboradorEncontrado.getSalario());
			   }else{
				   throw new CommandException("El Colaborador " + persona + " no existe.");
			   }  
		  }				
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
	protected static void limpiaListas() {
		listColaboradores.clear();
		 listEdificios.clear();
		 listProyectos.clear();
		 listActivos.clear();
		 listCompania.clear();
		 tablaDeSimbolos.clear();
	}
}//fin clase
