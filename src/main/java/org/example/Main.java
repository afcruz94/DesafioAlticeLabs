package org.example;

import org.example.billing.BillingAccount;
import org.example.charging.ChargingRequest;
import org.example.service.Service;
import org.example.service.tariffs.Tariff;
import org.example.validations.Validations;

public class Main {
    public static void main(String[] args) {
        // Vars
        Character serviceChar = 'A';
        Boolean roaming = true, onlyWeekdays = true, isNightPeriod = false;
        Integer msisdn = 919919919;
        Integer rsu = 2;
        Integer counterA = 50, counterB = 50, counterC = 50;
        Float bucketA = 5f, bucketB = 10f, bucketC = 100f;

        // Validation of Charging Request
        boolean validation = Validations.isValidChargingRequest(serviceChar, roaming, msisdn, rsu);

        if (validation) {
            // Create Billing Account
            BillingAccount billingAccount = new BillingAccount(msisdn, bucketA, bucketB, bucketC);

            // Create Already Validated Service
            Service service = new Service(serviceChar);

            // Create Charging Request
            ChargingRequest chargingRequest = new ChargingRequest(service, roaming, billingAccount.getMsisdn(), rsu);

            // Get Phone Tariff + Calculate Total Cost + Apply Discounts + Find bucket where value will be charged
            Tariff tariff = new Tariff(
                    service.getService(),
                    onlyWeekdays, chargingRequest.getRoaming(), isNightPeriod,
                    counterA, counterB, counterC,
                    billingAccount.getBucketA(), billingAccount.getBucketB(), billingAccount.getBucketC());

            System.out.println(tariff.getName() + " " + tariff.getRating() + " " + tariff.getCharging());
        } else {
            // Non Eligible Response
            System.out.println("non eligible response");
        }

        // CDR Transaction
    }
}