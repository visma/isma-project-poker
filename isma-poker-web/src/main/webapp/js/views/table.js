TableView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = table_template;
        this.$el.html(this.template);
        this.render();
    },
    render: function () {
    }
});