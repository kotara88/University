package com.foxminded.university.domain;

public class Lecturer extends Person{

    private String department;

    public Lecturer(String name, String lastName, int age, String department) {
        super(name, lastName, age);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
