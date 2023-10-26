package org.example.validations;

import org.example.enums.Services;

import java.util.Arrays;
import java.util.Objects;

public final class Validations {
    public static boolean isValidChargingRequest(Character service,
                                                 Boolean roaming, Boolean onlyWeekdays, Boolean nightPeriod,
                                                 Long msisdn, Integer rsu) {
        int errorCount = 0;

        if (!isValidService(service)) errorCount++;
        if (!isBoolean(roaming.toString())) errorCount++;
        if (!isBoolean(onlyWeekdays.toString())) errorCount++;
        if (!isBoolean(nightPeriod.toString())) errorCount++;
        if (!isValidValue(msisdn.toString())) errorCount++;
        if (!isValidRsu(rsu)) errorCount++;

        return errorCount == 0;
    }

    private static boolean isBoolean(String value) {
        return value != null && Arrays.stream(new String[]{"true", "false", "1", "0"}).anyMatch(b -> b.equalsIgnoreCase(value));
    }

    private static boolean isValidValue(String text) {
        return !text.isEmpty() && text.length() <= 15;
    }

    private static boolean isValidRsu(Integer value) {
        return value > 0;
    }

    public static Boolean isValidService(Character c) {
        for (Services s : Services.values()) {
            if (Objects.equals(s.getValue(), c.toString())) return true;
        }

        return false;
    }
}
