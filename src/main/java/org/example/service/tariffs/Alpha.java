package org.example.service.tariffs;

import org.example.enums.Buckets;
import org.example.enums.Tariffs;

import java.util.Objects;

public class Alpha {
    private String name;
    private Float rating;
    private String charging;

    public Alpha(Boolean onlyWeekdays, Boolean isRoaming, Boolean isNightPeriod,
                 Integer counterA, Integer counterB, Integer counterC,
                 Float bucketB, Float bucketC) {

        String whichAlphaTariff = checkEligibility(onlyWeekdays, counterA, isRoaming, bucketB, bucketC);
        if (whichAlphaTariff != null) {
            this.name = whichAlphaTariff;
            this.rating = priceForUnit(whichAlphaTariff, isRoaming, isNightPeriod, !onlyWeekdays, bucketB, bucketC, counterA, counterB, counterC);
            this.charging = whereToCharge(whichAlphaTariff, isRoaming, counterB);
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

    private String checkEligibility(Boolean onlyWeekdays, Integer counterA, Boolean roaming, Float bucketB, Float bucketC) {
        if (onlyWeekdays && counterA < 100) return Tariffs.ALPHA1.getValue();
        else {
            if (!roaming && bucketB > 100) return Tariffs.ALPHA2.getValue();
            if (roaming && bucketC > 10) return Tariffs.ALPHA3.getValue();
            else return null;
        }
    }

    private Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd,
                               Float bucketB, Float bucketC, Integer counterA, Integer counterB, Integer counterC) {
        float price = 0f;

        if (Objects.equals(Tariffs.ALPHA1.getValue(), service)) {
            price = calculateRatingAlphaA(isRoaming, bucketC, counterA);
        }
        if (Objects.equals(Tariffs.ALPHA2.getValue(), service)) {
            price = calculateRatingAlphaB(isNightPeriod, bucketB, counterB);
        }
        if (Objects.equals(Tariffs.ALPHA3.getValue(), service)) {
            price = calculateRatingAlphaC(isWeekEnd, bucketC, counterC);
        }

        return price;
    }

    private String whereToCharge(String service, Boolean isRoaming, Integer counterB) {
        if (Objects.equals(Tariffs.ALPHA1.getValue(), service)) {
            if (!isRoaming) return Buckets.BUCKET_A.getValue();
            else {
                return counterB > 5 ? Buckets.BUCKET_B.getValue() : Buckets.BUCKET_C.getValue();
            }
        }
        if (Objects.equals(Tariffs.ALPHA2.getValue(), service)) return Buckets.BUCKET_B.getValue();

        if (Objects.equals(Tariffs.ALPHA3.getValue(), service)) return Buckets.BUCKET_C.getValue();

        return null;
    }

    private Float calculateRatingAlphaA(Boolean isRoaming, Float bucketC, Integer counterA) {
        int callCost = isRoaming ? 2 : 1;
        float discount = counterA >= 10 ? 0.25f : 0;
        if (bucketC > 50) discount += 0.1f;

        return callCost - discount;
    }

    private Float calculateRatingAlphaB(Boolean isNightPeriod, Float bucketB, Integer counterB) {
        float callCost = isNightPeriod ? 0.25f : 0.5f;
        float discount = counterB > 10 ? 0.2f : 0;
        if (bucketB > 15) discount += 0.05f;

        return callCost - discount;
    }

    private Float calculateRatingAlphaC(Boolean isWeekEnd, Float bucketC, Integer counterC) {
        float callCost = isWeekEnd ? 0.25f : 1f;
        float discount = counterC > 10 ? 0.2f : 0;
        if (bucketC > 15) discount += 0.05f;

        return callCost - discount;
    }
}
