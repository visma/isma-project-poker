package org.isma.poker.game.step;

import org.isma.poker.game.step.Step;

import static java.lang.String.format;

public class InvalidStepActionException extends Exception {
    public InvalidStepActionException(Step expected, Step actual) {
        super(format("unexpected step ! expected : %s, actual : %s", expected, actual));
    }
}
