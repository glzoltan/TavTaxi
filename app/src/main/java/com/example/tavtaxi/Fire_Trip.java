package com.example.tavtaxi;

public class Fire_Trip {
    private String ID;
    private String FROM;
    private String WHERE;
    private String WHEN;
    public Fire_Trip(String ID, String FROM, String WHERE, String WHEN) {
        this.ID = ID;
        this.FROM = FROM;
        this.WHERE = WHERE;
        this.WHEN = WHEN;
    }
    public String getID() {
        return ID;
    }

    public String getFROM() {
        return FROM;
    }

    public String getWHERE() {
        return WHERE;
    }

    public String getWHEN() {
        return WHEN;
    }






}

