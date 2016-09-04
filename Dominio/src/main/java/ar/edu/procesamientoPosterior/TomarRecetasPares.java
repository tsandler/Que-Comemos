package ar.edu.procesamientoPosterior;

import java.util.ArrayList;
import java.util.List;

import ar.edu.receta.Receta;

public class TomarRecetasPares extends ProcesamientoPosterior {

	public TomarRecetasPares(){
		this.setNombreProcesamientoPosterior("TomarRecetasPares");
	}
	
	@Override
	public List<Receta> aplicarProcesamientoPosterior(List<Receta> listaAProcesar) {
		
		List<Receta> copiaDeListaAProcesar = new ArrayList<Receta>();
		for(int i = 0; i < listaAProcesar.size();i = i+2){
			copiaDeListaAProcesar.add(listaAProcesar.get(i));
		}
		return copiaDeListaAProcesar;
	}

}
