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

    /**
     * Check if is eligible for Alpha Tariffs
     *
     * @param onlyWeekdays Boolean
     * @param counter      Integer
     * @param roaming      Boolean
     * @param buckets      Float[]
     * @return List<String>
     */
    abstract List<String> checkEligibilityAlpha(Boolean onlyWeekdays, Integer counter, Boolean roaming, Float[] buckets);

    /**
     * Check if is eligible for Beta Tariffs
     *
     * @param onlyWeekdays  Boolean
     * @param isNightPeriod Boolean
     * @param counter       Integer
     * @param roaming       Boolean
     * @param buckets       Float[]
     * @return List<String>
     */
    abstract List<String> checkEligibilityBeta(Boolean onlyWeekdays, Boolean isNightPeriod, Integer counter, Boolean roaming, Float[] buckets);

    /**
     * Get unit price
     *
     * @param service       Service
     * @param isRoaming     Boolean
     * @param isNightPeriod Boolean
     * @param isWeekEnd     Boolean
     * @param buckets       Float[]
     * @param counters      Integer[]
     * @return Float
     */
    abstract Float priceForUnit(String service, Boolean isRoaming, Boolean isNightPeriod, Boolean isWeekEnd,
                                Float[] buckets, Integer[] counters);

    /**
     * Search the correct bucket where will charge
     * @param service Service
     * @param isRoaming Boolean
     * @param counter Integer
     * @return String
     */
    abstract String whereToCharge(String service, Boolean isRoaming, Integer counter);

    /**
     * Check if is possible to charge the value from the bucket
     * @param bucket String
     * @param buckets Float[]
     * @param value Float
     * @return Boolean
     */
    abstract Boolean isPossibleToGetTheMoney(String bucket, Float[] buckets, Float value);

    /**
     * Calculate rating for A tariffs (Alpha/Beta)
     * @param isRoaming Boolean
     * @param isNightPeriod Boolean
     * @param bucket Float
     * @param counter Integer
     * @return Float
     */
    abstract Float calculateRatingA(Boolean isRoaming, Boolean isNightPeriod, Float bucket, Integer counter);

    /**
     * Calculate rating for B tariffs (Alpha/Beta)
     * @param isNightPeriod Boolean
     * @param bucket Float
     * @param counter Integer
     * @return Float
     */
    abstract Float calculateRatingB(Boolean isNightPeriod, Float bucket, Integer counter);

    /**
     * Calculate rating for C tariffs (Alpha/Beta)
     * @param isWeekEnd Boolean
     * @param bucket Float
     * @param counter Integer
     * @return Float
     */
    abstract Float calculateRatingC(Boolean isWeekEnd, Float bucket, Integer counter);
}
