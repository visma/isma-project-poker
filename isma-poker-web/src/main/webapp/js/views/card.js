CardView = Backbone.View.extend({
    initialize:function () {
        console.debug("initialize card");
        this.template = _.template(tpl.get('card-template'));
        console.debug("initialize card ended");
    },
    render:function () {
        this.$el.html( this.template() );
        return this.el;
    }
});
var cardLoaded = true;