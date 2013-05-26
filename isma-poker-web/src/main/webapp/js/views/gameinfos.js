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
        var defaultvalue = '---';
        this.step.html(defaultIfNotDefined(this.model.get('step'), defaultvalue));
        if (isset(this.model.get('smallBlind'))) {
            this.blinds.html(this.model.get('smallBlind') + "/" + this.model.get('bigBlind') + "$");
        }else{
            this.blinds.html(defaultvalue);
        }
        this.pot.html(defaultIfNotDefined(this.model.get('pot'), defaultvalue));
        this.currentBet.html(defaultIfNotDefined(this.model.get('currentBet'), defaultvalue));
        this.currentPlayer.html(defaultIfNotDefined(this.model.get('currentPlayer'), defaultvalue));
    }
});