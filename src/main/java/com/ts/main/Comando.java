package com.ts.main;

import java.util.ArrayList;

public class Comando {
	
	private String instance;
	private String comando;
	private String metodo;
	private String [] parametros;
	private String fecha;
	private String hora;
	
	public static final String SHOW="SHOW";
    public static final String WRITE="WRITE";
    public static final String EXECUTE="EXECUTE";	
	public static final String CREAR_COMPANNIA="CREAR_COMPANNIA";
    public static final String CREAR_COLABORADOR="CREAR_COLABORADOR";
    public static final String CREAR_PROYECTO="CREAR_PROYECTO";
    public static final String CREAR_EDIFICIO="CREAR_EDIFICIO";
    public static final String CREAR_ACTIVO="CREAR_ACTIVO";   	
    public static final String MOSTRAR_SALARIO="MOSTRAR_SALARIO";  
    public static final String MOSTRAR_VACACIONES="MOSTRAR_VACACIONES";
    public static final String MOSTRAR_VACACIONES_DISPONIBLES="MOSTRAR_VACACIONES_DISPONIBLES";
    public static final String MOSTRAR_VACACIONES_LIQUIDACION="MOSTRAR_VACACIONES_LIQUIDACION";    
    public static final String CALCULAR_SALARIO_NETO_IQ="CALCULAR_SALARIO_NETO_IQ";    
    public static final String ESTABLECER_RANGO_RENTA = "ESTABLECER_RANGO_RENTA";    
    public static final String AUMENTAR_SALARIO="AUMENTAR_SALARIO";
    public static final String TOMAR_VACACIONES="TOMAR_VACACIONES";
    public static final String CARGAR_LOG="CARGARACTUALIZACION_MONTO_CONYUGE_HIJO_LOG"; 
    public static final String EXIT="EXIT";
    public static final String LIMPIAR_RANGO_RENTA="LIMPIAR_RANGO_RENTA";
    public static final String ACTUALIZAR_MONTO_CONYUGE_HIJO="ACTUALIZAR_MONTO_CONYUGE_HIJO";
    
    public static ArrayList<String> listaParaMetodosExcecute = new ArrayList<String>();
    public static ArrayList<String> listaParaMetodosWrite = new ArrayList<String>();
    public static ArrayList<String> listaParaMetodosShow = new ArrayList<String>();    
    
	public Comando(String fecha, String hora, String instance, String comando, String metodo, String[] parametros) {
		
		this.fecha=fecha;
		this.hora=hora;
		this.comando=comando;
		this.instance = instance;
		this.metodo = metodo;
		this.parametros = parametros;		
	}
	
	public static void llenarListas (){
		
		listaParaMetodosWrite.add(CREAR_COLABORADOR);
		listaParaMetodosWrite.add(CREAR_COMPANNIA);
		listaParaMetodosWrite.add(CREAR_PROYECTO);
		listaParaMetodosWrite.add(CREAR_EDIFICIO);
		listaParaMetodosWrite.add(CREAR_ACTIVO);
		
		listaParaMetodosExcecute.add(CALCULAR_SALARIO_NETO_IQ);
		listaParaMetodosExcecute.add(ESTABLECER_RANGO_RENTA);
		listaParaMetodosExcecute.add(AUMENTAR_SALARIO);
		listaParaMetodosExcecute.add(TOMAR_VACACIONES);	
		listaParaMetodosExcecute.add(ACTUALIZAR_MONTO_CONYUGE_HIJO);		
		
		listaParaMetodosShow.add(MOSTRAR_VACACIONES);		
		listaParaMetodosShow.add(MOSTRAR_SALARIO);		
		listaParaMetodosShow.add(MOSTRAR_VACACIONES_DISPONIBLES);		
		listaParaMetodosShow.add(MOSTRAR_VACACIONES_LIQUIDACION);
		
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String[] getParametros() {
		return parametros;
	}

	public void setParametros(String[] parametros) {
		this.parametros = parametros;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}
	
	public static ArrayList<String> getListaParaMetodosShow() {
		return listaParaMetodosShow;
	}

	public static void setListaParaMetodosShow(
			ArrayList<String> listaParaMetodosShow) {
		Comando.listaParaMetodosShow = listaParaMetodosShow;
	}

	public static ArrayList<String> getListaParaMetodosWrite() {
		return listaParaMetodosWrite;
	}

	public static void setListaParaMetodosWrite(
			ArrayList<String> listaParaMetodosWrite) {
		Comando.listaParaMetodosWrite = listaParaMetodosWrite;
	}

	public static ArrayList<String> getListaParaMetodosExcecute() {
		return listaParaMetodosExcecute;
	}

	public static void setListaParaMetodosExcecute(
			ArrayList<String> listaParaMetodosExcecute) {
		Comando.listaParaMetodosExcecute = listaParaMetodosExcecute;
	}
}
