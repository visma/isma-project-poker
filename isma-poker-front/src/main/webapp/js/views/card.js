CardView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = card_template;
        this.template_empty = card_empty_template;
        this.model = attrs.model;
        this.index = attrs.index;

        this.render();
        this.model.on('change', this.render, this);
    },

    _loadTemplate: function () {
        if (this.model.loaded()) {
            while (this.template.indexOf('%index%') != -1) {
                this.template = this.template.replace('%index%', this.index);
            }
            this.$el.html(this.template);
            this.value = $('#card_' + this.index + '-value');
        } else {
            this.$el.html(this.template_empty);
        }

    },

    render: function () {
        console.debug("card view rendered " + this.index);
        this._loadTemplate();
        if (this.model.loaded()) {
            var cardImageFile = 'images/ecarddeck/' + this.model.get('value') + ".jpg";
            $(this.value.selector).attr('src', cardImageFile.toLowerCase());
        }
    }
});