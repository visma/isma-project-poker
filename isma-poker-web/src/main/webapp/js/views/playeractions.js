PlayerActionsView = Backbone.View.extend({
    initialize:function () {
        console.debug("initialize playeractions");
        this.template = _.template(tpl.get('playeractions-template'));
        console.debug("initialize playeractions ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var playerActionsLoaded = true;