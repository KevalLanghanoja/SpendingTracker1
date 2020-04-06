package com.example.spendingtracker.Model;


public class AddExpenses {
    String description;
    String amount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
