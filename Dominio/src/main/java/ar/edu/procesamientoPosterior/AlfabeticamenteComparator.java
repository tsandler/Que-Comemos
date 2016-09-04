package ar.edu.procesamientoPosterior;

import java.util.Comparator;

import ar.edu.receta.Receta;

public class AlfabeticamenteComparator implements Comparator<Receta>{

	@Override
	public int compare(Receta o1, Receta o2) {
		return o1.getNombrePlato().compareToIgnoreCase(o2.getNombrePlato());
	}
	

}
