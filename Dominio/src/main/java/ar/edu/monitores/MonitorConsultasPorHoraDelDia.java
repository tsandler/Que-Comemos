package ar.edu.monitores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class MonitorConsultasPorHoraDelDia extends Monitor {
	
	public ArrayList<Integer> consultasPorHora = new ArrayList<Integer>(Collections.nCopies(24, 0));

	public void actualizarInformacion(Receta unaReceta, Usuario unUsuario) {
		consultasPorHora.set(this.horaDelDia(), consultasPorHora.get(this.horaDelDia()) + 1);
	}
	
	public int horaDelDia() {
		return Calendar.HOUR_OF_DAY;
	}
	
	public boolean sonTodosCeroMenos(int cantidad){
		int cantidadQueNoSonCero = 0;
		for(int cont=0;cont<24;cont++){
			if(consultasPorHora.get(cont) != 0){
				cantidadQueNoSonCero ++;
			}
		}
		return cantidadQueNoSonCero == cantidad;
	}
	
	@Override
	public List<Receta> getRecetasConsultadas() {
		// TODO Auto-generated method stub
		return null;
	}

}
