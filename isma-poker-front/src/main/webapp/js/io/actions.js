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
    getPlayers: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: buildURL("room/1/players/" + authCode),
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
    getTable: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: buildURL("room/1/" + authCode),
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
    getPlayer: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "GET",
            url: buildURL("room/1/player/" + authCode),
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
            url: buildURL("room/1/actions/" + nickname),
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
    login: function (nickname) {
        var authCode;
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("login/" + nickname),
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.debug("succes de login " + JSON.stringify(response));
                authCode = response;
            },
            error: function (response) {
                game._ajax_error(this.url, response);
            }

        });
        return authCode;
    },
    sitIn: function (authCode) {
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/sitin/" + authCode),
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
    buyChips: function (authCode, chips) {
        console.info("buyChips start");
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/buychips/" + authCode + "/" + chips),
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
    paySmallBlind: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/paysmallblind/" + authCode),
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
    payBigBlind: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/paybigblind/" + authCode),
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
    check: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/check/" + authCode),
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
    call: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/call/" + authCode),
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
    bet: function (authCode, chips) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/bet/" + authCode + "/" + chips),
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
    raise: function (authCode, chips) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/raise/" + authCode + "/" + chips),
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
    allin: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/allin/" + authCode),
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
    fold: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/fold/" + authCode),
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
    show: function (authCode) {
        var data = "no";
        $.ajax({
            async: false,
            type: "POST",
            url: buildURL("room/1/show/" + authCode),
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
    connect: function (authCode) {
        var location = "ws://localhost:8081/poker?authCode=" + authCode;
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