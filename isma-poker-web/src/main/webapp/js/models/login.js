LoginModel = Backbone.Model.extend({
    initialize:function () {
        console.log("initialize LoginModel");
    },
    connect:function () {
        console.log("LoginModel.connect()");
        game.connect();
    },
    login:function () {
        console.log("LoginModel.login()");
        var nickname = $('#nicknameText').val();
        console.log("nickname = " + nickname);
        game.join(nickname);
        $('#nicknameText').attr("disabled", true);
        $('#loginButton').attr("disabled", true);
    },
    enableLogin:function (actionClass) {
        alert("erreur lors de l'authentification : " + actionClass.get("message"));
        $('#nicknameText').attr("disabled", false);
        $('#loginButton').attr("disabled", false);
    }
});