package ar.edu.usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.TransactionalAndObservable;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;
import ar.edu.condicionPreexistente.*;
import ar.edu.exception.BusinessException;
import ar.edu.filtros.Filtros;
import ar.edu.grupo.Grupo;
import ar.edu.monitores.Monitor;
import ar.edu.monitores.MonitorRecetasMasConsultadas;
import ar.edu.procesamientoPosterior.ProcesamientoPosterior;
import ar.edu.receta.Receta;
import ar.edu.repositorios.RepositorioRecetas;
import ar.edu.requerimientos.Requerimiento;

@TransactionalAndObservable
@PersistentClass
public class Usuario extends Entity {
	private static final long serialVersionUID = 1L;
	@PersistentField
	public double peso;
	@PersistentField
	public double altura;
	@PersistentField
	public String sexo;
	@PersistentField
	private String nombre;
	@PersistentField
	private Date fechaDeNacimiento;
	@PersistentField
	private Rutina rutina;
	private ProcesamientoPosterior procPosterior;
	@PersistentField
	private String password;
	

	@PersistentField
	public String mail;
	@PersistentField
	private String complexion;

	public List<Grupo> grupos;
	public List<String> preferenciasAlimenticias;
	public List<String> palabrasQueDisgustan;
	public List<CondicionPreexistente> condicionesPreexistentes;
	//@Relation
	public List<Receta> recetas;
	public List<Filtros> filtrosAplicables;

	//@Relation
	public List<Receta> recetasFavoritas;
	public List<Monitor> actualizacionesObservers;
	public List<Requerimiento> requerimientos;

	public Usuario() {
		//recetas = new ArrayList<Receta>();
		//recetasFavoritas = new ArrayList<Receta>();
	}

	public Usuario(UsuarioBuilder usuarioBuilder) {
		this.instanciar(usuarioBuilder);
	}

	private void instanciar(UsuarioBuilder usuarioBuilder) {
		this.nombre = usuarioBuilder.nombre;
		this.fechaDeNacimiento = usuarioBuilder.fechaDeNacimiento;
		this.sexo = usuarioBuilder.sexo;
		this.rutina = usuarioBuilder.rutina;
		this.altura = usuarioBuilder.altura;
		this.peso = usuarioBuilder.peso;
		this.procPosterior = usuarioBuilder.procPosterior;
		this.mail = usuarioBuilder.mail;
		this.password = usuarioBuilder.password;
		this.complexion = this.datosDeComplexion(this.peso, this.altura);

		this.condicionesPreexistentes = usuarioBuilder.condicionesPreexistentes;
		this.filtrosAplicables = usuarioBuilder.filtrosAplicables;
		this.grupos = usuarioBuilder.grupos;
		this.palabrasQueDisgustan = usuarioBuilder.palabrasQueDisgustan;
		this.preferenciasAlimenticias = usuarioBuilder.preferenciasAlimenticias;
		this.recetas = usuarioBuilder.recetas;
		this.recetasFavoritas = usuarioBuilder.recetasFavoritas;
		this.requerimientos = usuarioBuilder.requerimientos;
		this.actualizacionesObservers = usuarioBuilder.actualizacionesObservers;
		this.actualizacionesObservers.add(new MonitorRecetasMasConsultadas());
	}

	@Override
	public int hashCode() {
		return this.getId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario unUsuario = (Usuario) obj;
		return this.getId().intValue() == unUsuario.getId().intValue();
	}

	// GETTERS & SETTERS

	public Date getFechaDeNacimiento() {
		return this.fechaDeNacimiento;
	}

