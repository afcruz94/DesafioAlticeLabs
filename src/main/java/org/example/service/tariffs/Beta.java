package org.example.service.tariffs;

import java.util.List;

public class Beta extends Tariff {
    public Beta(Character s) {
        super();
    }

    @Override
    List<String> checkEligibility(Boolean onlyWeekdays, Integer counterA, Boolean roaming, Float[] buckets) {
        return null;
    }

    @Override
    Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd, Float[] buckets, Integer[] counters) {
        return null;
    }

    @Override
    String whereToCharge(String service, Boolean isRoaming, Integer counter) {
        return null;
    }

    @Override
    Boolean isPossibleToGetTheMoney(String bucket, Float[] buckets, Float value) {
        return null;
    }

    @Override
    Float calculateRatingAlphaA(Boolean isRoaming, Boolean isNightPeriod, Float bucket, Integer counter) {
        return null;
    }

    @Override
    Float calculateRatingAlphaB(Boolean isNightPeriod, Float bucket, Integer counter) {
        return null;
    }

    @Override
    Float calculateRatingAlphaC(Boolean isWeekEnd, Float bucket, Integer counter) {
        return null;
    }
}
