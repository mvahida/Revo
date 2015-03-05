package com.example.mitra.revo;

/**
 * Created by Mitra on 3/4/2015.
 */
public class Message {
    private int id;
    private String title;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String location;
    private String repeatType;
    private String notificationIntervals;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String newTitle) { this.title = newTitle; }

    public String getTitle() { return title; }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom (String newDate) {
        this.dateFrom = newDate;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo (String newDate) {
        this.dateTo = newDate;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom (String newTime) {
        this.timeFrom = newTime;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo (String newTime) {
        this.timeTo = newTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    public void setRepeatType(String newLocation) {
        this.repeatType = newLocation;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setNotificationIntervals(String newLocation) {
        this.notificationIntervals = newLocation;
    }

    public String getNotificationIntervals() {
        return notificationIntervals;
    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return title;
    }
}
