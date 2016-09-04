package ar.edu.condicionPreexistente;

import ar.edu.exception.BusinessException;
import ar.edu.receta.Receta;
import ar.edu.usuario.Rutina;
import ar.edu.usuario.Usuario;

public class Hipertenso extends CondicionPreexistente {
	
	public Hipertenso(){
		this.nombre = "Hipertenso";
	}
	
	@Override
	public void subsanaCondicion(Usuario persona){
		if (!(persona.getRutina() == Rutina.INTENSIVOCON))
			throw new BusinessException("el usuario no sigue una rutina saludable por no tener una rutina activa con ejercicio adicional"); 
	}
	
	@Override
	public boolean esInadecuadaPara(Receta unaReceta){
		return unaReceta.contieneIngrediente("sal") || unaReceta.contieneIngrediente("caldo") || unaReceta.contieneIngrediente("sal gruesa");
	}
}
