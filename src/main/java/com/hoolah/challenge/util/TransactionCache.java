package com.hoolah.challenge.util;

import com.hoolah.challenge.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public final class TransactionCache {

    final static String INPUT_FILE_NAME = "input.csv";
    List<Transaction> transactionCache = new ArrayList<>();

    private TransactionCache (){}

    private static class InstanceHolder {
        public static TransactionCache INSTANCE = new TransactionCache();
    }

    public static TransactionCache getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void addTransaction(Transaction transaction) {
        transactionCache.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactionCache.remove(transaction);
    }

    public int size() {
        return transactionCache.size();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactionCache);
    }

    public void emptyCache() {
        transactionCache.clear();
    }

    public int loadCache() {
        transactionCache = CsvLoader.loadCsv(Transaction.class, INPUT_FILE_NAME);
        return transactionCache.size();
    }
}
