package ar.edu.receta;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.TransactionalAndObservable;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import ar.edu.exception.BusinessException;

@TransactionalAndObservable
@PersistentClass
public class Ingrediente extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistentField public String nombre;
	@PersistentField double cantidad;
	@PersistentField double peso;
	
	public Ingrediente(){
		super();
	}
	
	public double getPeso() {
		return peso;
	}
	
	public void validarIngrediente(){
		if (this.nombre.isEmpty() || Double.isNaN(this.peso))
			throw new BusinessException("El ingrediente no es valido");
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Ingrediente(String nombreIngrediente, double cantidadDelIngrediente){
		this.setNombre(nombreIngrediente);
		this.setCantidad(cantidadDelIngrediente);
	}
	
	public boolean tieneElMismoNombreQue(String nombreIngrediente){
		return this.nombre.equals(nombreIngrediente);
	}
	
	public boolean tienePesoMayorA(double peso){
		return this.peso > peso;
	}
	
	public void setNombre(String nombreIngrediente){
		this.nombre = nombreIngrediente;
	}
	
	public void setCantidad(double peso){
		this.cantidad = peso;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public double getCantidad(){
		return this.cantidad;
	}
}
