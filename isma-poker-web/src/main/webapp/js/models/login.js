LoginModel = Backbone.Model.extend({
    initialize:function () {
        console.debug("initialize LoginModel");
    },
    connect:function () {
        console.debug("LoginModel.connect()");
        game.connect();
    },
    login:function () {
        var nickname = $('#nicknameText').val();
        game.sitIn(nickname);
        game.buyChips(nickname, 100);
        $('#nicknameText').attr("disabled", true);
        $('#loginButton').attr("disabled", true);
    },
    enableLogin:function (actionClass) {
        alert("erreur lors de l'authentification : " + actionClass.get("message"));
        $('#nicknameText').attr("disabled", false);
        $('#loginButton').attr("disabled", false);
    }
});