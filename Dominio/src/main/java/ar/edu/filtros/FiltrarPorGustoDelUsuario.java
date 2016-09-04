package ar.edu.filtros;

import java.util.ArrayList;
import java.util.List;

//import static java.util.stream.Collectors.toList;
import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class FiltrarPorGustoDelUsuario extends Filtros{

	public FiltrarPorGustoDelUsuario(){
		this.setNombreFiltro("FiltrarPorGustoDelUsuario");
	}

	@Override
	public List<Receta> filtrarRecetas(Usuario unUser, List<Receta> recetasAFiltrar) {
		//return (recetasAFiltrar.stream().filter(rec -> !(rec.ingredientes.stream().anyMatch(ing -> unUser.palabrasQueDisgustan.contains(ing))))).collect(toList());
		if(!(recetasAFiltrar.isEmpty())){
			List<Receta> copiaDeRecetasAFiltrar = new ArrayList<Receta>();
			for(Receta unaReceta : recetasAFiltrar){
				if(!(unaReceta.ingredientes.stream().anyMatch(ingrediente -> unUser.palabrasQueDisgustan.contains(ingrediente.nombre)))){
					copiaDeRecetasAFiltrar.add(unaReceta);
				}
			}
			return copiaDeRecetasAFiltrar;
		} else {
			return recetasAFiltrar;
		}
	}

}
