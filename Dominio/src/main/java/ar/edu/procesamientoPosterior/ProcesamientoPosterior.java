
package ar.edu.procesamientoPosterior;

import java.util.List;

import ar.edu.receta.Receta;


public abstract class ProcesamientoPosterior{
	
	private String nombreProcesamientoPosterior;
	
	public String getNombreProcesamientoPosterior(){
		return nombreProcesamientoPosterior;
	}
	
	public void setNombreProcesamientoPosterior(String nombre){
		nombreProcesamientoPosterior = nombre;
	}
	
	public abstract List<Receta> aplicarProcesamientoPosterior(List<Receta> listaAProcesar);
}