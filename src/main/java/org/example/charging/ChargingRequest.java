package org.example.charging;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class ChargingRequest {
    private final String requestId;
    private final Timestamp timestamp;
    private final Character service;
    private final Boolean roaming;
    private final Long msisdn;
    private Integer rsu;

    public ChargingRequest(Character service, Boolean roaming, Long msisdn, Integer rsu) {
        this.requestId = UUID.randomUUID().toString();
        this.timestamp = Timestamp.from(Instant.now());
        this.service = service;
        this.roaming = roaming;
        this.msisdn = msisdn;
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

    public Integer getRsu() {
        return rsu;
    }

    public void setRsu(Integer rsu) {
        this.rsu = rsu;
    }

    @Override
    public String toString() {
        return "ChargingRequest{" + "requestId='" + requestId + '\'' + ", timestamp=" + timestamp + ", service=" + service + ", roaming=" + roaming + ", msisdn=" + msisdn + ", rsu=" + rsu + '}';
    }
}
