package com.foxminded.university.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class University {

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();

    private static Logger log = LogManager.getLogger(University.class.getName());

    public Student findStudent(Student searchingStudent) {
        log.info("Finding student");
        if (searchingStudent == null) {
            log.error("Searching student can't be null");
            throw new IllegalArgumentException("Searching student can't be null");
        }

        return (Student) findPerson(searchingStudent, students);
    }

    public Lecturer findLecturer(Lecturer searchingLecturer) {
        log.info("Finding lecturer");
        if (searchingLecturer == null) {
            log.error("Searching lecturer can't be null");
            throw new IllegalArgumentException("Searching lecturer can't be null");
        }

        return (Lecturer) findPerson(searchingLecturer, lecturers);
    }

    private Person findPerson(Person searchingPerson, ArrayList<? extends Person> list) {
        for (Person person : list) {
            log.trace("Comparing persons");
            if (person.equals(searchingPerson)) {
                log.trace("Return found person id = {}", person.getId());
                return person;
            }
        }
        log.debug("Person wasn't found");
        return null;
    }

    public void acceptStudent(Student student) {
        log.info("Accepting students");
        if (student == null) {
            log.error("Accepting student can't be null");
            throw new IllegalArgumentException("Accepting student can't be null");
        }

        if (!students.contains(student)) {
            log.trace("Adding student id = {}", student.getId());
            students.add(student);
        }
    }

    public void expelStudent(Student student) {
        log.info("Expelling student");
        if (student == null) {
            log.error("Expelling student can't be null");
            throw new IllegalArgumentException("Expelling student can't be null");
        }

        for (Lesson lesson : lessons) {
            if (lesson.getStudents().equals(student)) {
                log.trace("Remove student id = {} from students list of lessons", student.getId());
                lesson.getStudents().remove(student);
            }
        }
        log.trace("Remove student id = {} from students list of university", student.getId());
        students.remove(student);
    }

    public void employLecturer(Lecturer lecturer) {
        log.info("Employing lecturer");
        if (lecturer == null) {
            log.error("Employing lecturer can't be null");
            throw new IllegalArgumentException("Employing lecturer can't be null");
        }

        if (!lecturers.contains(lecturer)) {
            log.trace("Adding lecturer id = {}", lecturer.getId());
            lecturers.add(lecturer);
        }
    }

    public void sackLecturer(Lecturer lecturer) {
        log.info("Sacking lecturer");
        if (lecturer == null) {
            log.error("Sacking lecturer can't be null");
            throw new IllegalArgumentException("Sacking lecturer can't be null");
        }

        for (Lesson lesson : new ArrayList<Lesson>(lessons)) {
            if (lesson.getLecturer().equals(lecturer)) {
                log.error("The sacking lecturer id = {} has scheduled lectures", lecturer.getId());
                throw new IllegalArgumentException("The sacking lecturer id = " + lecturer.getId() + " has scheduled lectures");
            }
        }
        log.trace("Removing lecturer id = {}", lecturer.getId());
        lecturers.remove(lecturer);
    }

    public void addLesson(Lesson addingLesson) {
        log.info("Adding lesson id = {}", addingLesson.getId());
        if (lessons.size() > 0) {
            for (Lesson lesson : lessons) {
                if (isLessonHasSameClassroomAndTimeLesson(lesson, addingLesson) ||
                        isLessonHasSameLecturerAndTimeLesson(lesson, addingLesson)) {
                    log.error("Adding lesson can't be added. It has the same classroom or time period with lesson id = {}", lesson.getId());
                    throw new IllegalArgumentException("Adding lesson can't be added. It has the same classroom or time period with lesson id = " + lesson.getId());
                }
            }
            log.trace("Adding lesson id = {}", addingLesson.getId());
            lessons.add(addingLesson);
        } else {
            log.trace("Adding lesson id = {}", addingLesson.getId());
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
        log.info("Removing lesson id = {}", lesson.getId());
        lessons.remove(lesson);
    }

    public Schedule getSchedule(Date date, Person person) {
        log.info("Getting day schedule for person id = {}", person.getId());
        Schedule schedule = new Schedule();
        Date lessonDate;
        for (Lesson lesson : lessons) {
            lessonDate = removeTimeInDate(lesson.getTimePeriod().getStartTime());
            if (lessonDate.equals(date)) {
                addLessonToSchedule(lesson, person, schedule);
            }
        }
        log.trace("Return schedule");
        return schedule;
    }

    private Date removeTimeInDate(Date date) {
        log.info("Removing time from lesson date");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        log.trace("Return date without time");
        return calendar.getTime();
    }

    public Schedule getSchedule(TimePeriod timePeriod, Person person) {
        log.info("Getting time period schedule for {} id = {}", person.getClass().getSimpleName(), person.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timePeriod.getEndTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Date endTimePeriod = calendar.getTime();
        Schedule schedule = new Schedule();
        log.trace("Searching lessons for {} id = {}", person.getClass().getSimpleName(), person.getId());
        for (Lesson lesson : lessons) {
            if (lesson.getTimePeriod().getStartTime().after(timePeriod.getStartTime()) &&
                    lesson.getTimePeriod().getStartTime().before(endTimePeriod)) {
                schedule = addLessonToSchedule(lesson, person, schedule);
            }
        }
        return schedule;
    }

    private Schedule addLessonToSchedule(Lesson lesson, Person person, Schedule schedule) {
        log.info("Adding lesson to schedule");
        if (person instanceof Student) {
            if (lesson.getStudents().contains(person)) {
                schedule.getAllLesson().add(lesson);
            }
        } else if (lesson.getLecturer().equals(person)) {
            schedule.getAllLesson().add(lesson);
        }
        log.trace("Return schedule");
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

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setLecturers(ArrayList<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
}
