package com.example.spendingtracker.Model;

public class ExpenseListRetrieve {

    private int amount;
    private String description;

    public ExpenseListRetrieve() {
    }
    public ExpenseListRetrieve(int amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
