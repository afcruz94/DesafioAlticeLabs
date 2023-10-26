package org.example.serviceUnits;

public class ServiceUnits {
    private Character serviceChar;
    private Boolean roaming;
    private Boolean onlyWeekdays;
    private Boolean nightPeriod;
    private Long msisdn;
    private Integer minutes;

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

    public void setServiceChar(Character serviceChar) {
        this.serviceChar = serviceChar;
    }

    public void setRoaming(Boolean roaming) {
        this.roaming = roaming;
    }

    public void setOnlyWeekdays(Boolean onlyWeekdays) {
        this.onlyWeekdays = onlyWeekdays;
    }

    public void setNightPeriod(Boolean nightPeriod) {
        this.nightPeriod = nightPeriod;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
