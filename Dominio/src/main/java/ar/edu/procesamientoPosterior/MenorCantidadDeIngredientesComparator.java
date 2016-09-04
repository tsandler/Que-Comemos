package ar.edu.procesamientoPosterior;

import java.util.Comparator;

import ar.edu.receta.Receta;

public class MenorCantidadDeIngredientesComparator implements Comparator<Receta> {

	@Override
	public int compare(Receta o1, Receta o2) {
		return o1.ingredientes.size() < o2.ingredientes.size() ? -1 : o1.ingredientes.size() == o2.ingredientes.size() ? 0 : 1 ;
	}

}
