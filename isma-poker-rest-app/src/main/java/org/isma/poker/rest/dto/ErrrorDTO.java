package org.isma.poker.rest.dto;

public class ErrrorDTO {
    private String message;
    private String type;

    public ErrrorDTO(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public boolean isError() {
        return true;
    }
}
