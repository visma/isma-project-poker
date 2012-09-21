package org.isma.poker;

public enum HandEvaluationEnum {
    KICKER(1),
    PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9);

    private int rank;

    HandEvaluationEnum(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
