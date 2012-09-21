package org.isma.web.poker;

import org.isma.web.poker.messages.AbstractPokerAction;

import java.util.ArrayList;
import java.util.List;

public class PokerMessagesHandler {
    private final List<AbstractPokerAction> actions = new ArrayList<AbstractPokerAction>();

    void registerMessages(AbstractPokerAction action) {
        actions.add(action);

    }


    public AbstractPokerAction findAction(String message) {
        for (AbstractPokerAction action : actions) {
            if (action.getRequest().isValidMessage(message)) {
                return action;
            }
        }
        throw new IllegalArgumentException("AbstractPokerAction not found ! invalid message : " + message);
    }

}
