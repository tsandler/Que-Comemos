package ar.edu.controller;

import java.util.List;

import spark.Spark;
import ar.edu.receta.Ingrediente;
import ar.edu.repositorios.RepositorioIngredientes;


public class IngredientesController {

private JsonTransformer jsonTransformer;
	
	public IngredientesController(JsonTransformer jsonTransformer) {
		this.jsonTransformer = jsonTransformer;
	}
	
	public void register() {
		Spark.get("/ingredientes/", (request, response) -> {
			List<Ingrediente> ingredientes = RepositorioIngredientes.getInstance().list();
			response.type("application/json;charset=utf-8");
			return ingredientes;
		}, this.jsonTransformer);
	}
}
