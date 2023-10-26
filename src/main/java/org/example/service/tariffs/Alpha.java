package org.example.service.tariffs;

import org.example.enums.Buckets;
import org.example.enums.Result;
import org.example.enums.Tariffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Alpha extends Tariff {
    private final String name;
    private final Float rating;
    private final String charging;
    private final Result result;

    public Alpha(Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                 Integer[] counters,
                 Integer[] buckets) {
        super();

        HashMap<String, Float> getPricesForAlphas = new HashMap<>();
        // Get List of all possible valid tariffs
        List<String> listOptions = checkEligibility(onlyWeekdays, counters[0], isRoaming, buckets);

        // Loop each one of them and get unit price with discount
        for (String s : listOptions) {
            float unitPrice = priceForUnit(s, isRoaming, isNightPeriod, !onlyWeekdays, buckets, counters);
            getPricesForAlphas.put(s, unitPrice);
        }

        // Get the cheapest one
        Float smallestValue = Float.MAX_VALUE;
        String key = "";
        for (HashMap.Entry<String, Float> entry : getPricesForAlphas.entrySet()) {
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
            this.rating = smallestValue;
            this.charging = bucketId;
            this.result = isPossibleToGetTheMoney(bucketId, buckets, smallestValue) ? Result.OK : Result.CREDIT_LIMIT_REACHED;
        } else {
            this.name = null;
            this.rating = null;
            this.charging = null;
            this.result = Result.NOT_ELIGIBLE;
        }
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

    @Override
    protected List<String> checkEligibility(Boolean onlyWeekdays, Integer counterA, Boolean roaming, Integer[] buckets) {
        List<String> res = new ArrayList<>();

        if (!roaming) {
            if (onlyWeekdays && counterA < 100) res.add(Tariffs.ALPHA1.getValue());
            if (buckets[1] > 100) res.add(Tariffs.ALPHA2.getValue());
        } else {
            if (onlyWeekdays && counterA < 100) res.add(Tariffs.ALPHA1.getValue());
            if (buckets[2] > 10) res.add(Tariffs.ALPHA3.getValue());
        }
        return res;
    }

    @Override
    protected Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd,
                                 Integer[] buckets, Integer[] counters) {
        float price = 0f;

        if (Objects.equals(Tariffs.ALPHA1.getValue(), service)) {
            price = calculateRatingAlphaA(isRoaming, isNightPeriod, buckets[2], counters[0]);
        }

        if (Objects.equals(Tariffs.ALPHA2.getValue(), service)) {
            price = calculateRatingAlphaB(isNightPeriod, buckets[1], counters[1]);
        }

        if (Objects.equals(Tariffs.ALPHA3.getValue(), service)) {
            price = calculateRatingAlphaC(isWeekEnd, buckets[2], counters[2]);
        }

        return price;
    }

    @Override
    protected String whereToCharge(String service, Boolean isRoaming, Integer counterB) {
        if (Objects.equals(Tariffs.ALPHA1.getValue(), service)) {
            if (!isRoaming) return Buckets.BUCKET_A.getValue();
            else {
                return counterB > 5 ? Buckets.BUCKET_B.getValue() : Buckets.BUCKET_C.getValue();
            }
        }

        if (Objects.equals(Tariffs.ALPHA2.getValue(), service)) return Buckets.BUCKET_B.getValue();

        if (Objects.equals(Tariffs.ALPHA3.getValue(), service)) return Buckets.BUCKET_C.getValue();

        return "";
    }

    @Override
    protected Boolean isPossibleToGetTheMoney(String bucket, Integer[] buckets, Float value) {
        if (Objects.equals(Buckets.BUCKET_A.getValue(), bucket)) return buckets[0] - value >= 0;

        if (Objects.equals(Buckets.BUCKET_B.getValue(), bucket)) return buckets[1] - value >= 0;

        if (Objects.equals(Buckets.BUCKET_C.getValue(), bucket)) return buckets[2] - value >= 0;

        return false;
    }

    @Override
    protected Float calculateRatingAlphaA(Boolean isRoaming, Boolean isNightPeriod, Integer bucketC, Integer counterA) {
        float callCost = isRoaming ? 2f : isNightPeriod ? 0.5f : 1f;
        float discount = counterA >= 10 ? 0.25f : 0;
        if (bucketC > 50) discount += 0.1f;

        return callCost - discount;
    }

    @Override
    protected Float calculateRatingAlphaB(Boolean isNightPeriod, Integer bucketB, Integer counterB) {
        float callCost = isNightPeriod ? 0.25f : 0.5f;
        float discount = counterB > 10 ? 0.2f : 0;
        if (bucketB > 15) discount += 0.05f;

        return callCost - discount;
    }

    @Override
    protected Float calculateRatingAlphaC(Boolean isWeekEnd, Integer bucketC, Integer counterC) {
        float callCost = isWeekEnd ? 0.25f : 1f;
        float discount = counterC > 10 ? 0.2f : 0;
        if (bucketC > 15) discount += 0.05f;

        return callCost - discount;
    }
}
