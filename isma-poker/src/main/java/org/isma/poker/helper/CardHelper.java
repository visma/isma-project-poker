package org.isma.poker.helper;

import org.isma.poker.model.Card;
import org.isma.poker.model.SuitEnum;
import org.isma.poker.model.ValueEnum;

public class CardHelper {
    public static final String SEPARATOR = "-";

    public static Card toCard(String valueAndSuit) {
        String[] strs = valueAndSuit.split(SEPARATOR);
        ValueEnum value = ValueHelper.toValue(strs[0]);
        SuitEnum suit = SuitHelper.toValue(strs[1]);
        return new Card(value, suit);
    }

    public static String toString(Card card) {
        return ValueHelper.toString(card.getValue()) + SEPARATOR + SuitHelper.toString(card.getSuit());
    }
}
