package org.isma.poker.helper;

import org.isma.poker.model.ValueEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ValueHelperTest {
    @Test
    public void parse_ok() {
        assertEquals(ValueEnum.ACE, ValueHelper.toValue("A"));
        assertEquals(ValueEnum.KING, ValueHelper.toValue("K"));
        assertEquals(ValueEnum.QUEEN, ValueHelper.toValue("Q"));
        assertEquals(ValueEnum.KNAVE, ValueHelper.toValue("J"));

        assertEquals(ValueEnum.TEN, ValueHelper.toValue("10"));
        assertEquals(ValueEnum.NINE, ValueHelper.toValue("9"));
        assertEquals(ValueEnum.EIGHT, ValueHelper.toValue("8"));
        assertEquals(ValueEnum.SEVEN, ValueHelper.toValue("7"));
        assertEquals(ValueEnum.SIX, ValueHelper.toValue("6"));
        assertEquals(ValueEnum.FIVE, ValueHelper.toValue("5"));
        assertEquals(ValueEnum.FOUR, ValueHelper.toValue("4"));
        assertEquals(ValueEnum.THREE, ValueHelper.toValue("3"));
        assertEquals(ValueEnum.TWO, ValueHelper.toValue("2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_ko() {
        assertNull(ValueHelper.toValue("LOL"));
    }
}
