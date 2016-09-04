package ar.edu.requerimientos;

import java.util.List;

import ar.edu.filtros.Filtros;
import ar.edu.procesamientoPosterior.ProcesamientoPosterior;
import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class EnviarConsultaPorMail implements Requerimiento{
	List<Filtros> filtrosAplicados;
	ProcesamientoPosterior procesamientoPosteriorAplicado;
	int cantRecetasConsultadas;
	Usuario usuario;
	MailSender mailSender;
	
	public EnviarConsultaPorMail(Usuario unUsuario){
		usuario = unUsuario;
		filtrosAplicados = unUsuario.filtrosAplicables;
		procesamientoPosteriorAplicado = unUsuario.getProcPosterior(); 
	}
	
	public void setMailSender(MailSender mailSender){
		this.mailSender = mailSender;
	}
	
	public void realizarRequerimiento(List<Receta> recetasAConsultar){
		cantRecetasConsultadas = recetasAConsultar.size();
		mailSender.enviarMail(this.crearMail());
	}
	
	private Mail crearMail() {
		String asunto = "Datos consulta realizada";
		String mensaje = this.mensaje();
		String destinatario = usuario.getMail();
		return new Mail(asunto, mensaje, destinatario);
	}

	public String mensaje(){
		String mensaje;
		if(this.seAplicoProcesamientoPosterior()){
			mensaje = "Los filtros aplicados en la consulta son: " + this.mostrarFiltrosAplicados() + 
					", el procesamiento posterior aplicado es: " + procesamientoPosteriorAplicado.getNombreProcesamientoPosterior() + 
					" y la cantidad de resultados de la consulta es: " + cantRecetasConsultadas;
		}else
		{
			mensaje = "Los filtros aplicados en la consulta son: " + this.mostrarFiltrosAplicados()
					+ " y la cantidad de resultados de la consulta es: " + cantRecetasConsultadas;
		}
		return mensaje;
	}
	
	public boolean seAplicoProcesamientoPosterior(){
		return procesamientoPosteriorAplicado != null;
	}
	
	public String mostrarFiltrosAplicados(){
		String filtrosAMostrar = "";
		for(Filtros filtro: filtrosAplicados){
			filtrosAMostrar = filtrosAMostrar + ", " + filtro.getNombreFiltro();
		}
		return filtrosAMostrar.substring(2, filtrosAMostrar.length());
	}
}


