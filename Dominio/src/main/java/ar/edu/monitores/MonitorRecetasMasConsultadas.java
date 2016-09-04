package ar.edu.monitores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.receta.Receta;
import ar.edu.repositorios.RepositorioRecetas;
import ar.edu.usuario.Usuario;

public class MonitorRecetasMasConsultadas extends Monitor {

	public Map<Receta, Integer> getRecetasMasConsultadas() {
		return recetasMasConsultadas;
	}

	public void setRecetasMasConsultadas(Map<Receta, Integer> recetasMasConsultadas) {
		this.recetasMasConsultadas = recetasMasConsultadas;
	}

	public Map<Receta, Integer> recetasMasConsultadas = new HashMap<Receta, Integer>();
	
	public void actualizarInformacion(Receta unaReceta, Usuario unUsuario) {
		//this.actualizarHash(unaReceta, recetasMasConsultadas);
		//this.actualizarHash(unaReceta, RepositorioRecetas.getInstance().getHashMasConsultadas());
	}
	
	private void actualizarHash(Receta unaReceta, Map<Receta, Integer> consultas){
		if(consultas.containsKey(unaReceta)){
			consultas.put(unaReceta, consultas.get(unaReceta) + 1);
		}else{
			consultas.put(unaReceta, 1);
		}
	}
	
	public List<Receta> getRecetasConsultadas(){
		List<Receta> recetasConsultadas = new ArrayList<Receta>();
		for(Receta receta: this.recetasMasConsultadas.keySet()){
			receta.consultas = this.recetasMasConsultadas.get(receta);
			recetasConsultadas.add(receta);
		}
		return recetasConsultadas;
	}
}
