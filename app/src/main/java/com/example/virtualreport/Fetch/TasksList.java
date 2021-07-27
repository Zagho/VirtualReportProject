package com.example.virtualreport.Fetch;

public class TasksList {
    String Title;
    String Text;
    String RescuerName;
    String Date;
    String CurrentDate;
    String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public TasksList(String title, String text, String rescuerName, String date, String currentDate,String id) {
        Title = title;
        Text = text;
        RescuerName = rescuerName;
        Date = date;
        CurrentDate=currentDate;
        Id=id;
    }

    public TasksList() {
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setRescuerName(String rescuerName) {
        RescuerName = rescuerName;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public String getText() {
        return Text;
    }

    public String getRescuerName() {
        return RescuerName;
    }

    public String getDate() {
        return Date;
    }
}
