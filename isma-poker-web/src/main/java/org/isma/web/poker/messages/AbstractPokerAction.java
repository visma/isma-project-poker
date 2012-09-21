package org.isma.web.poker.messages;

import org.isma.poker.game.GameSession;
import org.isma.web.poker.messages.response.ErrorResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractPokerAction<O> {
    public static final String NICKNAME = "nickname";
    protected final AbstractObjectMessageRequest request;
    protected final AbstractObjectMessageResponse response;

    protected AbstractPokerAction(AbstractObjectMessageRequest request, AbstractObjectMessageResponse response) {
        this.request = request;
        this.response = response;
    }

    public AbstractObjectMessageResponse update(GameSession game, String message) {
        String nickname = "";
        try {
            Map<String, String> attributeMap = request.decode(message);
            nickname = attributeMap.get(NICKNAME);
            checkBefore(game, attributeMap);
            O object = execute(game, attributeMap);
            response.setObject(object);
            return response;
        } catch (Exception ex) {
            ErrorResponse errorMessageResponse = new ErrorResponse(getErrorType(), nickname);
            errorMessageResponse.setObject(ex);
            return errorMessageResponse;
        }
    }

    protected abstract String getErrorType();

    protected abstract void checkBefore(GameSession game, Map<String, String> messageMap) throws Exception;

    protected abstract O execute(GameSession game, Map<String, String> messageMap) throws Exception;

    public AbstractObjectMessageRequest getRequest() {
        return request;
    }

    public List<AbstractPokerAction> nextActions(GameSession game) {
        return Collections.EMPTY_LIST;
    }
}
