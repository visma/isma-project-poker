function isset(variable) {
    return typeof(variable) != undefined && variable != null && variable != "";
}

function defaultIfNotDefined(variable, defaultvalue) {
    return isset(variable) ? variable : defaultvalue;
}