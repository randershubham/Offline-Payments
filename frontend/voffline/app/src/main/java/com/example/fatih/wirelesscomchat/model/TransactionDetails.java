
package com.example.fatih.wirelesscomchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails {

    @SerializedName("amount")
    @Expose
    private String amount;
    private String id;
    private String date;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "amount='" + amount + '\'' +
                ", id='" + id + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
