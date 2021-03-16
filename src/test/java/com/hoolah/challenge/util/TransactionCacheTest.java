package com.hoolah.challenge.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionCacheTest {

    @Test
    public void loadCacheFromCsv_shouldReturnCorrectCount() {
        TransactionCache transactionCache = TransactionCache.getInstance();
        int actual = transactionCache.loadCache();
        int expected = 6;

        assertEquals(expected, actual);
        assertEquals(expected, transactionCache.size());
    }
}
