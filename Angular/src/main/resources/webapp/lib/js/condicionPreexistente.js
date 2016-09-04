function CondicionPreexistente(){

}

CondicionPreexistente.asCondicion = function(jsoncondicion){
    return angular.extend(new CondicionPreexistente(), jsoncondicion);
};


