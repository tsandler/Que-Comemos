package ar.edu.condicionPreexistente;

import ar.edu.exception.BusinessException;
import ar.edu.receta.Receta;
import ar.edu.usuario.Rutina;
import ar.edu.usuario.Usuario;

public class Diabetico extends CondicionPreexistente {
	
	public Diabetico(){
		this.nombre = "Diabetico";
	}
	
	@Override
	public void esValido(Usuario persona) {
		super.esValido(persona);
		if (persona.sexo == "")
			throw new BusinessException("El usuario no es valido por ser diabetico y no definir sexo");
	}
	
	@Override
	public void subsanaCondicion(Usuario persona){
		if (!(persona.getRutina() == Rutina.INTENSIVOSIN || persona.getRutina()== Rutina.INTENSIVOCON || persona.peso <= 70))
			throw new BusinessException("El usuario no sigue una rutina saludable por ser diabetico y pesar menos de 70 o  no tener una rutina intensiva");
	}
	
	@Override
	public boolean esInadecuadaPara(Receta unaReceta){
	return unaReceta.contieneIngredienteConPesoMayorA("azucar",0.1) || unaReceta.contieneIngrediente("helado de chocolate"); 
	}
}
