package org.isma.poker.helper;

import org.isma.poker.model.SuitEnum;

public class SuitHelper {
    private SuitHelper() {
    }

    public static SuitEnum parse(String suit) {
        if (suit.equals("C")) {
            return SuitEnum.CLUBS;
        } else if (suit.equals("S")) {
            return SuitEnum.SPADES;
        } else if (suit.equals("D")) {
            return SuitEnum.DIAMONDS;
        } else if (suit.equals("H")) {
            return SuitEnum.HEARTS;
        } else {
            throw new IllegalArgumentException("illegal value : '" + suit + "'");
        }
    }

}
