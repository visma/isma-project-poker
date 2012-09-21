package org.isma.poker.model;

import static org.isma.poker.model.SuitEnum.*;
import static org.isma.poker.model.ValueEnum.*;

public enum FiftyTwoCardsEnum {
    TWO_OF_SPADES(new Card(TWO, SPADES)),
    THREE_OF_SPADES(new Card(THREE, SPADES)),
    FOUR_OF_SPADES(new Card(FOUR, SPADES)),
    FIVE_OF_SPADES(new Card(FIVE, SPADES)),
    SIX_OF_SPADES(new Card(SIX, SPADES)),
    SEVEN_OF_SPADES(new Card(SEVEN, SPADES)),
    EIGHT_OF_SPADES(new Card(EIGHT, SPADES)),
    NINE_OF_SPADES(new Card(NINE, SPADES)),
    TEN_OF_SPADES(new Card(TEN, SPADES)),
    KNAVE_OF_SPADES(new Card(KNAVE, SPADES)),
    QUEEN_OF_SPADES(new Card(QUEEN, SPADES)),
    KING_OF_SPADES(new Card(KING, SPADES)),
    ACE_OF_SPADES(new Card(ACE, SPADES)),

    TWO_OF_HEARTS(new Card(TWO, HEARTS)),
    THREE_OF_HEARTS(new Card(THREE, HEARTS)),
    FOUR_OF_HEARTS(new Card(FOUR, HEARTS)),
    FIVE_OF_HEARTS(new Card(FIVE, HEARTS)),
    SIX_OF_HEARTS(new Card(SIX, HEARTS)),
    SEVEN_OF_HEARTS(new Card(SEVEN, HEARTS)),
    EIGHT_OF_HEARTS(new Card(EIGHT, HEARTS)),
    NINE_OF_HEARTS(new Card(NINE, HEARTS)),
    TEN_OF_HEARTS(new Card(TEN, HEARTS)),
    KNAVE_OF_HEARTS(new Card(KNAVE, HEARTS)),
    QUEEN_OF_HEARTS(new Card(QUEEN, HEARTS)),
    KING_OF_HEARTS(new Card(KING, HEARTS)),
    ACE_OF_HEARTS(new Card(ACE, HEARTS)),

    TWO_OF_DIAMONDS(new Card(TWO, DIAMONDS)),
    THREE_OF_DIAMONDS(new Card(THREE, DIAMONDS)),
    FOUR_OF_DIAMONDS(new Card(FOUR, DIAMONDS)),
    FIVE_OF_DIAMONDS(new Card(FIVE, DIAMONDS)),
    SIX_OF_DIAMONDS(new Card(SIX, DIAMONDS)),
    SEVEN_OF_DIAMONDS(new Card(SEVEN, DIAMONDS)),
    EIGHT_OF_DIAMONDS(new Card(EIGHT, DIAMONDS)),
    NINE_OF_DIAMONDS(new Card(NINE, DIAMONDS)),
    TEN_OF_DIAMONDS(new Card(TEN, DIAMONDS)),
    KNAVE_OF_DIAMONDS(new Card(KNAVE, DIAMONDS)),
    QUEEN_OF_DIAMONDS(new Card(QUEEN, DIAMONDS)),
    KING_OF_DIAMONDS(new Card(KING, DIAMONDS)),
    ACE_OF_DIAMONDS(new Card(ACE, DIAMONDS)),

    TWO_OF_CLUBS(new Card(TWO, CLUBS)),
    THREE_OF_CLUBS(new Card(THREE, CLUBS)),
    FOUR_OF_CLUBS(new Card(FOUR, CLUBS)),
    FIVE_OF_CLUBS(new Card(FIVE, CLUBS)),
    SIX_OF_CLUBS(new Card(SIX, CLUBS)),
    SEVEN_OF_CLUBS(new Card(SEVEN, CLUBS)),
    EIGHT_OF_CLUBS(new Card(EIGHT, CLUBS)),
    NINE_OF_CLUBS(new Card(NINE, CLUBS)),
    TEN_OF_CLUBS(new Card(TEN, CLUBS)),
    KNAVE_OF_CLUBS(new Card(KNAVE, CLUBS)),
    QUEEN_OF_CLUBS(new Card(QUEEN, CLUBS)),
    KING_OF_CLUBS(new Card(KING, CLUBS)),
    ACE_OF_CLUBS(new Card(ACE, CLUBS));


    private final Card card;

    FiftyTwoCardsEnum(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