	public String datosDeComplexion(double pesoNuevo, double alturaNueva) {
		this.peso = pesoNuevo;
		this.altura = alturaNueva;
		if (this.calcularIMC() < 18) {
			this.complexion = "Pequeña";
		} else if (this.calcularIMC() > 30) {
			this.complexion = "Grande";
		} else {
			this.complexion = "Mediana";
		}
		return complexion;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<String> getPalabrasQueDisgustan() {
		return palabrasQueDisgustan;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public void setPalabrasQueDisgustan(List<String> palabrasQueDisgustan) {
		this.palabrasQueDisgustan = palabrasQueDisgustan;
	}

	public List<Receta> getRecetasFavoritas() {
		return recetasFavoritas;
	}

	public void setRecetasFavoritas(List<Receta> recetasFavoritas) {
		this.recetasFavoritas = recetasFavoritas;
	}

	public List<Monitor> getActualizacionesObservers() {
		return actualizacionesObservers;
	}

	public void setActualizacionesObservers(List<Monitor> actualizacionesObservers) {
		this.actualizacionesObservers = actualizacionesObservers;
	}

	public List<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}

	public void setFechaDeNacimiento(Date fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public void setPreferenciasAlimenticias(List<String> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public void setCondicionesPreexistentes(
			List<CondicionPreexistente> condicionesPreexistentes) {
		this.condicionesPreexistentes = condicionesPreexistentes;
	}

	public void setFiltrosAplicables(List<Filtros> filtrosAplicables) {
		this.filtrosAplicables = filtrosAplicables;
	}

	public ProcesamientoPosterior getProcPosterior() {
		return procPosterior;
	}

	public void agregarRequerimiento(Requerimiento unRequerimiento) {
		requerimientos.add(unRequerimiento);
	}

	public void quitarRequerimiento(Requerimiento unRequerimiento) {
		requerimientos.remove(unRequerimiento);
	}

	public void setMail(String unMail) {
		mail = unMail;
	}

	public String getMail() {
		return mail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setFechaDeNacimiento(String fechaNueva) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		try {
			this.fechaDeNacimiento = formato.parse(fechaNueva
					.concat(" 23:59:59"));
		} catch (ParseException ex) {
		}
	}

	public void setNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
	}

	public List<String> getPreferenciasAlimenticias() {
		return this.preferenciasAlimenticias;
	}

	public List<CondicionPreexistente> getCondicionesPreexistentes() {
		return this.condicionesPreexistentes;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

	public List<Filtros> getFiltrosAplicables() {
		return filtrosAplicables;
	}

	public List<Receta> getrecetasFavoritas() {
		return recetasFavoritas;
	}

	public void setProcPosterior(ProcesamientoPosterior procPosterior) {
		this.procPosterior = procPosterior;
	}

	public void agregarNuevoFiltro(Filtros unFiltro) {
		this.filtrosAplicables.add(unFiltro);
	}

	public void eliminarFiltro(Filtros unFiltro) {
		this.filtrosAplicables.remove(unFiltro);
	}

	// @param fechaDeNacimientoNueva en formato "dd/MM/yyyy")
	public void datosBasicos(String nombreNuevo, String fechaDeNacimientoNueva) {
		this.nombre = nombreNuevo;
		setFechaDeNacimiento(fechaDeNacimientoNueva);
	}

	public boolean contienePreferenciasAlimenticias() {
		return !(this.preferenciasAlimenticias.isEmpty());
	}

	public void esPersonaValida() throws BusinessException {
		this.tieneNombreValido();
		this.tienePesoValido();
		this.tieneAlturaValida();
		this.tieneFechaDeNacimientoValida();
		this.cumpleCondicionesPreexistentes();
	}

	public double calcularIMC() {
		return this.peso / Math.pow(this.altura, 2);
	}

	public void sigueRutinaSaludable() {
		if (this.condicionesPreexistentes.isEmpty()) {
			if (!this.tieneIMCNormal()) {
				throw new BusinessException(
						"El usuario no sigue una rutina saludable por no tener condiciones preexistentes ni un IMC normal");
			}
		} else {
			this.subsanaCondicionesPreexistentes();
		}
	}

	public void agregarReceta(Receta unaReceta) {
		unaReceta.validarReceta();
		recetas.add(unaReceta);
		// unaReceta.sosDeUsuario(this);
		RepositorioRecetas.getInstance().update(unaReceta);
	}

	public void agregarNuevaReceta(Receta nuevaReceta) {
		nuevaReceta.esRecetaPrivada();
		this.agregarReceta(nuevaReceta);
	}

	public boolean puedeVerOModificarReceta(Receta unaReceta) {
		return this.creoLaReceta(unaReceta) || !unaReceta.isPrivada()
				|| this.comparteGrupoConElCreador(unaReceta);
	}

	public boolean tieneRecetas() {
		return !recetas.isEmpty();
	}

	public Receta dameReceta(Receta unaReceta) {
		if (this.creoLaReceta(unaReceta)) {
			Receta recetaModificada = (Receta) unaReceta.clone();
			return recetaModificada;
		}
		throw new BusinessException("No tiene la receta pedida");
	}

	public void agregarRecetaModificada(Receta unaReceta)
			throws BusinessException {
		unaReceta.esRecetaPrivada();
		this.agregarReceta(unaReceta);
	}

	public boolean creoLaReceta(Receta unaReceta) {
		return recetas.contains(unaReceta);
	}

	public List<Receta> recetasALasQueTieneAcceso() {
		return RepositorioRecetas.getInstance().recetasQuePuedeVer(this);
	}

	public void agregarPalabraQueDisgusta(String unaPalabra) {
		this.palabrasQueDisgustan.add(unaPalabra);
	}

	public boolean meSugerisLaReceta(Receta unaReceta) {
		return !this.tieneIngredientesQueLeDisgustan(unaReceta)
				&& this.laRecetaEsAdecuadaParaMi(unaReceta);
	}

	public boolean laRecetaEsAdecuadaParaMi(Receta unaReceta) {
		for (CondicionPreexistente condicionPreexistente : condicionesPreexistentes) {
			if (condicionPreexistente.esInadecuadaPara(unaReceta)) {
				return false;
			}
		}
		return true;
	}

	public List<Receta> consultarPorRecetasVisibles() {
		List<Receta> recetasFiltradas = consultarPorTodasRecetasVisibles();
		recetasFiltradas = this.filtrarRecetas(recetasFiltradas);
		recetasFiltradas = this
				.realizarProcesamientoPosterior(recetasFiltradas);
		this.actualizarInformacionDe(recetasFiltradas);
		this.realizarRequerimiento(recetasFiltradas);
		return recetasFiltradas;
	}

	private void realizarRequerimiento(List<Receta> recetasAConsultar) {
		if (!requerimientos.isEmpty()) {
			for (Requerimiento requerimiento : requerimientos) {
				requerimiento.realizarRequerimiento(recetasAConsultar);
			}
		}
	}

	public List<Receta> filtrarRecetas(List<Receta> recetas) {
		List<Receta> recetasFiltradas = recetas;
		if (!recetas.isEmpty()) {
			for (Filtros unFiltro : filtrosAplicables) {
				recetasFiltradas = unFiltro.filtrarRecetas(this,
						recetasFiltradas);
			}
		}
		return recetasFiltradas;
	}

	public List<Receta> realizarProcesamientoPosterior(List<Receta> recetas) {
		List<Receta> recetasProcesadas = recetas;
		if (this.getProcPosterior() != null) {
			recetasProcesadas = this.getProcPosterior()
					.aplicarProcesamientoPosterior(recetasProcesadas);
		}
		return recetasProcesadas;
	}

	public List<Receta> actualizarInformacionDe(List<Receta> recetas) {
		if (actualizacionesObservers.size() > 0) {
			for (Receta receta : recetas) {
				this.actualizarInformacion(receta);
			}
		}
		return recetas;
	}

	public void setRecetas(List<Receta> recetas) {
		this.recetas = recetas;
	}

	public List<Receta> getRecetas() {
		return this.recetas;
	}

	public void agregarARecetasFavoritas(Receta recetaInteresante) {
		if (this.consultarPorTodasRecetasVisibles().contains(recetaInteresante)) {
			this.recetasFavoritas.add(recetaInteresante);
		} else {
			throw new BusinessException("No puede agregar la receta");
		}
	}

	private List<Receta> consultarPorTodasRecetasVisibles() {
		return RepositorioRecetas.getInstance().responderConsulta();
	}

	public void actualizarInformacion(Receta unaReceta) {
		actualizacionesObservers.stream().forEach(
				actualizacion -> actualizacion.actualizarInformacion(unaReceta,
						this));
	}

	public void agregarActualizacion(Monitor actualizacion) {
		actualizacionesObservers.add(actualizacion);
	}

	// ///PRIVATE METHODS/////

	private void subsanaCondicionesPreexistentes() {
		condicionesPreexistentes.forEach(condicion -> condicion
				.subsanaCondicion(this));
	}

	private boolean tieneIMCNormal() {
		return numeroComprendidoEntre(this.calcularIMC(), 18, 30);

	}

	private boolean numeroComprendidoEntre(double numero, double min, double max) {
		return numero >= min && numero <= max;
	}

	private void tieneNombreValido() {
		if (this.nombre.length() <= 4)
			throw new BusinessException("El usuario no tiene nombre valido");
	}

	private void tienePesoValido() {
		if (!(this.peso > 0))
			throw new BusinessException("El usuario no tiene peso valido");
	}

	private void tieneAlturaValida() {
		if (!(this.altura > 0))
			throw new BusinessException("El usuario no tiene altura valida");
	}

	public void tieneFechaDeNacimientoValida() {
		if (fechaDeNacimiento.after(new Date()))
			throw new BusinessException(
					"El usuario no tiene fecha de nacimiento valida");
	}

	private void cumpleCondicionesPreexistentes() {
		condicionesPreexistentes.forEach(condicion -> condicion.esValido(this));
	}

	private boolean comparteGrupoConElCreador(Receta unaReceta) {
		return grupos.stream().anyMatch(
				grupo -> grupo.contieneReceta(unaReceta));
	}

	private boolean tieneIngredientesQueLeDisgustan(Receta unaReceta) {
		for (String nombreIngrediente : palabrasQueDisgustan) {
			if (unaReceta.contieneIngrediente(nombreIngrediente)) {
				return true;
			}
		}
		return false;
	}

	public boolean tieneTodasLasCondicionesDe(Usuario user) {
		for (CondicionPreexistente condicion : user.condicionesPreexistentes) {
			if (!this.condicionesPreexistentes.stream().anyMatch(
					cond -> cond.equals(condicion)))
				return false;
		}
		return true;
	}

	public boolean esVegano() {
		return condicionesPreexistentes.stream().anyMatch(
				condicion -> condicion.equals(new Vegano()));
	}

	public boolean tieneLaRecetaComoFavorita(Receta receta) {
		return this.recetasFavoritas.contains(receta);
	}

	public void actualizarFavoritismoReceta(Receta receta, boolean esFavorita) {
		if (esFavorita && !this.tieneLaRecetaComoFavorita(receta))
			this.recetasFavoritas.add(receta);
		if (!esFavorita && this.tieneLaRecetaComoFavorita(receta))
			this.recetasFavoritas.remove(receta);
	}

	public String getComplexion() {
		return complexion;
	}

	public void setComplexion(String complexion) {
		this.complexion = complexion;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
}
