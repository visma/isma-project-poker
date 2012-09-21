function ActionClass(actionType, attributes) {
    this._actionType = actionType;
    this._attributes = attributes;
    this.get = function (key) {
        return this._attributes[key];
    };
    this.getType = function () {
        return this._actionType;
    };
}
function buildActionClass(message) {
    var result = message.split("{");
    var actionType = result[0];
    var attributesStr = result[1].substring(0, result[1].length - 1);
    var resultAttributes = attributesStr.split(',');
    var attributes = {};
    for (var i = 0; i < resultAttributes.length; i++) {
        var key = resultAttributes[i].split('=')[0];
        attributes[key] = resultAttributes[i].split('=')[1];
    }
    var action = new ActionClass(actionType, attributes);
    console.log("action instanciée : " + action.getType());
    return action;
}

function onmessagereceive(message) {
    console.log("------ onmessagereceive().start : " + message);
    var actionClass = buildActionClass(message);

    if (actionClass.getType() == 'Join') {
        onmessage_join(actionClass);
    } else if (actionClass.getType() == 'Error') {
        onmessage_error(actionClass);
    }
    console.log("------ onmessagereceive().end");
}

function onmessage_join(actionClass) {
    console.log("onmessage_join() not implemented");
}
function onmessage_error(actionClass) {
    console.log("onmessage_error()");
    if (actionClass.get("type") == "loginError") {
        console.log("onmessage_error() : type=loginError");
        loginModel.enableLogin(actionClass);
    }
}
