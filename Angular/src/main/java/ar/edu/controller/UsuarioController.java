package ar.edu.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import spark.Spark;
import ar.edu.condicionPreexistente.CondicionPreexistente;
import ar.edu.exception.BusinessException;
import ar.edu.repositorios.RepositorioCondiciones;
import ar.edu.repositorios.RepositorioUsuarios;
import ar.edu.usuario.Rutina;
import ar.edu.usuario.Usuario;
import ar.edu.usuario.UsuarioBuilder;



public class UsuarioController {
	
	private JsonTransformer jsonTransformer;
	
	public UsuarioController(JsonTransformer jsonTransformer) {
		this.jsonTransformer = jsonTransformer;
	}
	
	public void register() {
		Spark.get("/usuarios/:nombre", (request, response) -> {
			String nombre = request.params(":nombre");
			Usuario user = RepositorioUsuarios.getInstance().getxNombre(nombre);
			response.type("application/json;charset=utf-8");
			return user;
		}, this.jsonTransformer);
		
		Spark.get("/usuarios/imc/:nombre", (request, response) -> {
			String nombre = request.params(":nombre");
			Usuario user = RepositorioUsuarios.getInstance().getxNombre(nombre);
			response.type("application/json;charset=utf-8");
			return user.calcularIMC();
		}, this.jsonTransformer);
		
		Spark.get("/usuarios/date/:nombre", (request, response) -> {
			String nombre = request.params(":nombre");
			Usuario user = RepositorioUsuarios.getInstance().getxNombre(nombre);
			response.type("application/json;charset=utf-8");
			return this.dateToString(user.getFechaDeNacimiento());
		}, this.jsonTransformer);
		
		Spark.get("/autenticacion/:usuario/:password", (request, response) -> {
			String nombre = request.params(":usuario");
			String pass = request.params(":password");
			Usuario user = RepositorioUsuarios.getInstance().getxNombre(nombre);
			if ( user != null){
				if(user.getPassword().equals(pass))
					return 1;
			}
			return 0;
		}, this.jsonTransformer);
		
		Spark.get("/consultarFecha/:fecha", (request, response) -> {
			String fecha = this.convertirFecha(request.params(":fecha"));
			Usuario user = new Usuario();
			user.setFechaDeNacimiento(fecha);
			try{
				user.tieneFechaDeNacimientoValida();
				return 1;
			}catch(Exception e){
				return 0;
			}
		}, this.jsonTransformer);
		
		Spark.put("/usuarios/condiciones/:usuario/:condiciones", (request, response) -> {
			Usuario user = RepositorioUsuarios.getInstance().getxNombre(request.params(":usuario"));
			this.condicionesPreexistentes(user, request.params(":condiciones"));
			return 1;
		}, this.jsonTransformer);
		
		Spark.put("/usuarios/registrar/:fecha", "application/json", (request, response) -> {
			UsuarioBuilder builder = new UsuarioBuilder();
			Usuario usuarioJson =  (Usuario) this.jsonTransformer.fromJson(request.body(), Usuario.class);
			String fecha = this.convertirFecha(request.params(":fecha"));
			Usuario nuevoUsuario = builder.conDatosBasicos(usuarioJson.getNombre(), fecha)
				.conMail(usuarioJson.mail)
				.password(usuarioJson.getPassword())
				.sexo(usuarioJson.getSexo())
				.conDatosDeComplexion(usuarioJson.peso, usuarioJson.altura)
				.build();
			
			RepositorioUsuarios.getInstance().add(nuevoUsuario);
			return 1;
		}, this.jsonTransformer);
		
		Spark.exception(RuntimeException.class, (ex, request, response) -> {
			response.status(400);
			response.body(ex.getMessage());
		});
	}
	
	private void condicionesPreexistentes(Usuario user, String condicionesString){
		if (condicionesString != "undefined"){
			String[] condiciones;
			condiciones = condicionesString.split(",");
			for(String condicion: condiciones)
				user.condicionesPreexistentes.add(this.condicionPreexistente(condicion));
		}
	}
	
	private CondicionPreexistente condicionPreexistente (String string){
		for(CondicionPreexistente condicion: RepositorioCondiciones.getInstance().list()){
			if (condicion.getNombre().equals(string))
				return condicion;
		}
		throw new BusinessException("No se encontro la condicion preexistente");
	}

	private String convertirFecha(String fecha){
		String f = "";
		int n;
		for (n = 0; n <fecha.length (); n++) { 
			if(fecha.charAt(n) == '-')
				f = f.concat("/");
			else
				f = f.concat("" + fecha.charAt(n));
		}
		return f;
	}
	
	private String dateToString(Date fecha){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(fecha);
	}
}
