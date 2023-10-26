package org.example.service.tariffs;

import org.example.enums.Result;

import java.util.List;

public abstract class Tariff {
    private String name;
    private Float rating;
    private String charging;

    private Result result;

    protected Tariff() {
    }

    protected Tariff(String name, Float rating, String charging, Result result) {
        this.name = name;
        this.rating = rating;
        this.charging = charging;
        this.result = result;
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

    abstract List<String> checkEligibilityAlpha(Boolean onlyWeekdays, Integer counterA, Boolean roaming, Float[] buckets);
    abstract List<String> checkEligibilityBeta(Boolean onlyWeekdays, Boolean isNightPeriod, Integer counterA, Boolean roaming, Float[] buckets);

    abstract Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd,
                                Float[] buckets, Integer[] counters);

    abstract String whereToCharge(String service, Boolean isRoaming, Integer counter);

    abstract Boolean isPossibleToGetTheMoney(String bucket, Float[] buckets, Float value);

    abstract Float calculateRatingA(Boolean isRoaming, Boolean isNightPeriod, Float bucket, Integer counter);

    abstract Float calculateRatingB(Boolean isNightPeriod, Float bucket, Integer counter);

    abstract Float calculateRatingC(Boolean isWeekEnd, Float bucket, Integer counter);
}
