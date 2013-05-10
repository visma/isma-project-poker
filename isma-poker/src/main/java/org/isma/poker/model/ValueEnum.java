package org.isma.poker.model;

public enum ValueEnum {
    ACE("Ace", 14),
    KING("King", 13),
    QUEEN("Queen", 12),
    KNAVE("Knave", 11),
    TEN("10", 10),
    NINE("9", 9),
    EIGHT("8", 8),
    SEVEN("7", 7),
    SIX("6", 6),
    FIVE("5", 5),
    FOUR("4", 4),
    THREE("3", 3),
    TWO("2", 2);

    private String label;
    private int value;

    ValueEnum(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public ValueEnum nextValue() {
        switch (this) {
            case ACE:
                return TWO;
            case KING:
                return ACE;
            case QUEEN:
                return KING;
            case KNAVE:
                return QUEEN;
            case TEN:
                return KNAVE;
            case NINE:
                return TEN;
            case EIGHT:
                return NINE;
            case SEVEN:
                return EIGHT;
            case SIX:
                return SEVEN;
            case FIVE:
                return SIX;
            case FOUR:
                return FIVE;
            case THREE:
                return FOUR;
            case TWO:
                return THREE;
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }
}
