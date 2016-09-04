package ar.edu.usuario;

import static org.junit.Assert.*;

import org.junit.*;

import ar.edu.condicionPreexistente.*;
import ar.edu.exception.BusinessException;
import ar.edu.filtros.*;
import ar.edu.grupo.Grupo;
import ar.edu.procesamientoPosterior.*;
import ar.edu.receta.*;
import ar.edu.repositorios.*;


public class TestUsuario {

	Usuario pedro;
	Usuario carlos;
	Usuario marcos;
	Usuario daniel;
	
	Receta fideos;
	Receta polloConPapas;
	Receta cafeDeStarbucks;
	Receta pizza;
	Receta sanguche;
	Receta choriDeCancha;
	Receta salmonALaRomana;
	
	Ingrediente jamon;
	Ingrediente pan;
	Ingrediente aceituna;
	Ingrediente azucar;
	Ingrediente pollo;
	Ingrediente sal;
	Ingrediente manteca;
	Ingrediente queso;
	Ingrediente chorizo;
	Ingrediente salmon;
	Grupo grupon;
	
	@Before
	public void init(){
		
		pedro = new UsuarioBuilder().conDatosBasicos("Pedro", "12/03/1996").esHombre().conDatosDeComplexion(55, 1.6).
				esVegano().esCeliaco().esHipertenso().esDiabetico().leGusta("frutas").conRutina(Rutina.INTENSIVOCON)
				.filtraxLoQueLeGusta().noLeGusta("aceituna").build();
		
		grupon = new Grupo("The git team");
		grupon.agregarUsuario(pedro);
		
		marcos = new UsuarioBuilder().conDatosBasicos("Marcos", "12/03/1996").esHombre().esVegano().conDatosDeComplexion(70, 1.9)
				.leGusta("acelga").conRutina(Rutina.LEVE).filtraPorCondPreexistentes().build();
		
		carlos = new UsuarioBuilder().conDatosBasicos("Carlos", "2/12/1976").esHombre().esHipertenso().conDatosDeComplexion(75, 1.8)
				.noLeGusta("aceituna").leGusta("mani").conRutina(Rutina.INTENSIVOSIN).filtraPorExcesoDeCalorias().build();
		
		daniel = new UsuarioBuilder().conDatosBasicos("Daniel", "12/03/1996").esHombre().esDiabetico().conRutina(Rutina.LEVE).conDatosDeComplexion(69, 1.9)
				.filtraEliminandoCaras().build();
		
		sal = new Ingrediente("sal",10);
		manteca = new Ingrediente("manteca", 10);
		queso = new Ingrediente ("queso",25);
		jamon = new Ingrediente("jamon", 0.05);
		pan = new Ingrediente("pan", 0.1);
		pollo = new Ingrediente("pollo", 1);
		azucar = new Ingrediente("azucar", 150);
		aceituna = new Ingrediente("aceituna", 8);
		chorizo = new Ingrediente("chori",1);
		salmon = new Ingrediente("salmon",1);
		
		fideos = new RecetaBuilder().nombre("fideos").calorias(100)
				.agregarIngrediente(sal).build();
		
		sanguche = new RecetaBuilder().nombre("Sanguche de Jamon y Queso").calorias(300)
				.agregarIngrediente(jamon).agregarIngrediente(queso).agregarIngrediente(pan).build();

		polloConPapas = new RecetaBuilder().nombre("Pollo con papas").calorias(1500)
				.agregarIngrediente(pollo).agregarIngrediente(sal).build();
		
		cafeDeStarbucks = new RecetaBuilder().nombre("cafe").calorias(600)
				.agregarIngrediente(azucar).build();
		
		pizza = new RecetaBuilder().nombre("pizza").calorias(1200)
				.agregarIngrediente(aceituna).build();
		
		choriDeCancha = new RecetaBuilder().nombre("chori de cancha").calorias(1600)
				.agregarIngrediente(chorizo).agregarIngrediente(pan).build();
		
		salmonALaRomana = new RecetaBuilder().nombre("salmonALaRomana").calorias(400)
				.agregarIngrediente(salmon).build();
		
		RepositorioRecetas.getInstance().add(sanguche);
		RepositorioRecetas.getInstance().add(fideos);
		RepositorioRecetas.getInstance().add(pizza);
		RepositorioRecetas.getInstance().add(choriDeCancha);
		RepositorioRecetas.getInstance().add(salmonALaRomana);
		RepositorioRecetas.getInstance().add(cafeDeStarbucks);
		RepositorioRecetas.getInstance().add(polloConPapas);
		
	}

