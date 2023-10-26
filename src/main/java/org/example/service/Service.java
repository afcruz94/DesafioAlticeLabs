package org.example.service;

import org.example.billing.BillingAccount;
import org.example.enums.Services;
import org.example.service.tariffs.Alpha;
import org.example.service.tariffs.Beta;
import org.example.service.tariffs.Tariff;

public class Service {
    private final Character service;
    private final Tariff tariff;

    public Service(Character service, Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod, Long msisdn) {
        this.service = service;
        this.tariff = findTariffByService(service, onlyWeekdays, isRoaming, isNightPeriod, msisdn);
    }

    public Character getService() {
        return service;
    }

    public Tariff getTariff() {
        return tariff;
    }

    private Tariff findTariffByService(Character service, Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod, Long msisdn) {
        Tariff t;
        BillingAccount billingAccount = new BillingAccount(msisdn);

        if (Services.valueOf(service.toString()) == Services.A) {
            Integer[] counters = new Integer[]{billingAccount.getCounterA(), billingAccount.getCounterB(), billingAccount.getCounterC()};
            Integer[] buckets = new Integer[]{billingAccount.getBucketA(), billingAccount.getBucketB(), billingAccount.getBucketC()};

            t = new Alpha(onlyWeekdays, isRoaming, isNightPeriod, counters, buckets);

        } else {
            t = new Beta(this.service);

            billingAccount.setTariffB(t);
        }

        return t;
    }
}
