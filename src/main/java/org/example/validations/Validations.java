package org.example.validations;

import org.example.service.Service;
import java.util.Arrays;

public class Validations {
    public static boolean isValidChargingRequest(Character service, Boolean roaming, Integer msisdn, Integer rsu) {
        int errorCount = 0;

        if (!Service.isValidService(service)) errorCount++;
        if (!isBoolean(roaming.toString())) errorCount++;
        if (!isValidValue(msisdn.toString())) errorCount++;
        if (!isValidRsu(rsu)) errorCount++;

        return errorCount == 0;
    }

    private static boolean isBoolean(String value) {
        return value != null && Arrays.stream(new String[]{"true", "false", "1", "0"}).anyMatch(b -> b.equalsIgnoreCase(value));
    }

    private static boolean isValidValue(String text) {
        return text.length() == 9;
    }

    private static boolean isValidRsu(Integer value) {
        return value > 0;
    }
}
