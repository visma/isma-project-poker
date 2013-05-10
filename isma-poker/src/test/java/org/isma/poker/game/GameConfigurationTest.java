package org.isma.poker.game;

import org.isma.poker.game.model.InvalidGameConfigurationException;
import org.isma.poker.game.model.GameConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class GameConfigurationTest {

    @Test(expected = InvalidGameConfigurationException.class)
    public void minimum_invalid_big_blind_amount() throws Exception {
        int smallBlindAmount = 10;
        int bigBlindAmount = 5;
        new GameConfiguration(smallBlindAmount, bigBlindAmount, false, false);
    }

    @Test
    public void minimum_bet_allowed() throws Exception {
        int smallBlindAmount = 5;
        int bigBlindAmount = 10;
        GameConfiguration conf = new GameConfiguration(smallBlindAmount, bigBlindAmount, false, false);
        Assert.assertEquals(10, conf.getMinimumBetAllowed());
        conf = new GameConfiguration(smallBlindAmount, 52, false, false);
        Assert.assertEquals(52, conf.getMinimumBetAllowed());
    }
}
