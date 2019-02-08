package com.foxminded.university.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class University {

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();

    public Student findStudent(Student searchingStudent) {
        return (Student) findPerson(searchingStudent, students);
    }

    public Lecturer findLecturer(Lecturer searchingLecturer) {
        return (Lecturer) findPerson(searchingLecturer, lecturers);
    }

    private Person findPerson(Person searchingPerson, ArrayList<? extends Person> list) {
        for (Person person : list) {
            if (person.equals(searchingPerson)) {
                return person;
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
        if (!lecturers.contains(lecturer)) {
            lecturers.add(lecturer);
        }

    }

    public void sackLecturer(Lecturer lecturer) {
        for (Lesson lesson : new ArrayList<Lesson>(lessons)) {
            if (lesson.getLecturer().equals(lecturer)) {
                lessons.remove(lesson);
            }
        }
        lecturers.remove(lecturer);
    }

    public void addLesson(Lesson addingLesson) {
        if (lessons.size() > 0) {
            for (Lesson lesson : lessons) {
                if (isLessonHasSameClassroomAndTimeLesson(lesson, addingLesson) ||
                        isLessonHasSameLecturerAndTimeLesson(lesson, addingLesson)) {
                    return;
                }
            }
            lessons.add(addingLesson);
        } else {
            lessons.add(addingLesson);
        }
    }

    private boolean isLessonHasSameClassroomAndTimeLesson(Lesson lesson, Lesson addingLesson) {
        return lesson.getClassroom().equals(addingLesson.getClassroom()) && lesson.getTimePeriod().equals(addingLesson.getTimePeriod());
    }

    private boolean isLessonHasSameLecturerAndTimeLesson(Lesson lesson, Lesson addingLesson) {
        return lesson.getLecturer().equals(addingLesson.getLecturer()) && lesson.getTimePeriod().equals(addingLesson.getTimePeriod());
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public Schedule getSchedule(Date date, Person person) {
        Schedule schedule = new Schedule();
        Date lessonDate;
        for (Lesson lesson : lessons) {
            lessonDate = removeTimeInDate(lesson.getTimePeriod().getStartTime());
            if (lessonDate.equals(date)) {
                addLessonToSchedule(lesson, person, schedule);
            }
        }
        return schedule;
    }

    private Date removeTimeInDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Schedule getSchedule(TimePeriod timePeriod, Person person) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timePeriod.getEndTine());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Date endTimePeriod = calendar.getTime();
        Schedule schedule = new Schedule();
        for (Lesson lesson : lessons) {
            if (lesson.getTimePeriod().getStartTime().after(timePeriod.getStartTime()) &&
                    lesson.getTimePeriod().getStartTime().before(endTimePeriod)) {
                schedule = addLessonToSchedule(lesson, person, schedule);
            }
        }
        return schedule;
    }

    private Schedule addLessonToSchedule(Lesson lesson, Person person, Schedule schedule) {
        if (person instanceof Student) {
            if (lesson.getStudents().contains(person)) {
                schedule.getAllLesson().add(lesson);
            }
        } else if (lesson.getLecturer().equals(person)) {
            schedule.getAllLesson().add(lesson);
        }
        return schedule;
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
