package ar.edu.repositorios;

import java.util.List;

import org.apache.commons.collections15.Predicate;
import org.uqbar.commons.model.CollectionBasedHome;

import ar.edu.exception.BusinessException;
import ar.edu.usuario.Usuario;
import ar.edu.usuario.UsuarioBuilder;

public class RepositorioUsuariosPendientes extends CollectionBasedHome<Usuario>{

	private static RepositorioUsuariosPendientes instance;

	public static RepositorioUsuariosPendientes getInstance(){
		if (instance == null){
			instance = new RepositorioUsuariosPendientes();
		}
		return instance;
	}
	
	public void add(Usuario user){
		this.create(user);
	}
	
	public Usuario get(int i){
		return this.list().get(i);
	}
	
	public void remove(Usuario user){
		this.delete(user);
	}
	
	public List<Usuario> list(){
		return this.allInstances();
	}
	
	public void acceptUser(){
		if (!this.list().isEmpty()){
			Usuario user = this.get(0);
			this.remove(user);
			RepositorioUsuarios.getInstance().add(user);
		}else{
			throw new BusinessException("La lista de usuarios pendientes esta vacia");
		}
	}	
	
	public void rejectUser(String reason){
		if (!this.list().isEmpty()){
			Usuario user = this.get(0);
			this.remove(user);
			throw new BusinessException(reason);
		}else{
			throw new BusinessException("La lista de usuarios pendientes esta vacia");
		}
	}
	
	public void cleanUp(){
		instance = null;
	}
	
	@Override
	protected void validateCreate(Usuario user){
		user.esPersonaValida();
	}
	
	@Override
	public Class<Usuario> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario createExample() {
		return new Usuario(new UsuarioBuilder());
	}

	@Override
	protected Predicate<?> getCriterio(Usuario example) {
		// TODO Auto-generated method stub
		return null;
	}

}
