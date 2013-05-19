package org.isma.poker.game.model;

import org.isma.poker.game.exceptions.PokerGameException;

public class InvalidPlayerTurnException extends PokerGameException {
    public InvalidPlayerTurnException(Player player) {
        super(player.toString());
    }
}
