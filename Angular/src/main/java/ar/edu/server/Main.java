package ar.edu.server;

import spark.Spark;
import ar.edu.controller.*;
import ar.edu.repositorios.RepositorioRecetas;
import ar.edu.repositorios.RepositorioUsuarios;
import uqbar.arena.persistence.Configuration;




import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonTransformer jsonTransformer = new JsonTransformer(gson);
		Configuration.configure();

		Spark.port(8080);
		Spark.staticFileLocation("/webapp");
		

		new RecetasController(jsonTransformer).register();
		//new UsuarioController(jsonTransformer).register();
		new CondicionesController(jsonTransformer).register();
		new IngredientesController(jsonTransformer).register();
		
	}
} 