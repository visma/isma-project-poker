package org.isma.poker.game.step;

import org.isma.poker.game.exceptions.PokerGameException;

import static java.lang.String.format;

public class InvalidStepActionException extends PokerGameException {
    public InvalidStepActionException(Step expected, Step actual) {
        super(format("unexpected step ! expected : %s, actual : %s", expected, actual));
    }
}
