package com.example.spendingtracker.Model;

public class friend {
    String fname;
    String fMoblie;

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


    public friend(String fname, String fMoblie) {
        this.fname = fname;
        this.fMoblie = fMoblie;
    }
}
