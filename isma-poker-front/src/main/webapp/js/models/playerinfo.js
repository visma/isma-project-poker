PlayerInfo = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        name: "---",
        status: "---",
        chips: "---",
        bet: "---",
//        hand: "---",
        card1: "---",
        card2: "---"
    },

    reset: function () {
        this.set('name', '---');
        this.set('status', '---');
        this.set('chips', '---');
        this.set('bet', '---');
//        this.set('hand', '---');
        this.set('card1', '---');
        this.set('card2', '---');
    },

    updateDTO: function () {
        var nickname = models['login'].get('nickname');
        var authCode = models['login'].get('authCode');
        var playerDTO = game.getPlayer(authCode);

        this.set('name', nickname);
        this.set('status', playerDTO.status);
        this.set('chips', playerDTO.chips);
        this.set('bet', playerDTO.currentBet);
        if (playerDTO.holeCard1 != null) {
//            this.set('hand', playerDTO.holeCard1 + " - " + playerDTO.holeCard2);
            console.warn("card1 : " + playerDTO.holeCard1);
            this.set('card1', playerDTO.holeCard1);
            this.set('card2', playerDTO.holeCard2);
        } else {
//            this.set('hand', '---');
            this.set('card1', '---');
            this.set('card2', '---');
        }
    },

    loaded: function () {
        return this.get('name') != "---";
    }
});