package org.example.serviceUnits;

public class ServiceUnits {
    private final Character serviceChar;
    private final Boolean roaming;
    private final Boolean onlyWeekdays;
    private final Boolean nightPeriod;
    private final Long msisdn;
    private final Integer minutes;

    public ServiceUnits(Character serviceChar, Boolean roaming, Boolean onlyWeekdays, Boolean nightPeriod, Long msisdn, Integer minutes) {
        this.serviceChar = serviceChar;
        this.roaming = roaming;
        this.onlyWeekdays = onlyWeekdays;
        this.nightPeriod = nightPeriod;
        this.msisdn = msisdn;
        this.minutes = minutes;
    }

    public Character getServiceChar() {
        return serviceChar;
    }

    public Boolean getRoaming() {
        return roaming;
    }

    public Boolean getOnlyWeekdays() {
        return onlyWeekdays;
    }

    public Boolean getNightPeriod() {
        return nightPeriod;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public Integer getMinutes() {
        return minutes;
    }
}
