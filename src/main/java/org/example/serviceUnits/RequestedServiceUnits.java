package org.example.serviceUnits;

public class RequestedServiceUnits extends ServiceUnits {
    public RequestedServiceUnits(Character serviceChar, Boolean roaming, Boolean onlyWeekdays, Boolean nightPeriod, Long msisdn, Integer minutes) {
        super(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, minutes);
    }
}
