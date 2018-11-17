package com.example.fatih.wirelesscomchat.model;



/**
 * Created by vmanohar on 2/27/18.
 */

public class Transaction {


    String name;
    String transactionId;
    String date;
    String transactionAmt;

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

    boolean isCredit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "name='" + name + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", date='" + date + '\'' +
                ", transactionAmt='" + transactionAmt + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionAmt() {
        return transactionAmt;
    }

    public void setTransactionAmt(String transactionAmt) {
        this.transactionAmt = transactionAmt;
    }



}
