package com.hoolah.challenge.util;

import com.hoolah.challenge.model.Transaction;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CsvLoaderTest {


    @Test
    public void validCsvFile_shouldReadSuccessfully() {

        List<Transaction> transactions = CsvLoader.loadCsv(Transaction.class, "input.csv");
        assertFalse(transactions.isEmpty());
    }
}