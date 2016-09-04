package ar.edu.procesamientoPosterior;

import java.util.List;

import ar.edu.receta.Receta;

public class TomarPrimeros10 extends ProcesamientoPosterior{

	public TomarPrimeros10(){
		this.setNombreProcesamientoPosterior("TomarPrimeros10");
	}
	
	@Override
	public List<Receta> aplicarProcesamientoPosterior(List<Receta> listaAProcesar) {
		return listaAProcesar.subList(0, 10);
		
	}

}
