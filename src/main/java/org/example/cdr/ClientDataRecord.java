package org.example.cdr;

import org.example.charging.ChargingReply;

import java.sql.Timestamp;

public class ClientDataRecord {
    private final Timestamp timestamp;
    private final Long msisdn;
    private final Character service;
    private final ChargingReply chargingReply;
    private final Integer[] buckets;
    private final Integer[] counters;
    private final String tariffId;


    public ClientDataRecord(Timestamp timestamp, Long msisdn, Character service, ChargingReply chargingReply, Integer[] buckets, Integer[] counters, String tariffId) {
        this.timestamp = timestamp;
        this.msisdn = msisdn;
        this.service = service;
        this.chargingReply = chargingReply;
        this.buckets = buckets;
        this.counters = counters;
        this.tariffId = tariffId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public Character getService() {
        return service;
    }

    public ChargingReply getChargingReply() {
        return chargingReply;
    }

    public Integer[] getBuckets() {
        return buckets;
    }

    public Integer[] getCounters() {
        return counters;
    }

    public String getTariffId() {
        return tariffId;
    }
}
