PlayerCollection = Backbone.Collection.extend({
    model: PlayerInfo,

    updateDTOs: function () {
        var players = game.getPlayers(models['login'].get('authCode'));
        console.debug("PlayerCollection.updateDTOs() : " + players);
        for (var i = 0; i < players.length; i++) {
            var player = players[i];
            console.debug("\t- update player : " + i + " | name : " + player.name);

            this.at(i).set('name', player.name);
            this.at(i).set('chips', player.chips);
            this.at(i).set('bet', player.currentBet);
            //this.at(i).set('hand', player.holeCard1 + ", " + player.holeCard2);
            this.at(i).set('card1', player.holeCard1);
            this.at(i).set('card2', player.holeCard2);
            this.at(i).set('status', player.status);
        }
        for (var i = players.length; i < this.length; i++) {
            console.debug("\t- clean player at index : " + i);
            this.at(i).reset();
        }
    }
});