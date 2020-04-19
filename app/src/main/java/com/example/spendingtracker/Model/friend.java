package com.example.spendingtracker.Model;

import java.util.List;

public class friend {
    String fname;
    String fMoblie;
    int paid=0;

    public friend(int paid) {
        this.paid = paid;
    }

    int expense=0;



    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = 0;
    }

    public friend(String fname, String fMoblie, int paid, int expense) {
        this.fname = fname;
        this.fMoblie = fMoblie;
        this.paid = paid;
        this.expense = expense;
    }


    public String getfMoblie() {
        return fMoblie;
    }

    public void setfMoblie(String fMoblie) {
        this.fMoblie = fMoblie;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = 0;
    }
}
