package ar.edu.receta;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import queComemos.entrega3.repositorio.BusquedaRecetas;
import queComemos.entrega3.repositorio.RepoRecetas;

public class AdapterRepoRecetas {
	RepoRecetas copiaDelRepositorio;
	BusquedaRecetas buscadorDefault;
	
	public AdapterRepoRecetas() {
		buscadorDefault = new BusquedaRecetas();
		copiaDelRepositorio = new RepoRecetas();
	}

	
	public String obtenerJsonDeTodasLasRecetasDelRepo(){
		String recetasJsonStr = copiaDelRepositorio.getRecetas(buscadorDefault);
		return recetasJsonStr;
	}
	
	public List<ar.edu.receta.Receta> getRecetasDelPlugin(){
		List<Receta> recetasAdaptadas = new ArrayList<Receta>();
		JSONArray recetasJsonArray = new JSONArray(this.obtenerJsonDeTodasLasRecetasDelRepo());
		for (int i = 0; i < recetasJsonArray.length(); i++){
			recetasAdaptadas.add(transformarJsonStrAReceta((JSONObject) recetasJsonArray.get(i)));
		}
		return recetasAdaptadas;
	}
	
	public Receta transformarJsonStrAReceta(JSONObject JsonObj){
		Receta nuevaReceta = new RecetaBuilder()
			.nombre(JsonObj.get("nombre").toString())
			.calorias(JsonObj.getInt("totalCalorias"))
			.dificultad(JsonObj.get("dificultadReceta").toString().toLowerCase())
			.build();
		
		for(int i = 0; i < JsonObj.getJSONArray("ingredientes").length(); i++){
			nuevaReceta.agregarIngrediente(new Ingrediente(JsonObj.getJSONArray("ingredientes").get(i).toString(),1));
		}
		return nuevaReceta;
	}
	
}

