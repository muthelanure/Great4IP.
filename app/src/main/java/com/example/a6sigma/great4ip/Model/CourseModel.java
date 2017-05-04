package com.example.a6sigma.great4ip.Model;

/**
 * Created by Res Non Verba on 04/05/2017.
 */

public class CourseModel {
    private String course_id, course_name;

    public CourseModel() {
    }

    public CourseModel(String course_id, String course_name) {
        this.course_id = course_id;
        this.course_name = course_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
