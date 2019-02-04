package com.foxminded.university.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class University {

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();

    public Student findStudent(String studentName, String studentLastName) {
        for (Student student : students) {
            if (student.getName().equals(studentName) && student.getLastName().equals(studentLastName)) {
                return student;
            }
        }
        return null;
    }

    public Lecturer findLecturer(String lecturerName, String lecturerLastName) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getName().equals(lecturerName) && lecturer.getLastName().equals(lecturerLastName)) {
                return lecturer;
            }
        }
        return null;
    }

    public void acceptStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void expelStudent(Student student) {
        for (Lesson lesson : lessons) {
            if (lesson.getStudents().equals(student)) {
                lesson.getStudents().remove(student);
            }
        }
        students.remove(student);
    }

    public void employLecturer(Lecturer lecturer) {
        if (lecturers.contains(lecturer)) {
            lecturers.add(lecturer);
        }

    }

    public void sackLecturer(Lecturer lecturer) {
        for (Lesson lesson : lessons) {
            if (lesson.getLecturer().equals(lecturer)) {
                lessons.remove(lesson);
            }
        }
        lecturers.remove(lecturer);
    }

    public void addLesson(Lesson addingLesson) {
        if (lessons.size() > 0) {
            for (int i = 0; i < lessons.size(); i++) {
                if (!isSameClassroomAndTimeLesson(lessons.get(i), addingLesson)) {
                    if (!isSameLecturerAndTimeLesson(lessons.get(i), addingLesson)) {
                        lessons.add(addingLesson);
                        break;
                    }
                }
            }
        } else {
            lessons.add(addingLesson);
        }
    }

    private boolean isSameClassroomAndTimeLesson(Lesson lesson, Lesson addingLesson) {
        return lesson.getClassroom().equals(addingLesson.getClassroom()) && lesson.getTimePeriod().equals(addingLesson.getTimePeriod());
    }

    private boolean isSameLecturerAndTimeLesson(Lesson lesson, Lesson addingLesson) {
        return lesson.getLecturer().equals(addingLesson.getLecturer()) && lesson.getTimePeriod().equals(addingLesson.getTimePeriod());
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public Schedule getSchedule(Date date, Person person) {
        Schedule schedule = new Schedule();
        for (Lesson lesson : lessons) {
            if (isSameDate(date, lesson.getTimePeriod().getStartTime())) {
                addLessonToSchedule(lesson, person, schedule);
            }
        }
        return schedule;
    }

    private boolean isSameDate(Date firstDate, Date secondDate) {
        Calendar searchingDate = Calendar.getInstance();
        searchingDate.setTime(firstDate);
        Calendar lessonTime = Calendar.getInstance();
        lessonTime.setTime(secondDate);
        return searchingDate.get(Calendar.DAY_OF_MONTH) == lessonTime.get(Calendar.DAY_OF_MONTH) &&
                searchingDate.get(Calendar.MONTH) == lessonTime.get(Calendar.MONTH) &&
                searchingDate.get(Calendar.YEAR) == lessonTime.get(Calendar.YEAR);
    }

    public Schedule getSchedule(TimePeriod timePeriod, Person person) {
        Schedule schedule = new Schedule();
        for (Lesson lesson : lessons) {
            if (lesson.getTimePeriod().getStartTime().after(timePeriod.getStartTime()) &&
                    lesson.getTimePeriod().getStartTime().before(timePeriod.getEndTine())) {
                addLessonToSchedule(lesson, person, schedule);
            }
        }
        return schedule;
    }

    private void addLessonToSchedule(Lesson lesson, Person person, Schedule schedule) {
        if (person instanceof Student) {
            if (lesson.getStudents().contains(person)) {
                schedule.getAllLesson().add(lesson);
            }
        } else if (lesson.getLecturer().equals(person)) {
            schedule.getAllLesson().add(lesson);
        }
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
