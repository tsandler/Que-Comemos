queComemosApp.config(function ($stateProvider, $compileProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise("/");
    $compileProvider.debugInfoEnabled(false);
    $stateProvider
        .state('verRecetas',{
            url:"/recetas/",
            templateUrl:"/partials/verRecetas.html",
            controller: "RecetasCtrl as Recetas"
        })

        .state('verRecetasFavoritas', {
            url:"/recetas/favoritas",
            templateUrl: "/partials/verRecetas.html",
            controller: "RecetasFavoritas as Recetas"
        })

        .state('monitoreoDeRecetas',{
            url: "/receta/monitoreo/:nombre",
            templateUrl: "/partials/verRecetas.html",
            controller: "ConsultasDeRecetas as Recetas"
        })

        .state('busquedaRecetas', {
            url:"/busquedaRecetas/",
            templateUrl:"/partials/verRecetas.html",
            controller: "BusquedaRecetas as Recetas"
        })

        .state('perfilDeUsuario',{
            url: "/usuarios/:nombre",
            templateUrl: "/partials/perfilDeUsuario.html",
            controller: "VerPerfilUsuario as UsuarioCtrl"
        })

        .state('nuevoIngrediente',{
            url:"/ingredientes/:receta",
            templateUrl:"/partials/nuevoIngrediente.html",
            controller: "IngredienteController as IngredienteCtrl"
        })

        .state('login', {
            url: "/index",
            templateUrl: "/partials/login.html",
            controller: "Login as login"
        })

        .state('registro', {
            url: "/registro",
            templateUrl: "/partials/registro.html",
            controller: "Registro as registro"

        })

        .state('detalleDeReceta',{
            url:"/recetas/:nombre",
            templateUrl:"/partials/detalleReceta.html",
            controller: "RecetaController as receta"
        })

        .state('crearReceta', {
            url:"/nuevaReceta",
            templateUrl:"/partials/detalleReceta.html",
            controller:"CrearReceta as receta"
        })

        .state('nuevoIngredienteRecetaNueva',{
            url:"/nuevaReceta/ingredientes/:receta",
            templateUrl:"/partials/nuevoIngrediente.html",
            controller: "IngredienteControllerNuevaReceta as IngredienteCtrl"
        })

        .state('nuevaReceta',  {
            url:"/nuevaReceta0",
            templateUrl:"/partials/nuevaRecetaForm.html",
            controller: "NuevaReceta as builder"
        })
});
