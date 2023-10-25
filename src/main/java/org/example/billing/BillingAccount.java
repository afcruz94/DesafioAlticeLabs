package org.example.billing;

import org.example.service.tariffs.Tariff;

public final class BillingAccount {
    private final Integer msisdn;
    private final Float bucketA;
    private final Float bucketB;
    private final Float bucketC;
    private Integer counterA;
    private Integer counterB;
    private Integer counterC;
    private Integer counterD;
    private Tariff tariffA;
    private Tariff tariffB;

    public BillingAccount(Integer msisdn, Float bucketA, Float bucketB, Float bucketC) {
        this.msisdn = msisdn;
        this.bucketA = bucketA;
        this.bucketB = bucketB;
        this.bucketC = bucketC;
    }

    public BillingAccount(Integer msisdn, Float bucketA, Float bucketB, Float bucketC, Integer counterA, Integer counterB, Integer counterC, Integer counterD, Tariff tariffA, Tariff tariffB) {
        this.msisdn = msisdn;
        this.bucketA = bucketA;
        this.bucketB = bucketB;
        this.bucketC = bucketC;
        this.counterA = counterA;
        this.counterB = counterB;
        this.counterC = counterC;
        this.counterD = counterD;
        this.tariffA = tariffA;
        this.tariffB = tariffB;
    }

    public Integer getMsisdn() {
        return msisdn;
    }

    public Float getBucketA() {
        return bucketA;
    }

    public Float getBucketB() {
        return bucketB;
    }

    public Float getBucketC() {
        return bucketC;
    }

    public Integer getCounterA() {
        return counterA;
    }

    public void setCounterA(Integer counterA) {
        this.counterA = counterA;
    }

    public Integer getCounterB() {
        return counterB;
    }

    public void setCounterB(Integer counterB) {
        this.counterB = counterB;
    }

    public Integer getCounterC() {
        return counterC;
    }

    public void setCounterC(Integer counterC) {
        this.counterC = counterC;
    }

    public Integer getCounterD() {
        return counterD;
    }

    public void setCounterD(Integer counterD) {
        this.counterD = counterD;
    }

    public Tariff getTariffA() {
        return tariffA;
    }

    public void setTariffA(Tariff tariffA) {
        this.tariffA = tariffA;
    }

    public Tariff getTariffB() {
        return tariffB;
    }

    public void setTariffB(Tariff tariffB) {
        this.tariffB = tariffB;
    }
}
