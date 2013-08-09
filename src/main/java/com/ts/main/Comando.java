package com.ts.main;

public class Comando {
	
	private String instance;
	private String metodo;
	private String [] parametros;
	private String fecha;
	private String hora;
	public static final String CREAR_COMPANNIA="CREAR_COMPANNIA";
    public static final String CREAR_COLABORADOR="CREAR_COLABORADOR";
    public static final String CREAR_PROYECTO="CREAR_PROYECTO";
    public static final String CREAR_EDIFICIO="CREAR_EDIFICIO";
    public static final String CREAR_ACTIVO="CREAR_ACTIVO";
    public static final String CARGAR_LOG="CARGAR_LOG"; 
    public static final String EXIT="EXIT";	
    public static final String MOSTRAR_SALARIO="MOSTRAR_SALARIO";
    public static final String AUMENTAR_SALARIO="AUMENTAR_SALARIO";
    public static final String TOMAR_VACACIONES="TOMAR_VACACIONES";
    public static final String MOSTRAR_VACACIONES="MOSTRAR_VACACIONES";
    public static final String MOSTRAR_VACACIONES_DISPONIBLES="MOSTRAR_VACACIONES_DISPONIBLES";
    public static final String MOSTRAR_VACACIONES_LIQUIDACION="MOSTRAR_VACACIONES_LIQUIDACION";
    public static final String CALCULAR_SALARIO_NETO_IQ="CALCULAR_SALARIO_NETO_IQ";    
    
	public Comando(String instance, String metodo, String[] parametros,String fecha, String hora) {
		this.instance = instance;
		this.metodo = metodo;
		this.parametros = parametros;
		this.fecha=fecha;
		this.hora=hora;
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

}
