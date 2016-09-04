package ar.edu.receta;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;
import org.uqbar.commons.utils.TransactionalAndObservable;

import uqbar.arena.persistence.annotations.*;
import ar.edu.condicionPreexistente.CondicionPreexistente;
import ar.edu.exception.BusinessException;
import ar.edu.repositorios.RepositorioCondiciones;
import ar.edu.usuario.Usuario;
import ar.edu.usuario.UsuarioBuilder;


@TransactionalAndObservable
@PersistentClass
public class Receta extends Entity implements Cloneable  {
	private static final long serialVersionUID = 1L;
	@PersistentField 
	private String nombrePlato;
	@PersistentField 
	private String explicacionPreparacion;
	@PersistentField 
	private int calorias;
	@PersistentField 
	private String dificultadPreparacion;
	@PersistentField 
	private String temporadaCorrespondiente;
	@PersistentField 
	private boolean privada;
	//private Usuario usuario;
	@PersistentField 
	private String nombreUsuario;
	
	@Relation 
	public List<Ingrediente> ingredientes;
	//@Relation 
	public List<Receta> subrecetas;
	public int consultas;
	@PersistentField 
	private Boolean favorita;

	public Receta(){
		ingredientes = new ArrayList<Ingrediente>();
	}
	
	public Receta(RecetaBuilder builder){
		this.setPrivada(false);
		//this.usuario = new Usuario(new UsuarioBuilder());
		this.instanciar(builder);
	}

	public void instanciar(RecetaBuilder builder){
		this.nombrePlato = builder.nombre;
		this.explicacionPreparacion = builder.explicacion;
		this.calorias = builder.calorias;
		this.dificultadPreparacion = builder.dificultad;
		this.temporadaCorrespondiente = builder.temporada;
		this.ingredientes = builder.ingredientes;
		this.subrecetas = builder.subrecetas;
		this.nombreUsuario = "el sistema";
	}
	
	@Override
	public int hashCode(){
		return this.getNombrePlato().hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Receta)){
			return false;
		}
		Receta unaReceta = (Receta) obj;
		//return( this.getNombrePlato().equals(unaReceta.getNombrePlato()) && this.usuario.equals(unaReceta.getUsuario()));
		return (this.getNombrePlato().equals(unaReceta.getNombrePlato()));
	}
	
	public void setNombreUsuario(String nombreUsuario){
		this.nombreUsuario = nombreUsuario;
	}
	
	
	public String getNombreUsuario(){
		return this.nombreUsuario;
	}
	public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
        	throw new BusinessException("No se pudo clonar la receta");
        }
        return obj;
    }
	
	public int getCalorias() {
		return this.calorias;
	}
	
	public void setCalorias(int cantCalorias){
		calorias = cantCalorias;
	}
	
	public String getNombrePlato() {
		return this.nombrePlato;
	}
	
	public boolean recetaPrivada(){
		return privada;
	}
	
	public void setNombrePlato(String unNombrePlato){
		nombrePlato = unNombrePlato;
	}
	
	public String getExplicacionPreparacion(){
		return this.explicacionPreparacion;
	}
	public void setExplicacionPreparacion(String unaExplicacion){
		explicacionPreparacion = unaExplicacion;
	}
	
	public String getDificultadPreparacion(){
		return this.dificultadPreparacion;
	}
	
	public void setDificultadPreparacion(String dificultad){
		dificultadPreparacion = dificultad;
	}
	
	public String getTemporadaCorrespondiente(){
		return this.temporadaCorrespondiente;
	}
	
	public void setTemporadaCorrespondiente(String temporada){
		temporadaCorrespondiente = temporada;
	}
	
	public int getConsultas(){
		return this.consultas;
	}
	
	public void setConsultas(int cantidad){
		this.consultas = cantidad;
	}
	
	public void agregarIngrediente(Ingrediente unIngrediente){
		ingredientes.add(unIngrediente);
	}
	

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<Receta> getSubrecetas() {
		return subrecetas;
	}

	public void setSubrecetas(List<Receta> subrecetas) {
		this.subrecetas = subrecetas;
	}

