package org.example.service.tariffs;

public class Beta extends Tariff {
    public Beta(Character s) {
        super(s);
    }

    private String checkEligibility(Boolean onlyWeekdays, Boolean nightWeekEnds, Boolean roaming, Integer bucketB, Boolean localCall, Integer bucketC) {
        if (onlyWeekdays && nightWeekEnds) return "Beta 1";
        else {
            if (!roaming && bucketB > 100) return "Beta 2";
            if (localCall && bucketC > 100) return "Beta 3";
            else return "";
        }
    }
}
