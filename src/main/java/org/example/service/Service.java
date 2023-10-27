package org.example.service;

import org.example.billing.BillingAccount;
import org.example.enums.Result;
import org.example.enums.Services;
import org.example.enums.Tariffs;
import org.example.service.tariffs.Alpha;
import org.example.service.tariffs.Beta;
import org.example.service.tariffs.Tariff;
import org.example.validations.Validations;

import java.sql.Timestamp;
import java.time.Instant;

public class Service {
    private final Character service;
    private final Tariff tariff;
    private static Integer counterA = 0;
    private static Integer counterB = 0;
    private static Integer counterC = 0;

    public Service(Character service, Boolean isRoaming, Boolean onlyWeekdays, Boolean isNightPeriod, Integer minutes, BillingAccount billingAccount) {
        this.service = service;
        this.tariff = findTariffByService(service, onlyWeekdays, isRoaming, isNightPeriod, minutes, billingAccount);
    }

    public Character getService() {
        return service;
    }

    public Tariff getTariff() {
        return tariff;
    }

    /**
     * Find the best tariff by the given arguments
     *
     * @param service        Service
     * @param onlyWeekdays   Boolean
     * @param isRoaming      Boolean
     * @param isNightPeriod  Boolean
     * @param minutes        Integer
     * @param billingAccount Billing Account
     * @return Tariff (Alpha/Beta)
     */
    private Tariff findTariffByService(Character service, Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                                       Integer minutes, BillingAccount billingAccount) {
        Tariff t = null;
        Integer[] counters = new Integer[]{0, 0, 0};
        Float[] buckets = new Float[]{0f, 0f, 0f};

        if (Validations.isPossibleToCreateService(service, onlyWeekdays, isRoaming, isNightPeriod, minutes, billingAccount)) {
            counters = new Integer[]{billingAccount.getCounterA(), billingAccount.getCounterB(), billingAccount.getCounterC()};
            buckets = new Float[]{billingAccount.getBucketA(), billingAccount.getBucketB(), billingAccount.getBucketC()};

            if (Services.valueOf(service.toString()) == Services.A) {
                t = new Alpha(onlyWeekdays, isRoaming, isNightPeriod, minutes, counters, buckets);
            } else {
                t = new Beta(onlyWeekdays, isRoaming, isNightPeriod, minutes, counters, buckets);
            }

            if (t.getResult() != Result.NOT_ELIGIBLE)
                updateBillingAccountCounters(t, isRoaming, billingAccount, minutes);
        }

        billingAccount.setCounterD(Timestamp.from(Instant.now()));

        return t;
    }

    /**
     * Update billing accounts counters
     *
     * @param t              Tariff (Alpha/Beta)
     * @param isRoaming      Boolean
     * @param billingAccount Billing Account
     * @param minutes        Integer
     */
    private static void updateBillingAccountCounters(Tariff t, Boolean isRoaming, BillingAccount billingAccount, Integer minutes) {
        if (t.getName().substring(0, 1).equals(Services.A.getValue())) {
            billingAccount.setTariffA(t);
            counterA++;
            billingAccount.setCounterA(minutes * counterA);
        } else {
            billingAccount.setTariffB(t);
            if (t.getName().equals(Tariffs.BETA1.getValue())) {
                counterB++;
                billingAccount.setCounterB(counterB);

            }
        }

        if (isRoaming) {
            counterC++;
            billingAccount.setCounterC(counterC);
        }
    }
}
