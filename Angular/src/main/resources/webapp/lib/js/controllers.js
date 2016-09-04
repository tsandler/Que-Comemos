queComemosApp.controller('IngredienteController', function($stateParams, $state, RecetasServices){
    var self = this;
    this.receta = $stateParams.receta;
    this.agregarIngrediente = function(){
        if (self.nombre == null)
            alert("Debe seleccionar un ingrediente de la lista");
        else{
                if( (! (self.cantidad - Math.floor(self.cantidad) == 0)) || (self.cantidad <= 0) )
                    alert("La Cantidad debe ser un entero");
                else{
                         RecetasServices.agregarIngrediente(self.nombre, self.cantidad, $stateParams.receta, function(){
                        self.volver();
                    });
            };
        }
    };

    this.volver = function(){
        $state.go('detalleDeReceta', {nombre: $stateParams.receta});
    };

    this.eliminar = function(){
        RecetasServices.eliminarIngrediente(receta,nombreIngrediente, function(data){
            alert("El ingrediente se elimin� satisfactoriamente");
            this.volver();
        });
    };
});


queComemosApp.controller('CrearReceta', function($cookies, $state, RecetasServices) {
    var self = this;
    self.receta = $cookies.getObject('nuevaReceta');
    self.ingredientes = self.receta.ingredientes;
    self.labelBoton = "Crear Receta";

    this.getReceta = function(){
        self.receta = $cookies.getObject('nuevaReceta');
        self.ingredientes = self.receta.ingredientes;
    };

    this.condiciones = function(nombre) {
        //null
    };

    this.eliminarIngrediente = function(nombre) {
        for(i = 0; i < self.receta.ingredientes.length; i++) {
            if (self.receta.ingredientes[i].nombre === nombre) {
                self.receta.ingredientes.splice(i, 1);
                break;
            }
        }
        $cookies.putObject('nuevaReceta', self.receta);
    };

    this.validar = function() {
        var validacion = "";
        if (!self.receta.explicacionPreparacion || self.receta.explicacionPreparacion.length === 0) {
            self.receta.explicacionPreparacion = "Sin explicacion";
        }
        if (self.receta.ingredientes.length === 0) {
            validacion = validacion + "Debe agregar al menos un ingrediente.\n";
        }
        return validacion;
    };


    this.actualizar = function() {
        var validacion =  self.validar();
        if (validacion === "") {
            this.actualizar = function(){
                RecetasServices.actualizarReceta(self.receta, $cookies.get("usuario"))
            };
        } else {
            alert(validacion);
        }

    };

    this.nuevoIngrediente = function() {
        $cookies.putObject('nuevaReceta', self.receta);
        $state.go('nuevoIngredienteRecetaNueva', {receta: self.receta.nombrePlato});
    };
});

queComemosApp.controller('RecetaController', function ($window, $cookies, $stateParams, $state, RecetasServices){
    var self = this;
    self.labelBoton = "Actualizar";
    this.getReceta = function(){
        RecetasServices.getRecetaByNombre($stateParams.nombre, function (data){
            self.receta = Receta.asReceta(data.data);
            self.ingredientes = self.receta.ingredientes.map(Ingrediente.asIngrediente);
            self.condiciones(self.receta.nombrePlato);
            RecetasServices.obtenerFavoritismo(self.receta.nombrePlato, function(data){
                self.receta.favorita = data.data;
            });
            self.consultarReceta(self.receta.nombrePlato);
        });
    };

    this.consultarReceta = function(nombre){
        RecetasServices.consultarReceta(nombre);
    };

    this.condiciones = function(nombre){
        RecetasServices.condicionesPreexistentes(nombre, function(data){
            self.condicionesPreexistentes = _.map(data.data, CondicionPreexistente.asCondicion);
        });
    };

    this.actualizar = function(){
        RecetasServices.actualizarReceta(self.receta, $cookies.get("usuario"))
    };

    this.nuevoIngrediente = function() {
        $state.go('nuevoIngrediente', {receta: self.receta.nombrePlato});
    };

    this.eliminarIngrediente = function(nombre) {
        if (self.receta.ingredientes.length == 1) {
            alert('No puede borrar todos los ingredientes de una receta');
            return;
        }
        RecetasServices.eliminarIngrediente(self.receta.nombrePlato, nombre, function(data) {
            self.getReceta();
        });
    };

    this.getReceta();
});

