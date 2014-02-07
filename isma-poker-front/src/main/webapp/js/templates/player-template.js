var player_template =
    '\
<div class="player-class">\
    <p>\
        <label id="player_%index%-nickname"/>\
        &lt;\
        <label id="player_%index%-status"/>\
        &gt; : \
         <label id="player_%index%-chips">\
         <label>chips</label>\
    </p> \
    <p><label>Bet : </label><label id="player_%index%-bet"></label></p> \
    <p><img id="player_%index%-card1"/>&nbsp;<img id="player_%index%-card2"/></p> \
</div>\
';