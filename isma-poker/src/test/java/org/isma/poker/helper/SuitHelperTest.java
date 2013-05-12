package org.isma.poker.helper;

import org.isma.poker.model.SuitEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class SuitHelperTest {

    @Test
    public void parse_ok() {
        assertEquals(SuitEnum.CLUBS, SuitHelper.parse("C"));
        assertEquals(SuitEnum.DIAMONDS, SuitHelper.parse("D"));
        assertEquals(SuitEnum.SPADES, SuitHelper.parse("S"));
        assertEquals(SuitEnum.HEARTS, SuitHelper.parse("H"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_ko() {
        assertNull(SuitHelper.parse("TOTO"));
    }
}
