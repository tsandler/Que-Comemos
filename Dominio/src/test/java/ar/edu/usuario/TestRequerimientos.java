package ar.edu.usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.receta.Ingrediente;
import ar.edu.receta.Receta;
import ar.edu.receta.RecetaBuilder;
import ar.edu.repositorios.RepositorioRecetas;
import ar.edu.requerimientos.AgregarFavoritos;
import ar.edu.requerimientos.EnviarConsultaPorMail;
import ar.edu.requerimientos.Logueo;
import ar.edu.requerimientos.Mail;
import ar.edu.requerimientos.MailSender;

public class TestRequerimientos {

	Usuario pedro;
	
	Receta fideos;
	Receta sanguche;
	
	Ingrediente jamon;
	Ingrediente pan;
	Ingrediente mayonesa;
	Ingrediente aceituna;
	Ingrediente azucar;
	Ingrediente pollo;
	Ingrediente sal;
	Ingrediente manteca;
	Ingrediente queso;
	MailSender mockMailSender;
	Logger mockLogueo;
	
	
	List<Receta> recetasAConsultar;
	
	@Before
	public void init(){

		pedro = new UsuarioBuilder().conDatosBasicos("Pedro", "12/03/1996").esHombre().conDatosDeComplexion(55, 1.6)
				.leGusta("frutas").conMail("pedro@gmail.com").filtraPorCondPreexistentes().build();
		
		sal = new Ingrediente("sal",10);
		queso = new Ingrediente ("queso",25);
		jamon = new Ingrediente("jamon", 0.05);
		pan = new Ingrediente("pan", 0.1);
		
		fideos = new RecetaBuilder().nombre("fideos").calorias(100)
				.agregarIngrediente(sal).build();
		
		sanguche = new RecetaBuilder().nombre("Sanguche de Jamon y Queso").calorias(300)
				.agregarIngrediente(jamon).agregarIngrediente(queso).agregarIngrediente(pan).build();
		
		recetasAConsultar = new ArrayList<>();
		recetasAConsultar.add(sanguche);
		recetasAConsultar.add(fideos);
		RepositorioRecetas.getInstance().add(sanguche);
		RepositorioRecetas.getInstance().add(fideos);
		
		mockMailSender = mock(MailSender.class);
		mockLogueo = mock(Logger.class);
	}
	
	@Test
	public void envioDeUnMailLuegoDeConsultarRecetas(){
		EnviarConsultaPorMail enviarConsultaPorMail = new EnviarConsultaPorMail(pedro);
		enviarConsultaPorMail.setMailSender(mockMailSender);
		pedro.agregarRequerimiento(enviarConsultaPorMail);
		enviarConsultaPorMail.realizarRequerimiento(recetasAConsultar);
		verify(mockMailSender, times(1)).enviarMail(any(Mail.class));
	}
	@Test
	public void agregaSangucheAFavoritos(){
		AgregarFavoritos agregarFavoritos = new AgregarFavoritos(pedro);
		pedro.agregarRequerimiento(agregarFavoritos);
		agregarFavoritos.realizarRequerimiento(recetasAConsultar);
		assertTrue(pedro.recetasFavoritas.contains(sanguche));
	}
	
	@Test
	public void fideosEstaUnaVezEnLaLista(){
		pedro.recetasFavoritas.add(fideos);
		AgregarFavoritos agregarFavoritos = new AgregarFavoritos(pedro);
		pedro.agregarRequerimiento(agregarFavoritos);
		agregarFavoritos.realizarRequerimiento(recetasAConsultar);
		assertEquals(2,pedro.recetasFavoritas.size());
	}
	
	@Test
	public void logueoConsultasConMasDe100Resultados(){
		Logueo logger = new Logueo();
		logger.setLogger(mockLogueo);
		List<Receta> spy = spy(new ArrayList<Receta>());
		when(spy.size()).thenReturn(101);
		logger.realizarRequerimiento(spy);
		verify(mockLogueo, times(1)).info("La consulta devolvio mas de 100 resultados");
	}
	
	@After
	public void tearDown(){
		RepositorioRecetas.getInstance().cleanUp();
	}

}
