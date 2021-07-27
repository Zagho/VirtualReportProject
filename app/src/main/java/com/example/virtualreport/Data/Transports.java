package com.example.virtualreport.Data;

public class Transports {
    String currentDate;
    String currentTime;
    String ambulance;
    String region;
    String patientName;
    String patientAge;
    String patientCase;
    String patientNationality;
    String companion;
    String from;
    String to;
    String ambulancier;
    String chefDeMission;
    String secouriste1;
    String secouriste2;
    String writtenReportCode;
    String notes;
    String id;



    public Transports()
    {

    }

    public Transports(String id, String currentDate, String currentTime, String ambulance, String region, String patientName, String patientAge, String patientCase, String patientNationality, String companion, String from, String to, String ambulancier, String chefDeMission, String secouriste1, String secouriste2, String writtenReportCode, String notes) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.ambulance = ambulance;
        this.region = region;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientCase = patientCase;
        this.patientNationality = patientNationality;
        this.companion = companion;
        this.from = from;
        this.to = to;
        this.ambulancier = ambulancier;
        this.chefDeMission = chefDeMission;
        this.secouriste1 = secouriste1;
        this.secouriste2 = secouriste2;
        this.writtenReportCode = writtenReportCode;
        this.notes = notes;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setAmbulance(String ambulance) {
        this.ambulance = ambulance;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public void setPatientCase(String patientCase) {
        this.patientCase = patientCase;
    }

    public void setPatientNationality(String patientNationality) {
        this.patientNationality = patientNationality;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setAmbulancier(String ambulancier) {
        this.ambulancier = ambulancier;
    }

    public void setChefDeMission(String chefDeMission) {
        this.chefDeMission = chefDeMission;
    }

    public void setSecouriste1(String secouriste1) {
        this.secouriste1 = secouriste1;
    }

    public void setSecouriste2(String secouriste2) {
        this.secouriste2 = secouriste2;
    }

    public void setWrittenReportCode(String writtenReportCode) {
        this.writtenReportCode = writtenReportCode;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
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

    public String getCompanion() {
        return companion;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
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

    public String getWrittenReportCode() {
        return writtenReportCode;
    }

    public String getNotes() {
        return notes;
    }
}
