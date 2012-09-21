GameInfosView = Backbone.View.extend({
    initialize:function () {
        console.log("initialize gameinfos");
        this.template = _.template(tpl.get('gameinfos-template'));
        console.log("initialize gameinfos ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var gameInfosLoaded = true;