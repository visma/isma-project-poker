LoginView = Backbone.View.extend({
    initialize:function () {
        console.log("initialize LoginView");
        this.template = _.template(tpl.get('login-template'));
        console.log("initialize LoginView ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var playerActionsLoaded = true;