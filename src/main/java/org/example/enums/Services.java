package org.example.enums;

public enum Services {
    A("A"),
    B("B");

    private final String value;

    Services(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
