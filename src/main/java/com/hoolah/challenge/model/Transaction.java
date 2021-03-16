package com.hoolah.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    public static final DateTimeFormatter STANDARD_FORMAT=
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @JsonProperty("ID")
    String id;
    @JsonProperty("Date")
    String date;
    @JsonProperty("Amount")
    BigDecimal amount;
    @JsonProperty("Merchant")
    String merchant;
    @JsonProperty("Type")
    String type;
    @JsonProperty("Related Transaction")
    String relatedTransaction;

    LocalDateTime dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        this.dateTime = LocalDateTime.parse(date, STANDARD_FORMAT);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedTransaction() {
        return relatedTransaction;
    }

    public void setRelatedTransaction(String relatedTransaction) {
        this.relatedTransaction = relatedTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
