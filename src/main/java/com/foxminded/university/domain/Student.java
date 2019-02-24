package com.foxminded.university.domain;

public class Student extends Person {

    private String studentGroup;

    public Student(){}

    public Student(String name, String lastName, int age, String studentGroup) {
        super(name, lastName, age);
        this.studentGroup = studentGroup;
    }

    public Student(long id, String name, String lastName, int age, String studentGroup) {
        super(id, name, lastName, age);
        this.studentGroup = studentGroup;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Student student = (Student) o;

        return studentGroup != null ? studentGroup.equals(student.studentGroup) : student.studentGroup == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (studentGroup != null ? studentGroup.hashCode() : 0);
        return result;
    }
}
