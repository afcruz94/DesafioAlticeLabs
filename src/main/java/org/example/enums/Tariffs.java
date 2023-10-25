package org.example.enums;

public enum Tariffs {
    ALPHA1("Alpha A"),
    ALPHA2("Alpha  B"),
    ALPHA3("Alpha C"),
    BETA1("Beta A"),
    BETA2("Beta B"),
    BETA3("Beta C");

    private final String value;

    Tariffs(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
