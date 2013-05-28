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

    updateDTO: function (tableDTO) {
        this.set('smallBlind', tableDTO.smallBlindAmount);
        this.set('bigBlind', tableDTO.bigBlindAmount);
        this.set('step', tableDTO.step);
        if (tableDTO.currentPlayer != null) {
            this.set('currentPlayer', tableDTO.currentPlayer.name);
        } else {
            this.set('currentPlayer', 'no');
        }
        if (tableDTO.pot != null) {
            this.set('pot', tableDTO.pot);
        } else {
            this.set('pot', '0');
        }
        if (tableDTO.currentBet != null) {
            this.set('currentBet', tableDTO.currentBet);
        } else {
            this.set('currentBet', '0');
        }
    }

});