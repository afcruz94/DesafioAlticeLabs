package org.example.enums;

public enum TariffBeta {
    BETA1("A"),
    BETA2("B"),
    BETA3("C");

    private final String value;

    TariffBeta(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
