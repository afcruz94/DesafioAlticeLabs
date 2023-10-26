package org.example.cdr;

import org.example.charging.ChargingReply;

import java.sql.Timestamp;
import java.util.Arrays;

public class ClientDataRecord {
    private final Timestamp timestamp;
    private final Long msisdn;
    private final Character service;
    private final ChargingReply chargingReply;
    private final Float[] buckets;
    private final Integer[] counters;
    private final String tariffId;


    public ClientDataRecord(Timestamp timestamp, Long msisdn, Character service, ChargingReply chargingReply, Float[] buckets, Integer[] counters, String tariffId) {
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

    public Float[] getBuckets() {
        return buckets;
    }

    public Integer[] getCounters() {
        return counters;
    }

    public String getTariffId() {
        return tariffId;
    }

    @Override
    public String toString() {
        return "ClientDataRecord{" +
                "timestamp=" + timestamp +
                ", msisdn=" + msisdn +
                ", service=" + service +
                ", chargingReply=" + chargingReply +
                ", buckets=" + Arrays.toString(buckets) +
                ", counters=" + Arrays.toString(counters) +
                ", tariffId='" + tariffId + '\'' +
                '}';
    }
}
