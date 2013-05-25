GameInfosModel = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        smallBlind: "0",
        bigBlind: "0",
        step: "",
        currentBet: "0",
        pot: "0",
        currentPlayer: ""
    },

    refresh: function () {
        var infos = game.infos();
        this.set('smallBlind', infos.smallBlindAmount);
        this.set('bigBlind', infos.bigBlindAmount);
        this.set('step', infos.step);
        if (infos.currentPlayer != null) {
            this.set('currentPlayer', infos.currentPlayer.name);
        } else {
            this.set('currentPlayer', 'no');
        }
        if (infos.pot != null) {
            this.set('pot', infos.pot);
        } else {
            this.set('pot', '0');
        }
        if (infos.currentBet != null) {
            this.set('currentBet', infos.currentBet);
        } else {
            this.set('currentBet', '0');
        }
    }

});