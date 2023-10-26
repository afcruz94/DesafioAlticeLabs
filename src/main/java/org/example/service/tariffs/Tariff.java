package org.example.service.tariffs;

import org.example.enums.Result;

import java.util.List;

public abstract class Tariff {
    private String name;
    private Float rating;
    private String charging;

    private Result result;

    public Tariff() {
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

    public Result getResult() {
        return result;
    }

    abstract List<String> checkEligibility(Boolean onlyWeekdays, Integer counterA, Boolean roaming, Integer[] buckets);

    abstract Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd,
                                Integer[] buckets, Integer[] counters);

    abstract String whereToCharge(String service, Boolean isRoaming, Integer counter);

    abstract Boolean isPossibleToGetTheMoney(String bucket, Integer[] buckets, Float value);

    abstract Float calculateRatingAlphaA(Boolean isRoaming, Boolean isNightPeriod, Integer bucket, Integer counter);

    abstract Float calculateRatingAlphaB(Boolean isNightPeriod, Integer bucket, Integer counter);

    abstract Float calculateRatingAlphaC(Boolean isWeekEnd, Integer bucket, Integer counter);
}
