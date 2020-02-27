package com.example.spendingtracker.Model;

public class friend {
    String fname;
    String femail;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail;
    }

    public friend(String fname, String femail) {
        this.fname = fname;
        this.femail = femail;
    }
}
