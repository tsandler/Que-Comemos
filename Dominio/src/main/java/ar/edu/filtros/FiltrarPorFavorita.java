package ar.edu.filtros;

import java.util.ArrayList;
import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class FiltrarPorFavorita extends Filtros {

	@Override
	public List<Receta> filtrarRecetas(Usuario unUser,
			List<Receta> recetasAFiltrar) {
		if(!(recetasAFiltrar.isEmpty())){
			List<Receta> copiaDeRecetasAFiltrar = new ArrayList<Receta>();
			for(Receta unaReceta : recetasAFiltrar) {
				if (unUser.recetasFavoritas.contains(unaReceta)) {
					copiaDeRecetasAFiltrar.add(unaReceta);
				}
			}
			return copiaDeRecetasAFiltrar;
		} else {
			return recetasAFiltrar;
		}
	}

}
