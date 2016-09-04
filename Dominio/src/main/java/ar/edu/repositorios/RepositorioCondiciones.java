package ar.edu.repositorios;

import java.util.List;

import org.apache.commons.collections15.Predicate;
import org.uqbar.commons.model.CollectionBasedHome;

import ar.edu.condicionPreexistente.Celiaco;
import ar.edu.condicionPreexistente.CondicionPreexistente;
import ar.edu.condicionPreexistente.Diabetico;
import ar.edu.condicionPreexistente.Hipertenso;
import ar.edu.condicionPreexistente.Vegano;

public class RepositorioCondiciones extends CollectionBasedHome<CondicionPreexistente>{
	
	private static RepositorioCondiciones instance;
	
	public static RepositorioCondiciones getInstance(){
		if (instance == null){
			instance = new RepositorioCondiciones();
			instance.add(new Hipertenso());
			instance.add(new Vegano());
			instance.add(new Celiaco());
			instance.add(new Diabetico());
		}
		return instance;
	}
	
	public void add(CondicionPreexistente unaCondicion){
		this.create(unaCondicion);
	}
	
	public void remove(CondicionPreexistente unaCondicion){
		this.delete(unaCondicion);
	}
	
	public List<CondicionPreexistente> list(){
		return this.allInstances();
	}
	
	public void cleanUp(){
		instance = null;
	}
	
	@Override
	public Class<CondicionPreexistente> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CondicionPreexistente createExample() {
		return new Hipertenso();
	}
	
	@Override
	protected Predicate<?> getCriterio(CondicionPreexistente example) {
		// TODO Auto-generated method stub
		return null;
	}
}
