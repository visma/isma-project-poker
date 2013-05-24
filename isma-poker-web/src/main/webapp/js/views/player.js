PlayerView = Backbone.View.extend({
    initialize:function () {
        console.debug("initialize player");
        var templ = tpl.get('player-template');
        console.debug("template = " + templ);
        this.template = _.template(templ);
        console.debug("initialize player ended");
    },
    render:function () {
        console.debug("render player");
        var html = this.template();
        console.debug("rendering : " + html);
        this.$el.html(html);
        console.debug("render player end");
        return this.el;
    }
});
var playerLoaded = true;