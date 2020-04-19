package com.example.spendingtracker.Model;

import java.util.List;

public class AddExpenses {
    String description;
    String amount;
    List<CharSequence> payer;
    List<String> paid;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddExpenses(String description, String amount, List<CharSequence> payer, List<String> paid) {
        this.description = description;
        this.amount = amount;
        this.payer = payer;
        this.paid = paid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public AddExpenses(String description, String amount) {
        this.description = description;
        this.amount = amount;
    }

    public List<CharSequence> getPayer() {
        return payer;
    }

    public void setPayer(List<CharSequence> payer) {
        this.payer = payer;
    }

    public List<String> getPaid() {
        return paid;
    }

    public void setPaid(List<String> paid) {
        this.paid = paid;
    }
}
