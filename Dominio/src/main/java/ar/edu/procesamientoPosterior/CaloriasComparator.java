package ar.edu.procesamientoPosterior;

import java.util.Comparator;

import ar.edu.receta.Receta;

public class CaloriasComparator implements Comparator<Receta> {
	
	@Override
	public int compare(Receta o1, Receta o2) {
		return o1.getCalorias() < o2.getCalorias() ? -1 : o1.getCalorias() == o2.getCalorias() ? 0 : 1;
	}


}
