package ar.edu.controller;

import java.util.List;


import spark.Spark;
import ar.edu.condicionPreexistente.CondicionPreexistente;
import ar.edu.repositorios.RepositorioCondiciones;

public class CondicionesController {
	
	private JsonTransformer jsonTransformer;
	
	public CondicionesController(JsonTransformer jsonTransformer) {
		this.jsonTransformer = jsonTransformer;
	}
	
	public void register() {
		Spark.get("/condiciones/", (request, response) -> {
			List<CondicionPreexistente> condiciones = RepositorioCondiciones.getInstance().list();
			response.type("application/json;charset=utf-8");
			return condiciones;
		}, this.jsonTransformer);
	}
}
