package ar.edu.controller;

import java.util.ArrayList;
import java.util.List;

import spark.Spark;
import ar.edu.condicionPreexistente.CondicionPreexistente;
import ar.edu.monitores.Monitor;
import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;
import ar.edu.receta.RecetaBuilder;
import ar.edu.repositorios.RepositorioRecetas;
import ar.edu.repositorios.RepositorioUsuarios;
import ar.edu.usuario.Usuario;

public class RecetasController {

	boolean PASA_CRITERIO = true;
	private JsonTransformer jsonTransformer;

	public RecetasController(JsonTransformer jsonTransformer) {
		this.jsonTransformer = jsonTransformer;
	}

	public void register() {
		Spark.get("/recetasUsuario/:usuario",
				(request, response) -> {
					Usuario usuario = RepositorioUsuarios.getInstance()
							.getxNombre(request.params(":usuario"));
					response.type("application/json;charset=utf-8");
					return usuario.recetasALasQueTieneAcceso();
				}, this.jsonTransformer);

		Spark.get("/recetasUsuario/favoritas/:usuario",
				(request, response) -> {
					Usuario usuario = RepositorioUsuarios.getInstance()
							.getxNombre(request.params(":usuario"));
					response.type("application/json;charset=utf-8");
					return usuario.recetasFavoritas;
				}, this.jsonTransformer);

		Spark.get("/recetasUsuario/masConsultadas/:usuario",
				(request, response) -> {
					Usuario user = RepositorioUsuarios.getInstance()
							.getxNombre(request.params(":usuario"));
					response.type("application/json;charset=utf-8");
					return RepositorioRecetas.getInstance().getRecetasMasConsultadasPara(user);
				}, this.jsonTransformer);
		
		Spark.get("/recetas/:nombre",
				(request, response) -> {
					Receta receta = RepositorioRecetas.getInstance()
							.getxNombre(request.params(":nombre"));
					response.type("application/json;charset=utf-8");
					return receta;
				}, this.jsonTransformer);

		Spark.get(
				"/recetas/condicionesPreexistentes/:receta",
				(request, response) -> {
					Receta receta = RepositorioRecetas.getInstance()
							.getxNombre(request.params(":receta"));
					List<CondicionPreexistente> condiciones = receta
							.condicionesInadecuadas();
					response.type("application/json;charset=utf-8");
					return condiciones;
				}, this.jsonTransformer);

		Spark.get(
				"/receta/favorita/:receta/:usuario",
				(request, response) -> {
					Receta receta = RepositorioRecetas.getInstance()
							.getxNombre(request.params(":receta"));
					Usuario usuarioQueVeLaReceta = RepositorioUsuarios
							.getInstance().getxNombre(
									request.params(":usuario"));
					response.type("application/json;charset=utf-8");
					return usuarioQueVeLaReceta
							.tieneLaRecetaComoFavorita(receta);
				}, this.jsonTransformer);

		Spark.put("/recetas/:ingrediente/:cantidad/:receta",
				(request, response) -> {
					Receta receta = RepositorioRecetas.getInstance()
							.getxNombre(request.params(":receta"));
					Ingrediente ingrediente = new Ingrediente();
					ingrediente.setNombre(request.params(":ingrediente"));
					ingrediente.setCantidad(Double.parseDouble(request
							.params(":cantidad")));
					receta.agregarIngrediente(ingrediente);
					RepositorioRecetas.getInstance().update(receta);
					response.type("application/json;charset=utf-8");
					return null;
				}, this.jsonTransformer);

		Spark.put("/guardarReceta/:nombreUsuario",
				(request, response) -> {
					Usuario usuario = RepositorioUsuarios.getInstance()
							.getxNombre(request.params(":nombreUsuario"));
					Receta recetaJson = (Receta) this.jsonTransformer.fromJson(request.body(), Receta.class);
//					RecetaBuilder recetaBuilder = new RecetaBuilder();
//					recetaBuilder.nombre(recetaJson.getNombrePlato());
//					recetaBuilder.calorias(recetaJson.getCalorias());
//					recetaBuilder.dificultad(recetaJson.getDificultadPreparacion());
//					recetaBuilder.explicacion(recetaJson.getExplicacionPreparacion());
//					recetaBuilder.temporada(recetaJson.getTemporadaCorrespondiente());
//					if (!recetaJson.ingredientes.isEmpty())
//						recetaBuilder.ingredientes = recetaJson.ingredientes;
					RepositorioRecetas.getInstance().update(recetaJson);
					usuario.actualizarFavoritismoReceta(
							RepositorioRecetas.getInstance().getxNombre(
									recetaJson.getNombrePlato()), recetaJson.getFavorita());

					return 1;
				}, this.jsonTransformer);

		Spark.put("/consultarReceta/:receta/:usuario",
				(request, response) -> {
					Usuario user = RepositorioUsuarios.getInstance()
							.getxNombre(request.params(":usuario"));
					Receta receta = RepositorioRecetas.getInstance()
							.getxNombre(request.params(":receta"));
					user.actualizarInformacion(receta);
					response.type("application/json;charset=utf-8");
					return 1;
				}, this.jsonTransformer);
		
		Spark.get("/busquedaRecetas/:usuario/:nombreReceta/:caloriasMin/:caloriasMax/:dificultad/:temporada/:ingrediente/:filtro",
				(request, response) -> {
					Receta recetaJson = (Receta) this.jsonTransformer.fromJson(request.body(), Receta.class);
					Receta recetaBusqueda = new RecetaBuilder().nombre(request.params(":nombreReceta")).dificultad(request.params(":dificultad")).
							temporada(request.params(":temporada")).agregarIngrediente(new Ingrediente(request.params(":ingrediente"), 1)).build();
					
					Usuario user = RepositorioUsuarios.getInstance().getxNombre(request.params(":usuario"));
					List<Receta> recetas = user.recetasALasQueTieneAcceso();
					
					if(!(request.params(":filtro")).equals("undefined")){
						if(Boolean.parseBoolean(request.params(":filtro")))
							recetas = user.filtrarRecetas(recetas);
					}
				
					List<Receta> recetasBuscadas = new ArrayList<Receta>();
					for(Receta unaReceta: recetas){
						if(!(this.noPasaCriterioDeBusqueda(unaReceta, recetaBusqueda, request.params(":caloriasMin"), request.params(":caloriasMax")))){
							recetasBuscadas.add(unaReceta);
						}
					}
					return recetasBuscadas;		
				},this.jsonTransformer);

		Spark.exception(RuntimeException.class, (ex, request, response) -> {
			response.status(400);
			response.body(ex.getMessage());
		});

		Spark.delete("recetas/:receta/:nombreIngrediente",
				(request, response) -> {
					Receta unaReceta = RepositorioRecetas.getInstance()
							.getxNombre(request.params(":receta"));
					unaReceta.sacarIngredienteSegunNombre(request
							.params(":nombreIngrediente"));
					response.type("application/json;charset=utf-8");
					return null;
				}, this.jsonTransformer);
	}
	
