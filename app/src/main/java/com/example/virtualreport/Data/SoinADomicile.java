package com.example.virtualreport.Data;

public class SoinADomicile {
    String currentDate;
    String currentTime;
    String ambulance;
    String region;
    String patientName;
    String patientAge;
    String patientCase;
    String patientNationality;
    String location;
    String ambulancier;
    String chefDeMission;
    String secouriste1;
    String secouriste2;
    String notes;
    String id;

    public String getId() {
        return id;
    }

    public SoinADomicile()
    {

    }

    public SoinADomicile(String id, String currentDate, String currentTime, String ambulance, String region, String patientName, String patientAge, String patientCase, String patientNationality, String location, String ambulancier, String chefDeMission, String secouriste1, String secouriste2, String notes) {
        this.currentDate=currentDate;
        this.currentTime=currentTime;
        this.ambulance = ambulance;
        this.region = region;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientCase = patientCase;
        this.patientNationality = patientNationality;
        this.location = location;
        this.ambulancier = ambulancier;
        this.chefDeMission = chefDeMission;
        this.secouriste1 = secouriste1;
        this.secouriste2 = secouriste2;
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

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public String getPatientCase() {
        return patientCase;
    }

    public String getPatientNationality() {
        return patientNationality;
    }

    public String getLocation() {
        return location;
    }

    public String getAmbulancier() {
        return ambulancier;
    }

    public String getChefDeMission() {
        return chefDeMission;
    }

    public String getSecouriste1() {
        return secouriste1;
    }

    public String getSecouriste2() {
        return secouriste2;
    }

    public String getNotes() {
        return notes;
    }
}
