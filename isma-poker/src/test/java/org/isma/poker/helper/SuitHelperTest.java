package org.isma.poker.helper;

import org.isma.poker.model.SuitEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class SuitHelperTest {

    @Test
    public void parse_ok() {
        assertEquals(SuitEnum.CLUBS, SuitHelper.toValue("C"));
        assertEquals(SuitEnum.DIAMONDS, SuitHelper.toValue("D"));
        assertEquals(SuitEnum.SPADES, SuitHelper.toValue("S"));
        assertEquals(SuitEnum.HEARTS, SuitHelper.toValue("H"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_ko() {
        assertNull(SuitHelper.toValue("TOTO"));
    }
}
