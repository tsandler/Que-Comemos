var cantidadConsultas;

function Receta(){
	
};

Receta.asReceta = function(jsonreceta){
    return angular.extend(new Receta(), jsonreceta);
};

function sumarConsulta(){
	cantidadConsultas = cantidadConsultas+1;
};