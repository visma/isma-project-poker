package org.isma.poker.game.exceptions;

public class InvalidPlayerBetException extends PokerGameException {
    public enum InvalidBetEnum {
        CHECK_FORBIDDEN,
        BET_FORBIDDEN,
        RAISE_FORBIDDEN,
        ALLIN_FORBIDDEN,
        TO_CHEAP_BET,
        NOT_ENOUGH_CHIPS,;
    }

    public InvalidPlayerBetException(InvalidBetEnum invalidBetEnum) {
        super(InvalidPlayerBetException.class.getName() + " : " + invalidBetEnum.name());
    }
}
