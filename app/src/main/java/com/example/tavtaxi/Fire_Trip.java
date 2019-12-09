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

    public  Fire_Trip(String ID, String FROM, String WHERE, String WHEN, String FREESTATES,ArrayList<String> CITIES,String PHONENUMBER ){
        this.id = ID;
        this.from = FROM;
        this.where = WHERE;
        this.when = WHEN;
        this.freeStates = FREESTATES;
        this.cities = CITIES;
        this.phoneNumber=PHONENUMBER;
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

