package ar.edu.filtros;

import java.util.ArrayList;
import java.util.List;

//import static java.util.stream.Collectors.toList;
import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class FiltrarPorCondicionesPreexistentes extends Filtros {
	
	public FiltrarPorCondicionesPreexistentes(){
		this.setNombreFiltro("FiltrarPorCondicionesPreexistentes");
	}

	@Override
	public List<Receta> filtrarRecetas(Usuario unUser, List<Receta> recetasAFiltrar) {
	
		if(!(recetasAFiltrar.isEmpty())){
			List<Receta> copiaRecetasAFiltrar = new ArrayList<Receta>();
			for(Receta unaReceta : recetasAFiltrar){
				if(!(unUser.getCondicionesPreexistentes().stream().anyMatch(condicion -> condicion.esInadecuadaPara(unaReceta)))){
					copiaRecetasAFiltrar.add(unaReceta);
				}		
			}
			return copiaRecetasAFiltrar;
		}else{
			return recetasAFiltrar;
		}
		
	}


}
