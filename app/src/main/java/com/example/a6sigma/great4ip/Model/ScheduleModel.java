package com.example.a6sigma.great4ip.Model;

/**
 * Created by Res Non Verba on 01/05/2017.
 */

public class ScheduleModel {
    private String classRoom, class_location, day, key, schedule_id, time, course_id;

    public ScheduleModel() {
    }

    public ScheduleModel(String classRoom, String class_location, String day, String key, String schedule_id, String time, String course_id) {
        this.classRoom = classRoom;
        this.class_location = class_location;
        this.day = day;
        this.key = key;
        this.schedule_id = schedule_id;
        this.time = time;
        this.course_id = course_id;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getClass_location() {
        return class_location;
    }

    public void setClass_location(String class_location) {
        this.class_location = class_location;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
