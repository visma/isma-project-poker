package org.isma.poker.exceptions;

import org.isma.poker.game.StepEnum;

import static java.lang.String.format;

public class InvalidStepActionException extends Exception {
    public InvalidStepActionException(StepEnum expected, StepEnum actual) {
        super(format("unexpected step ! expected : %s, actual : %s", expected, actual));
    }
}
