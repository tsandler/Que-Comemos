package ar.edu.condicionPreexistente;

import java.util.ArrayList;
import java.util.List;

import ar.edu.exception.BusinessException;
import ar.edu.receta.*;
import ar.edu.usuario.Usuario;

public class Vegano extends CondicionPreexistente {
	
	List<String> alimentosProhibidos;
	
	public Vegano(){
		alimentosProhibidos = new ArrayList<String>();
		alimentosProhibidos.add("parmesano");
		alimentosProhibidos.add("pollo");
		alimentosProhibidos.add("carne");
		alimentosProhibidos.add("chivito");
		alimentosProhibidos.add("chori");
		alimentosProhibidos.add("jamon");
		alimentosProhibidos.add("berberechos");
		alimentosProhibidos.add("salmon");
		alimentosProhibidos.add("langostinos");
		alimentosProhibidos.add("mejillones");
		alimentosProhibidos.add("bife angosto");
		alimentosProhibidos.add("mozzarella");
		alimentosProhibidos.add("ricota");
		alimentosProhibidos.add("leche");
		this.nombre = "Vegano";
	}

	@Override
	public void esValido(Usuario persona){
		if (persona.getPreferenciasAlimenticias().contains("pollo") || persona.getPreferenciasAlimenticias().contains("carne") ||
				persona.getPreferenciasAlimenticias().contains("chivito") || persona.getPreferenciasAlimenticias().contains("chori"))
			throw new BusinessException("El usuario no es valido por ser vegano y no respetar");
	}
	
	@Override
	public void subsanaCondicion(Usuario persona){
		if(!(persona.getPreferenciasAlimenticias().contains("frutas")))
			throw new BusinessException("el usuario no sigue una rutina saludable por ser vegano y no gustarle las frutas");
	}
	
	@Override
	public boolean esInadecuadaPara(Receta unaReceta){
		return unaReceta.ingredientes.stream().anyMatch(ingrediente -> alimentosProhibidos.contains(ingrediente.nombre));
	}
}
