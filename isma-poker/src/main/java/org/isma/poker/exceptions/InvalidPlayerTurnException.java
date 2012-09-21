package org.isma.poker.exceptions;

import org.isma.poker.game.Player;

public class InvalidPlayerTurnException extends Exception {
    public InvalidPlayerTurnException(Player player) {
        super(player.toString());
    }
}
