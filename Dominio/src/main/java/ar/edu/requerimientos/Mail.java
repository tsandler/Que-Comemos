package ar.edu.requerimientos;

public class Mail {
	
	private String asunto;
	private String texto;
	private String destinatario;
	
	Mail(String unAsunto, String unTexto, String unDestinatario){
		asunto = unAsunto;
		texto = unTexto;
		destinatario = unDestinatario;
	}

	public String getAsunto(){
		return asunto;
	}
	
	public String getTexto(){
		return texto;
	}
	
	public String getDestinatario(){
		return destinatario;
	}
	
}
