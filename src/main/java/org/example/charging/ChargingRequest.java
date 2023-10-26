package org.example.charging;

import org.example.serviceUnits.RequestedServiceUnits;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class ChargingRequest {
    private final String requestId;
    private final Timestamp timestamp;
    private final Character service;
    private final Boolean roaming;
    private final Long msisdn;
    private final RequestedServiceUnits rsu;

    public ChargingRequest(RequestedServiceUnits rsu) {
        this.requestId = UUID.randomUUID().toString();
        this.timestamp = Timestamp.from(Instant.now());
        this.service = rsu.getServiceChar();
        this.roaming = rsu.getRoaming();
        this.msisdn = rsu.getMsisdn();
        this.rsu = rsu;
    }

    public String getRequestId() {
        return requestId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Character getService() {
        return service;
    }

    public Boolean getRoaming() {
        return roaming;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public RequestedServiceUnits getRsu() {
        return rsu;
    }

    @Override
    public String toString() {
        return "ChargingRequest{" + "requestId='" + requestId + '\'' + ", timestamp=" + timestamp + ", service=" + service + ", roaming=" + roaming + ", msisdn=" + msisdn + ", rsu=" + rsu + '}';
    }
}