	private boolean noPasaCriterioDeBusqueda(Receta unaReceta,Receta recetaBusqueda,String caloriasMin, String caloriasMax){
		return  (!this.pasaCriterioNombre(unaReceta, recetaBusqueda.getNombrePlato())) ||
				(!this.pasaCriterioCalorias(unaReceta, caloriasMin, caloriasMax)) ||
				(!this.pasaCriterioTemporada(unaReceta, recetaBusqueda.getTemporadaCorrespondiente())) ||
				(!this.pasaCriterioDificultad(recetaBusqueda, recetaBusqueda.getDificultadPreparacion())) ||
				(!this.pasaCriterioIngredienteEnReceta(unaReceta, recetaBusqueda.getIngredientes().get(0).getNombre()));
				
	}
	
	private boolean pasaCriterioNombre(Receta receta, String unNombre){	
		if(!(unNombre.equals("undefined"))){
			return receta.getNombrePlato().contains(unNombre);
		}
		return PASA_CRITERIO;
	}
	
	private boolean pasaCriterioCalorias(Receta receta, String caloriasMin, String caloriasMax){
		
		if(!(caloriasMin.equals("undefined"))){
			if(!(caloriasMax.equals("undefined"))){
				return receta.getCalorias() <= Integer.parseInt(caloriasMax) && receta.getCalorias() > Integer.parseInt(caloriasMin); 
			}
			return receta.getCalorias() >= Integer.parseInt(caloriasMin);
		}else{
			if(!(caloriasMax.equals("undefined"))){
				return receta.getCalorias() <= Integer.parseInt(caloriasMax);
			}
			return PASA_CRITERIO;
		}
	}
	
	private boolean pasaCriterioTemporada(Receta receta, String temporada){
		if(!(temporada.equals("undefined"))){
			return receta.getTemporadaCorrespondiente().equals(temporada.toString());
		}
		return PASA_CRITERIO;
	}
	
	private boolean pasaCriterioDificultad(Receta receta, String dificultad){
		if(!(dificultad.equals("undefined"))){
			return receta.getDificultadPreparacion().equals(dificultad);
		}
		return PASA_CRITERIO;
	}
	
	private boolean pasaCriterioIngredienteEnReceta(Receta receta, String ingrediente){
		if(!(ingrediente.equals("undefined"))){
			return receta.getIngredientes().stream().anyMatch(ingre -> ingre.getNombre().equals(ingrediente));
		}
		return PASA_CRITERIO;
	}	
}
