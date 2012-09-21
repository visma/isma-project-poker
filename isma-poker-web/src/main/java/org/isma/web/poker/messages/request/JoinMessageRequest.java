package org.isma.web.poker.messages.request;

import org.isma.web.poker.messages.AbstractObjectMessageRequest;

import java.util.Arrays;
import java.util.List;

public class JoinMessageRequest extends AbstractObjectMessageRequest<String> {
    public static final String NICKNAME = "nickname";

    @Override
    public String getIdentifier() {
        return "Join";
    }

    @Override
    public List<String> getAttributesKeys() {
        return Arrays.asList(NICKNAME);
    }


}
