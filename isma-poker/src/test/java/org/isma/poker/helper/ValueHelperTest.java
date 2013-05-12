package org.isma.poker.helper;

import org.isma.poker.model.ValueEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ValueHelperTest {
    @Test
    public void parse_ok() {
        assertEquals(ValueEnum.ACE, ValueHelper.parse("A"));
        assertEquals(ValueEnum.KING, ValueHelper.parse("K"));
        assertEquals(ValueEnum.QUEEN, ValueHelper.parse("Q"));
        assertEquals(ValueEnum.KNAVE, ValueHelper.parse("J"));

        assertEquals(ValueEnum.TEN, ValueHelper.parse("10"));
        assertEquals(ValueEnum.NINE, ValueHelper.parse("9"));
        assertEquals(ValueEnum.EIGHT, ValueHelper.parse("8"));
        assertEquals(ValueEnum.SEVEN, ValueHelper.parse("7"));
        assertEquals(ValueEnum.SIX, ValueHelper.parse("6"));
        assertEquals(ValueEnum.FIVE, ValueHelper.parse("5"));
        assertEquals(ValueEnum.FOUR, ValueHelper.parse("4"));
        assertEquals(ValueEnum.THREE, ValueHelper.parse("3"));
        assertEquals(ValueEnum.TWO, ValueHelper.parse("2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_ko() {
        assertNull(ValueHelper.parse("LOL"));
    }
}
