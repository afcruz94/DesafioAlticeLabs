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
import org.junit.jupiter.api.Test;

import static org.example.Tariffs.Alpha.*;
import static org.example.Tariffs.Beta.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    char serviceChar;
    boolean roaming, onlyWeekdays, nightPeriod;
    long msisdn;
    int minutes;
    RequestedServiceUnits requestedServiceUnits;
    ChargingRequest chargingRequest;

    @Test
    void testValidations() {
        assertFalse(Validations.isValidChargingRequest(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, minutes));
        assertFalse(Validations.isValidChargingRequest('D', !roaming, !onlyWeekdays, nightPeriod, 123456789L, 10));
        assertFalse(Validations.isValidChargingRequest('A', !roaming, !onlyWeekdays, nightPeriod, 1234567891111122L, 10));

        assertTrue(Validations.isValidChargingRequest('A', !roaming, !onlyWeekdays, nightPeriod, 123456789L, 10));
        assertTrue(Validations.isValidChargingRequest('B', roaming, roaming, nightPeriod, 123456789123456L, 10));
    }

    @Test
    void testRequestServiceUnits() {
        requestedServiceUnits = new RequestedServiceUnits('A', roaming,
                onlyWeekdays, nightPeriod, msisdn, 20);

        assertEquals('A', requestedServiceUnits.getServiceChar());
        assertEquals(20, requestedServiceUnits.getMinutes());
    }

    @Test
    void testChargingRequest() {
        requestedServiceUnits = new RequestedServiceUnits('A', roaming,
                !onlyWeekdays, nightPeriod, 123456789L, 20);

        chargingRequest = new ChargingRequest(requestedServiceUnits);

        assertEquals(20, chargingRequest.getRsu().getMinutes());
        assertNotEquals(40, chargingRequest.getRsu().getMinutes());
    }

    @Test
    void testBillingAccount() {
        BillingAccount billingAccount = new BillingAccount(123456789L);

        assertNotEquals(10, billingAccount.getBucketA());
        assertNotEquals(100, billingAccount.getBucketB());
        assertNotEquals(1000, billingAccount.getBucketC());
    }

    @Test
    void testService() {
        Service service1 = new Service(' ', null, null, null, null, null);
        assertNull(service1.getTariff());

        Service service2 = new Service('A', false, false, false, -1, new BillingAccount(1234567894561L));
        assertNull(service2.getTariff());

        Service service3 = new Service('D', true, true, true, 10, new BillingAccount(123456L));
        assertNull(service3.getTariff());

        Service service4 = new Service('A', false, false, false, 10, new BillingAccount(1234567894561L));
        assertNotNull(service4.getTariff());
    }

    @Test
    void testAlphaTariffs() {
        testAlpha1();
        testAlpha2();
        testAlpha3();
    }

    @Test
    void testBetaTariffs() {
        testBeta1();
        testBeta2();
        testBeta3();
    }

    @Test
    void testCdrTransaction() {
        requestedServiceUnits = new RequestedServiceUnits('D', !roaming,
                !onlyWeekdays, !nightPeriod, 123456L, 20);

        // Create Charging Request
        ChargingRequest chargingRequest = new ChargingRequest(requestedServiceUnits);

        BillingAccount ba = new BillingAccount(123456L);

        Service service3 = new Service('D', true, true, true, 10, ba);

        if (service3.getTariff() == null) {
            // Create Granted Service Unit
            GrantedServiceUnits grantedServiceUnits = new GrantedServiceUnits(requestedServiceUnits, "", "", 0f);

            // Charging Reply
            ChargingReply chargingReply = new ChargingReply(chargingRequest.getRequestId(), Result.NOT_ELIGIBLE, grantedServiceUnits);

            // CDR Transaction
            ClientDataRecord cdr = new ClientDataRecord(chargingRequest.getTimestamp(), chargingRequest.getMsisdn(),
                    'A', chargingReply, ba, "");

            assertNotNull(cdr);
        } else {
            assertNotNull(service3.getTariff());
        }

    }

}