package com.hoolah.challenge.service;

import com.hoolah.challenge.model.Transaction;
import com.hoolah.challenge.model.TransactionOutput;
import com.hoolah.challenge.model.TransactionQuery;

public interface TransactionAnalyserService {

    TransactionOutput analyze(TransactionQuery query);

    void addTransaction(Transaction transaction);
    void emptyCache();
}
