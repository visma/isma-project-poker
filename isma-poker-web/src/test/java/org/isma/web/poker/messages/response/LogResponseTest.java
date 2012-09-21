package org.isma.web.poker.messages.response;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LogResponseTest {
    @Test
    public void test_toMessage() throws Exception {
        LogResponse response = new LogResponse();
        response.setObject("ceci est un log");
        assertEquals("Log{log=ceci est un log}", response.encode());
    }
}
