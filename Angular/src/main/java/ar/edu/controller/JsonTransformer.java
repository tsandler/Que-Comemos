package ar.edu.controller;

import spark.ResponseTransformer;

import com.google.gson.Gson;

public class JsonTransformer implements ResponseTransformer {

	private Gson gson;

	public JsonTransformer(Gson gson) {
		this.gson = gson;
	}

	public String render(Object model) {
		return this.gson.toJson(model);
	}
	
	public Object fromJson(String model, Class<?> klass){
		return this.gson.fromJson(model, klass);
		
	}

}