if (!window.WebSocket) {
    alert("WebSocket not supported by this browser");
}

var game = {
    connect: function (user) {
        var location = "ws://localhost:8081/poker?user=" + user;
        this._ws = new WebSocket(location);
        this._ws.onopen = this._onopen;
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
        this._ws.onerror = this._onerror;
    },

    getGameStatus: function () {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: "http://localhost:8080/poker/room/1/status",
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de getGameStatus " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de getGameStatus " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },
    getPlayerStatus: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: "http://localhost:8080/poker/room/1/player/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de getPlayerStatus " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de getPlayerStatus " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    getActions: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: "http://localhost:8080/poker/room/1/actions/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de getActions " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de getActions " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    sitIn: function (nickname) {
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/sitin/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de sitIn " + JSON.stringify(response));
            },
            failure: function (response) {
                console.error("echec de sitIn " + JSON.stringify(response));
            }
        });
    },

    buyChips: function (nickname, chips) {
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/buychips/" + nickname + "/" + chips,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de buyChips " + JSON.stringify(response));
            },
            failure: function (response) {
                console.error("echec de buyChips " + JSON.stringify(response));
            }
        });
    },

    paySmallBlind: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/paysmallblind/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de paySmallBlind " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de paySmallBlind " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    payBigBlind: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/paybigblind/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de payBigBlind " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de payBigBlind " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    check: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/check/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de check " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de check " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },
    call: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/call/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de call " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de call " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    bet: function (nickname, chips) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/bet/" + nickname + "/" + chips,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de bet " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de bet " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    raise: function (nickname, chips) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/raise/" + nickname + "/" + chips,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de raise " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de raise " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },

    allin: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/allin/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de allin " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de allin " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },
    fold: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/fold/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de fold " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de fold " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
    },
    show: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: "http://localhost:8080/poker/room/1/show/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de show " + JSON.stringify(response));
                data = response;
            },
            failure: function (response) {
                console.error("echec de show " + JSON.stringify(response));
                data = response;
            }
        });
        return data;
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