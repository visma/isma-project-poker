package org.isma.poker.helper;

import org.isma.poker.model.ValueEnum;
import static org.isma.poker.model.ValueEnum.*;

public class ValueHelper {
    private ValueHelper() {
    }

    public static ValueEnum parse(String c) {
        if (c.equals("A")) {
            return ACE;
        } else if (c.equals("K")) {
            return KING;
        } else if (c.equals("Q")) {
            return QUEEN;
        } else if (c.equals("J")) {
            return KNAVE;
        } else if (c.equals("10")) {
            return TEN;
        } else if (c.equals("9")) {
            return NINE;
        } else if (c.equals("8")) {
            return EIGHT;
        } else if (c.equals("7")) {
            return SEVEN;
        } else if (c.equals("6")) {
            return SIX;
        } else if (c.equals("5")) {
            return FIVE;
        } else if (c.equals("4")) {
            return FOUR;
        } else if (c.equals("3")) {
            return THREE;
        } else if (c.equals("2")) {
            return TWO;
        } else {
            throw new IllegalArgumentException("illegal value : '" + c + "'");
        }
    }
}
