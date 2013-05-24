GameInfosView = Backbone.View.extend({
    initialize:function () {
        console.debug("initialize gameinfos");
        this.template = _.template(tpl.get('gameinfos-template'));
        console.debug("initialize gameinfos ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var gameInfosLoaded = true;