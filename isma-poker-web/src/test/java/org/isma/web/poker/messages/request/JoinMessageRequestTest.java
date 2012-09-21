package org.isma.web.poker.messages.request;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class JoinMessageRequestTest {
    @Test
    public void testValidMessage() throws Exception {
        JoinMessageRequest encoder = new JoinMessageRequest();
        Assert.assertTrue(encoder.isValidMessage("Join{nickname=aaaa}"));
    }

    @Test
    public void testJoin() throws Exception {
        JoinMessageRequest encoder = new JoinMessageRequest();
        Map<String, String> decodage = encoder.decode("Join{nickname=aaaa}");
        assertEquals("aaaa", decodage.get("nickname"));
    }
}
