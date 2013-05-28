PlayerCollectionView = Backbone.View.extend({
    initialize: function (attrs) {
        this.collection = attrs.collection;
        var that = this;
        this._playerViews = [];

        for (var i = 0; i < this.collection.length; i++) {
            var player = this.collection.at(i);
            var playerView = new PlayerView({el: $('#player_' + i), model: player, index: i});
            this._playerViews.push(playerView);
        }
        this.render();
    },

//    add: function (player) {
//        var length = this._playerViews.length;
//        var view = new PlayerView({model: player, index: length});
//        this._playerViews.push(view);
//        view.render();
//    },


    render: function () {
        var that = this;
        $(this.el).empty();
        _(this._playerViews).each(function (view) {
            view.render();
        });
    }
});