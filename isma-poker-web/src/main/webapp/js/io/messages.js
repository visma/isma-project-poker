var models = [];

function onmessagereceive(message) {
    console.info("<-- incoming : " + message);
    var obj = jQuery.parseJSON(message);
    var id = obj.id;
    console.info(id);
    if (id == "playerTurn") {
        models['playerActions'].refresh();
        models['gameInfos'].refresh();
    }
}