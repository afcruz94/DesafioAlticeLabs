package org.example.enums;

public enum Buckets {
    BUCKET_A("bucketA"),
    BUCKET_B("bucketB"),
    BUCKET_C("bucketC");
    private final String value;

    Buckets(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
