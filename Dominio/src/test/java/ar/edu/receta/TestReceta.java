package ar.edu.receta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import ar.edu.condicionPreexistente.Diabetico;
import ar.edu.condicionPreexistente.Hipertenso;
import ar.edu.condicionPreexistente.Vegano;
import ar.edu.repositorios.RepositorioRecetas;
import queComemos.entrega3.creacionales.RecetaBuilder;
import queComemos.entrega3.repositorio.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class TestReceta {
	
	private class RepoRecetasMock{
		
		public String getRecetas(){
			List<queComemos.entrega3.dominio.Receta> recetas = new ArrayList<queComemos.entrega3.dominio.Receta>();
			recetas.add(new RecetaBuilder("Ravioles a la Bolognesa").agregar("ravioles").agregar("tomates").agregar("Carne Picada")
					.tiempoPreparacion(150).inventadaPor("El Tano").dificil().build());
			String resultJSon = new Gson().toJson(recetas);
			return resultJSon;
		}
	
	}

	Receta sanguche;
	
	@Before
	public void init(){
		Ingrediente queso = new Ingrediente("queso", 0.05);
		Ingrediente pan = new Ingrediente("pan", 0.1);
		Ingrediente mayonesa = new Ingrediente("mayonesa", 0.01);
		
		sanguche = new ar.edu.receta.RecetaBuilder().nombre("Sanguche de jamon y queso").calorias(300)
				.agregarIngrediente(queso).agregarIngrediente(pan).agregarIngrediente(mayonesa).build();
		
		RepositorioRecetas.getInstance().add(sanguche);
	}
	
	@Test
	public void noTieneCondicionesInadecuadas(){
		assertFalse(sanguche.tieneCondicionesInadecuadas());
	}
	
	@Test
	public void laRecetaEsPublica(){
		assertFalse(sanguche.isPrivada());
	}
	
	@Test(expected=RuntimeException.class)
	public void noEsRecetaValidaPorqueNoTieneIngredientes(){
		sanguche.ingredientes.clear();
		sanguche.validarReceta();
	}
	
	@Test(expected=RuntimeException.class)
	public void noEsRecetaValidaPorqueTieneMenosDe10Calorias(){
		sanguche.setCalorias(9);
		sanguche.validarReceta();
	}
	
	@Test(expected=RuntimeException.class)
	public void noEsRecetaValidaPorqueTieneMasDe5000Calorias(){
		sanguche.setCalorias(5001);
		sanguche.validarReceta();
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsHipertensoPorTenerSal() {
		sanguche.agregarIngrediente(new Ingrediente("sal", 0.02));
		assertTrue(sanguche.condicionEsInadecuada(new Hipertenso()));
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsHipertensoPorTenerCaldo() {
		sanguche.agregarIngrediente(new Ingrediente("caldo", 0.02));
		assertTrue(sanguche.condicionEsInadecuada(new Hipertenso()));
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsDiabetico() {
		sanguche.agregarIngrediente(new Ingrediente("azucar", 0.2));
		assertTrue(sanguche.condicionEsInadecuada(new Diabetico()));
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsVeganoPorTenerPollo() {
		sanguche.agregarIngrediente(new Ingrediente("pollo", 0.2));
		assertTrue(sanguche.condicionEsInadecuada(new Vegano()));
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsVeganoPorTenerCarne() {
		sanguche.agregarIngrediente(new Ingrediente("carne", 0.2));
		assertTrue(sanguche.condicionEsInadecuada(new Vegano()));
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsVeganoPorTenerChivito() {
		sanguche.agregarIngrediente(new Ingrediente("chivito", 0.2));
		assertTrue(sanguche.condicionEsInadecuada(new Vegano()));
	}
	
	@Test
	public void condicionesPreexistentesInadecuadasEsVeganoPorTenerChori() {
		sanguche.agregarIngrediente(new Ingrediente("chori", 0.2));
		assertTrue(sanguche.condicionEsInadecuada(new Vegano()));
	}
	
	public void laListaDeCondicionesPreexistentesInadecuadasEstaVacia(){
		sanguche.condicionesInadecuadas().isEmpty();
	}
	
	@Test
	public void laListaDeCondicionesPreexistentesInadecuadasNoEsVaciaPorTenerChori(){
		sanguche.agregarIngrediente(new Ingrediente("chori", 0.2));
		sanguche.condicionesInadecuadas();
	}
	
	@Test
	public void laRecetaCreadaPerteneceALasRecetasDelSistema(){
		assertTrue(RepositorioRecetas.getInstance().list().contains(sanguche));
	}
		
	@Test
	public void mockeoParaProbarQueSeAdaptanBienlasRecetas(){
		RepoRecetasMock repoExternoImpostor = new RepoRecetasMock();
		List<Receta> listaEsperada = new ArrayList<Receta>();
		RepoRecetas mockRepo = mock(RepoRecetas.class);
		AdapterRepoRecetas adapterAProbar = new AdapterRepoRecetas();
		adapterAProbar.copiaDelRepositorio = mockRepo;
		listaEsperada.add(new ar.edu.receta.RecetaBuilder().nombre("Ravioles a la Bolognesa").calorias(150)
				.agregarIngrediente(new Ingrediente("ravioles", 1)).agregarIngrediente(new Ingrediente("tomates", 1)).agregarIngrediente(new Ingrediente("Carne Picada", 1)).dificultad("dificil").build());
		when(mockRepo.getRecetas(adapterAProbar.buscadorDefault)).thenReturn(repoExternoImpostor.getRecetas());
		assertEquals(adapterAProbar.getRecetasDelPlugin(), listaEsperada);
	}
	
	@Test
	public void testsEstructuralDeLasRecetaDelPluguinLuegoDeParsearlaDesdeJSON(){
		AdapterRepoRecetas adapter = new AdapterRepoRecetas();
		List<Receta> recetas = new ArrayList<Receta>();
		recetas = adapter.getRecetasDelPlugin();
		assertEquals(recetas.get(0).ingredientes.get(0).getNombre(), "lechuga");
		assertEquals(recetas.get(1).getNombrePlato(), "ensalada lechuga agridulce");
		assertEquals(recetas.get(1).getDificultadPreparacion(),"mediana");
		assertEquals(recetas.get(2).getCalorias(), 30);
		assertEquals(recetas.size(), 12);	
	}
	
	@Test
	public void buscoALaRecetaPorNombreYLaEncuentra(){
		Receta receta = RepositorioRecetas.getInstance().getxNombre("Sanguche de jamon y queso");
		assertEquals(receta, sanguche);
	}
}
