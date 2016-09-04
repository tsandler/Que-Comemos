package ar.edu.monitores;

import java.util.HashMap;
import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class MonitorRecetasMasConsultadasPorGenero extends Monitor{
	
	HashMap<Receta, Integer> busquedasPorRecetasHombre = new HashMap<Receta, Integer>();
	HashMap<Receta, Integer> busquedasPorRecetasMujer = new HashMap<Receta, Integer>();

	public void actualizarInformacion(Receta unaReceta, Usuario unUsuario) {
		if(unUsuario.getSexo().equals("Masculino")){
			if(busquedasPorRecetasHombre.containsKey(unaReceta)){
				busquedasPorRecetasHombre.put(unaReceta, busquedasPorRecetasHombre.get(unaReceta) + 1);
			}else{
				busquedasPorRecetasHombre.put(unaReceta,1);
			}
		}else{
			if(busquedasPorRecetasMujer.containsKey(unaReceta)){
				busquedasPorRecetasMujer.put(unaReceta, busquedasPorRecetasMujer.get(unaReceta) + 1);
			}else{
				busquedasPorRecetasMujer.put(unaReceta,1);
			}
		}
	}
	
	@Override
	public List<Receta> getRecetasConsultadas() {
		// TODO Auto-generated method stub
		return null;
	}

}
