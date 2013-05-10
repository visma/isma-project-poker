package org.isma.poker.game.model;

import org.isma.poker.game.model.Player;

public class InvalidPlayerTurnException extends Exception {
    public InvalidPlayerTurnException(Player player) {
        super(player.toString());
    }
}