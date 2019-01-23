package com.foxminded.university.domain;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    public ArrayList<Lesson> getAllLesson(){
        return lessons;
    }
}
