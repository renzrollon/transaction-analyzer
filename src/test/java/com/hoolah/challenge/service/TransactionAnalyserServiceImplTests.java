package com.hoolah.challenge.service;

import com.hoolah.challenge.model.Transaction;
import com.hoolah.challenge.model.TransactionOutput;
import com.hoolah.challenge.model.TransactionQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TransactionAnalyserServiceImplTests {

    @InjectMocks
    TransactionAnalyserServiceImpl transactionAnalyserService;

    @Before
    public void setup(){
        transactionAnalyserService.emptyCache();
    }

    @Test
    public void emptyTransaction_shouldReturn0Average() {
        TransactionQuery query = new TransactionQuery();
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        assertEquals(0, actual.getCount());
        assertEquals(BigDecimal.ZERO, actual.getAverageValue());
    }

    @Test
    public void singleTransaction_shouldReturn1CountAndCorrectAverage() {

        addTransaction("WLMFRDGD", "20/08/2020 12:45:33", "59.99",
                "Kwik-E-Mart", "PAYMENT", null);

        TransactionQuery query = new TransactionQuery();
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        int expectedCount = 1;
        BigDecimal expectedAverageValue = new BigDecimal("59.99");
        assertEquals(expectedCount, actual.getCount());
        assertEquals(expectedAverageValue, actual.getAverageValue());
    }

    @Test
    public void multipleTransactions_shouldReturnCorrectCountAndCorrectAverage() {

        addTransaction("WLMFRDGD", "20/08/2020 12:45:33", "59.99", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("YGXKOEIA", "20/08/2020 12:46:17", "10.95", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("LFVCTEYM", "20/08/2020 12:50:02", "5.00", "MacLaren", "PAYMENT", null);
        addTransaction("SUOVOISP", "20/08/2020 13:12:22", "5.00", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("AKNBVHMN", "20/08/2020 13:14:11", "10.95", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("JYAPKZFZ", "20/08/2020 14:07:10", "99.50", "MacLaren", "PAYMENT", null);

        TransactionQuery query = new TransactionQuery();
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        int expectedCount = 6;
        BigDecimal expectedAverageValue = new BigDecimal("31.90");
        assertEquals(expectedCount, actual.getCount());
        assertEquals(expectedAverageValue, actual.getAverageValue());
    }

    @Test
    public void multipleTransactionsWithReversal_shouldExcludeReversal() {

        addTransaction("WLMFRDGD", "20/08/2020 12:45:33", "59.99", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("YGXKOEIA", "20/08/2020 12:46:17", "10.95", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("LFVCTEYM", "20/08/2020 12:50:02", "5.00", "MacLaren", "PAYMENT", null);
        addTransaction("SUOVOISP", "20/08/2020 13:12:22", "5.00", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("AKNBVHMN", "20/08/2020 13:14:11", "10.95", "Kwik-E-Mart", "REVERSAL", "YGXKOEIA");
        addTransaction("JYAPKZFZ", "20/08/2020 14:07:10", "99.50", "MacLaren", "PAYMENT", null);

        TransactionQuery query = new TransactionQuery();
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        int expectedCount = 4;
        BigDecimal expectedAverageValue = new BigDecimal("42.37");
        assertEquals(expectedCount, actual.getCount());
        assertEquals(expectedAverageValue, actual.getAverageValue());
    }

    @Test
    public void transactionDatesExcludedInDateQuery_shouldReturn0() {

        addTransaction("WLMFRDGD", "20/08/2020 12:45:33", "59.99", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("YGXKOEIA", "20/08/2020 12:46:17", "10.95", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("LFVCTEYM", "20/08/2020 12:50:02", "5.00", "MacLaren", "PAYMENT", null);
        addTransaction("SUOVOISP", "20/08/2020 13:12:22", "5.00", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("AKNBVHMN", "20/08/2020 13:14:11", "10.95", "Kwik-E-Mart", "REVERSAL", "YGXKOEIA");
        addTransaction("JYAPKZFZ", "20/08/2020 14:07:10", "99.50", "MacLaren", "PAYMENT", null);

        TransactionQuery query = new TransactionQuery();
        query.setFromDate("20/09/2020 12:00:00");
        query.setToDate("20/10/2020 12:00:00");
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        int expectedCount = 0;
        BigDecimal expectedAverageValue = BigDecimal.ZERO;
        assertEquals(expectedCount, actual.getCount());
        assertEquals(expectedAverageValue, actual.getAverageValue());
    }

    @Test
    public void transactionDatesIncludedInDateQuery_shouldReturnIncludedTransactionsOnly() {

        addTransaction("WLMFRDGD", "20/08/2020 12:45:33", "59.99", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("YGXKOEIA", "20/08/2020 12:46:17", "10.95", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("LFVCTEYM", "20/08/2020 12:50:02", "5.00", "MacLaren", "PAYMENT", null);
        addTransaction("SUOVOISP", "20/08/2020 13:12:22", "5.00", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("AKNBVHMN", "20/08/2020 13:14:11", "10.95", "Kwik-E-Mart", "REVERSAL", "YGXKOEIA");
        addTransaction("JYAPKZFZ", "20/08/2020 14:07:10", "99.50", "MacLaren", "PAYMENT", null);

        TransactionQuery query = new TransactionQuery();
        query.setFromDate("20/08/2020 12:00:00");
        query.setToDate("20/08/2020 13:00:00");
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        int expectedCount = 2;
        BigDecimal expectedAverageValue = (new BigDecimal("59.99")).add(new BigDecimal("5.0"))
                .divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
        assertEquals(expectedCount, actual.getCount());
        assertEquals(expectedAverageValue, actual.getAverageValue());
    }

    @Test
    public void transactionMerchantIncludedInMerchantQuery_shouldReturnIncludedTransactionsOnly() {

        addTransaction("WLMFRDGD", "20/08/2020 12:45:33", "59.99", "Kwik-E-Mart", "PAYMENT", null);
        addTransaction("JYAPKZFZ", "20/08/2020 14:07:10", "99.50", "MacLaren", "PAYMENT", null);

        TransactionQuery query = new TransactionQuery();
        query.setMerchant("Kwik-E-Mart");
        TransactionOutput actual =  transactionAnalyserService.analyze(query);

        int expectedCount = 1;
        BigDecimal expectedAverageValue = (new BigDecimal("59.99"));
        assertEquals(expectedCount, actual.getCount());
        assertEquals(expectedAverageValue, actual.getAverageValue());
    }

    private void addTransaction(String ...args) {
        Transaction transaction = new Transaction();
        transaction.setId(args[0]);
        transaction.setDate(args[1]);
        transaction.setAmount(args[2]);
        transaction.setMerchant(args[3]);
        transaction.setType(args[4]);
        transaction.setRelatedTransaction(args[5]);

        transactionAnalyserService.addTransaction(transaction);
    }



}
