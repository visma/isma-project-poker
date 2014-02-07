LoginModel = Backbone.Model.extend({
    initialize: function () {
    },
    defaults: {
        nickname: "",
        authCode: ""
    },

    signIn: function(nickname){
        console.warn("loginModel = " + nickname);

        var authCode = game.login(nickname);

        this.set('nickname', nickname);
        this.set('authCode', authCode);

        game.connect(authCode);
        game.sitIn(authCode);
        game.buyChips(authCode, 100);
        models['gameInfos'].updateDTO(game.getTable());
    }
});