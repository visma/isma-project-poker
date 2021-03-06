package org.isma.poker.game.model;

public class GameConfiguration {
    private static final int DEFAULT_MAX_PLAYERS = 2;

    private final int smallBlindAmount;
    private final int bigBlindAmount;

    //TODO a tester !
    private final int maxPlayers = DEFAULT_MAX_PLAYERS;

    private final boolean tournament;
    private final boolean noLimit;

    public GameConfiguration(int smallBlindAmount, int bigBlindAmount, boolean tournament, boolean noLimit) throws InvalidGameConfigurationException {
        this.smallBlindAmount = smallBlindAmount;
        this.bigBlindAmount = bigBlindAmount;
        this.tournament = tournament;
        this.noLimit = noLimit;
        if (smallBlindAmount > bigBlindAmount) {
            throw new InvalidGameConfigurationException("smallBlind cannot be greather than bigblind");
        }
    }

    public int getSmallBlindAmount() {
        return smallBlindAmount;
    }

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public int getMinimumBetAllowed() {
        return bigBlindAmount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
