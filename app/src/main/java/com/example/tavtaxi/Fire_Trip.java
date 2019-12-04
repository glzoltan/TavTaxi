package com.example.tavtaxi;

public class Fire_Trip{
    public String id;
    public String from;
    public String where;
    public String when;
    public String freeStates;

    public Fire_Trip(){

    }
    public Fire_Trip(String ID, String FROM, String WHERE, String WHEN) {
        this.id = ID;
        this.from = FROM;
        this.where = WHERE;
        this.when = WHEN;
    }
    public  Fire_Trip(String ID, String FROM, String WHERE, String WHEN, String FREESTATES){
        this.id = ID;
        this.from = FROM;
        this.where = WHERE;
        this.when = WHEN;
        this.freeStates = FREESTATES;
    }
    public String getID() {
        return id;
    }

    public String getFROM() {
        return from;
    }

    public String getWHERE() {
        return where;
    }

    public String getWHEN() {
        return when;
    }

    public String getFreeStates(){return freeStates;}






}

