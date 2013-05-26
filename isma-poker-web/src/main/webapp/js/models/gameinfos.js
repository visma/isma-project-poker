GameInfosModel = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        smallBlind: null,
        bigBlind: null,
        step: null,
        currentBet: null,
        pot: null,
        currentPlayer: null
    },

    refresh: function () {
        var infos = game.getGameStatus();

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