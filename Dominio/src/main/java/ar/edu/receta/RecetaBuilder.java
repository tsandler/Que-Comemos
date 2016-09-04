package ar.edu.receta;

import java.util.ArrayList;
import java.util.List;

import ar.edu.repositorios.RepositorioIngredientes;

public class RecetaBuilder {
	public String nombre;
	public  String explicacion;
	public int calorias;
	public String dificultad;
	public String temporada;
	
	public List<Ingrediente> ingredientes;
	public List<Receta> subrecetas;
	
	public RecetaBuilder(){
		ingredientes = new ArrayList<Ingrediente>();
		subrecetas = new ArrayList<Receta>();
	}
	
	public RecetaBuilder nombre(String unNombre){
		this.nombre = unNombre;
		return this;
	}
	
	public RecetaBuilder explicacion(String unaExplicacion){
		this.explicacion = unaExplicacion;
		return this;
	}
	
	public RecetaBuilder calorias(int unasCalorias){
		this.calorias = unasCalorias;
		return this;
	}
	
	public RecetaBuilder dificultad(String unaDificultad){
		this.dificultad = unaDificultad;
		return this;
	}
	
	public RecetaBuilder temporada(String unaTemporada){
		this.temporada = unaTemporada;
		return this;
	}
	
	public RecetaBuilder agregarIngrediente(Ingrediente ingrediente){
		this.ingredientes.add(ingrediente);
		RepositorioIngredientes.getInstance().add(ingrediente);
		return this;
	}
	
	public RecetaBuilder agregarSubreceta(Receta subreceta){
		this.subrecetas.add(subreceta);
		return this;
	}
	
	public Receta build(){
		return new Receta(this);
	}
	
}