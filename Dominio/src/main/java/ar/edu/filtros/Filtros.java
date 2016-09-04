
package ar.edu.filtros;

import java.util.List;

import ar.edu.receta.*;
import ar.edu.usuario.Usuario;


public abstract class Filtros{
	
	private String nombreFiltro;
	
	public Filtros(){
		super();
	}
	
	public String getNombreFiltro(){
		return nombreFiltro;
	}
	
	public void setNombreFiltro(String nombre){
		nombreFiltro = nombre;
	}
	
	public abstract List<Receta> filtrarRecetas(Usuario unUser,List<Receta> recetasAFiltrar);
}