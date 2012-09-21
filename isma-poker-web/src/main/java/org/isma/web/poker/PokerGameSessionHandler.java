package org.isma.web.poker;

import org.isma.web.poker.actions.JoinAction;
import org.isma.web.poker.messages.AbstractPokerAction;

public class PokerGameSessionHandler {
    private final PokerMessagesHandler messagesHandler = new PokerMessagesHandler();

    public PokerGameSessionHandler() {
        messagesHandler.registerMessages(new JoinAction());
    }


    public AbstractPokerAction getAction(String message) {
        return messagesHandler.findAction(message);
    }
}
