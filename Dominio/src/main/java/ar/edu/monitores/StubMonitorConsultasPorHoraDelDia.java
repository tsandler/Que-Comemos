package ar.edu.monitores;

public class StubMonitorConsultasPorHoraDelDia extends MonitorConsultasPorHoraDelDia {
	int hora;
	
	public StubMonitorConsultasPorHoraDelDia(int horaQueQuieroQueSea){
		hora = horaQueQuieroQueSea;
	}
	
	public void setHora(int nuevaHora){
		hora = nuevaHora;
	}
	
	public int horaDelDia() {
		return hora;
	}

}
