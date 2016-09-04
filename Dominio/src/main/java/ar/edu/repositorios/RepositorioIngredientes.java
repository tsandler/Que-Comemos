package ar.edu.repositorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Predicate;

import org.uqbar.commons.utils.Observable;

import uqbar.arena.persistence.PersistentHome;
import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;


@Observable
public class RepositorioIngredientes extends PersistentHome<Ingrediente> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepositorioIngredientes instance;
	private List<Ingrediente> data = new ArrayList<Ingrediente>();
	
		
	public static synchronized RepositorioIngredientes getInstance() {
		if (instance == null) {
			instance = new RepositorioIngredientes();
			instance.create(new Ingrediente("sal",10));
			instance.create(new Ingrediente("manteca", 10));
			instance.create(new Ingrediente ("queso",25));
			instance.create(new Ingrediente("jamon", 0.05));
			instance.create(new Ingrediente("pan", 0.1));
			instance.create(new Ingrediente("pollo", 1));
			instance.create(new Ingrediente("azucar", 150));
			instance.create(new Ingrediente("aceituna", 8));
			instance.create(new Ingrediente("chori",1));
			instance.create(new Ingrediente("salmon",1));
		}
		return instance;
	}
	
	private RepositorioIngredientes() {
	}

	public void create(Ingrediente unIngrediente) {
		//this.validateCreate(unIngrediente);
		super.create(unIngrediente);
	}
	
	public void add(Ingrediente unIngrediente){
		this.create(unIngrediente);
	}
	
	public void remove(Ingrediente unIngrediente){
		this.delete(unIngrediente);
	}
	
	public List<Ingrediente> list(){
		return this.allInstances();
	}
	
	public void cleanUp(){
		instance = null;
	}
	
	protected void validateCreate(Ingrediente ingrediente){
		ingrediente.validarIngrediente();
	}
	
	@Override
	public Class<Ingrediente> getEntityType() {
		return Ingrediente.class;
	}
	
	@Override
	public Ingrediente createExample() {
		return new Ingrediente();
	}
	
	protected Predicate<?> getCriterio(Ingrediente example) {
		// TODO Auto-generated method stub
		return null;
	}
}


