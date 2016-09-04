package ar.edu.requerimientos;

import java.util.List;

import org.apache.log4j.Logger;

import ar.edu.receta.Receta;

public class Logueo implements Requerimiento{
	
	Logger logger;
	
	@Override
	public void realizarRequerimiento(List<Receta> recetasAConsultar) {
		if (recetasAConsultar.size() > 100)
			logger.info("La consulta devolvio mas de 100 resultados");
	}
	
	public void setLogger(Logger logger){
		this.logger = logger;
	}
	
}
