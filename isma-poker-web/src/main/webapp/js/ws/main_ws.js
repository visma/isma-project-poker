if (!window.WebSocket) {
    alert("WebSocket not supported by this browser");
}
/*function $() {
 return document.getElementById(arguments[0]);
 }
 function $F() {
 return document.getElementById(arguments[0]).value;
 }
 */
function getKeyCode(ev) {
    if (window.event)
        return window.event.keyCode;
    return ev.keyCode;
}

var game = {
    connect:function () {
        var location = "ws://localhost:8081/"
        this._ws = new WebSocket(location);
        this._ws.onopen = this._onopen;
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
        this._ws.onerror = this._onerror;
    },
    join:function (nickname) {
        this._nickname = nickname;
        game._send(game._nickname, 'Join{nickname=' + nickname + '}');
    },

    chat:function (text) {
        if (text != null && text.length > 0)
            game._send(game._nickname, text);
    },

    _onopen:function () {
        //Nothing to do...
    },

    _onmessage:function (m) {
        if (m.data) {
            onmessagereceive(m.data);
        }
    },

    _onclose:function (m) {
        this._ws = null;
        console.log("_onclose");
    },

    _onerror:function (e) {
        alert(e);
        console.log('WebSocket Error ' + e);
    },

    _send:function (user, message) {
        user = user.replace(':', '_');
        if (this._ws)
            this._ws.send(message);
    }
};