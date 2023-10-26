package org.example;

import org.example.billing.BillingAccount;
import org.example.cdr.ClientDataRecord;
import org.example.charging.ChargingReply;
import org.example.charging.ChargingRequest;
import org.example.enums.Result;
import org.example.service.Service;
import org.example.serviceUnits.GrantedServiceUnits;
import org.example.serviceUnits.RequestedServiceUnits;
import org.example.validations.Validations;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Start Variables
        char serviceChar;
        boolean roaming, onlyWeekdays, nightPeriod;
        long msisdn;
        float totalCost;
        int rsu;
        String tariffName, bucketName;
        Result result;


        // Validation of Charging Request (check by service which vars are needed)
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the desired service (A/B): ");
            serviceChar = scanner.next().charAt(0);
            System.out.println("Roaming needed (y/n)? ");
            char r = scanner.next().charAt(0);
            roaming = r == 'y';
            System.out.println("Only Weekdays (y/n)? ");
            char ow = scanner.next().charAt(0);
            onlyWeekdays = ow == 'y';
            System.out.println("Night Period (y/n)? ");
            char np = scanner.next().charAt(0);
            nightPeriod = np == 'y';
            System.out.println("Enter the MSISDN (15 digits max): ");
            msisdn = scanner.nextLong();
            System.out.println("Enter the Requested Service Units: ");
            rsu = scanner.nextInt();

            scanner.nextLine();
        } while (!Validations.isValidChargingRequest(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, rsu));

        // Create Request Service Unit
        RequestedServiceUnits requestedServiceUnits = new RequestedServiceUnits(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, rsu);

        // Create Charging Request
        ChargingRequest chargingRequest = new ChargingRequest(requestedServiceUnits);

        // Create Billing Account
        BillingAccount billingAccount = new BillingAccount(requestedServiceUnits.getMsisdn());
        Integer[] counters = new Integer[]{billingAccount.getCounterA(), billingAccount.getCounterB(), billingAccount.getCounterC()};
        Float[] buckets = new Float[]{billingAccount.getBucketA(), billingAccount.getBucketB(), billingAccount.getBucketC()};

        // Create Service and get the best tariff
        Service service = new Service(chargingRequest.getService(), chargingRequest.getRoaming(),
                requestedServiceUnits.getOnlyWeekdays(), requestedServiceUnits.getNightPeriod(), requestedServiceUnits.getMinutes(),
                counters, buckets);

        if (service.getTariff().getResult() != Result.NOT_ELIGIBLE) {
            tariffName = service.getTariff().getName();
            bucketName = service.getTariff().getCharging();
            totalCost = service.getTariff().getRating();
            result = service.getTariff().getResult();
        } else {
            // Non Eligible Response
            tariffName = "";
            bucketName = "";
            totalCost = 0;
            result = Result.NOT_ELIGIBLE;
        }

        // Create Granted Service Unit
        GrantedServiceUnits grantedServiceUnits = new GrantedServiceUnits(requestedServiceUnits, tariffName, bucketName, totalCost);

        // Charging Reply
        ChargingReply chargingReply = new ChargingReply(chargingRequest.getRequestId(), result, grantedServiceUnits);

        // CDR Transaction
        ClientDataRecord cdr = new ClientDataRecord(chargingRequest.getTimestamp(), chargingRequest.getMsisdn(),
                service.getService(), chargingReply, buckets, counters, tariffName);

        System.out.println(cdr);
    }
}