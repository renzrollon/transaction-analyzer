package com.hoolah.challenge.service;

import com.hoolah.challenge.model.Transaction;
import com.hoolah.challenge.model.TransactionOutput;
import com.hoolah.challenge.model.TransactionQuery;
import com.hoolah.challenge.util.TransactionCache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_UP;

public class TransactionAnalyserServiceImpl implements TransactionAnalyserService {

    public static final String PAYMENT_TYPE = "PAYMENT";
    public static final String REVERSAL_TYPE = "REVERSAL";
    public static final int DEFAULT_DECIMAL_PLACES = 2;

    TransactionCache transactionCache = TransactionCache.getInstance();

    @Override
    public TransactionOutput analyze(TransactionQuery query) {
        TransactionOutput output = new TransactionOutput();
        List<Transaction> includedTransactions = transactionCache.getTransactions().stream()
                .filter(query.getMerchant() != null ?
                        transaction -> transaction.getMerchant().equals(query.getMerchant()) :
                        transaction -> true)
                .filter(query.getFromDateTime() != null ?
                        transaction -> transaction.getDateTime().isAfter(query.getFromDateTime()) :
                        transaction -> true)
                .filter(query.getToDateTime() != null ?
                        transaction -> transaction.getDateTime().isBefore(query.getToDateTime()) :
                        transaction -> true)
                .collect(Collectors.toList());

        output.setCount(includedTransactions.size());
        output.setAverageValue(calculateAverageValue(includedTransactions));

        return output;
    }

    private BigDecimal calculateAverageValue(List<Transaction> transactions) {
        int size = transactions.size();
        if(size == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for(Transaction transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
        }
        return totalAmount.divide(new BigDecimal(size),DEFAULT_DECIMAL_PLACES, HALF_UP);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if(REVERSAL_TYPE.equals(transaction.getType())) {
            Transaction forReversal = new Transaction();
            forReversal.setId(transaction.getRelatedTransaction());
            transactionCache.removeTransaction(forReversal);
        } else {
            transactionCache.addTransaction(transaction);
        }

    }

    @Override
    public void emptyCache() {
        transactionCache.emptyCache();
    }
}
