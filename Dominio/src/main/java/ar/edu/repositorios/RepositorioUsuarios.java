package ar.edu.repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections15.Predicate;
import org.uqbar.commons.model.CollectionBasedHome;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;


import uqbar.arena.persistence.PersistentHome;
import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;
import ar.edu.receta.RecetaBuilder;
import ar.edu.usuario.Rutina;
import ar.edu.usuario.Usuario;
import ar.edu.usuario.UsuarioBuilder;

@Observable
public class RepositorioUsuarios extends PersistentHome<Usuario> implements Serializable {

	private static final long serialVersionUID = -4277061775215637190L;
	private static RepositorioUsuarios instance;
	
	public RepositorioUsuarios(){
		super();
	}

	private void init() {
		Usuario marcos = new UsuarioBuilder().conDatosBasicos("Marcos", "12/03/1996").esHombre().esDiabetico().conDatosDeComplexion(70, 1.9)
				.leGusta("acelga").esHipertenso().conRutina(Rutina.LEVE)
				.password("123").build();
		Usuario martin = new UsuarioBuilder().conDatosBasicos("Martin", "18/03/1940").esHombre().conDatosDeComplexion(90, 1.6)
				.leGusta("lechuga").esVegano().leGusta("pizza").noLeGusta("pescado")
				.conRutina(Rutina.MEDIANO).password("123").build();
		
		Receta sanguche;
		if (this.getxNombre("Marcos") == null)
			 sanguche = new ar.edu.receta.RecetaBuilder()
				.nombre("Sanguche de jamon y queso").calorias(300)
				.agregarIngrediente(new Ingrediente("queso", 0.05))
				.agregarIngrediente(new Ingrediente("pan", 0.1))
				.agregarIngrediente(new Ingrediente("mayonesa", 0.01))
				.agregarIngrediente(new Ingrediente("jamon", 0.02))
				.dificultad("Facil").temporada("Verano").build();
		else
			sanguche = RepositorioRecetas.getInstance().getxNombre("Sanguche de jamon y queso");
		
		this.createIfNotExists(martin);
		this.createIfNotExists(marcos);
		
		sanguche.setNombreUsuario(marcos.getNombre());
		sanguche.setPrivada(true);
		marcos.recetas.add(sanguche);
		marcos.recetasFavoritas.add(sanguche);
		RepositorioRecetas.getInstance().createIfNotExists(sanguche);
	}

	/*public static RepositorioUsuarios getInstance(){
		if (instance == null){
			instance = new RepositorioUsuarios();
			instance.init();
		}
		return instance;
	}*/
	
	public static synchronized RepositorioUsuarios getInstance() {
		if (instance == null) {
			instance = new RepositorioUsuarios();
			instance.init();
		}
		return instance;
	}
	
		
	
	public void create(Usuario unUsuario) {
		this.validarUsuariosDuplicados(unUsuario);
		unUsuario.validateCreate();
		super.create(unUsuario);
	}

	protected void validarUsuariosDuplicados(Usuario unUsuario) {
		if (!this.search(unUsuario.getNombre()).isEmpty()) {
			throw new UserException("Ya existe una receta con ese nombre");
		}
	}
	
	public List<Usuario> search(String nombre) {
		Usuario example = new Usuario(new UsuarioBuilder().setNombre(nombre));
		return this.searchByExample(example);
	}

	
	private void createIfNotExists(Usuario unUsuario) {
		if(this.search(unUsuario.getNombre()).isEmpty()){
			this.create(unUsuario);
		}
	}
	
//ABML Methods
	public void add(Usuario user){
		this.create(user);
	}
	
	public void remove(Usuario user){
		this.delete(user);
	}
	
	public Usuario get(Usuario user){
		Optional<Usuario> oneUser = this.allInstances().stream().filter(usr -> usr.getNombre().equals(user.getNombre())).findFirst();
		return oneUser.orElse(null);
	}
	
	public Usuario getxNombre(String nombre){
		List<Usuario> paso1 = this.allInstances();
		Stream<Usuario> paso2 = paso1.stream().filter(usr -> usr.getNombre().equals(nombre));
		Optional<Usuario> oneUser = paso2.findFirst();
		return oneUser.orElse(null);
	}
	
	@Override
	public void update(Usuario user){
		this.validateCreate(user);
		super.update(user);
	}
	
	public List<Usuario> list(){
		return this.allInstances();
	}
	
	public List<Usuario> list(Usuario user){
		return this.searchByExample(user);
	}

//
	public void cleanUp(){
		instance = null;
	}
	
	public void solicitarAlta(Usuario user){
		RepositorioUsuariosPendientes.getInstance().add(user);
	}
	
	protected void validateCreate(Usuario user){
		user.esPersonaValida();
	}
	
	@Override
	public Class<Usuario> getEntityType() {
		return Usuario.class;
	}

	@Override
	public Usuario createExample() {
		return new Usuario();
	}
	
	protected Predicate<Usuario> getCriterio(Usuario user){
		return new Predicate<Usuario>(){
			@Override
			public boolean evaluate(Usuario listUser){
				if (listUser.getNombre().equals(user.getNombre())){
					if (!user.condicionesPreexistentes.isEmpty())
						return listUser.tieneTodasLasCondicionesDe(user);
					return true;
				}
				return false;
			}
		};
	}
}
