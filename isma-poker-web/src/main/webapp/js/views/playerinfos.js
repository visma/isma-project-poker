PlayerInfosView = Backbone.View.extend({
    initialize:function () {
        console.log("initialize playerinfos");
        this.template = _.template(tpl.get('playerinfos-template'));
        console.log("initialize playerinfos ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var playerInfosLoaded = true;