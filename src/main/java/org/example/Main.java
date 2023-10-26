package org.example;

import org.example.billing.BillingAccount;
import org.example.cdr.ClientDataRecord;
import org.example.charging.ChargingReply;
import org.example.charging.ChargingRequest;
import org.example.enums.Result;
import org.example.service.Service;
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
        String tariff, bucket;
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

        // Create Charging Request
        ChargingRequest chargingRequest = new ChargingRequest(serviceChar, roaming, msisdn, rsu);

        // Create Service
        Service service = new Service(chargingRequest.getService(), onlyWeekdays, chargingRequest.getRoaming(), nightPeriod, chargingRequest.getMsisdn());

        if (service.getTariff() != null) {
            // Tariff + Bucket
            tariff = service.getTariff().getName();
            bucket = service.getTariff().getCharging();
            // Calculate total cost
            totalCost = service.getTariff().getRating() * chargingRequest.getRsu();
            // Result
            result = service.getTariff().getResult();
        } else {
            // Non Eligible Response
            bucket = "";
            tariff = "";
            totalCost = 0;
            result = Result.NOT_ELIGIBLE;
            chargingRequest.setRsu(0);
        }

        // Charging Reply
        ChargingReply chargingReply = new ChargingReply(chargingRequest.getRequestId(), result, chargingRequest.getRsu());

        // CDR Transaction
//        ClientDataRecord cdr = new ClientDataRecord(chargingRequest.getTimestamp(), chargingRequest.getMsisdn(),
//                service.getService(), chargingReply,
//                new Integer[]{bucket, totalCost}, new Integer[]{0, 0},
//                tariff);
    }
}