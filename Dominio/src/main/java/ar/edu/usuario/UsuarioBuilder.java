package ar.edu.usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.condicionPreexistente.Celiaco;
import ar.edu.condicionPreexistente.CondicionPreexistente;
import ar.edu.condicionPreexistente.Diabetico;
import ar.edu.condicionPreexistente.Hipertenso;
import ar.edu.condicionPreexistente.Vegano;
import ar.edu.filtros.FiltrarEliminandoRecetasCaras;
import ar.edu.filtros.FiltrarPorCondicionesPreexistentes;
import ar.edu.filtros.FiltrarPorGustoDelUsuario;
import ar.edu.filtros.FiltroPorExcesoDeCalorias;
import ar.edu.filtros.Filtros;
import ar.edu.grupo.Grupo;
import ar.edu.monitores.Monitor;
import ar.edu.procesamientoPosterior.ProcesamientoPosterior;
import ar.edu.receta.Receta;
import ar.edu.requerimientos.Requerimiento;

public class UsuarioBuilder {
	public double peso;
	public double altura;
	public String sexo;
	public String nombre;
	public Date fechaDeNacimiento;
	public Rutina rutina; 
	public ProcesamientoPosterior procPosterior;
	public String mail;
	public String password;
	
	public List<String> preferenciasAlimenticias;
	public List<String> palabrasQueDisgustan;
	public List<CondicionPreexistente> condicionesPreexistentes;
	public List<Receta> recetas;
	public List<Filtros> filtrosAplicables;
	public List<Receta> recetasFavoritas;
	public List<Monitor> actualizacionesObservers;
	public List<Requerimiento> requerimientos;
	public List<Grupo> grupos;
	
	public UsuarioBuilder(){
		preferenciasAlimenticias = new ArrayList<String>();
		palabrasQueDisgustan = new ArrayList<String>();
		condicionesPreexistentes = new ArrayList<CondicionPreexistente>();
		recetas = new ArrayList<Receta>();
		grupos = new ArrayList<Grupo>();
		recetasFavoritas = new ArrayList<Receta>();
		filtrosAplicables = new ArrayList<Filtros>();
		actualizacionesObservers = new ArrayList<Monitor>();
		requerimientos = new ArrayList<Requerimiento>();
	}
	
	public UsuarioBuilder conDatosBasicos(String nombre, String fecha){
		this.nombre = nombre;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		try{
			this.fechaDeNacimiento = formato.parse(fecha.concat(" 23:59:59"));
		}catch(ParseException ex) { }
		return this;
	}
	
	public UsuarioBuilder password(String password){
		this.password = password;
		return this;
	}
	
	public UsuarioBuilder setNombre(String nombre){
		this.nombre = nombre;
		return this;
	}
	
	public UsuarioBuilder sexo(String sexo){
		this.sexo = sexo;
		return this;
	}
	
	public UsuarioBuilder esHombre(){
		this.sexo = "Masculino";
		return this;
	}
	
	public UsuarioBuilder esMujer(){
		this.sexo = "Femenino";
		return this;
	}
	
	public UsuarioBuilder conDatosDeComplexion(double peso, double altura){
		this.peso = peso;
		this.altura = altura;
		return this;
	}
	
	public UsuarioBuilder conRutina(Rutina rutina){
		this.rutina = rutina;
		return this;
	}
	
	public UsuarioBuilder conMail(String dirMail){
		if (dirMail != null)
			this.mail = dirMail;
		return this;
	}
	
	public UsuarioBuilder noLeGusta(String alimento){
		this.palabrasQueDisgustan.add(alimento);
		return this;
	}
	
	public UsuarioBuilder leGusta(String alimento){
		this.preferenciasAlimenticias.add(alimento);
		return this;
	}
	
	public UsuarioBuilder esVegano(){
		this.condicionesPreexistentes.add(new Vegano());
		return this;
	}
	
	public UsuarioBuilder esHipertenso(){
		this.condicionesPreexistentes.add(new Hipertenso());
		return this;
	}
	
	public UsuarioBuilder esDiabetico(){
		this.condicionesPreexistentes.add(new Diabetico());
		return this;
	}
	
	public UsuarioBuilder esCeliaco(){
		this.condicionesPreexistentes.add(new Celiaco());
		return this;
	}
	
	public UsuarioBuilder conReceta(Receta unaReceta){
		unaReceta.esRecetaPrivada();
		this.recetas.add(unaReceta);
		return this;
	}
	
	public UsuarioBuilder filtraPorCondPreexistentes(){
		this.filtrosAplicables.add(new FiltrarPorCondicionesPreexistentes());
		return this;
	}
	
	public UsuarioBuilder filtraEliminandoCaras(){
		this.filtrosAplicables.add(new FiltrarEliminandoRecetasCaras());
		return this;
	}
	
	public UsuarioBuilder filtraxLoQueLeGusta(){
		this.filtrosAplicables.add(new FiltrarPorGustoDelUsuario());
		return this;
	}
	
	public UsuarioBuilder filtraPorExcesoDeCalorias(){
		this.filtrosAplicables.add(new FiltroPorExcesoDeCalorias());
		return this;
	}
	
	public UsuarioBuilder perteneceAlGrupo(Grupo unGrupo){
		this.grupos.add(unGrupo);
		return this;
	}
	
	public Usuario build(){
		return new Usuario(this);
	}

}
