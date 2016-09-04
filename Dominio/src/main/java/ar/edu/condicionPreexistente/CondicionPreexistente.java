package ar.edu.condicionPreexistente;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.Observable;

import ar.edu.exception.BusinessException;
import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

@Observable
public abstract class CondicionPreexistente extends Entity{
	
	protected String nombre;
	
	public CondicionPreexistente(){
		super();
	}
	
	public void esValido(Usuario persona){
		if (!persona.contienePreferenciasAlimenticias())
			throw new BusinessException("el usuario no es valido por no contener preferencias alimenticias");
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof CondicionPreexistente)){
			return false;
		}
		CondicionPreexistente unaCondicion = (CondicionPreexistente) obj;
		return this.getNombre().equals(unaCondicion.getNombre());
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String setNombre(String nombre){
		return this.nombre = nombre;
	}
	
	public abstract void subsanaCondicion(Usuario usuario);
	public abstract boolean esInadecuadaPara(Receta unaReceta);
}
