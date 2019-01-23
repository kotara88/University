package com.foxminded.university.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class University {

    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    public Student findStudent(String studentFullName) {
        for (Student student : students){
            if ((student.getName() + " " + student.getLastName()).equals(studentFullName)){
                return student;
            }
        }
        return null;
    }

    public Lecturer findLecturer(String lecturerFullName) {
        for (Lecturer lecturer : lecturers){
            if ((lecturer.getName() + " " + lecturer.getLastName()).equals(lecturerFullName)){
                return lecturer;
            }
        }
        return null;
    }

    public void acceptStudent(Student student) {
        students.add(student);
    }

    public void expelStudent(Student student) {
        students.remove(student);
    }

    public void employLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
    }

    public void sackLecturer(Lecturer lecturer) {
        lecturers.remove(lecturer);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public Schedule getSchedule(Date date, Person person) {
        Schedule schedule = new Schedule();
        Calendar searchingDate = Calendar.getInstance();
        searchingDate.setTime(date);
        Calendar lessonTime = Calendar.getInstance();
        for(Lesson lesson : lessons ){
            lessonTime.setTime(lesson.getTimePeriod().getStartDate());
            if (searchingDate.get(Calendar.DAY_OF_MONTH) == lessonTime.get(Calendar.DAY_OF_MONTH) &&
                    searchingDate.get(Calendar.MONTH) == lessonTime.get(Calendar.MONTH) &&
                    searchingDate.get(Calendar.YEAR) == lessonTime.get(Calendar.YEAR)){
                if (person instanceof Student){
                    if (lesson.getStudents().contains(person)){
                        schedule.getAllLesson().add(lesson);
                    }
                }else if (lesson.getLecturer().equals(person)){
                    schedule.getAllLesson().add(lesson);
                }
            }
        }
        return schedule;
    }

    public Schedule getSchedule(TimePeriod timePeriod, Person person) {
        Schedule schedule = new Schedule();

        /*to do something*/

        return schedule;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setLecturers(ArrayList<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }
}
