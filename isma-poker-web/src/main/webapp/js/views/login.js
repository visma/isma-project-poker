LoginView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = login_template;
        this.render();
        this.loginButton = $("#loginButton");
        this.nickname = $("#login");
        this.model = attrs.model;
    },
    render: function () {
        this.$el.html(this.template);
    },
    events: {
        "click input[type=button]": "signIn"
    },
    signIn: function(){
        console.info("signIn");
        this.model.set('nickname', this.nickname.val());
        game.connect(this.nickname.val());
        game.sitIn(this.nickname.val());
        game.buyChips(this.nickname.val(), 100);
        this.nickname.attr("disabled", true);
        this.loginButton.attr("disabled", true);

        models['gameInfos'].refresh();
    }
});