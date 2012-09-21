package org.isma.web.poker.messages.response;

import org.isma.web.poker.messages.AbstractObjectMessageResponse;

import java.util.List;

import static java.util.Arrays.asList;

public class LogResponse extends AbstractObjectMessageResponse<String> {
    public static final String LOG = "log";

    @Override
    public String getIdentifier() {
        return "Log";
    }

    @Override
    public List<String> getAttributesKeys(String log) {
        return asList(LOG);
    }

    @Override
    public String getAttributeValue(String log, String attributeKey) {
        if (attributeKey.equals(LOG)) {
            return log;
        }
        return throwUnexpectedAttributeValue(attributeKey);
    }
}