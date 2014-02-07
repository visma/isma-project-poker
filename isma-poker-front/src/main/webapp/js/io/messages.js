var models = [];

function onmessagereceive(message) {
    console.info("<-- incoming : " + message);
    var obj = jQuery.parseJSON(message);
    var id = obj.id;
    console.info("type de message :" + id);
    if (id == "playerTurn" || id == "newStep") {
        var authCode = models['login'].get('authCode');
        var tableDTO = game.getTable(authCode);

        models['playerActions'].updateActions();
        models['player'].updateDTO();
        models['gameInfos'].updateDTO(tableDTO);
        models['cardCollection'].updateDTOs(tableDTO);
        models['playerCollection'].updateDTOs();
    } else if (id == "results") {
        _onResults(obj);
    }
}

function _onResults(obj) {
    var results = obj.object.results;
    var losers = results.losers;
    var winners = results.winners;
    var index;
    for (index = 0; index < losers.length; ++index) {
        var loser = losers[index];
        alert("loser : " + loser.player.nickname + " avec " + loser.handEvaluation);
    }
    for (index = 0; index < winners.length; ++index) {
        var winner = winners[index];
        alert("winner : " + winner.player.nickname + " avec " + winner.handEvaluation);
    }
}
