package com.example.a6sigma.great4ip.Model;

/**
 * Created by Res Non Verba on 02/05/2017.
 */

public class ReminderModel {
    private String event, location, reminder_id, reminder_time, share, time;

    public ReminderModel() {
    }

    public ReminderModel(String event, String location, String reminder_id, String reminder_time, String share, String time) {
        this.event = event;
        this.location = location;
        this.reminder_id = reminder_id;
        this.reminder_time = reminder_time;
        this.share = share;
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(String reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
