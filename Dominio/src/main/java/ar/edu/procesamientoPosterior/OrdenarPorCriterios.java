package ar.edu.procesamientoPosterior;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import ar.edu.receta.Receta;

public class OrdenarPorCriterios extends ProcesamientoPosterior {
	
	public Comparator<Receta> criterioComparador;
	public OrdenarPorCriterios(Comparator<Receta> unCriterio){
		this.setNombreProcesamientoPosterior("OrdenarPorCriterios");
		this.setCriterioComparador(unCriterio);
	}
	
	@Override
	public List<Receta> aplicarProcesamientoPosterior(List<Receta> listaAProcesar) {
		Collections.sort(listaAProcesar, criterioComparador);
		return listaAProcesar;
	}

	public Comparator<Receta> getCriterioComparador() {
		return criterioComparador;
	}

	public void setCriterioComparador(Comparator<Receta> criterioComparador) {
		this.criterioComparador = criterioComparador;
	}
}
