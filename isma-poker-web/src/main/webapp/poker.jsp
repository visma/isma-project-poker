<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="form" uri="/struts-tags" %>
<%@ taglib prefix="html" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Poker client</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" media="screen" href="css/poker.css" type="text/css"/>

    <script type="text/javascript" src="js/lib/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="js/lib/underscore.js"></script>
    <script type="text/javascript" src="js/lib/backbone.js"></script>
    <script type="text/javascript" src="js/lib/require.js"></script>
    <script type="text/javascript" src="js/util.js"></script>

    <script type="text/javascript" src="js/views/player.js"></script>
    <script type="text/javascript" src="js/views/card.js"></script>
    <script type="text/javascript" src="js/views/login.js"></script>
    <script type="text/javascript" src="js/views/gameinfos.js"></script>
    <script type="text/javascript" src="js/views/playerinfos.js"></script>
    <script type="text/javascript" src="js/views/playeractions.js"></script>

    <script type="text/javascript" src="js/models/player.js"></script>
    <script type="text/javascript" src="js/models/login.js"></script>

    <script type="text/javascript" src="js/main.js"></script>

    <script type="text/javascript" src="js/ws/incoming.js"></script>
    <script type="text/javascript" src="js/ws/main_ws.js"></script>


    <script type="text/javascript">
        loadAllTemplates();
        console.log("templates loaded");
        var loginView;
        var playerActionsView;
        var playerInfosView;
        var gameInfosView;
        var loginModel;

        // MODELS
        function createModels() {
            loginModel = new LoginModel();
        }
        // VIEWS
        function createPanelViews() {
            playerActionsView = new PlayerActionsView({ el:$('#playerActionsView') });
            playerInfosView = new PlayerInfosView({ el:$('#playerInfosView') });
            gameInfosView = new GameInfosView({ el:$('#gameInfosView') });
            loginView = new LoginView({ el:$('#loginView') });
        }
        function renderPanelViews() {
            playerActionsView.render();
            playerInfosView.render();
            gameInfosView.render();
            loginView.render();
        }
        // BINDINGS
        function addListeners(){
            $('#loginButton').click(function(){
                loginModel.login();
            });
        }
        function main() {
            createModels();
            createPanelViews();
            renderPanelViews();
            addListeners();

            loginModel.connect();
        }
        //$(document).ready(function () {});
    </script>
</head>
<body>
<input type="button" onclick="main()" value="initialisation"/>
<table border="0" width="100%">
    <tr>
        <td>
            <div id="gameInfosView"></div>
        </td>
        <td align="center">
            <H1>POKER</h1>
        </td>
        <td align="right">
            <div id="loginView"></div>
        </td>
    </tr>
</table>
<table>
    <tr>
        <td>
            <table class="pokertable">
                <tr>
                    <td>
                        <div id="player_0"></div>
                    </td>
                    <td>
                        <div id="player_1"></div>
                    </td>
                    <td>
                        <div id="player_2"></div>
                    </td>
                    <td>
                        <div id="player_3"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="player_9"></div>
                    </td>
                    <td colspan="2">
                        <table class="pokerboard" align="center">
                            <tr>
                                <td>
                                    <div id="card_0"></div>
                                </td>
                                <td>
                                    <div id="card_1"></div>
                                </td>
                                <td>
                                    <div id="card_2"></div>
                                </td>
                                <td>
                                    <div id="card_3"></div>
                                </td>
                                <td>
                                    <div id="card_4"></div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">Pot : amount</td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <div id="player_4"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="player_8"></div>
                    </td>
                    <td>
                        <div id="player_7"></div>
                    </td>
                    <td>
                        <div id="player_6"></div>
                    </td>
                    <td>
                        <div id="player_5"></div>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <div>Chat todo</div>
        </td>
    </tr>
</table>
<div id="playerInfosView"></div>
<div id="playerActionsView"></div>
</body>
</html>