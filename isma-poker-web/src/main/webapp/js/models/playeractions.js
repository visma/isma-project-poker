PlayerActions = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        actions: []
    },

    updateActions: function () {
        var nick = models['login'].get('nickname');
        this.set('actions', game.getActions(nick));
        console.debug("PlayerActions.updateActions() : " + this.get('actions'));
    }

});