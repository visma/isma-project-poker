if (!window.WebSocket) {
    alert("WebSocket not supported by this browser");
}

var game = {
    connect: function () {
        var location = "ws://localhost:8081/"
        this._ws = new WebSocket(location);
        this._ws.onopen = this._onopen;
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
        this._ws.onerror = this._onerror;
    },
    sitIn: function (nickname) {
        $.ajax({
            async:false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/sitin/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.info("succes de sitIn " + JSON.stringify(response));
            },
            failure: function (response) {
                console.error("echec de sitIn " + JSON.stringify(response));
            }
        });
    },

    buyChips: function (nickname, chips) {
        $.ajax({
            async:false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/buychips/" + nickname + "/" + chips,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.info("succes de buyChips " + JSON.stringify(response));
            },
            failure: function (response) {
                console.error("echec de buyChips " + JSON.stringify(response));
            }
        });
    },

    _onopen: function () {
        //Nothing to do...
    },

    _onmessage: function (m) {
        if (m.data) {
            onmessagereceive(m.data);
        }
    },

    _onclose: function (m) {
        this._ws = null;
        console.info("_onclose");
    },

    _onerror: function (e) {
        alert(e);
        console.info('WebSocket Error ' + e);
    },

    _send: function (user, message) {
        user = user.replace(':', '_');
        if (this._ws)
            this._ws.send(message);
    }
};