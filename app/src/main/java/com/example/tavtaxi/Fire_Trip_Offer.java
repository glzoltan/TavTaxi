package com.example.tavtaxi;

import java.util.ArrayList;

public class Fire_Trip_Offer {
    public String id;
    public String from;
    public String where;
    public String when;
    public ArrayList<String> freeStates= new ArrayList<>();

    public Fire_Trip_Offer(String id, String from, String where, String when, ArrayList<String> freeStates) {
        this.id = id;
        this.from = from;
        this.where = where;
        this.when = when;
        this.freeStates = freeStates;
    }

    public Fire_Trip_Offer() {
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getWhere() {
        return where;
    }

    public String getWhen() {
        return when;
    }


    public ArrayList<String> getFreeStates() {
        return freeStates;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public void setFreeStates(ArrayList<String> freeStates) {
        this.freeStates = freeStates;
    }
}
