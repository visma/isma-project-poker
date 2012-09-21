package org.isma.web.poker.messages.response;

import org.isma.web.poker.messages.AbstractObjectMessageResponse;

import java.util.Arrays;
import java.util.List;

public class JoinResponse extends AbstractObjectMessageResponse<String> {
    public static final String NICKNAME = "nickname";


    @Override
    public String getIdentifier() {
        return "Join";
    }

    @Override
    public List<String> getAttributesKeys(String nickname) {
        return Arrays.asList(NICKNAME);
    }

    @Override
    public String getAttributeValue(String object, String attributeKey) {
        if (attributeKey.equals(NICKNAME)) {
            return object;
        }
        return throwUnexpectedAttributeValue(attributeKey);
    }
}
