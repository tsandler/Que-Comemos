package ar.edu.monitores;

import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public abstract class Monitor {
	
	public Monitor(){
		super();
	}
	
	public void actualizarInformacion(Receta unaReceta, Usuario unUsuario){}
	
	public List<Receta> getRecetasConsultadas() { return null; }
}
