package org.example.service.tariffs;

import org.example.enums.Buckets;
import org.example.enums.Result;
import org.example.enums.Tariffs;
import org.example.service.tariffs.Variables.BetaVariables;
import org.example.service.tariffs.Variables.Variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class Beta extends Tariff {
    private final String name;
    private final Float rating;
    private final String charging;
    private final Result result;

    public Beta(Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                Integer minutes, Integer[] counters, Float[] buckets) {
        super();

        HashMap<String, Float> getPricesForBetas = new HashMap<>();
        // Get List of all possible valid tariffs
        List<String> listOptions = isEligibleToTariff(onlyWeekdays, isNightPeriod, counters[0], isRoaming, buckets);

        // Loop each one of them and get unit price with discount
        for (String s : listOptions) {
            float unitPrice = priceForUnit(s, isRoaming, isNightPeriod, !onlyWeekdays, buckets, counters);
            getPricesForBetas.put(s, unitPrice);
        }

        // Get the cheapest one
        Float smallestValue = Float.MAX_VALUE;
        String key = "";
        for (HashMap.Entry<String, Float> entry : getPricesForBetas.entrySet()) {
            if (entry.getValue() < smallestValue) {
                key = entry.getKey();
                smallestValue = entry.getValue();
            }
        }

        // Get the bucket where will be charging
        // And check if is possible to remove the money from the bucket
        String bucketId = whereToCharge(key, isRoaming, counters[1]);
        if (!bucketId.isEmpty()) {
            this.name = key;
            this.rating = minutes * smallestValue;
            this.charging = bucketId;
            this.result = isPossibleToGetTheMoney(bucketId, buckets, minutes * smallestValue) ? Result.OK : Result.CREDIT_LIMIT_REACHED;
        } else {
            this.name = null;
            this.rating = null;
            this.charging = null;
            this.result = Result.NOT_ELIGIBLE;
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Float getRating() {
        return rating;
    }

    @Override
    public String getCharging() {
        return charging;
    }

    @Override
    public Result getResult() {
        return result;
    }

    @Override
    List<String> isEligibleToTariff(Boolean onlyWeekdays, Boolean isNightPeriod, Integer counterA, Boolean roaming, Float[] buckets) {
        List<String> res = new ArrayList<>();

        if (!roaming) {
            if (onlyWeekdays || (!onlyWeekdays && isNightPeriod)) res.add(Tariffs.BETA1.getValue());
            if (buckets[1] > Variables.bucketB) res.add(Tariffs.BETA2.getValue());
        } else {
            if (onlyWeekdays || (!onlyWeekdays && isNightPeriod)) res.add(Tariffs.BETA1.getValue());
            if (buckets[2] > Variables.bucketC) res.add(Tariffs.BETA3.getValue());
        }

        return res;
    }

    @Override
    Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd, Float[] buckets, Integer[] counters) {
        float price = 0f;

        if (Objects.equals(Tariffs.BETA1.getValue(), service)) {
            price = calculateRatingA(isRoaming, isNightPeriod, buckets[2], counters[0]);
        }

        if (Objects.equals(Tariffs.BETA2.getValue(), service)) {
            price = calculateRatingB(isNightPeriod, buckets[1], counters[1]);
        }

        if (Objects.equals(Tariffs.BETA3.getValue(), service)) {
            price = calculateRatingC(isWeekEnd, buckets[1], counters[1]);
        }

        return price;
    }

    @Override
    String whereToCharge(String service, Boolean isRoaming, Integer counterB) {
        if (Objects.equals(Tariffs.BETA1.getValue(), service)) {
            if (!isRoaming) return Buckets.BUCKET_A.getValue();
            else {
                return counterB > Variables.counterB ? Buckets.BUCKET_B.getValue() : Buckets.BUCKET_C.getValue();
            }
        }

        if (Objects.equals(Tariffs.BETA2.getValue(), service)) return Buckets.BUCKET_B.getValue();

        if (Objects.equals(Tariffs.BETA3.getValue(), service)) return Buckets.BUCKET_C.getValue();

        return "";
    }

    @Override
    Boolean isPossibleToGetTheMoney(String bucket, Float[] buckets, Float value) {
        if (Objects.equals(Buckets.BUCKET_A.getValue(), bucket)) return buckets[0] - value >= 0;

        if (Objects.equals(Buckets.BUCKET_B.getValue(), bucket)) return buckets[1] - value >= 0;

        if (Objects.equals(Buckets.BUCKET_C.getValue(), bucket)) return buckets[2] - value >= 0;

        return false;
    }

    @Override
    Float calculateRatingA(Boolean isRoaming, Boolean isNightPeriod, Float bucketC, Integer counterA) {
        float callCost = isRoaming ? BetaVariables.betaARoaming : isNightPeriod ? BetaVariables.betaALocalCallNightPeriod : BetaVariables.betaALocalCall;
        float discount = counterA >= Variables.counterA ? BetaVariables.betaADiscountCounterA : 0;
        if (bucketC > (Variables.bucketC + 40)) discount += BetaVariables.betaADiscountBucketC;

        return callCost - discount;
    }

    @Override
    Float calculateRatingB(Boolean isNightPeriod, Float bucketB, Integer counterA) {
        float callCost = isNightPeriod ? BetaVariables.betaBLocalCallNightPeriod : BetaVariables.betaBLocalCall;
        float discount = counterA > Variables.counterA ? BetaVariables.betaBDiscountCounterB : 0;
        if (bucketB > (Variables.bucketB + 5)) discount += BetaVariables.betaBDiscountBucketB;

        return callCost - discount;
    }

    @Override
    Float calculateRatingC(Boolean isWeekEnd, Float bucketB, Integer counterB) {
        float callCost = isWeekEnd ? BetaVariables.betaCWeekend : BetaVariables.betaCRoaming;
        float discount = counterB > (Variables.counterB + 5) ? BetaVariables.betaCDiscountCounterB : 0;
        if (bucketB > (Variables.bucketB + 5)) discount += BetaVariables.betaCDiscountBucketB;

        return callCost - discount;
    }
}
