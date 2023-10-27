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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Start Variables
        char serviceChar;
        boolean roaming, onlyWeekdays, nightPeriod;
        long msisdn;
        int minutes, res;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Assign a new tariff or get list of dummy results? (1/2) ");
            res = scanner.nextInt();
        } while (res > 2 || res < 1);

        if (res == 1) {
            do {
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
                System.out.println("Enter the number of minutes: ");
                minutes = scanner.nextInt();

                scanner.nextLine();
            } while (!Validations.isValidChargingRequest(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, minutes));

            ClientDataRecord cdr = implementService(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, minutes);

            System.out.println(cdr);
        } else {
            Random r = new Random();
            long range = 1234567L;
            List<ClientDataRecord> crs = new ArrayList<>();

            crs.add(implementService('A', true, true, false, (long) (r.nextDouble() * range), 20));
            crs.add(implementService('A', false, false, false, (long) (r.nextDouble() * range), 20));
            crs.add(implementService('A', true, false, false, (long) (r.nextDouble() * range), 20));
            crs.add(implementService('B', false, true, true, (long) (r.nextDouble() * range), 20));
            crs.add(implementService('B', false, false, false, (long) (r.nextDouble() * range), 20));
            crs.add(implementService('B', true, false, false, (long) (r.nextDouble() * range), 20));

            for (ClientDataRecord cdr : crs) {
                System.out.println(cdr);
                System.out.println("=================");
            }
        }
    }

    private static ClientDataRecord implementService(Character serviceChar, Boolean roaming, Boolean onlyWeekdays, Boolean nightPeriod,
                                                     Long msisdn, Integer minutes) {
        String tariffName, bucketName;
        Float totalCost;
        Result result;

        RequestedServiceUnits requestedServiceUnits = new RequestedServiceUnits(serviceChar, roaming,
                onlyWeekdays, nightPeriod, msisdn, minutes);

        // Create Charging Request
        ChargingRequest chargingRequest = new ChargingRequest(requestedServiceUnits);

        // Create Billing Account
        BillingAccount billingAccount = new BillingAccount(chargingRequest.getRsu().getMsisdn());

        // Create Service and get the best tariff
        Service service = new Service(chargingRequest.getService(), chargingRequest.getRoaming(),
                requestedServiceUnits.getOnlyWeekdays(), requestedServiceUnits.getNightPeriod(), requestedServiceUnits.getMinutes(),
                billingAccount);

        if (service.getTariff() != null) {
            if (service.getTariff().getResult() != Result.NOT_ELIGIBLE) {
                tariffName = service.getTariff().getName();
                bucketName = service.getTariff().getCharging();
                totalCost = service.getTariff().getRating();
                result = service.getTariff().getResult();
            } else {
                // Non Eligible Response
                tariffName = "";
                bucketName = "";
                totalCost = 0f;
                result = Result.NOT_ELIGIBLE;
            }

            // Create Granted Service Unit
            GrantedServiceUnits grantedServiceUnits = new GrantedServiceUnits(requestedServiceUnits, tariffName, bucketName, totalCost);

            // Charging Reply
            ChargingReply chargingReply = new ChargingReply(chargingRequest.getRequestId(), result, grantedServiceUnits);

            // CDR Transaction
            return new ClientDataRecord(chargingRequest.getTimestamp(), chargingRequest.getMsisdn(),
                    service.getService(), chargingReply, billingAccount, tariffName);
        }

        return new ClientDataRecord(chargingRequest.getTimestamp(), chargingRequest.getMsisdn(), null, null, billingAccount, null);
    }
}