package ar.edu.grupo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;
import ar.edu.receta.RecetaBuilder;
import ar.edu.usuario.Usuario;
import ar.edu.usuario.UsuarioBuilder;

public class TestGrupo {

	Usuario pedro;
	Usuario marcos;
	Receta sanguche;
	Grupo grupon;
	Receta pizza4Quesos;
	Receta cafeDeStarbucks;
	Receta polloConPapas;

	@Before
	public void init(){
		Ingrediente jamon = new Ingrediente("jamon", 0.05);
		Ingrediente queso = new Ingrediente("queso", 0.05);
		Ingrediente pan = new Ingrediente("pan", 0.1);
		Ingrediente aceituna = new Ingrediente("aceituna", 8);
		Ingrediente azucar = new Ingrediente("azucar", 150);
		Ingrediente pollo = new Ingrediente("pollo", 1);
		Ingrediente sal = new Ingrediente("sal", 50);
		
		sanguche = new RecetaBuilder().nombre("Sanguche de jamon y queso").calorias(300)
				.agregarIngrediente(pan).agregarIngrediente(queso).agregarIngrediente(jamon).build();
		
		pizza4Quesos = new RecetaBuilder().nombre("pizza 4 quesos").calorias(1200)
				.agregarIngrediente(aceituna).build();
		
		cafeDeStarbucks = new RecetaBuilder().nombre("cafe de starbucks").calorias(600)
				.agregarIngrediente(azucar).build();
		
		polloConPapas = new RecetaBuilder().nombre("pollo con papas").calorias(1500)
				.agregarIngrediente(sal).agregarIngrediente(pollo).build();
		
		pedro = new UsuarioBuilder().conDatosBasicos("Pedro", "12/03/1996").esHombre().conDatosDeComplexion(55, 1.6)
				.conReceta(sanguche).esDiabetico().esVegano().esHipertenso().build();
		
		marcos = new UsuarioBuilder().conDatosBasicos("Marcos", "12/03/1996").esHombre().conDatosDeComplexion(70, 1.9).build();
		
		grupon = new Grupo("The git team");
		grupon.agregarUsuario(pedro);
		
		grupon.agregarPreferenciaAlimenticia("pizza");
		grupon.agregarPreferenciaAlimenticia("cafe");
		grupon.agregarPreferenciaAlimenticia("pollo");
	}
	
	@Test
	public void alGrupoNoLeSugieroSanguchePorqueNoTieneNingunaPreferenciaAlimenticia(){
		assertFalse(grupon.nosSugerisLaReceta(sanguche));
	}
	
	@Test
	public void alGrupoNoLeSugieroCafeDeStarbucksPorquePedroEsDiabetico(){
		assertFalse(grupon.nosSugerisLaReceta(cafeDeStarbucks));
	}
	
	@Test
	public void alGrupoNoLeSugieroPolloPorquePedroEsVegano(){
		assertFalse(grupon.nosSugerisLaReceta(polloConPapas));
	}
	
	@Test
	public void alGrupoNoLeSugieroPolloPorquePedroEsHipertenso(){
		assertFalse(grupon.nosSugerisLaReceta(polloConPapas));
	}
	
	@Test
	public void alGrupoLeSugieroPizzaPorqueLaTienenPreferenciaAlimenticiaYNoLeHaceMalANadie(){
		assertTrue(grupon.nosSugerisLaReceta(pizza4Quesos));
	}
	
	@Test
	public void pedroAgregaUnaRecetaPersonalYElGrupoNoLaTienePorqueLoSacanDelGrupo(){
		grupon.usuarios.remove(pedro);
		assertFalse(grupon.recetasDelGrupo().contains(sanguche));
	}
	
	@Test
	public void pedroAgregaUnaRecetaPersonalYPasaASerDelGrupo(){
		assertTrue(grupon.recetasDelGrupo().contains(sanguche));
	}
	
	@Test
	public void marcosNoPuedeVerUnaRecetaDePedroPorqueNoCompartenGrupo(){
		assertFalse(marcos.puedeVerOModificarReceta(sanguche));
	}
	
	@Test
	public void marcosPuedeVerUnaRecetaDePedroPorqueCompartenUnGrupo(){
		grupon.agregarUsuario(marcos);
		assertTrue(marcos.puedeVerOModificarReceta(sanguche));
	}
	
}
