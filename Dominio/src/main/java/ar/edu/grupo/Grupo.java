package ar.edu.grupo;
import java.util.ArrayList;
import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class Grupo {
	
	private String nombre;
	public List<Usuario> usuarios;
	public List<String> preferenciasAlimenticias;
	
	public Grupo(){
		super();
	}
	public Grupo(String nombre_grupo){
		this.nombre = nombre_grupo;
		usuarios = new ArrayList<Usuario>();
		preferenciasAlimenticias = new ArrayList<String>();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public List<Receta> recetasDelGrupo(){
		List<Receta> recetas = new ArrayList<Receta>();
		for (Usuario usuario: usuarios){
			recetas.addAll(usuario.recetas);
		}
		return recetas;
	}
	
	public boolean contieneReceta(Receta unaReceta){
		return this.recetasDelGrupo().contains(unaReceta);
	}
	
	public void agregarUsuario(Usuario usuario){
		this.usuarios.add(usuario);
		usuario.grupos.add(this);
	}
	
	public void agregarPreferenciaAlimenticia(String preferencia){
		this.preferenciasAlimenticias.add(preferencia);
	}
	
	public boolean nosSugerisLaReceta(Receta unaReceta){
		return this.tieneAlgunaPreferenciaAlimenticia(unaReceta) && this.aTodosLesPareceApropiada(unaReceta);
	}
	
	//PRIVATE METHODS
	
	private boolean tieneAlgunaPreferenciaAlimenticia(Receta unaReceta){
		for(String preferenciaAlimenticia : preferenciasAlimenticias){
			if(this.correspondeAAlgunNombreDeReceta(preferenciaAlimenticia, unaReceta.getNombrePlato())){
				return true;
			}
		}
		return false;
	}
	private boolean correspondeAAlgunNombreDeReceta(String preferenciaAlimenticia, String nombreReceta){
		int resultado = nombreReceta.indexOf(preferenciaAlimenticia);
		return (resultado != -1);
	}
	
	private boolean aTodosLesPareceApropiada(Receta unaReceta){
		return usuarios.stream().allMatch(usuario->usuario.laRecetaEsAdecuadaParaMi(unaReceta));
	}
}
