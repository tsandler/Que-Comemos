package ar.edu.receta;

import static org.junit.Assert.*;
import ar.edu.condicionPreexistente.Hipertenso;

import org.junit.Before;
import org.junit.Test;

public class TestRecetaCompuesta {
	
	private Receta milanesaConPure;
	
	@Before
	public void init() {
		Receta pure = new RecetaBuilder().nombre("Pure de papa").calorias(500)
				.agregarIngrediente(new Ingrediente("papa", 300)).agregarIngrediente(new Ingrediente("leche", 100))
				.agregarIngrediente(new Ingrediente("pimienta", 0.05)).agregarIngrediente(new Ingrediente("sal", 0.10)).build();
		
		milanesaConPure = new RecetaBuilder().nombre("Milanesa con pure").calorias(600)
				.agregarIngrediente(new Ingrediente("pan", 50)).agregarIngrediente(new Ingrediente("huevo", 2))
				.agregarIngrediente(new Ingrediente("carne", 2)).agregarIngrediente(new Ingrediente("sal", 0.10))
				.agregarSubreceta(pure).build();
	}
	
	@Test
	public void testCaloriasDeSubrecetaSuman() {
		assertEquals(milanesaConPure.cantidadCalorias(), 1100);
	}
	
	@Test
	public void testContieneIngredienteDeSubreceta() {
		assertTrue(milanesaConPure.contieneIngrediente("leche"));
	}
	
	@Test
	public void testContieneCondimentoDeSubreceta() {
		assertTrue(milanesaConPure.contieneIngrediente("pimienta"));
	}
	
	@Test
	public void testCondicionPreexistenteFuncionaEnSubreceta() {
		assertTrue(milanesaConPure.condicionEsInadecuada(new Hipertenso()));
	}
	
	@Test
	public void testCantidadDeIngredienteTieneEnCuentaSubrecetas() {
		assertTrue(milanesaConPure.contieneIngredienteConPesoMayorA("sal", 0.199));
	}
	
	@Test
	public void testCantidadDeIngredienteTieneEnCuentaSubrecetasFalse() {
		assertTrue(!milanesaConPure.contieneIngredienteConPesoMayorA("sal", 0.201));
	}
}
