
function Usuario(id, nombre){
    this.id = id;
    this.nombre = nombre;
}

Usuario.asUsuario = function(jsonUsuario){
	return angular.extend(new Usuario(), jsonUsuario);
}