CardCollectionView = Backbone.View.extend({
    initialize: function (attrs) {
        this.collection = attrs.collection;
        var that = this;
        this._cardViews = [];

        for (var i = 0; i < this.collection.length; i++) {
            var card = this.collection.at(i);
            var cardView = new CardView({el: $('#card_' + i), model: card, index: i});
            this._cardViews.push(cardView);
        }
        this.render();
    },

    render: function () {
        var that = this;
        $(this.el).empty();
        _(this._cardViews).each(function (view) {
            view.render();
        });
    }
});