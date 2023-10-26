package org.example.serviceUnits;

public class GrantedServiceUnits extends ServiceUnits {
    private final String tariff;
    private final String bucket;
    private final Float totalCost;

    public GrantedServiceUnits(ServiceUnits rsu, String tariff, String bucket, Float totalCost) {
        super(rsu.getServiceChar(), rsu.getRoaming(), rsu.getOnlyWeekdays(), rsu.getNightPeriod(), rsu.getMsisdn(), rsu.getMinutes());

        this.bucket = bucket;
        this.totalCost = totalCost;
        this.tariff = tariff;
    }

    @Override
    public String toString() {
        return "GrantedServiceUnits{" +
                "tariff='" + tariff + '\'' +
                ", bucket='" + bucket + '\'' +
                ", totalCost=" + totalCost +
                "} " + super.toString();
    }

    public String getTariff() {
        return tariff;
    }

    public String getBucket() {
        return bucket;
    }

    public Float getTotalCost() {
        return totalCost;
    }
}
