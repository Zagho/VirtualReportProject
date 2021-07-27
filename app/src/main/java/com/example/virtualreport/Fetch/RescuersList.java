package com.example.virtualreport.Fetch;

public class RescuersList {
    private String RescuerName;
    private Integer Day,RenewDay;
    private Integer Month,RenewMonth;
    private Integer Year,RenewYear;
    private Integer ReservisteRemaining;
    private String IsCurrentlyReserviste;
    private String IsLongReserviste;
    private Integer Promo;
    private String FullName;
    private String Ambulancier;
    private String Cm;
    private String Dossard;
    private String Location;
    private String PhoneNumber;

    public String getAmbulancier() {
        return Ambulancier;
    }

    public void setAmbulancier(String ambulancier) {
        Ambulancier = ambulancier;
    }

    public void setCm(String cm) {
        Cm = cm;
    }

    public void setDossard(String dossard) {
        Dossard = dossard;
    }

    public String getCm() {
        return Cm;
    }

    public String getDossard() {
        return Dossard;
    }

    public Integer getPromo() {
        return Promo;
    }

    public String getFullName() {
        return FullName;
    }

    public void setPromo(Integer promo) {
        Promo = promo;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public RescuersList(Integer day, Integer renewDay, Integer month, Integer renewMonth, Integer year, Integer renewYear, Integer reservisteRemaining) {
        Day = day;
        RenewDay = renewDay;
        Month = month;
        RenewMonth = renewMonth;
        Year = year;
        RenewYear = renewYear;
        ReservisteRemaining = reservisteRemaining;
    }



    public RescuersList(String rescuerName, Integer day, Integer renewDay, Integer month, Integer renewMonth, Integer year, Integer renewYear, Integer reservisteRemaining, String isCurrentlyReserviste, String isLongReserviste, String location, String phoneNumber,Integer promo,String fullName,String ambulancier,String cm, String dossard) {
        RescuerName = rescuerName;
        Day = day;
        RenewDay = renewDay;
        Month = month;
        RenewMonth = renewMonth;
        Year = year;
        RenewYear = renewYear;
        ReservisteRemaining = reservisteRemaining;
        IsCurrentlyReserviste = isCurrentlyReserviste;
        IsLongReserviste = isLongReserviste;
        Location=location;
        PhoneNumber=phoneNumber;
        Promo=promo;
        FullName=fullName;
        Ambulancier=ambulancier;
        Cm=cm;
        Dossard=dossard;
    }

    public RescuersList(String rescuerName, Integer day, Integer renewDay, Integer month, Integer renewMonth, Integer year, Integer renewYear, Integer reservisteRemaining, String isCurrentlyReserviste, String isLongReserviste) {
        RescuerName = rescuerName;
        Day = day;
        RenewDay = renewDay;
        Month = month;
        RenewMonth = renewMonth;
        Year = year;
        RenewYear = renewYear;
        ReservisteRemaining = reservisteRemaining;
        IsCurrentlyReserviste = isCurrentlyReserviste;
        IsLongReserviste = isLongReserviste;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLocation() {
        return Location;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setIsLongReserviste(String isLongReserviste) {
        IsLongReserviste = isLongReserviste;
    }

    public String getIsLongReserviste() {
        return IsLongReserviste;
    }

    public void setRenewDay(Integer renewDay) {
        RenewDay = renewDay;
    }

    public void setRenewMonth(Integer renewMonth) {
        RenewMonth = renewMonth;
    }

    public void setRenewYear(Integer renewYear) {
        RenewYear = renewYear;
    }

    public Integer getRenewDay() {
        return RenewDay;
    }

    public Integer getRenewMonth() {
        return RenewMonth;
    }

    public Integer getRenewYear() {
        return RenewYear;
    }

    public void setRescuerName(String rescuerName) {
        RescuerName = rescuerName;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public void setMonth(Integer month) {
        Month = month;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public void setReservisteRemaining(Integer reservisteRemaining) {
        ReservisteRemaining = reservisteRemaining;
    }

    public void setIsCurrentlyReserviste(String isCurrentlyReserviste) {
        IsCurrentlyReserviste = isCurrentlyReserviste;
    }

    public String getRescuerName() {
        return RescuerName;
    }

    public Integer getDay() {
        return Day;
    }

    public Integer getMonth() {
        return Month;
    }

    public Integer getYear() {
        return Year;
    }

    public Integer getReservisteRemaining() {
        return ReservisteRemaining;
    }

    public String getIsCurrentlyReserviste() {
        return IsCurrentlyReserviste;
    }

    public RescuersList() {

    }
}
