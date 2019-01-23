package com.foxminded.university.domain;

public class Student extends Person {

    private String group;

    public Student(String name, String lastName, int age, String group) {
        super(name, lastName, age);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
