package org.isma.poker.rest.dto;

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class SomeDTO {
    private final String name;

    public SomeDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
