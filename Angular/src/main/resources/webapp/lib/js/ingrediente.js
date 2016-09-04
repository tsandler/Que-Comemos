function Ingrediente(){

}

Ingrediente.asIngrediente = function(jsoningrediente){
    return angular.extend(new Ingrediente(), jsoningrediente);
};