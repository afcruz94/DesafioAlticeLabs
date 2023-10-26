package org.example.charging;

import org.example.enums.Result;
import org.example.serviceUnits.GrantedServiceUnits;

public class ChargingReply {
    private final String requestId;
    private final Result result;
    private final GrantedServiceUnits gsu;

    public ChargingReply(String requestId, Result result, GrantedServiceUnits gsu) {
        this.requestId = requestId;
        this.result = result;
        this.gsu = gsu;
    }

    public String getRequestId() {
        return requestId;
    }

    public Result getResult() {
        return result;
    }

    public GrantedServiceUnits getGsu() {
        return gsu;
    }
}
