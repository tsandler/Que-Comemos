package ar.edu.usuario;

public enum Rutina{
	INTENSIVOCON("Activa con ejercicio adicional"), INTENSIVOSIN("Activa sin ejercicio adicional"),
	MEDIANO("Sedentaria con ejercicio"),NADA("Sedentaria con nada de ejercicio"),
	LEVE("Sedentaria con algo de ejercicio");
	
	private String descripcion;
	
	private Rutina(String unaDescripcion){
		this.descripcion = unaDescripcion;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}		
}