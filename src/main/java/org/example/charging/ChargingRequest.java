package org.example.charging;

import org.example.service.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class ChargingRequest {
    private String requestId;
    private Timestamp timestamp;
    private Service service;
    private Boolean roaming;
    private Integer msisdn;
    private Integer rsu;

    public ChargingRequest(Service service, Boolean roaming, Integer msisdn, Integer rsu) {
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

    public Service getService() {
        return service;
    }

    public Boolean getRoaming() {
        return roaming;
    }

    public void setRoaming(Boolean roaming) {
        this.roaming = roaming;
    }

    public Integer getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Integer msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getRsu() {
        return rsu;
    }

    @Override
    public String toString() {
        return "ChargingRequest{" + "requestId='" + requestId + '\'' + ", timestamp=" + timestamp + ", service=" + service + ", roaming=" + roaming + ", msisdn=" + msisdn + ", rsu=" + rsu + '}';
    }
}
