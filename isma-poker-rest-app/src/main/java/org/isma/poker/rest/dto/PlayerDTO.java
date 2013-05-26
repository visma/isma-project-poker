package org.isma.poker.rest.dto;

public class PlayerDTO {
    private final String name;
    private final int chips;
    private final boolean fold;
    private final String holeCard1;
    private final String holeCard2;
    private int position;
    private int currentBet;

    public PlayerDTO(String name, int chips, boolean fold, String holeCard1, String holeCard2) {
        this.name = name;
        this.chips = chips;
        this.fold = fold;
        this.holeCard1 = holeCard1;
        this.holeCard2 = holeCard2;
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

    public String getHoleCard1() {
        return holeCard1;
    }

    public String getHoleCard2() {
        return holeCard2;
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