queComemosApp.controller('RecetasCtrl', function (ConsultasServices) {
    ConsultasServices.initialize(this, "Recetas");
    ConsultasServices.findall(this, "/");
    this.verTodas = true;
});

queComemosApp.controller('RecetasFavoritas', function(ConsultasServices) {
    ConsultasServices.initialize(this, "Recetas Favoritas");
    ConsultasServices.findall(this, "/favoritas/");
    this.verFavoritas = true;
});

queComemosApp.controller('ConsultasDeRecetas', function(ConsultasServices){
    ConsultasServices.initialize(this, "Monitoreo de Recetas");
    ConsultasServices.findall(this, "/masConsultadas/");
    this.quantity = 10;
    this.verMonitoreo = true;
});

queComemosApp.controller('BusquedaRecetas', function(ConsultasServices, RecetasServices, $cookies, $state){
    var self = this;
    ConsultasServices.initialize(this, "Busqueda de Recetas");
    ConsultasServices.findall(this, "/");
    this.verBusqueda = true;

    this.buscarRecetas = function(){
    	if(self.nombreBuscado == ""){
    		self.nombreBuscado = "undefined";
    	};
    	
    	if(self.caloriasMin == ""){
    		self.caloriasMin = "undefined";
    	};
    	
    	if(self.caloriasMax == ""){
    		self.caloriasMax = "undefined";
    	}
    	
    	if(self.dificultadBuscada == ""){
    		self.dificultadBuscada = "undefined";
    	};
    	
    	if(self.temporadaBuscada == ""){
    		self.temporadaBuscada = "undefined";
    	};
    	
    	if(self.conIngrediente == ""){
    		self.conIngrediente = "undefined";
    	};
    	
    	if(self.aplicaFiltro == ""){
    		self.aplicaFiltro = "undefined";
    	};
    	
        RecetasServices.buscarRecetas($cookies.get("usuario"), self.nombreBuscado, self.caloriasMin, self.caloriasMax, self.dificultadBuscada, self.temporadaBuscada, self.conIngrediente, self.aplicaFiltro, function(data){
        	self.recetas = _.map(data.data, Receta.asReceta);
        });
    };
});


queComemosApp.controller('VerPerfilUsuario', function (UsuarioService, $cookies){
    var self = this;
    this.getUsuario = function(){
        UsuarioService.getUsuarioByUsername($cookies.get("usuario"), function (data){
            self.usuario = Usuario.asUsuario(data.data);
        });

        UsuarioService.getIMCByUsername($cookies.get("usuario"), function (data) {
            self.imc = parseFloat(data.data.toFixed(2));
        }); //No estoy seguro si esta es la mejor manera


        UsuarioService.getDateByUsername($cookies.get("usuario"), function (data) {
            self.date = data.data;
        });

    };

    this.getUsuario();
});

queComemosApp.controller('Login', function(AutenticacionService, $cookies, $window) {
    var self = this;
    this.errorMessage = "";
    $cookies.remove("usuario");
    this.autenticar = function(logueoForm) {
        AutenticacionService.autenticar(self.usuario, self.password, function(response) {
            if (response.data == 1) {
                //ir a pagina principal con nombre de usuario, setearlo en una cookie seguramente
                $cookies.put("usuario", self.usuario);
                $window.location = ('principal.html');
            } else {
                self.errorMessage = "El usuario y la password no coinciden";
            };
        });
    };
});

