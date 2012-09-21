package org.isma.poker.model;

public enum SuitEnum {
    SPADES("Spades"),
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs");

    private String label;

    SuitEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
