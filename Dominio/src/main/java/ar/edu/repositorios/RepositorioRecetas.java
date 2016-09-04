package ar.edu.repositorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections15.Predicate;
import org.uqbar.commons.model.CollectionBasedHome;
import org.uqbar.commons.model.UserException;

import uqbar.arena.persistence.PersistentHome;
import uqbar.arena.persistence.annotations.PersistentClass;
import ar.edu.exception.BusinessException;
import ar.edu.receta.AdapterRepoRecetas;
import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;
import ar.edu.receta.RecetaBuilder;
import ar.edu.usuario.Usuario;


public class RepositorioRecetas extends PersistentHome<Receta> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static RepositorioRecetas instance;
	private Map<Receta, Integer> recetasMasConsultadas;
	
	private void init() {
		Receta pure = new RecetaBuilder().nombre("PureDePapa").calorias(500).dificultad("Media").temporada("Otonio")
				.explicacion("Aplasto la papa")
				.agregarIngrediente(new Ingrediente("papa", 300))
				.agregarIngrediente(new Ingrediente("leche", 100))
				.agregarIngrediente(new Ingrediente("pimienta", 0.05))
				.agregarIngrediente(new Ingrediente("sal", 0.10)).build();

		Receta milanesaConPure = new RecetaBuilder()
				.nombre("Milanesa con pure").calorias(600).dificultad("Dificil").temporada("Invierno")
				.agregarIngrediente(new Ingrediente("pan", 50))
				.agregarIngrediente(new Ingrediente("huevo", 2))
				.agregarIngrediente(new Ingrediente("carne", 2))
				.agregarIngrediente(new Ingrediente("sal", 0.10))
				.agregarSubreceta(pure).build();

		this.createIfNotExists(pure);
		this.createIfNotExists(milanesaConPure);
	}

	/*public static RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
			instance.init();
		}
		return instance;
	}*/
	
	public RepositorioRecetas() {
		recetasMasConsultadas = new HashMap<Receta, Integer>();
	}
	
	public static synchronized RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
			instance.init();
		}
		return instance;
	}
		
	public void add(Receta unaReceta) {
		this.create(unaReceta);
	}

	public void create(Receta receta) {
		//this.validarRecetasDuplicadas(receta);
		receta.validarReceta();
		super.create(receta);
	}

	protected void validarRecetasDuplicadas(Receta receta) {
		if (!this.search(receta.getNombrePlato()).isEmpty()) {
			throw new UserException("Ya existe una receta con ese nombre");
		}
	}

	public List<Receta> search(String nombrePlato) {
		Receta example = new Receta();
		example.setNombrePlato(nombrePlato);
		return this.searchByExample(example);
	}
	
	public void createIfNotExists(Receta unaReceta) {
		if(this.search(unaReceta.getNombrePlato()).isEmpty()){
			this.create(unaReceta);
		}
	}
	
	
	public void remove(Receta unaReceta) {
		this.delete(unaReceta);
	}

	@Override
	public void update(Receta unaReceta) {
		this.validateCreate(unaReceta);
		super.update(unaReceta);
	}

	public List<Receta> responderConsulta() {
		AdapterRepoRecetas repositorioExterno = new AdapterRepoRecetas();
		List<Receta> recetasPropiasyDelPlugin = new ArrayList<Receta>();
		if (recetasPropiasyDelPlugin.addAll(this.allInstances())
				&& recetasPropiasyDelPlugin.addAll(repositorioExterno
						.getRecetasDelPlugin())) {
			return recetasPropiasyDelPlugin;
		} else {
			throw new BusinessException(
					"No se pudo generar la lista de recetas del plugin y del repositorio");
		}
	}
	
	public List<Receta> listarPublicas(){
		List<Receta> recetas = new ArrayList<Receta>();
		for (Receta receta: this.list()){
			if (!receta.isPrivada())
				recetas.add(receta);
		}
		return recetas;
	}
	public List<Receta> list() {
		return this.allInstances();
	}

	
	protected void validateCreate(Receta unaReceta) {
		unaReceta.validarReceta();
	}

	public void cleanUp() {
		instance = null;
	}
	
	public Receta getxNombre(String nombre){
		Optional<Receta> unaReceta = this.allInstances().stream().filter(receta -> receta.getNombrePlato().equals(nombre)).findFirst();
		return unaReceta.orElse(null);
	}
	
	public Map<Receta, Integer> getHashMasConsultadas(){
		return this.recetasMasConsultadas;
	}
	
	public List<Receta> getRecetasMasConsultadasPara(Usuario user){
		List<Receta> recetasConsultadas = new ArrayList<Receta>();
		for(Receta receta: this.recetasMasConsultadas.keySet()){
			if(user.puedeVerOModificarReceta(receta)){
				receta.consultas = this.recetasMasConsultadas.get(receta);
				recetasConsultadas.add(receta);
			}
		}
		return recetasConsultadas;
	}
/*
	@Override
	public Class<Receta> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public Class<Receta> getEntityType() {
		return Receta.class;
	}
	
	@Override
	public Receta createExample() {
		return new Receta();
	}

	
	protected Predicate<?> getCriterio(Receta example) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Receta> recetasQuePuedeVer(Usuario usuario) {
		List<Receta> recetasQuePuedeVer = new ArrayList<Receta>();
		for (Receta receta : this.list()) {
			if (usuario.puedeVerOModificarReceta(receta)) {
				receta.consultas = 0;
				recetasQuePuedeVer.add(receta);
			}
		}
		return recetasQuePuedeVer;
	}
}