queComemosApp.controller('Registro', function(AutenticacionService) {
    var self = this;
    this.errorMessage = "";
    this.sexo = 0;
    this.rutina = 0;
    this.condiciones = [];
    this.agregarCondicion = function(){
        self.condicionesPosibles.forEach(function(condicion){
            self.condiciones.push(condicion);
        });
    };

    this.eliminarCondiciones = function(){
        self.condiciones = [];
    };

    this.notificarError = function (mensaje) {
        self.result = "alert-danger";
        self.errorMessage = self.errores;
    };

    this.registrar = function(){
        self.errores = [];
        if (self.nombre == null || self.nombre.length <= 4)
            self.errores.push("El usuario debe tener mas de 4 caracteres");

        if (self.password == null)
            self.errores.push("Debe ingresar una password");

        if(self.altura == null || self.altura <= 0)
            self.errores.push("Debe ingresar una altura valida");

        if(self.peso == null || self.peso <= 0)
            self.errores.push("Debe ingresar un peso valido");

        AutenticacionService.consultarFecha(self.fecha, function(data){
            if(data.data == 0)
                self.errores.push("Debe ingresar una fecha valida");
        });

        if (self.errores.length == 0) {
            AutenticacionService.registrar(self, function (response) {
                self.result = 'alert-success';
                self.answer = "Se ha registrado satisfactoriamente";
            }, self.notificarError);
        }
    }
});


queComemosApp.controller('VerMonitoreo', function ($scope) {
    this.textoBusqueda = '';
    this.filtroActual = '';
    this.buscar = function() {
        this.filtroActual = this.textoBusqueda
    };
    $scope.IsVisible = false;
    $scope.ShowHide = function () {
        $scope.IsVisible = $scope.IsVisible ? false : true;
    }
});

queComemosApp.controller('CondicionesPreexistentesCtrl', function(CondicionesService){
    var self = this;
    this.findall = function(){
        CondicionesService.findall(function(data){
            self.condiciones = _.map(data.data, CondicionPreexistente.asCondicion);
        });
    };
    this.findall();
});

queComemosApp.controller('verRecetas', function ($scope) {
    this.textoBusqueda = '';
    this.filtroActual = '';
    this.buscar = function() {
        this.filtroActual = this.textoBusqueda
    };
    $scope.IsVisible = false;
    $scope.ShowHide = function () {
        $scope.IsVisible = $scope.IsVisible ? false : true;
    }
});

queComemosApp.controller('IngredienteControllerNuevaReceta', function($cookies, $state, $stateParams){
    var self = this;
    this.receta = $stateParams.receta;

    this.agregarIngrediente = function() {
        if (self.nombre == null)
            alert("Debe seleccionar un ingrediente de la lista");
        else{
            self.recetaObject = $cookies.getObject('nuevaReceta');
            var ingrediente = new Ingrediente();
            ingrediente.nombre = self.nombre;
            ingrediente.cantidad = self.cantidad;
            self.recetaObject.ingredientes.push(ingrediente);
            $cookies.putObject('nuevaReceta', self.recetaObject);
            $state.go('crearReceta');
        }

        this.volver = function(){
            $state.go('crearReceta');
        };
    };
});

queComemosApp.controller('NuevaReceta', function($cookies, $state) {
    var self = this;
    this.crearReceta = function() {
    	if (self.calorias >= 10 && self.calorias <= 5000) {
    		self.receta = new Receta();
            var nombreUsuario = $cookies.get("usuario");
            self.receta.nombreUsuario = nombreUsuario;
            self.receta.calorias = self.calorias;
            self.receta.nombrePlato = self.nombre;
            self.receta.dificultadPreparacion = "facil";
            self.receta.temporadaCorrespondiente = "invierno";
            self.receta.procesoPreparacion = "proceso de preparacion";
            self.receta.privada = true;
            self.receta.ingredientes = [];
            $cookies.putObject('nuevaReceta', self.receta);
            $state.go('crearReceta');
    	} else {
    		alert("Las calorias deben estar entre 10 y 5000");
    	}
        
    };

    $cookies.remove('nuevaReceta');
});

queComemosApp.controller('IngredientesCtrl', function(IngredientesService){
    var self = this;
    this.findall = function(){
        IngredientesService.findall(function(data){
            self.ingredientes = _.map(data.data, Ingrediente.asIngrediente);
        });
    };
    this.findall();
});


