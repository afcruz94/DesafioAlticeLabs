package org.example.Tariffs;

import org.example.enums.Result;
import org.example.service.tariffs.Tariff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Alpha {
    public static void testAlpha1() {
        Integer[] counters = new Integer[]{15, 0, 0};
        Float[] buckets = new Float[]{0f, 0f, 3f};

        // Alpha 1 bucketA
        Tariff t1 = new org.example.service.tariffs.Alpha(true, false, false, 20, counters, buckets);
        assertTrue(t1.getRating() > 0);
        assertEquals("bucketA", t1.getCharging());

        // Alpha 1 bucketC
        Tariff t3 = new org.example.service.tariffs.Alpha(true, true, false, 20, counters, buckets);
        assertTrue(t3.getRating() > 0);
        assertEquals("bucketC", t3.getCharging());

        // Alpha 1 bucketB
        counters[1] = 10;
        buckets[2] = 0f;
        Tariff t2 = new org.example.service.tariffs.Alpha(true, true, true, 20, counters, buckets);
        assertTrue(t2.getRating() > 0);
        assertEquals("bucketB", t2.getCharging());
    }

    public static void testAlpha2() {
        Integer[] counters = new Integer[]{0, 0, 0};
        Float[] buckets = new Float[]{0f, 50f, 0f};

        // Alpha 2
        Tariff t1 = new org.example.service.tariffs.Alpha(false, false, true, 20, counters, buckets);
        assertTrue(t1.getRating() > 0);
        assertEquals("bucketB", t1.getCharging());

        Tariff t2 = new org.example.service.tariffs.Alpha(false, false, true, 10000, counters, buckets);
        assertTrue(t2.getRating() > 0);
        assertEquals(Result.CREDIT_LIMIT_REACHED,t2.getResult());
    }

    public static void testAlpha3() {
        Integer[] counters = new Integer[]{0, 20, 0};
        Float[] buckets = new Float[]{0f, 20f, 30f};

        // Alpha 3
        Tariff t1 = new org.example.service.tariffs.Alpha(true, true, false, 20, counters, buckets);
        assertTrue(t1.getRating() > 0);
        assertEquals("bucketC", t1.getCharging());

        Tariff t2 = new org.example.service.tariffs.Alpha(true, true, false, 10000, counters, buckets);
        assertTrue(t2.getRating() > 0);
        assertEquals(Result.CREDIT_LIMIT_REACHED,t2.getResult());
    }
}