//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}

	public void sacarIngrediente(Ingrediente unIngrediente){
		ingredientes.remove(unIngrediente);
	}
	
	public void sacarIngredienteSegunNombre(String nombreIngrediente){
		ingredientes.removeIf(ingrediente -> ingrediente.tieneElMismoNombreQue(nombreIngrediente));
	}
	
	public void agregarSubreceta(Receta receta) {
		subrecetas.add(receta);
	}
		
	public boolean tieneCondicionesInadecuadas(){
		return RepositorioCondiciones.getInstance().list().stream().anyMatch(condicion -> condicion.esInadecuadaPara(this));
	}
	
	public List<CondicionPreexistente> condicionesInadecuadas(){
		List<CondicionPreexistente> condiciones = new ArrayList<CondicionPreexistente>();
		for (CondicionPreexistente condicion: RepositorioCondiciones.getInstance().list()){
			if (condicion.esInadecuadaPara(this))
				condiciones.add(condicion);
		}
		return condiciones;
	}
	
	public boolean contieneIngrediente(String nombreIngrediente){
		boolean estado;
		estado = ingredientes.stream().anyMatch(ingrediente -> ingrediente.tieneElMismoNombreQue(nombreIngrediente));
		if (estado)
			return true;
		
		return subrecetas.stream().anyMatch(receta -> receta.contieneIngrediente(nombreIngrediente));
	}
	
	public boolean condicionEsInadecuada(CondicionPreexistente unaCondicion){
		return unaCondicion.esInadecuadaPara(this);
	}
	
	public int cantidadCalorias() {
		int cantidadCalorias = this.getCalorias();
		for(Receta receta: subrecetas) {
			cantidadCalorias += receta.getCalorias();
		}
		return cantidadCalorias;
	}

	public void esRecetaPrivada() {
		this.setPrivada(true);
	}
	
	public boolean isPrivada() {
		return privada;
	}

	public void setPrivada(boolean privada) {
		this.privada = privada;
	}
	
	public void validarReceta(){
		this.tieneAlMenosUnIngrediente();
		this.cantidadCaloriasEntre(10, 5000);
	}
	
	public void sosDeUsuario(Usuario user){
		//this.usuario = user;
	}
	
	public Usuario getUsuario(){
		//return this.usuario;
		return new Usuario();
	}
	
	public boolean esDificil(){
		return dificultadPreparacion == "D";
	}
	
	// SETS PARA MODIFICAR RECETA
	
	private Receta getRecetaVieja(){
		return new RecetaBuilder().nombre(nombrePlato).explicacion(explicacionPreparacion)
				.calorias(calorias).dificultad(dificultadPreparacion).temporada(temporadaCorrespondiente).build();
	}
	
	public void setCambioCalorias(int cantCalorias){
		Receta recetaVieja = this.getRecetaVieja();
		Receta recetaNueva = new RecetaBuilder().nombre(nombrePlato).explicacion(explicacionPreparacion)
				.calorias(cantCalorias).dificultad(dificultadPreparacion).temporada(temporadaCorrespondiente).build();
		
		recetaNueva.ingredientes = this.ingredientes;
		recetaNueva.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, recetaNueva);
	}	
	
	public void setAgregoIngrediente(Ingrediente unIngrediente){
		Receta recetaVieja = this.getRecetaVieja();
		Receta recetaNueva = new RecetaBuilder().nombre(nombrePlato).explicacion(explicacionPreparacion)
				.calorias(calorias).dificultad(dificultadPreparacion).temporada(temporadaCorrespondiente).build();

		recetaNueva.ingredientes = this.ingredientes;
		recetaNueva.ingredientes.add(unIngrediente);
		recetaNueva.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, recetaNueva);
	}
	
	public void setCambioNombrePlato(String unNombrePlato){
		Receta recetaVieja = this.getRecetaVieja();
		Receta recetaNueva = new RecetaBuilder().nombre(unNombrePlato).explicacion(explicacionPreparacion)
				.calorias(calorias).dificultad(dificultadPreparacion).temporada(temporadaCorrespondiente).build();

		recetaNueva.ingredientes = this.ingredientes;
		recetaNueva.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, recetaNueva);
	}
	
	public void setCambioExplicacionPreparacion(String unaExplicacion){
		Receta recetaVieja = this.getRecetaVieja();
		Receta recetaNueva = new RecetaBuilder().nombre(nombrePlato).explicacion(unaExplicacion)
				.calorias(calorias).dificultad(dificultadPreparacion).temporada(temporadaCorrespondiente).build();

		recetaNueva.ingredientes = this.ingredientes;
		recetaNueva.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, recetaNueva);
	}
	
	public void setCambioDificultadPreparacion(String dificultad){
		Receta recetaVieja = this.getRecetaVieja();
		Receta nuevaReceta = new RecetaBuilder().nombre(nombrePlato).explicacion(explicacionPreparacion)
				.calorias(calorias).dificultad(dificultad).temporada(temporadaCorrespondiente).build();
		nuevaReceta.ingredientes = this.ingredientes;
		nuevaReceta.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, nuevaReceta);
	}
	
	public void setCambioTemporadaCorrespondiente(String temporada){
		Receta recetaVieja = this.getRecetaVieja();
		Receta recetaNueva = new RecetaBuilder().nombre(nombrePlato).explicacion(explicacionPreparacion)
				.calorias(calorias).dificultad(dificultadPreparacion).temporada(temporada).build();
		
		recetaNueva.ingredientes = this.ingredientes;
		recetaNueva.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, recetaNueva);
	}
	
	public void setCambioSacarIngrediente(Ingrediente unIngrediente){
		Receta recetaVieja = this.getRecetaVieja();
		Receta recetaNueva = new RecetaBuilder().nombre(nombrePlato).explicacion(explicacionPreparacion)
				.calorias(calorias).dificultad(dificultadPreparacion).temporada(temporadaCorrespondiente).build();
		
		recetaNueva.ingredientes = this.ingredientes;
		recetaNueva.ingredientes.remove(unIngrediente);
		recetaNueva.subrecetas= this.subrecetas;
		this.reemplazarReceta(recetaVieja, recetaNueva);
	}

	public void reemplazarReceta(Receta recetaVieja, Receta nuevaReceta){
//		if (usuario.puedeVerOModificarReceta(recetaVieja)){
//			if (usuario.creoLaReceta(recetaVieja))
//			usuario.recetas.remove(recetaVieja);
//		
//		usuario.agregarRecetaModificada(nuevaReceta);
//		}
	}
	
	public double pesoDeIngrediente(String nombreIngrediente){
		double cantidadIngrediente = 0.0;
		
		for(Ingrediente ingrediente: ingredientes) {
			if (ingrediente.tieneElMismoNombreQue(nombreIngrediente)) {
				cantidadIngrediente += ingrediente.getCantidad();
			}
		}
		
		for(Receta receta: subrecetas) {
			cantidadIngrediente += receta.pesoDeIngrediente(nombreIngrediente);
		}
		
		return cantidadIngrediente;
	}
	
	public boolean contieneIngredienteConPesoMayorA(String nombreIngrediente, double peso){		
		return this.pesoDeIngrediente(nombreIngrediente) > peso;		
	}
	
/////PRIVATE METHODS/////
	
	
	protected void tieneAlMenosUnIngrediente(){
		if(ingredientes.size()==0){
			throw new RuntimeException("No es receta valida porque no tiene ingredientes");
		}
		for(Receta receta: subrecetas) {
			receta.tieneAlMenosUnIngrediente();
		}
	}
	
	private void cantidadCaloriasEntre(int min, int max){
		int cantidadCalorias = this.cantidadCalorias();
		if((cantidadCalorias < min)||(cantidadCalorias > max))
			throw new RuntimeException("No es receta valida por no tener una cantidad de calorias valida");
	}
	
	
	public Boolean getFavorita() {
		return this.favorita;
	}

	public void setFavorita(Boolean favorita) {
		this.favorita = favorita;
	}
	
}