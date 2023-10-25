package org.example.service.tariffs;

import org.example.enums.Services;

public class Tariff {
    private Character service;
    protected String name;
    protected Float rating;
    protected String charging;

    public Tariff(Character service) {
        this.service = service;
    }

    public Tariff(Character service,
                  Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                  Integer counterA, Integer counterB, Integer counterC,
                  Float bucketA, Float bucketB, Float bucketC) {
        this.service = service;

        findTariffByService(onlyWeekdays, isRoaming, isNightPeriod,
                counterA, counterB, counterC, bucketB, bucketC);
    }

    public Character getService() {
        return service;
    }

    public String getName() {
        return name;
    }

    public Float getRating() {
        return rating;
    }

    public String getCharging() {
        return charging;
    }

    public void findTariffByService(Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                                    Integer counterA, Integer counterB, Integer counterC,
                                    Float bucketB, Float bucketC) {
        if (Services.valueOf(service.toString()) == Services.A) {
            Alpha a = new Alpha(onlyWeekdays, isRoaming, isNightPeriod, counterA, counterB, counterC, bucketB, bucketC);
            this.name = a.getName();
            this.rating = a.getRating();
            this.charging = a.getCharging();

        } else {
            new Beta(service);
        }
    }
}
