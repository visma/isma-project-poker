LoginView = Backbone.View.extend({
    initialize:function () {
        console.debug("initialize LoginView");
        this.template = _.template(tpl.get('login-template'));
        console.debug("initialize LoginView ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var playerActionsLoaded = true;