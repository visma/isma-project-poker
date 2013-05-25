GameInfosView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = gameinfos_template;
        this.model = attrs.model;
        this.$el.html(this.template);

        this.step = $('#step');
        this.blinds = $('#blinds');
        this.pot = $('#pot');
        this.currentBet = $('#currentBet');
        this.currentPlayer = $('#currentPlayer');

        this.render();
        this.model.on('change', this.render, this);
    },
    render: function () {
        this.step.html(this.model.get('step'));
        this.blinds.html(this.model.get('smallBlind') + "/" + this.model.get('bigBlind') + "$");
        this.pot.html(this.model.get('pot'));
        this.currentBet.html(this.model.get('currentBet'));
        this.currentPlayer.html(this.model.get('currentPlayer'));
    }
});