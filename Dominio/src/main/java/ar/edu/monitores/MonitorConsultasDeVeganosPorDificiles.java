package ar.edu.monitores;

import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class MonitorConsultasDeVeganosPorDificiles extends Monitor {

	public int cantidadDeVeganosQueConsultaronPorRecetasDificiles = 0;
	
	public void actualizarInformacion(Receta unaReceta, Usuario unUsuario) {
		if(unaReceta.esDificil() && unUsuario.esVegano()){
			cantidadDeVeganosQueConsultaronPorRecetasDificiles ++;
		}
	}

	@Override
	public List<Receta> getRecetasConsultadas() {
		// TODO Auto-generated method stub
		return null;
	}

}
