package org.example.service;

import org.example.billing.BillingAccount;
import org.example.enums.Services;
import org.example.service.tariffs.Alpha;
import org.example.service.tariffs.Beta;
import org.example.service.tariffs.Tariff;

public class Service {
    private final Character service;
    private final Tariff tariff;

    public Service(Character service, Boolean isRoaming, Boolean onlyWeekdays, Boolean isNightPeriod, Integer minutes, Integer[] counters, Float[] buckets) {
        this.service = service;
        this.tariff = findTariffByService(service, onlyWeekdays, isRoaming, isNightPeriod, minutes, counters, buckets);
    }

    public Character getService() {
        return service;
    }

    public Tariff getTariff() {
        return tariff;
    }

    private Tariff findTariffByService(Character service, Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                                       Integer minutes, Integer[] counters, Float[] buckets) {
        Tariff t;

        if (Services.valueOf(service.toString()) == Services.A) {
            t = new Alpha(onlyWeekdays, isRoaming, isNightPeriod, minutes, counters, buckets);

        } else {
            t = new Beta(onlyWeekdays, isRoaming, isNightPeriod, minutes, counters, buckets);
        }

        return t;
    }
}
