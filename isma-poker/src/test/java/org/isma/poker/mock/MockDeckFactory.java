package org.isma.poker.mock;

import org.apache.log4j.Logger;
import org.isma.poker.factory.IDeckFactory;
import org.isma.poker.model.Card;
import org.isma.poker.model.FiftyTwoCardsEnum;
import org.isma.poker.model.Hand;

import static java.lang.String.format;

public class MockDeckFactory implements IDeckFactory {
    private static final Logger LOG = Logger.getLogger(MockDeckFactory.class);
    private MockDeck lastMockedDeck;
    private final Hand forceHand = new Hand();

    public MockDeck buildPokerDeck(DeckTypeEnum deckTypeEnum) {
        lastMockedDeck = new MockDeck();
        if (deckTypeEnum == DeckTypeEnum.FIFTY_TWO_CARDS_DECK) {
            FiftyTwoCardsEnum.values();
            for (FiftyTwoCardsEnum cardEnum : FiftyTwoCardsEnum.values()) {
                lastMockedDeck.add(cardEnum);
            }
        }
        lastMockedDeck.prepareCards(forceHand);
        return lastMockedDeck;
    }

    public void forceTurnOrRiver(FiftyTwoCardsEnum card) {
        LOG.debug(format("forceTurnOrRiver(%s)", card));
        lastMockedDeck.prepareCards(card);
    }

    public void forceFlop(FiftyTwoCardsEnum card1, FiftyTwoCardsEnum card2, FiftyTwoCardsEnum card3) {
        LOG.debug(format("forceFlop(%s, %s, %s)", card1, card2, card3));
        lastMockedDeck.prepareCards(card1, card2, card3);
    }

    public void forceHands(Card... cards) {
        LOG.debug(format("forceHands(%s)", cards));
        for (Card card : cards) {
            forceHand.add(card);
        }
    }

    public void forceHands(FiftyTwoCardsEnum player1Card1, FiftyTwoCardsEnum player1Card2,
                           FiftyTwoCardsEnum player2Card1, FiftyTwoCardsEnum player2Card2) {
        LOG.debug(format("forceHands(%s, %s, %s, %s)", player1Card1, player1Card2, player2Card1, player2Card2));
        forceHand.add(player1Card1);
        forceHand.add(player2Card1);
        forceHand.add(player1Card2);
        forceHand.add(player2Card2);
        //lastMockedDeck.prepareCards(player1Card1, player2Card1, player1Card2, player2Card2);
    }

    public void forceHands(FiftyTwoCardsEnum player1Card1, FiftyTwoCardsEnum player1Card2,
                           FiftyTwoCardsEnum player2Card1, FiftyTwoCardsEnum player2Card2,
                           FiftyTwoCardsEnum player3Card1, FiftyTwoCardsEnum player3Card2) {
        LOG.debug(format("forceHands(%s, %s, %s, %s, %s, %s)",
                player1Card1, player1Card2, player2Card1,
                player2Card2, player3Card1, player3Card2));
        forceHand.add(player1Card1);
        forceHand.add(player2Card1);
        forceHand.add(player3Card1);
        forceHand.add(player1Card2);
        forceHand.add(player2Card2);
        forceHand.add(player3Card2);
        //lastMockedDeck.prepareCards(player1Card1, player2Card1, player3Card1, player1Card2, player2Card2, player3Card2);
    }


}
