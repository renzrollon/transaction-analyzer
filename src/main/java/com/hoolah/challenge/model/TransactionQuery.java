package com.hoolah.challenge.model;

import java.time.LocalDateTime;

import static com.hoolah.challenge.model.Transaction.STANDARD_FORMAT;

public class TransactionQuery {

    String fromDate;
    String toDate;
    String merchant;

    LocalDateTime fromDateTime;
    LocalDateTime toDateTime;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
        this.fromDateTime = LocalDateTime.parse(fromDate, STANDARD_FORMAT);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
        this.toDateTime = LocalDateTime.parse(toDate, STANDARD_FORMAT);
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }
}
