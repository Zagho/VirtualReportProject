package com.example.virtualreport.Data;

public class Poste {
    String currentDate;
    String currentTime;
    String ambulance;
    String region;
    String posteLocation;
    String posteType;
    String date1;
    String time1;
    String date2;
    String time2;
    String team;
    String notes;
    String id;

    public String getId() {
        return id;
    }

    public Poste()
    {

    }

    public Poste(String id, String currentDate, String currentTime, String ambulance, String region, String posteLocation, String posteType, String date1, String time1, String date2, String time2, String team, String notes) {
        this.currentDate=currentDate;
        this.currentTime=currentTime;
        this.ambulance = ambulance;
        this.region = region;
        this.posteLocation = posteLocation;
        this.posteType = posteType;
        this.date1 = date1;
        this.time1 = time1;
        this.date2 = date2;
        this.time2 = time2;
        this.team = team;
        this.notes=notes;
        this.id=id;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getAmbulance() {
        return ambulance;
    }

    public String getRegion() {
        return region;
    }

    public String getPosteLocation() {
        return posteLocation;
    }

    public String getPosteType() {
        return posteType;
    }

    public String getDate1() {
        return date1;
    }

    public String getTime1() {
        return time1;
    }

    public String getDate2() {
        return date2;
    }

    public String getTime2() {
        return time2;
    }

    public String getTeam() {
        return team;
    }

    public String getNotes() {
        return notes;
    }
}
