package org.isma.poker.rest.dto;

public class PlayerDTO {
    private final String name;
    private final int chips;
    private final boolean fold;
    private final String holdCard1;
    private final String holdCard2;
    private int position;
    private int currentBet;

    public PlayerDTO(String name, int chips, boolean fold, String holdCard1, String holdCard2) {
        this.name = name;
        this.chips = chips;
        this.fold = fold;
        this.holdCard1 = holdCard1;
        this.holdCard2 = holdCard2;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public boolean isFold() {
        return fold;
    }

    public String getHoldCard1() {
        return holdCard1;
    }

    public String getHoldCard2() {
        return holdCard2;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getCurrentBet() {
        return currentBet;
    }
}