	@Test
	public void marcosNoLeSugieroComerPolloPorSerVegano(){
		assertFalse(marcos.meSugerisLaReceta(polloConPapas));
	}

	@Test
	public void carlosNoLeSugieroComerPolloPorTenerSalPorSerHipertenso(){
		assertFalse(carlos.meSugerisLaReceta(polloConPapas));
	}
	
	@Test
	public void danielNoLeSugieroTomarCafeDeStarbucksxDiabetico(){
		assertFalse(daniel.meSugerisLaReceta(cafeDeStarbucks));
	}
	
	@Test
	public void carlosNoLeSugieroComerPizzaPorqueTieneAceitunasYLasOdia(){
		assertFalse(carlos.meSugerisLaReceta(pizza));
	}
	
	@Test
	public void carlosLeSugieroComerUnSanguche(){
		assertTrue(carlos.meSugerisLaReceta(sanguche));
	}
	
	@Test
	public void pedroEsPersonaValida() {
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorTenerNombreCorto() {
		pedro.setNombre("juan");
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorNoTenerPeso() {
		pedro.peso = 0;
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorNoTenerAltura(){
		pedro.altura = 0;
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorNacerDespuesDeHoy() {
		pedro.setFechaDeNacimiento("20/01/2030");
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorSerVeganoYNoRespetar() {
		pedro.preferenciasAlimenticias.add("pollo");
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorSerDiabeticoYNoDefinirElSexo() {
		pedro.sexo = "";
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorSerDiabeticoYNoTenerPreferenciaAlimenticia() {
		pedro.preferenciasAlimenticias.remove("frutas");
		pedro.esPersonaValida();
	}
	
	@Test(expected=BusinessException.class)
	public void pedroNoEsPersonaValidaPorSerHipertensoYNoTenerPreferenciaAlimenticia() {
		pedro.preferenciasAlimenticias.remove("frutas");
		pedro.esPersonaValida();
	}
	
	@Test
	public void elCalculoDelIMCEsValido(){
		assertEquals(21.484, pedro.calcularIMC(), 0.001);
	}
	
	@Test
	public void sigueUnaRutinaSaludable() {
		pedro.sigueRutinaSaludable();
	}
	
	
	@Test(expected=BusinessException.class)
	public void pedroNoSigueUnaRutinaSaludable() {
		pedro.setRutina(Rutina.LEVE);
		pedro.sigueRutinaSaludable();
	}
	
	@Test(expected=BusinessException.class)
	public void diabeticoNoSigueRutinaSaludable() {
		daniel.datosDeComplexion(71, 1.7);
		daniel.sigueRutinaSaludable();
	}
	
	@Test(expected=BusinessException.class)
	public void veganoNoSigueRutinaSaludable() {
		marcos.sigueRutinaSaludable();
	}
	
	@Test(expected=RuntimeException.class)
	public void hipertensoNoSigueRutinaSaludable() {
		carlos.sigueRutinaSaludable();
	}
	
	@Test
	public void veganoSigueRutinaSaludable() {
		marcos.preferenciasAlimenticias.add("frutas");
		marcos.sigueRutinaSaludable();
	}
	
	@Test
	public void diabeticoSigueRutinaSaludablePorTenerRutinaActiva() {
		daniel.datosDeComplexion(72, 1.7);
		daniel.setRutina(Rutina.INTENSIVOCON);
		daniel.sigueRutinaSaludable();
	}
	
	@Test
	public void diabeticoSigueRutinaPorNoPesarmasde70kg() {
		daniel.sigueRutinaSaludable();
	}
	
	@Test
	public void hipertensoSigueRutinaSaludable() {
		carlos.setRutina(Rutina.INTENSIVOCON);
		carlos.sigueRutinaSaludable();
	}
	
	@Test(expected=BusinessException.class)
	public void usuarioNoSigueRutinaPorNoTenerIMCNormal() {
		marcos.datosDeComplexion(7, 1);
		marcos.sigueRutinaSaludable();
	}
	
	@Test(expected=BusinessException.class)
	public void usuarioCumplePorVeganoPeroNoPorDiabetico() {
		marcos.condicionesPreexistentes.add(new Diabetico());
		marcos.preferenciasAlimenticias.add("frutas");
		marcos.datosDeComplexion(72, 1.5);
		marcos.sigueRutinaSaludable();
	}
	
	@Test
	public void usuarioCumplePorHipertensoYPorVegano() {
		marcos.condicionesPreexistentes.add(new Hipertenso());
		marcos.preferenciasAlimenticias.add("frutas");
		marcos.setRutina(Rutina.INTENSIVOCON);
		marcos.sigueRutinaSaludable();
	}
	@Test
	public void pedroNoTieneRecetas(){
		assertFalse(pedro.tieneRecetas());
	}
	
	@Test
	public void pedroCreaUnaRecetaYSeAlmacenaEnSusRecetas() {
		pedro.agregarNuevaReceta(fideos);
		assertTrue(pedro.tieneRecetas());
	}
	
	@Test
	public void pedroPuedeVerOModificarLaRecetaPorqueEsPublica(){
		assertTrue(pedro.puedeVerOModificarReceta(fideos));
	}
	
	//@Test
//	public void pedroModificaRecetaCalorias() {
	//	pedro.agregarReceta(fideos);
		//Receta recetaVieja = pedro.dameReceta(fideos);
		//recetaVieja.setCambioCalorias(200);
		//assertEquals(200,pedro.recetas.get(0).getCalorias());
	//}
	
	//@Test
	//public void pedroModificaRecetaNombrePlato() {
	//	pedro.agregarReceta(fideos);
	//	Receta recetaVieja = pedro.dameReceta(fideos);
	//	recetaVieja.setCambioNombrePlato("fideos con salsa");
	//	assertEquals("fideos con salsa", pedro.recetas.get(0).getNombrePlato());
	//}
	
//	@Test
//	public void pedroModificaRecetaExplicacion() {
//		
//		pedro.agregarReceta(fideos);
//		Receta recetaVieja = pedro.dameReceta(fideos);
//		recetaVieja.setCambioExplicacionPreparacion("se pone el agua a hervir, luego se introducen los fideos y cuando estan listos se sacan, después le pones salsa a gusto");
//		assertEquals("se pone el agua a hervir, luego se introducen los fideos y cuando estan listos se sacan, después le pones salsa a gusto", pedro.recetas.get(0).getExplicacion());
//	}
	
	//@Test
//	public void pedroModificaRecetaDificultad() {
	//	
		//pedro.agregarReceta(fideos);
		//Receta recetaVieja = pedro.dameReceta(fideos);
		//recetaVieja.setCambioDificultadPreparacion("media");
		//assertEquals("media", pedro.recetas.get(0).getDificultad());
	//}
	
//	@Test
//	public void pedroModificaRecetaTemporada() {
//		
//		pedro.agregarReceta(fideos);
//		Receta recetaVieja = pedro.dameReceta(fideos);
//		recetaVieja.setCambioTemporadaCorrespondiente("invierno");
//		assertEquals("invierno", pedro.recetas.get(0).getTemporada());
//	}
	
	@Test
	public void pedroModificaRecetaSacandoIngrediente() {
		
		pedro.agregarReceta(fideos);
		fideos.agregarIngrediente(manteca);
		Receta recetaVieja = pedro.dameReceta(fideos);
		recetaVieja.setCambioSacarIngrediente(sal);
		assertFalse(fideos.contieneIngrediente("sal"));
	}
		
	@Test
	public void pedroModificaRecetaCaloriasSinModificarLaPublica() {
		
		pedro.agregarReceta(fideos);
		Receta recetaVieja = pedro.dameReceta(fideos);
		recetaVieja.setCambioCalorias(200);
		assertEquals(100, fideos.getCalorias(), 0);
	}
	
	@Test
	public void pedroModificaRecetaAgregaIngrediente() {
		pedro.agregarReceta(fideos);
		Receta recetaVieja = pedro.dameReceta(fideos);
		recetaVieja.setAgregoIngrediente(manteca);
		assertTrue(fideos.contieneIngrediente("manteca"));
	}
	
	@Test
	public void pedroCreaRecetaYMarcosNoLaVe() {
		 pedro.agregarNuevaReceta(fideos);
		 assertFalse(marcos.puedeVerOModificarReceta(fideos));
	} 
	
	@Test
	public void elUsuarioModificaLaRecetaYSigueSiendoPublica() {
		pedro.agregarReceta(fideos);
		Receta recetaVieja = pedro.dameReceta(fideos);
		recetaVieja.setCambioCalorias(200);
		assertFalse(fideos.isPrivada());
	}
	
	//@Test
//	public void elUsuarioModificaLaRecetaYPasaASerPrivadaParaEl() {
	//	pedro.agregarReceta(fideos);
		//Receta recetaVieja = pedro.dameReceta(fideos);
		//recetaVieja.setCambioCalorias(200);
		//assertTrue(pedro.recetas.get(0).isPrivada());
	//}
	
	@Test
	public void laListaDeRecetasQuePuedeVerPedroIncluyeFideos(){
		assertTrue(pedro.recetasALasQueTieneAcceso().contains(fideos));
	}
	
	@Test
	public void laListaDeRecetasQuePuedeVerPedroIncluyeSanguchePorSerPublica(){
		assertTrue(pedro.recetasALasQueTieneAcceso().contains(sanguche));
	}
	
	@Test
	public void marcosAgregaAlSangucheASusRecetasYPedroTieneAccesoPorqueCompartenGrupo(){
		grupon.agregarUsuario(marcos);
		marcos.agregarNuevaReceta(sanguche);
		assertTrue(pedro.recetasALasQueTieneAcceso().contains(sanguche));
	}
	@Test
	public void pedroAgregaPolloConPapasComoFavorita(){
		pedro.agregarARecetasFavoritas(polloConPapas);
		assertTrue(pedro.recetasFavoritas.contains(polloConPapas));
	}
	
	@Test 
	public void marcosFiltraRecetasConChoriPolloYJamonPorVegano(){
		marcos.agregarNuevaReceta(choriDeCancha);
		assertFalse(marcos.consultarPorRecetasVisibles().contains(choriDeCancha));
		assertFalse(marcos.consultarPorRecetasVisibles().contains(polloConPapas));
		assertFalse(marcos.consultarPorRecetasVisibles().contains(sanguche));
		assertTrue(marcos.consultarPorRecetasVisibles().contains(fideos));
	}
	
	@Test
	public void danielFiltraRecetasConSalmonPorRata(){
		daniel.agregarNuevaReceta(salmonALaRomana);
		assertFalse(daniel.consultarPorRecetasVisibles().contains(salmonALaRomana));
		assertTrue(daniel.consultarPorRecetasVisibles().contains(fideos));
	}
	
	@Test
	public void pedroFiltraRecetasPorSuGusto(){
		assertFalse(pedro.consultarPorRecetasVisibles().contains(pizza));
		assertTrue(pedro.consultarPorRecetasVisibles().contains(salmonALaRomana));
	}
	
	@Test
	public void pedroFiltraSalmonYPizzaPorRataYPorqueNoLeGustanLasAceitunas(){
		pedro.agregarNuevoFiltro(new FiltrarEliminandoRecetasCaras());
		assertFalse(pedro.consultarPorRecetasVisibles().contains(pizza));
		assertFalse(pedro.consultarPorRecetasVisibles().contains(salmonALaRomana));
		assertTrue(pedro.consultarPorRecetasVisibles().contains(choriDeCancha));
	}
	
	@Test
	public void danielFiltraPorRataYOrdenaPorCalorias(){
		daniel.setProcPosterior(new OrdenarPorCriterios(new CaloriasComparator()));
		assertEquals(daniel.consultarPorRecetasVisibles().get(0).getCalorias(), 10);
		assertEquals(daniel.consultarPorRecetasVisibles().get(1).getCalorias(), 10);
		assertEquals(daniel.consultarPorRecetasVisibles().get(2).getCalorias(), 15);
		assertEquals(daniel.consultarPorRecetasVisibles().get(3).getCalorias(), 20);
	}
	
	@Test
	public void marcosFiltraPorCondicionesPreexistentesyOrdenaAlfabeticamente(){
		marcos.setProcPosterior(new OrdenarPorCriterios(new AlfabeticamenteComparator()));
		marcos.condicionesPreexistentes.add(new Hipertenso());
		assertTrue(marcos.consultarPorRecetasVisibles().get(0).getNombrePlato() == "cafe");
	}
	
	@Test
	public void danielFiltraPorCondPreexYPorRatayOrdenaPorMenorCantidadDeIngredientes(){
		daniel.setProcPosterior(new OrdenarPorCriterios(new MenorCantidadDeIngredientesComparator()));
		daniel.condicionesPreexistentes.add(new Hipertenso());
		daniel.filtrosAplicables.add(new FiltrarPorCondicionesPreexistentes());
		assertTrue(daniel.consultarPorRecetasVisibles().get(0).getNombrePlato() == "pizza");
	}
	
	@Test
	public void carlosFiltraPorTenerSobrePeso(){
		carlos.datosDeComplexion(300, 1.8);
		assertTrue(carlos.consultarPorRecetasVisibles().contains(fideos));
		assertFalse(carlos.consultarPorRecetasVisibles().contains(choriDeCancha));
		assertFalse(carlos.consultarPorRecetasVisibles().contains(cafeDeStarbucks));
	}
	
	@Test
	public void carlosAplicaProcesamientoDeTomarSolo10elementos(){
		carlos.setProcPosterior(new TomarPrimeros10());
		assertTrue(carlos.consultarPorRecetasVisibles().size() == 10);	
	}
	
	@Test
	public void carlosFiltraYAgregaAFavorita(){
		carlos.datosDeComplexion(300, 1.8);
		carlos.agregarARecetasFavoritas(fideos);
		assertTrue(carlos.recetasFavoritas.contains(fideos));
	}
	
	@Test
	public void carlosFiltraPorSuGustoYAgregaAFavorita(){
		carlos.agregarNuevoFiltro(new FiltrarPorGustoDelUsuario());
		carlos.agregarARecetasFavoritas(salmonALaRomana);
		assertFalse(carlos.recetasFavoritas.contains(fideos));
	}
	
	@Test
	public void seAgregaACarlosAlRepositorioYSeLoEncuentraPorElNombre(){
		RepositorioUsuarios.getInstance().add(carlos);
		assertEquals(carlos.getNombre(),RepositorioUsuarios.getInstance().get(carlos).getNombre());
	}
	
	@Test
	public void seAgregaACarlosAlRepositorioYSeLoEditaYEncuentra(){
		RepositorioUsuarios.getInstance().add(carlos);
		carlos.setNombre("Charlie");
		RepositorioUsuarios.getInstance().update(carlos);
		assertEquals(carlos.getNombre(), RepositorioUsuarios.getInstance().get(carlos).getNombre());
	}

	@Test
	public void seAgregaACarlosAlRepositorioYSeLoEditaYDejaDeEncontrarAlViejoCarlos(){
		
		RepositorioUsuarios.getInstance().add(carlos);
		carlos.setNombre("Charlie");
		RepositorioUsuarios.getInstance().update(carlos);
		marcos.setNombre("Carlos");
		assertNull(RepositorioUsuarios.getInstance().get(marcos));
	}
	
	@Test 
	public void seAgregaACarlosAlRepositorioYFiguraEnLaListaCuandoBuscoPorElNombre(){
		RepositorioUsuarios.getInstance().add(carlos);
		assertTrue(RepositorioUsuarios.getInstance().searchByExample(carlos).contains(carlos));
	}
	
	@Test 
	public void seAgregaAMarcosalRepositorioYSeBuscaPorCarlosYLaListaEstaVacia(){
		RepositorioUsuarios.getInstance().add(marcos);
		assertTrue(RepositorioUsuarios.getInstance().searchByExample(carlos).isEmpty());
	}
	
	@Test
	public void seAgregaAMarcosSeBuscaPorPedroYNoEncuentraAMarcosPorqueNoTieneCondicionesNiSeLlamaIgual(){
		RepositorioUsuarios.getInstance().add(marcos);
		assertFalse(RepositorioUsuarios.getInstance().searchByExample(pedro).contains(marcos));
	}
	
	@Test
	public void seAgregaAMarcosSeBuscaPorMarcosYLoEncuentraPorquSeLlamaIgual(){
		RepositorioUsuarios.getInstance().add(marcos);
		assertTrue(RepositorioUsuarios.getInstance().searchByExample(marcos).contains(marcos));
	}
	
	@Test
	public void seAgregaAMarcosSeBuscaPorCarlosYEncuentraAMarcosPorqueTieneLasMismasCondicionesYNombre(){
		marcos.condicionesPreexistentes.add(new Diabetico());
		daniel.condicionesPreexistentes.add(new Vegano());
		marcos.setNombre("Daniel");
		RepositorioUsuarios.getInstance().add(marcos);
		assertTrue(RepositorioUsuarios.getInstance().searchByExample(daniel).contains(marcos));
	}
	
	@Test
	public void seAgregaAMarcosSeBuscaPorCarlosYNoLoEncuentraPorNoTenerCondiciones(){
		marcos.condicionesPreexistentes.remove(new Vegano());
		marcos.setNombre("Carlos");
		RepositorioUsuarios.getInstance().add(marcos);
		assertFalse(RepositorioUsuarios.getInstance().searchByExample(carlos).contains(marcos));
	}
	
	@Test
	public void marcosPideAgregarseAlSistemaYNoFiguraPorqueAunNoLoAceptan(){
		RepositorioUsuarios.getInstance().solicitarAlta(marcos);
		assertFalse(RepositorioUsuarios.getInstance().list().contains(marcos));
	}
	
	@Test(expected=BusinessException.class)
	public void marcosPideAgregarseAlSistemaYNoFiguraPorqueLoRechazan(){
		RepositorioUsuarios.getInstance().solicitarAlta(marcos);
		RepositorioUsuariosPendientes.getInstance().rejectUser("No quiero que entres");
	}
	
	@Test
	public void marcosPideAgregarseAlSistemaYFiguraPorqueLoAceptan(){
		RepositorioUsuarios.getInstance().solicitarAlta(marcos);
		RepositorioUsuariosPendientes.getInstance().acceptUser();
		assertTrue(RepositorioUsuarios.getInstance().list().contains(marcos));
	}
	@Test
	public void veganoNoIncluyeRecetasConLacteosOCarnesDelPluguin(){
		assertFalse(marcos.consultarPorRecetasVisibles().stream().anyMatch(receta -> receta.getNombrePlato().equals("flan casero")));
		assertFalse(marcos.consultarPorRecetasVisibles().stream().anyMatch(receta -> receta.getNombrePlato().equals("churrasco a la sal")));
		assertFalse(marcos.consultarPorRecetasVisibles().stream().anyMatch(receta -> receta.getNombrePlato().equals("gambas al ajillo")));
		assertFalse(marcos.consultarPorRecetasVisibles().stream().anyMatch(receta -> receta.getNombrePlato().equals("ensalada capresse")));
	}
	
	@Test
	public void hipertensoNoIncluyeElChurrascoALaSal(){
		carlos.agregarNuevoFiltro(new FiltrarPorCondicionesPreexistentes());
		assertFalse(carlos.consultarPorRecetasVisibles().stream().anyMatch(receta -> receta.getNombrePlato().equals("churrasco a la sal")));
		assertTrue(carlos.consultarPorRecetasVisibles().stream().anyMatch(receta -> receta.getNombrePlato().equals("cassatta")));
	}
	@After
	public void tearDown(){
		RepositorioRecetas.getInstance().cleanUp();
		RepositorioUsuarios.getInstance().cleanUp();
	}
}