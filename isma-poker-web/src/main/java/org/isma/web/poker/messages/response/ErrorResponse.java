package org.isma.web.poker.messages.response;

import org.isma.web.poker.messages.AbstractObjectMessageResponse;

import java.util.Arrays;
import java.util.List;

public class ErrorResponse extends AbstractObjectMessageResponse<Exception> {
    public static final String MESSAGE = "message";
    private static final String TYPE = "type";
    public static final String NICKNAME = "nickname";
    protected final String nickname;

    private String errorType;

    public ErrorResponse(String errorType, String nickname) {
        this.errorType = errorType;
        this.nickname = nickname;
    }


    @Override
    public String getIdentifier() {
        return "Error";
    }

    @Override
    public List<String> getAttributesKeys(Exception ex) {
        return Arrays.asList(NICKNAME, TYPE, MESSAGE);
    }

    @Override
    public String getAttributeValue(Exception ex, String attributeKey) {
        if (attributeKey.equals(MESSAGE)) {
            return ex.getMessage();
        } else if (attributeKey.equals(TYPE)) {
            return errorType;
        } else if (attributeKey.equals(NICKNAME)) {
            return nickname;
        }
        return throwUnexpectedAttributeValue(attributeKey);
    }
}

