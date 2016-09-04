package ar.edu.usuario;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.condicionPreexistente.Vegano;
import ar.edu.monitores.MonitorConsultasDeVeganosPorDificiles;
import ar.edu.monitores.MonitorRecetasMasConsultadas;
import ar.edu.monitores.MonitorRecetasMasConsultadasPorGenero;
import ar.edu.monitores.StubMonitorConsultasPorHoraDelDia;
import ar.edu.receta.AdapterRepoRecetas;
import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;
import ar.edu.receta.RecetaBuilder;
import ar.edu.repositorios.RepositorioRecetas;
import ar.edu.repositorios.RepositorioUsuarios;

public class TestObservers {

	Usuario pedro;
	Usuario carlos;
	Usuario marcos;
	
	Receta fideos;
	Receta sanguche;
	
	AdapterRepoRecetas adapter = new AdapterRepoRecetas();
	
	MonitorRecetasMasConsultadas actualizacionRecetasMasConsultadas;
	MonitorConsultasDeVeganosPorDificiles actualizarCantidadDeVeganosQueConsultaronPorRecetasDificiles;
	MonitorRecetasMasConsultadasPorGenero actualizacionRecetasMasConsultadasPorGenero;
	StubMonitorConsultasPorHoraDelDia actualizacionConsultasPorHoraDelDia;
	
	@Before
	public void init(){

		pedro = new UsuarioBuilder().conDatosBasicos("Pedro", "12/03/1996").esHombre().conDatosDeComplexion(55, 1.6)
				.leGusta("frutas").filtraPorCondPreexistentes().build();
		
		marcos = new UsuarioBuilder().conDatosBasicos("Marcos", "12/03/1996").esHombre().conDatosDeComplexion(70, 1.9)
				.filtraPorCondPreexistentes().build();
		
		Ingrediente sal = new Ingrediente("sal",10);
		Ingrediente queso = new Ingrediente ("queso",25);
		Ingrediente jamon = new Ingrediente("jamon", 0.05);
		Ingrediente pan = new Ingrediente("pan", 0.1);
		
		fideos = new RecetaBuilder().nombre("fideos").calorias(100).dificultad("D")
				.agregarIngrediente(sal).build();
		
		sanguche = new RecetaBuilder().nombre("Sanguche de jamon y queso").calorias(300)
				.agregarIngrediente(pan).agregarIngrediente(jamon)
				.agregarIngrediente(queso).build();
		
		RepositorioRecetas.getInstance().add(sanguche);
		RepositorioRecetas.getInstance().add(fideos);
		
		actualizacionRecetasMasConsultadas = new MonitorRecetasMasConsultadas();
		actualizarCantidadDeVeganosQueConsultaronPorRecetasDificiles = new MonitorConsultasDeVeganosPorDificiles();
		actualizacionRecetasMasConsultadasPorGenero = new MonitorRecetasMasConsultadasPorGenero();
		actualizacionConsultasPorHoraDelDia = new StubMonitorConsultasPorHoraDelDia(10);
		
	}
	
	/*@Test
	public void sangucheDebeDar2PorqueLaConsultanLosDosYFideosLaConsultaSoloMarcosPorquePedroEsHipertensoYTieneSal(){
		pedro.condicionesPreexistentes.add(new Hipertenso());
		pedro.agregarActualizacion(actualizacionRecetasMasConsultadas);
		marcos.agregarActualizacion(actualizacionRecetasMasConsultadas);
		pedro.consultarPorRecetasVisibles();
		marcos.consultarPorRecetasVisibles();
		
		assertEquals((int) actualizacionRecetasMasConsultadas.recetasMasConsultadas.get(sanguche), (int) 2);
		assertEquals((int) actualizacionRecetasMasConsultadas.recetasMasConsultadas.get(fideos), (int) 1);
	}*/

	@Test
	public void debeDar1PorquePedroEsVeganoYConsultoPorFideosQueEsDificil(){
		pedro.condicionesPreexistentes.add(new Vegano());
		pedro.agregarActualizacion(actualizarCantidadDeVeganosQueConsultaronPorRecetasDificiles);
		marcos.agregarActualizacion(actualizarCantidadDeVeganosQueConsultaronPorRecetasDificiles);
		pedro.consultarPorRecetasVisibles();
		marcos.consultarPorRecetasVisibles();
		
		assertEquals((int) actualizarCantidadDeVeganosQueConsultaronPorRecetasDificiles.cantidadDeVeganosQueConsultaronPorRecetasDificiles, (int)1);
	}
	
	@Test
	/*public void pruebaLaCantidadDeConsultasQueSeRealizaronPorHoraDebeDar2EnCadaHorarioQueSeteoPorqueConsultaPorSangucheYFideos(){

		pedro.agregarActualizacion(actualizacionConsultasPorHoraDelDia);
		actualizacionConsultasPorHoraDelDia.setHora(10);
		List<Receta> recetasVisibles = new ArrayList<Receta>();
		recetasVisibles = pedro.consultarPorRecetasVisibles();
		actualizacionConsultasPorHoraDelDia.setHora(17);
		pedro.consultarPorRecetasVisibles();
		List<Receta> recetasDelPlugin = adapter.getRecetasDelPlugin();
		recetasDelPlugin = pedro.filtrarRecetas(recetasDelPlugin);
		recetasDelPlugin = pedro.realizarProcesamientoPosterior(recetasDelPlugin);
		int cantidadRecetasFiltradas = 2 + recetasDelPlugin.size();
		
		assertEquals(recetasVisibles.size(), cantidadRecetasFiltradas);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(0),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(1),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(2),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(3),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(4),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(5),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(6),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(7),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(8),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(9),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(10), cantidadRecetasFiltradas);
		
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(11),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(12),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(13),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(14),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(15),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(16),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(17), cantidadRecetasFiltradas);
		
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(18),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(19),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(20),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(21),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(22),(int) 0);
		assertEquals((int) actualizacionConsultasPorHoraDelDia.consultasPorHora.get(23),(int) 0);
	}*/
	

	@After
	public void tearDown(){
		RepositorioRecetas.getInstance().cleanUp();
		RepositorioUsuarios.getInstance().cleanUp();
	}

}
