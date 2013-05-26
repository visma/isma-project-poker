Player = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        name:"---",
        status:"---",
        chips:"---",
        bet:"---",
        hand:"---"
    },
    refresh: function () {
        var nick = models['login'].get('nickname');

        var playerDTO = game.getPlayerStatus(nick);

        this.set('name', nick);
        this.set('status', playerDTO.fold ? 'folded' : 'playing');
        this.set('chips', playerDTO.chips);
        this.set('bet', playerDTO.currentBet);
        if (playerDTO.holeCard1 != null) {
            this.set('hand', playerDTO.holeCard1 + " - " + playerDTO.holeCard2);
        } else {
            this.set('---');
        }
    }
});