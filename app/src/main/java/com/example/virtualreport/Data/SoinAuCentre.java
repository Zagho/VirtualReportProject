package com.example.virtualreport.Data;

public class SoinAuCentre {
    String currentDate;
    String currentTime;
    String patientName;
    String patientAge;
    String patientCase;
    String patientNationality;
    String rescuers;
    String notes;
    String id;

    public String getId() {
        return id;
    }

    public SoinAuCentre()
    {

    }

    public SoinAuCentre(String id, String currentDate, String currentTime, String patientName, String patientAge, String patientCase, String patientNationality, String rescuers, String notes) {
        this.currentDate=currentDate;
        this.currentTime=currentTime;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientCase = patientCase;
        this.patientNationality = patientNationality;
        this.rescuers = rescuers;
        this.notes=notes;
        this.id=id;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
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

    public String getRescuers() {
        return rescuers;
    }

    public String getNotes() {
        return notes;
    }

}
