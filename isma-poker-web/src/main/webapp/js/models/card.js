Card = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        value: "---"
    },

    reset: function () {
        this.set('value', '---');
    },

    loaded: function () {
        return this.get('value') != "---";
    }
});