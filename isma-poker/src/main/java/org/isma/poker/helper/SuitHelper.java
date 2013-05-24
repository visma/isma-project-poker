package org.isma.poker.helper;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.isma.poker.model.SuitEnum;

import static org.isma.poker.model.SuitEnum.*;

public class SuitHelper {
    private static final BidiMap conversionMap = new DualHashBidiMap();

    static {
        conversionMap.put("C", CLUBS);
        conversionMap.put("S", SPADES);
        conversionMap.put("D", DIAMONDS);
        conversionMap.put("H", HEARTS);
    }

    private SuitHelper() {
    }

    public static SuitEnum toValue(String c) {
        SuitEnum suitEnum = (SuitEnum) conversionMap.get(c);
        if (suitEnum != null) {
            return suitEnum;
        }
        throw new IllegalArgumentException("illegal suit : '" + c + "'");
    }

    //TODO TU
    public static String toString(SuitEnum suit) {
        return (String) conversionMap.getKey(suit);
    }
}
