package org.isma.poker.model;

public class Card {
    private final ValueEnum value;
    private final SuitEnum suit;

    public Card(ValueEnum value, SuitEnum suitEnum) {
        this.value = value;
        this.suit = suitEnum;
    }

    public ValueEnum getValue() {
        return value;
    }

    public SuitEnum getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", value.getLabel(), suit.getLabel());
    }
}
