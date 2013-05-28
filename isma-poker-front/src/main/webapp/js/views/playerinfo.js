PlayerInfoView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = playerinfo_template;
        this.model = attrs.model;
        this.$el.html(this.template);

        this.name = $('#player-nickname');
        this.status = $('#player-status');
        this.chips = $('#player-chips');
        this.bet = $('#player-bet');
        this.hand = $('#player-hand');


        this.render();
        this.model.on('change', this.render, this);
    },
    render: function () {
        this.name.html(this.model.get('name'));
        this.status.html(this.model.get('status'));
        this.chips.html(this.model.get('chips'));
        this.bet.html(this.model.get('bet'));
        this.hand.html(this.model.get('hand'));
    }
});