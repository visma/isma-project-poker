CardCollection = Backbone.Collection.extend({
    model: Card,

    updateDTOs: function (tableDTO) {
        console.debug("updateDTOs - cards");
        var cards = tableDTO.cards;
        for (var i = 0; i < cards.length; i++) {
            var card = cards[i];
            console.info("fill " + i);
            console.info("card " + card);
            this.at(i).set('value', card);
        }
        for (var i = cards.length; i < this.length; i++) {
            console.info("efface " + i);
            this.at(i).reset();
        }
    }
});
