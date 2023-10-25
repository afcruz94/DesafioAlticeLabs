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
        Boolean roaming = false;
        Integer msisdn = 919919919;
        Integer rsu = 2;
        Float bucketA = 5f, bucketB = 10f, bucketC = 100f;

        // Validation of Charging Request
        boolean validation = Validations.isValidChargingRequest(serviceChar, roaming, msisdn, rsu);

        if (validation) {
            // Create Billing Account
            BillingAccount billingAccount = new BillingAccount(msisdn, bucketA, bucketB, bucketC);

            // Create Already Validated Service
            Service service = new Service(serviceChar);

            // Create Charging Request
            ChargingRequest chargingRequest = new ChargingRequest(service, roaming, msisdn, rsu);

            // Get Phone Tariff + Calculate Total Cost + Apply Discounts + Find bucket where value will be charged
            Tariff tariff = new Tariff(
                    service.getService(),
                    true, chargingRequest.getRoaming(), false,
                    500, 5, 50, billingAccount.getBucketB(), billingAccount.getBucketC());

            System.out.println(tariff.getName() + " " + tariff.getRating() + " " + tariff.getCharging());
        } else {
            // Non Eligible Response
            System.out.println("non eligible response");
        }

        // CDR Transaction
    }
}