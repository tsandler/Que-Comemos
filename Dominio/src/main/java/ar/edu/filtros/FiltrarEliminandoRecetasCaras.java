package ar.edu.filtros;



import java.util.ArrayList;
import java.util.List;

//import static java.util.stream.Collectors.toList;
import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class FiltrarEliminandoRecetasCaras extends Filtros{
	private List<String> ingredientesCaros = new ArrayList<String>();
	
	public FiltrarEliminandoRecetasCaras() {
		this.getIngredientesCaros().add("lechon");
		this.getIngredientesCaros().add("lomo");
		this.getIngredientesCaros().add("salmon");
		this.getIngredientesCaros().add("alcaparras");
		this.getIngredientesCaros().add("mejillones");
		this.getIngredientesCaros().add("langostinos");
		this.getIngredientesCaros().add("bourbon");
		this.setNombreFiltro("FiltrarEliminandoRecetasCaras");
	}
	
	@Override
	public List<Receta> filtrarRecetas(Usuario unUser, List<Receta> recetasAFiltrar) {
		// return (recetasAFiltrar.stream().filter(rec -> !(rec.ingredientes.stream().anyMatch(ing -> this.getIngredientesCaros().contains(ing))))).collect(toList());
		if(!(recetasAFiltrar.isEmpty())){
			List<Receta> copiaDeRecetasAFiltrar = new ArrayList<Receta>();
			for(Receta unaReceta : recetasAFiltrar){
				if(!(unaReceta.ingredientes.stream().anyMatch(ingrediente -> this.getIngredientesCaros().contains(ingrediente.nombre)))){
					copiaDeRecetasAFiltrar.add(unaReceta);
				}	
			}
			return copiaDeRecetasAFiltrar;
		}else{
			return recetasAFiltrar;
		}
		
		
	}

	public List<String> getIngredientesCaros() {
		return ingredientesCaros;
	}

	public void setIngredienteCaro(String ingredienteCaro) {
		this.ingredientesCaros.add(ingredienteCaro);
	}

}
