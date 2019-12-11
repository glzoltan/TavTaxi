package com.example.tavtaxi;

import java.util.ArrayList;

public class Fire_Trip{
    public String id;
    public String from;
    public String where;
    public String when;
    public String freeStates;
    public ArrayList<String> cities= new ArrayList<>();
    public String phoneNumber;

    public Fire_Trip(){

    }

    public  Fire_Trip(String id, String from, String where, String when, String freeStates,ArrayList<String> cities,String phoneNumber ){
        this.id = id;
        this.from = from;
        this.where = where;
        this.when = when;
        this.freeStates = freeStates;
        this.cities = cities;
        this.phoneNumber=phoneNumber;
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

    public String getFreeStates(){return freeStates;}

    public ArrayList<String> getCities() {
        return cities;
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

    public void setFreeStates(String freeStates) {
        this.freeStates = freeStates;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

