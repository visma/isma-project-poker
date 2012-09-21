PlayerView = Backbone.View.extend({
    initialize:function () {
        console.log("initialize player");
        var templ = tpl.get('player-template');
        console.log("template = " + templ);
        this.template = _.template(templ);
        console.log("initialize player ended");
    },
    render:function () {
        console.log("render player");
        var html = this.template();
        console.log("rendering : " + html);
        this.$el.html(html);
        console.log("render player end");
        return this.el;
    }
});
var playerLoaded = true;