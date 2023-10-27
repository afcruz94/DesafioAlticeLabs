package org.example.Tariffs;

import org.example.enums.Result;
import org.example.service.tariffs.Tariff;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Beta {
    public static void testBeta1() {
        Integer[] counters = new Integer[]{15, 0, 0};
        Float[] buckets = new Float[]{0f, 0f, 3f};

        // Beta 1 bucketA
        Tariff t1 = new org.example.service.tariffs.Beta(true, false, true, 20, counters, buckets);
        assertEquals("bucketA", t1.getCharging());

        // Beta 1 bucketC
        Tariff t3 = new org.example.service.tariffs.Beta(true, true, false, 20, counters, buckets);
        assertEquals("bucketC", t3.getCharging());

        // Beta 1 bucketB
        counters[1] = 10;
        buckets[2] = 0f;
        Tariff t2 = new org.example.service.tariffs.Beta(true, true, true, 20, counters, buckets);
        assertEquals("bucketB", t2.getCharging());
    }

    public static void testBeta2() {
        Integer[] counters = new Integer[]{0, 0, 0};
        Float[] buckets = new Float[]{0f, 50f, 0f};

        // Beta 2
        Tariff t1 = new org.example.service.tariffs.Beta(false, false, true, 20, counters, buckets);
        assertEquals("bucketB", t1.getCharging());

        Tariff t2 = new org.example.service.tariffs.Beta(false, false, true, 10000, counters, buckets);
        assertEquals(Result.CREDIT_LIMIT_REACHED,t2.getResult());
    }

    public static void testBeta3() {
        Integer[] counters = new Integer[]{0, 20, 0};
        Float[] buckets = new Float[]{0f, 20f, 30f};

        // Beta 3
        Tariff t1 = new org.example.service.tariffs.Beta(true, true, true, 20, counters, buckets);
        assertEquals("bucketC", t1.getCharging());

        Tariff t2 = new org.example.service.tariffs.Beta(true, true, false, 10000, counters, buckets);
        assertEquals(Result.CREDIT_LIMIT_REACHED,t2.getResult());
    }
}
