package org.example.validations;

import org.example.billing.BillingAccount;
import org.example.enums.Services;

import java.util.Arrays;
import java.util.Objects;

public final class Validations {
    public static boolean isValidChargingRequest(Character service,
                                                 Boolean isRoaming, Boolean isOnlyWeekdays, Boolean isNightPeriod,
                                                 Long msisdn, Integer minutes) {
        int errorCount = 0;

        if (!isValidService(service)) errorCount++;
        if (isBoolean(isRoaming)) errorCount++;
        if (isBoolean(isOnlyWeekdays)) errorCount++;
        if (isBoolean(isNightPeriod)) errorCount++;
        if (!isValidValue(msisdn)) errorCount++;
        if (isNullOrNegativeInteger(minutes)) errorCount++;

        return errorCount == 0;
    }

    public static boolean isPossibleToCreateService(Character service, Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                                                    Integer minutes, BillingAccount billingAccount) {
        int errorCount = 0;
        if (!isValidService(service)) errorCount++;
        if (isBoolean(isRoaming)) errorCount++;
        if (isBoolean(onlyWeekdays)) errorCount++;
        if (isBoolean(isNightPeriod)) errorCount++;
        if (isNullOrNegativeInteger(minutes)) errorCount++;
        if (!isValidObject(billingAccount)) errorCount++;

        return errorCount == 0;
    }

    private static boolean isBoolean(Boolean value) {
        if (value != null) {
            return Arrays.stream(new String[]{"true", "false", "1", "0"}).noneMatch(b -> b.equalsIgnoreCase(value.toString()));
        }
        return false;
    }

    private static boolean isValidValue(Long value) {
        return value != null && !value.toString().isEmpty() && value.toString().length() <= 15;
    }

    private static boolean isNullOrNegativeInteger(Integer value) {
        return value != null && value < 0;
    }

    private static Boolean isValidService(Character c) {
        for (Services s : Services.values()) {
            if (Objects.equals(s.getValue(), c.toString())) return true;
        }

        return false;
    }

    private static Boolean isValidObject(Object object) {
        return Objects.nonNull(object);
    }
}
