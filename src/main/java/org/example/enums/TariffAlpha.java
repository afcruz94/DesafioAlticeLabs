package org.example.enums;

public enum TariffAlpha {
    ALPHA1("A"),
    ALPHA2("B"),
    ALPHA3("C");

    private final String value;

    TariffAlpha(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
