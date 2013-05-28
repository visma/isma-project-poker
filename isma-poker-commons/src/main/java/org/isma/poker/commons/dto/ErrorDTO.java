package org.isma.poker.commons.dto;

public class ErrorDTO {
    private String message;
    private String type;

    public ErrorDTO(String message, String type) {
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
