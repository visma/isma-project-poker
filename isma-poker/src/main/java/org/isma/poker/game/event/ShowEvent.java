package org.isma.poker.game.event;

import org.isma.poker.HandEvaluator;
import org.isma.poker.game.model.Player;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;

import static java.lang.String.format;

public class ShowEvent extends GameEvent {
    private final String player;
    private final Hand hand;
    private final HandEvaluation handEvaluation;

    public ShowEvent(Player player) {
        this.player = player.getNickname();
        hand = new Hand(player.getHand());
        handEvaluation = new HandEvaluator().evaluate(hand);
    }

    @Override
    public String getDescription() {
        return format("ShowEvent(%s - %s: %s/%s/%s/%s/%s)",
                player,
                handEvaluation,
                hand.get(0),
                hand.get(1),
                hand.get(2),
                hand.get(3),
                hand.get(4)
        );
    }

    public String getPlayer() {
        return player;
    }

    public HandEvaluation getHandEvaluation() {
        return handEvaluation;
    }

    public Hand getHand() {
        return hand;
    }
}
