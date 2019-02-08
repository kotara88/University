package com.foxminded.university.domain;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Lesson> lessons;

    public Schedule() {
        lessons = new ArrayList<Lesson>();
    }

    public ArrayList<Lesson> getAllLesson(){
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
}
