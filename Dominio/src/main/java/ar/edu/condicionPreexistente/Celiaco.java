package ar.edu.condicionPreexistente;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class Celiaco extends CondicionPreexistente {
	
	public Celiaco(){
		this.nombre = "Celiaco";
	}

	@Override
	public void esValido(Usuario persona){
		//No tiene validacion porque siempre es true, se redefine para sobreescribir el metodo heredado
	}
	
	@Override
	public void subsanaCondicion(Usuario persona){
		//No tiene validacion porque siempre es true, se redefine para sobreescribir el metodo heredado
	}
	
	@Override
	public boolean esInadecuadaPara(Receta unaReceta){
		return false;
	}
}
