
queComemosApp.service('ConsultasServices', function($http, RecetasServices){
    this.initialize = function(object, title){
        object.titulo = title;
        object.quantity = 50;
        object.verTodas = false;
        object.verBusqueda = false;
        object.verFavoritas = false;
        object.verMonitoreo = false;
    };

    this.findall = function(object, dir){
        RecetasServices.findAll(dir, function(data){
            object.recetas = _.map(data.data, Receta.asReceta);
        })
    };
});

queComemosApp.service('RecetasServices', function ($http, $cookies) {
    this.findAll = function (dir, callback) {
        $http.get('/recetasUsuario' + dir + $cookies.get("usuario")).then(callback);
    };

    this.consultarReceta = function(nombre){
        $http.put('/consultarReceta/' + nombre + '/' + $cookies.get("usuario"));
    };

    this.getRecetaByNombre = function(nombreReceta, callback){
       $http.get('/recetas/' + nombreReceta).then(callback);
    };

    this.condicionesPreexistentes = function(receta, callback){
        $http.get('/recetas/condicionesPreexistentes/' + receta).then(callback);
    };

    this.agregarIngrediente = function(nombre, cantidad, receta, callback){
        $http.put('/recetas/' + nombre + '/' + cantidad + '/' + receta).then(callback);
    };
    
    this.obtenerFavoritismo = function(nombrePlato, callback){
    	$http.get('/receta/favorita/' + nombrePlato + '/' + $cookies.get("usuario")).then(callback);
    };
    
    this.eliminarIngrediente = function(receta, nombreIngrediente, callback){
    	$http.delete('recetas/' + receta + '/' + nombreIngrediente).then(callback);
    };

    this.guardarReceta = function(receta, usuario, successCallback, failureCallback) {
        $http.put('/guardarReceta/' + usuario, receta).then(successCallback, failureCallback);
    };

    this.actualizarReceta = function(receta, usuario, $state){
        this.guardarReceta(receta, usuario, function(){
            $state.go('verRecetas');
        }, function(data){
            alert(data.data);
        })
    };

    this.buscarRecetas = function(nombreUsuario, nombreReceta, caloriasMin, caloriasMax, dificultad, temporada, ingrediente, filtro, callback){
    	 $http.get('/busquedaRecetas/' + nombreUsuario + '/' + nombreReceta + '/' + caloriasMin + '/' + caloriasMax + '/' + dificultad + '/' + temporada + '/' + ingrediente + '/' + filtro).then(callback);
     };
});

queComemosApp.service('UsuarioService', function ($http) {
	this.getUsuarioByUsername = function (nombreUsuario, callback){
        $http.get('/usuarios/' + nombreUsuario).then(callback);
	};

    this.getIMCByUsername = function(nombreUsuario, callback) {
        $http.get('/usuarios/imc/' + nombreUsuario).then(callback);
    };
    
    this.getDateByUsername = function(nombreUsuario, callback) {
    	$http.get('/usuarios/date/' + nombreUsuario).then(callback);
    };
});

queComemosApp.service('AutenticacionService', function($http) {
    this.autenticar = function(nombreUsuario, password, callback) {
        $http.get('/autenticacion/' + nombreUsuario + '/' + password).then(callback);
    };

    this.registrar = function(formulario, callback, errorHandler){
        /*$http.put('/usuarios/'+formulario.usuario+'/'+formulario.password+'/'+formulario.mail+'/'+formulario.sexo+'/'+formulario.altura
        +'/'+formulario.peso+'/'+formulario.fecha+'/'+formulario.rutina).then(callback, errorHandler);*/
        $http.put('/usuarios/registrar/' + formulario.fecha, formulario).then(callback, errorHandler);
        $http.put('/usuarios/condiciones/' + formulario.nombre + '/' + formulario.condiciones);
    };

    this.consultarFecha = function(fecha, callback){
        $http.get('/consultarFecha/' + fecha).then(callback);
    }
});

queComemosApp.service('CondicionesService', function($http){
   this.findall = function(callback){
     $http.get('/condiciones/').then(callback);
   };
});

queComemosApp.service('IngredientesService', function($http){
   this.findall = function(callback){
     $http.get('/ingredientes/').then(callback);
   };
});