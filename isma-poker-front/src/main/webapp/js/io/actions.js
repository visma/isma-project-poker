if (!window.WebSocket) {
    alert("WebSocket not supported by this browser");
}

var game = {
    /***************************************************************
     *  AJAX WEBSERVICES - GAME INFORMATIONS
     ***************************************************************/

    _ajax_error: function (url, response) {
        console.error("error " + response.responseText);
        var error = jQuery.parseJSON(response.responseText);
        alert("Error [" + error.object.type + "]\nurl=" + url + "\nmessage=" + error.object.message);
    },

    getPlayers: function () {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: "http://localhost:8080/poker//room/1/players",
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de getPlayers " + JSON.stringify(response));
                data = response;
            },
            error: function (response) {
                game._ajax_error(this.url, response);
            }
        });
        return data;
    },
    getTable: function () {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: "http://localhost:8080/poker/room/1",
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de getTable " + JSON.stringify(response));
                data = response;
            },
            error: function (response) {
                game._ajax_error(this.url, response);
            }

        });
        return data;
    },
    getPlayer: function (nickname) {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: "http://localhost:8080/poker/room/1/player/" + nickname,
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de getPlayer " + JSON.stringify(response));
                data = response;
            },
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
            }

        });
        return data;
    },

    /***************************************************************
     *  AJAX WEBSERVICES - PLAYER ACTIONS
     ***************************************************************/
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
            error: function (response) {
                game._ajax_error(this.url, response);
            }

        });
    },

    buyChips: function (nickname, chips) {
        console.info("buyChips start");
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
            error: function (response) {
                game._ajax_error(this.url, response);
            }
        });
        console.info("buyChips end");
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
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
            error: function (response) {
                game._ajax_error(this.url, response);
            }

        });
        return data;
    },
    /***************************************************************
     *  WEBSOCKETS PART
     ***************************************************************/
    connect: function (user) {
        var location = "ws://localhost:8081/poker?user=" + user;
        this._ws = new WebSocket(location);
        this._ws.onopen = this._onopen;
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
        this._ws.onerror = this._onerror;
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
        console.warn("WebSocket close : " + m);
    },

    _onerror: function (e) {
        alert(e);
        console.error("WebSocket error : " + e);
    },

    _send: function (user, message) {
        user = user.replace(':', '_');
        if (this._ws)
            this._ws.send(message);
    }
};