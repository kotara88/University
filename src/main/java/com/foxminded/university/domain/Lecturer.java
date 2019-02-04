package com.foxminded.university.domain;

public class Lecturer extends Person{

    private String department;

    public Lecturer(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Lecturer lecturer = (Lecturer) o;

        return department != null ? department.equals(lecturer.department) : lecturer.department == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }
}
