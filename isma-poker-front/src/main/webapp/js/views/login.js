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
    signIn: function(nickname){
        console.warn("loginView = " + this.nickname.val());
        models['login'].signIn(this.nickname.val());
        this.nickname.attr("disabled", true);
        this.loginButton.attr("disabled", true);
    }
});