package com.foxminded.university.domain;

import java.util.ArrayList;

public class Lesson {
    private String lessonName;
    private String classroom;
    private Lecturer lecturer;
    private ArrayList<Student> students;

    private TimePeriod timePeriod;

    public Lesson(String lessonName, String classroom, Lecturer lecturer, TimePeriod timePeriod) {
        this.lessonName = lessonName;
        this.classroom = classroom;
        this.lecturer = lecturer;
        this.timePeriod = timePeriod;
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public String getLessonName() {
        return lessonName;
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

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
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
