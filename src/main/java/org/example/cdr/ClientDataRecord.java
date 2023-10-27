package org.example.cdr;

import org.example.billing.BillingAccount;
import org.example.charging.ChargingReply;

import java.sql.Timestamp;

public class ClientDataRecord implements Comparable<ClientDataRecord> {
    private final Timestamp timestamp;
    private final Long msisdn;
    private final Character service;
    private final ChargingReply chargingReply;
    private final Float[] buckets;
    private final Integer[] counters;
    private final Timestamp counterD;
    private final String tariffId;


    public ClientDataRecord(Timestamp timestamp, Long msisdn, Character service, ChargingReply chargingReply, BillingAccount billingAccount, String tariffId) {
        this.timestamp = timestamp;
        this.msisdn = msisdn;
        this.service = service;
        this.chargingReply = chargingReply;
        this.buckets = new Float[]{billingAccount.getBucketA(), billingAccount.getBucketB(), billingAccount.getBucketC()};
        this.counters = new Integer[]{billingAccount.getCounterA(), billingAccount.getCounterB(), billingAccount.getCounterC()};
        this.counterD = billingAccount.getCounterD();
        this.tariffId = tariffId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(ClientDataRecord o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }

    @Override
    public String toString() {
        return String.format("Timestamp: %s\nMSISDN: %s\nService: %c\nName: %s\nBucket: %s\nResult: %s\nTotalCost: %.2f€\n" +
                        "BucketA: %.2f BucketB: %.2f BucketC: %.2f\n" +
                        "CounterA: %d CounterB: %d CounterC: %d CounterD: %s",
                timestamp, msisdn, service,
                tariffId, chargingReply.getGsu().getBucket(),
                chargingReply.getResult().toString(), chargingReply.getGsu().getTotalCost(),
                buckets[0], buckets[1], buckets[2],
                counters[0], counters[1], counters[2], counterD.toString());
    }
}
