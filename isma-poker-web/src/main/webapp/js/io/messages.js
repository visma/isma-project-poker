var models = [];

function onmessagereceive(message) {
    console.info("<-- incoming : " + message);
    var obj = jQuery.parseJSON(message);
    var id = obj.id;
    console.info("type de message :" + id);
    if (id == "playerTurn" || id == "newStep") {
        models['playerActions'].refresh();
        models['gameInfos'].refresh();
        models['player'].refresh();
    }else if (id == "results"){
        var results = obj.object.results;
        var losers = results.losers;
        var winners = results.winners;
        var index;
        for (index = 0; index < losers.length; ++index) {
            var loser = losers[index];
            console.error("loser : " + loser.player.nickname + " avec " + loser.handEvaluation);
        }
        for (index = 0; index < winners.length; ++index) {
            var winner = winners[index];
            console.error("winner : " + winner.player.nickname + " avec " + winner.handEvaluation);
        }
    }
}