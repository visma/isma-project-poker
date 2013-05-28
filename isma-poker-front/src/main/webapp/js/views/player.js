PlayerView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = player_template;
        this.template_empty = player_empty_template;
        this.model = attrs.model;
        this.index = attrs.index;

        this.render();
        this.model.on('change', this.render, this);
    },

    _loadTemplate: function () {
        if (this.model.loaded()) {
            while (this.template.indexOf('%index%') != -1) {
                this.template = this.template.replace('%index%', this.index);
            }
            this.$el.html(this.template);
            this.name = $('#player_' + this.index + '-nickname');
            this.status = $('#player_' + this.index + '-status');
            this.chips = $('#player_' + this.index + '-chips');
            this.bet = $('#player_' + this.index + '-bet');
            this.hand = $('#player_' + this.index + '-hand');
        } else {
            this.$el.html(this.template_empty);
        }

    },

    render: function () {
        console.debug("player view rendered : " + this.index);
        this._loadTemplate();
        if (this.model.loaded()) {
            this.name.html(this.model.get('name'));
            this.status.html(this.model.get('status'));
            this.chips.html(this.model.get('chips'));
            this.bet.html(this.model.get('bet'));
            this.hand.html(this.model.get('hand'));
        }
    }
});