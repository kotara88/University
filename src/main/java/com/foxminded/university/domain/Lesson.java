package com.foxminded.university.domain;

import java.util.ArrayList;

public class Lesson {
    private String subject;
    private String classroom;
    private Lecturer lecturer;
    private ArrayList<Student> students;
    private TimePeriod timePeriod;

    public Lesson() {
        students = new ArrayList<Student>();
    }

    public Lesson(String subject, String classroom, Lecturer lecturer, TimePeriod timePeriod) {
        this.subject = subject;
        this.classroom = classroom;
        this.lecturer = lecturer;
        this.timePeriod = timePeriod;
        this.students = new ArrayList<Student>();
    }

    public Lesson(String subject, String classroom, Lecturer lecturer, String startTime, String endTime) {
        this.subject = subject;
        this.classroom = classroom;
        this.lecturer = lecturer;
        this.timePeriod = new TimePeriod(startTime, endTime);
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public String getSubject() {
        return subject;
    }

    public String getClassroom() {
        return classroom;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
