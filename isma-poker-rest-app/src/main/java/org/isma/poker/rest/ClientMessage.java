package org.isma.poker.rest;

public class ClientMessage {
    private final String action;
    private final Object object;

    public ClientMessage(String action, Object object) {
        this.action = action;
        this.object = object;
    }

    public String getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }
}
