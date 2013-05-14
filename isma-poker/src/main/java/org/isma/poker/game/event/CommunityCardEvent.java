package org.isma.poker.game.event;

import org.isma.poker.model.Card;

public class CommunityCardEvent extends GameEvent {
    private final Card card;

    public CommunityCardEvent(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String getDescription() {
        return "new community card : " + card;
    }

}
