package org.isma.poker.helper;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.isma.poker.model.ValueEnum;

import static org.isma.poker.model.ValueEnum.*;

public class ValueHelper {
    private static final BidiMap conversionMap = new DualHashBidiMap();

    static {
        conversionMap.put("A", ACE);
        conversionMap.put("K", KING);
        conversionMap.put("Q", QUEEN);
        conversionMap.put("J", KNAVE);
        conversionMap.put("10", TEN);
        conversionMap.put("9", NINE);
        conversionMap.put("8", EIGHT);
        conversionMap.put("7", SEVEN);
        conversionMap.put("6", SIX);
        conversionMap.put("5", FIVE);
        conversionMap.put("4", FOUR);
        conversionMap.put("3", THREE);
        conversionMap.put("2", TWO);
    }

    private ValueHelper() {
    }

    public static ValueEnum toValue(String c) {
        ValueEnum valueEnum = (ValueEnum) conversionMap.get(c);
        if (valueEnum != null) {
            return valueEnum;
        }
        throw new IllegalArgumentException("illegal value : '" + c + "'");
    }

    //TODO TU
    public static String toString(ValueEnum value) {
        return (String) conversionMap.getKey(value);
    }

}
