package com.hoolah.challenge.model;

import java.math.BigDecimal;

public class TransactionOutput {
    int count;
    BigDecimal averageValue;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(BigDecimal averageValue) {
        this.averageValue = averageValue;
    }
}
