package org.example;

import org.example.Tariffs.Alpha;
import org.example.Tariffs.Beta;
import org.example.billing.BillingAccount;
import org.example.cdr.ClientDataRecord;
import org.example.charging.ChargingReply;
import org.example.charging.ChargingRequest;
import org.example.enums.Result;
import org.example.service.Service;
import org.example.service.tariffs.Tariff;
import org.example.serviceUnits.GrantedServiceUnits;
import org.example.serviceUnits.RequestedServiceUnits;
import org.example.validations.Validations;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    char serviceChar;
    boolean roaming, onlyWeekdays, nightPeriod;
    long msisdn;
    int minutes;
    RequestedServiceUnits requestedServiceUnits;
    ChargingRequest chargingRequest;

    @Test
    @DisplayName("Test if console inputs are valid or not")
    @Category({Validations.class})
    void testValidations() {
        assertAll(
                () -> assertFalse(Validations.isValidChargingRequest(serviceChar, roaming, onlyWeekdays, nightPeriod, msisdn, minutes)),
                () -> assertFalse(Validations.isValidChargingRequest('D', !roaming, !onlyWeekdays, nightPeriod, 123456789L, 10)),
                () -> assertFalse(Validations.isValidChargingRequest('A', !roaming, !onlyWeekdays, nightPeriod, 1234567891111122L, 10)),
                () -> assertTrue(Validations.isValidChargingRequest('A', !roaming, !onlyWeekdays, nightPeriod, 123456789L, 10)),
                () -> assertTrue(Validations.isValidChargingRequest('B', roaming, roaming, nightPeriod, 123456789123456L, 10))
        );
    }

    @Test
    @DisplayName("Test requested service units with the input data")
    @Category({RequestedServiceUnits.class})
    void testRequestServiceUnits() {
        requestedServiceUnits = new RequestedServiceUnits('A', roaming,
                onlyWeekdays, nightPeriod, msisdn, 20);

        assertAll(
                () -> assertEquals('A', requestedServiceUnits.getServiceChar()),
                () -> assertEquals(20, requestedServiceUnits.getMinutes())
        );
    }

    @Test
    @DisplayName("Test creation of charging request with input data")
    @Category({ChargingRequest.class})
    void testChargingRequest() {
        requestedServiceUnits = new RequestedServiceUnits('A', roaming,
                !onlyWeekdays, nightPeriod, 123456789L, 20);

        chargingRequest = new ChargingRequest(requestedServiceUnits);

        assertAll(
                () -> assertEquals(20, chargingRequest.getRsu().getMinutes()),
                () -> assertNotEquals(40, chargingRequest.getRsu().getMinutes())
        );
    }

    @Test
    @DisplayName("Test creation of a billing Account with msisdn value from input")
    @Category({BillingAccount.class})
    void testBillingAccount() {
        BillingAccount billingAccount = new BillingAccount(123456789L);

        assertAll(
                () -> assertFalse(billingAccount.getMsisdn().toString().isEmpty()),
                () -> assertTrue(billingAccount.getBucketA() > 0),
                () -> assertTrue(billingAccount.getBucketB() > 0),
                () -> assertTrue(billingAccount.getBucketC() > 0)
        );
    }

    @Test
    @DisplayName("Test if creating service with different values returns a valid one or null")
    @Category({Service.class})
    void testService() {
        Service service1 = new Service(' ', null, null, null, null, null);
        Service service2 = new Service('A', false, false, false, -1, new BillingAccount(1234567894561L));
        Service service3 = new Service('D', true, true, true, 10, new BillingAccount(123456L));
        Service service4 = new Service('A', false, false, false, 10, new BillingAccount(1234567894561L));

        assertAll(
                () -> assertNull(service1.getTariff()),
                () -> assertNull(service2.getTariff()),
                () -> assertNull(service3.getTariff()),
                () -> assertNotNull(service4.getTariff())
        );
    }

    @Test
    @DisplayName("Test if creating all different Alpha tariffs returns all the different info")
    @Category({Alpha.class, Tariff.class})
    void testAlphaTariffs() {
        assertAll(
                Alpha::testAlpha1,
                Alpha::testAlpha2,
                Alpha::testAlpha3
        );
    }

    @Test
    @DisplayName("Test if creating all different Beta tariffs returns all the different info")
    @Category({Beta.class, Tariff.class})
    void testBetaTariffs() {
        assertAll(
                Beta::testBeta1,
                Beta::testBeta2,
                Beta::testBeta3
        );
    }

    @Test
    @DisplayName("Test all the functionalities until return CDR Transaction response")
    @Category({RequestedServiceUnits.class, ChargingRequest.class, BillingAccount.class, Service.class, GrantedServiceUnits.class,
            ChargingReply.class, ClientDataRecord.class})
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
            assertAll(
                    () -> assertNotNull(service3.getTariff()),
                    () -> assertTrue(service3.getTariff().getRating() > 0),
                    () -> assertFalse(service3.getTariff().getName().isEmpty()),
                    () -> assertFalse(service3.getTariff().getCharging().isEmpty())
            );
        }

    }

}