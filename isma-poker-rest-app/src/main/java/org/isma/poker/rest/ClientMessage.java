package org.isma.poker.rest;

public class ClientMessage {
    private final String id;
    private final Object object;

    public ClientMessage(String id, Object object) {
        this.id = id;
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public Object getObject() {
        return object;
    }
}
