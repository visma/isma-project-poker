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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (suit != card.suit) return false;
        if (value != card.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }
}
