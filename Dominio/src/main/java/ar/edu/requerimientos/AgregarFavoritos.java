package ar.edu.requerimientos;

import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class AgregarFavoritos implements Requerimiento {
	Usuario usuario;
	Receta recetaConsultada;
	List<Receta> recetasFavoritas;
	
	public AgregarFavoritos(Usuario unUsuario){
		usuario = unUsuario;
		recetasFavoritas = unUsuario.recetasFavoritas;
	}
	
	public void realizarRequerimiento(List<Receta> recetasAConsultar){
		for(Receta recetaConsultada : recetasAConsultar){
			this.agregoFavoritos(recetaConsultada);
		}
	}
	
	public void agregoFavoritos(Receta recetaConsultada){
		if(!(usuario.recetasFavoritas.contains(recetaConsultada))){
				usuario.agregarARecetasFavoritas(recetaConsultada);
			}
		
	}
	
}
