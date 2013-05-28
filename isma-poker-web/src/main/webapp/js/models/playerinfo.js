PlayerInfo = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        name: "---",
        status: "---",
        chips: "---",
        bet: "---",
        hand: "---"
    },

    reset: function () {
        this.set('name', '---');
        this.set('status', '---');
        this.set('chips', '---');
        this.set('bet', '---');
        this.set('hand', '---');
    },

    updateDTO: function () {
        var nickname = models['login'].get('nickname');
        var playerDTO = game.getPlayer(nickname);

        this.set('name', nickname);
        this.set('status', playerDTO.status);
        this.set('chips', playerDTO.chips);
        this.set('bet', playerDTO.currentBet);
        if (playerDTO.holeCard1 != null) {
            this.set('hand', playerDTO.holeCard1 + " - " + playerDTO.holeCard2);
        } else {
            this.set('hand', '---');
        }
    },

    loaded: function () {
        return this.get('name') != "---";
    }
});