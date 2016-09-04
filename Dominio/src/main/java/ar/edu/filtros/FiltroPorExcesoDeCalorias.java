package ar.edu.filtros;

import java.util.ArrayList;
import java.util.List;

import ar.edu.receta.Receta;
import ar.edu.usuario.Usuario;

public class FiltroPorExcesoDeCalorias extends Filtros{

	public FiltroPorExcesoDeCalorias(){
		this.setNombreFiltro("FiltroPorExcesoDeCalorias");
	}

	@Override
	public List<Receta> filtrarRecetas(Usuario unUser, List<Receta> recetasAFiltrar) {
		 if(unUser.calcularIMC() >= 25 && !(recetasAFiltrar.isEmpty())){
			 List<Receta> copiaDeRecetasAFiltrar = new ArrayList<Receta>();
			 for(Receta unaReceta : recetasAFiltrar){
				 if(unaReceta.getCalorias()<= 500){
					 copiaDeRecetasAFiltrar.add(unaReceta);
				 }
			 }
			 return copiaDeRecetasAFiltrar;
		 }else{
			 return recetasAFiltrar;
		 }		
	}	
}

//UNA PERSONA SE CONSIDERA CON SOBREPESO SI SU IMC ES MAYOR O IGUAL A 25

//UNA PERSONA CON SOBREPESO NO PUEDE COMER RECETAS QUE CONTENGAN MAS DE 500 CALORIAS