PlayerActions = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        actions: []
    },

    refresh: function () {
        var nick = models['login'].get('nickname');
        this.set('actions', game.getActions(nick));
        console.info(playerActions);
    }

});