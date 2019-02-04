package com.foxminded.university.domain;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class UniversityTest {
    private University university;

    private Student firstStudent;
    private Student secondStudent;
    private Student thirdStudent;

    private Lecturer mathLecturer;
    private Lecturer programmingLecturer;
    private Lecturer foreignLanguagesLecturer;

    private Lesson mathLesson;
    private Lesson programmingLesson;
    private Lesson englishLanguagesLesson;
    private Lesson discreteMathLesson;
    private Lesson algorithmsLesson;
    private Lesson spanishLesson;
    private Lesson geometryLesson;
    private Lesson databasesLesson;
    private Lesson frenchLesson;

    private Lesson lessonThatHasSameTimeAndSameClassroomWithMathLesson;
    private Lesson lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson;

    @Before
    public void initialize() {
        university = new University();

        firstStudent = new Student("Patrick", "Kluivert", 19, "vm-41");
        secondStudent = new Student("Jack", "Nicholson", 17, "vm-21");
        thirdStudent = new Student("Marlon", "Brando", 18, "vm-31");

        mathLecturer = new Lecturer("Tom", "Hanks", 56, "computer engineering");
        programmingLecturer = new Lecturer("Denzel", "Washington", 43, "computer engineering");
        foreignLanguagesLecturer = new Lecturer("Morgan", "Freeman", 39, "foreign languages");

        mathLesson = new Lesson("Mathematics", "1", mathLecturer, "04-01-2019 08:00", "04-01-2019 09:35");
        mathLesson.addStudent(firstStudent);
        mathLesson.addStudent(secondStudent);
        programmingLesson = new Lesson("Programming Languages", "2", programmingLecturer, "04-01-2019 09:45", "04-01-2019 11:20");
        programmingLesson.addStudent(firstStudent);
        englishLanguagesLesson = new Lesson("English", "3", foreignLanguagesLecturer, "04-01-2019 11:30", "04-01-2019 13:05");
        discreteMathLesson = new Lesson("Discrete mathematics", "2", mathLecturer, "05-01-2019 08:00", "05-01-2019 09:35");
        algorithmsLesson = new Lesson("Algorithms", "2", programmingLecturer, "05-01-2019 09:45", "05-01-2019 11:20");
        algorithmsLesson.addStudent(firstStudent);
        spanishLesson = new Lesson("Spanish", "3", foreignLanguagesLecturer, "05-01-2019 11:30", "05-01-2019 13:05");
        geometryLesson = new Lesson("Geometry", "2", mathLecturer, "06-01-2019 08:00", "05-01-2019 09:35");
        databasesLesson = new Lesson("Databases", "2", programmingLecturer, "06-01-2019 09:45", "05-01-2019 11:20");
        databasesLesson.addStudent(firstStudent);
        frenchLesson = new Lesson("French", "3", foreignLanguagesLecturer, "06-01-2019 11:30", "05-01-2019 13:05");
        frenchLesson.addStudent(firstStudent);

        lessonThatHasSameTimeAndSameClassroomWithMathLesson = new Lesson("English", "1", foreignLanguagesLecturer, "04-01-2019 08:00", "04-01-2019 09:35");
        lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson = new Lesson("Spanish", "V-4", foreignLanguagesLecturer, "04-01-2019 11:30", "04-01-2019 13:05");
    }

    @Test
    public void mustAddStudentIntoStudentList() {
        university.getStudents().add(firstStudent);
        int expected = 1;
        int actual = university.getStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustRemoveStudentFromStudentList() {
        university.getStudents().add(firstStudent);
        university.expelStudent(firstStudent);
        int expected = 0;
        int actual = university.getStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustFindStudentInStudentList_whenStudentListContainsSearchingStudent() {
        university.acceptStudent(firstStudent);
        university.acceptStudent(secondStudent);
        university.acceptStudent(thirdStudent);
        Student expected = firstStudent;
        Student actual = university.findStudent("Patrick", "Kluivert");
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnNull_whenStudentListDoesNotContainSearchingStudent() {
        university.acceptStudent(firstStudent);
        university.acceptStudent(secondStudent);
        university.acceptStudent(thirdStudent);
        assertNull(university.findStudent("Mikhail", "Kotov"));
    }

    @Test
    public void mustAddLecturerIntoLecturerList() {
        university.employLecturer(mathLecturer);
        int expected = 1;
        int actual = university.getLecturers().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustRemoveLecturerFromLecturerList() {
        university.employLecturer(mathLecturer);
        university.sackLecturer(mathLecturer);
        int expected = 0;
        int actual = university.getStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustFindLecturerInLecturerList_whenLecturerListContainsSearchingLecturer() {
        university.employLecturer(mathLecturer);
        university.employLecturer(programmingLecturer);
        university.employLecturer(foreignLanguagesLecturer);
        Lecturer expected = mathLecturer;
        Lecturer actual = university.findLecturer("Tom", "Hanks");
        assertEquals(expected, actual);
    }

    @Test
    public void mustAddLessonsIntoLessonsList() {
        university.addLesson(mathLesson);
        university.addLesson(programmingLesson);
        university.addLesson(englishLanguagesLesson);
        int expected = 3;
        int actual = university.getLessons().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustNotAddLessonIntoLessonList_WhenAddingLessonHasSameClassroomAndTimeWithExistingLessons() {
        university.addLesson(mathLesson);
        university.addLesson(lessonThatHasSameTimeAndSameClassroomWithMathLesson);
        assertFalse(university.getLessons().contains(lessonThatHasSameTimeAndSameClassroomWithMathLesson));
    }

    @Test
    public void mustNotAddLessonIntoLessonList__WhenAddingLessonHasSameLecturerAndTimeWithExistingLessons() {
        university.addLesson(englishLanguagesLesson);
        university.addLesson(lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson);
        assertFalse(university.getLessons().contains(lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson));
    }

    @Test
    public void mustRemoveLessonFromLessonList() {
        university.addLesson(mathLesson);
        university.removeLesson(mathLesson);
        int expected = 0;
        int actual = university.getLessons().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnDayScheduleForStudent() throws Exception {
        university.addLesson(mathLesson);
        university.addLesson(programmingLesson);
        university.addLesson(englishLanguagesLesson);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Schedule schedule = university.getSchedule(dateFormat.parse("04-01-2019"), firstStudent);
        int expected = 2;
        int actual = schedule.getAllLesson().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnDayScheduleForLecturer() throws Exception {
        university.addLesson(mathLesson);
        university.addLesson(englishLanguagesLesson);
        university.addLesson(discreteMathLesson);
        university.addLesson(geometryLesson);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Schedule schedule = university.getSchedule(dateFormat.parse("05-01-2019"), mathLecturer);
        int expected = 1;
        int actual = schedule.getAllLesson().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnScheduleForStudent() throws Exception {
        university.addLesson(mathLesson);
        university.addLesson(englishLanguagesLesson);
        university.addLesson(programmingLesson);
        university.addLesson(discreteMathLesson);
        university.addLesson(algorithmsLesson);
        university.addLesson(geometryLesson);
        university.addLesson(databasesLesson);
        university.addLesson(frenchLesson);
        TimePeriod timePeriod = new TimePeriod("04-01-2019 00:00", "05-01-2019 23:59");
        Schedule schedule = university.getSchedule(timePeriod, firstStudent);
        int expected = 3;
        int actual = schedule.getAllLesson().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnScheduleForLecturer() throws Exception {
        university.addLesson(mathLesson);
        university.addLesson(englishLanguagesLesson);
        university.addLesson(programmingLesson);
        university.addLesson(discreteMathLesson);
        university.addLesson(algorithmsLesson);
        TimePeriod timePeriod = new TimePeriod("04-01-2019 00:00", "05-01-2019 23:59");
        Schedule schedule = university.getSchedule(timePeriod, mathLecturer);
        int expected = 2;
        int actual = schedule.getAllLesson().size();
        assertEquals(expected, actual);
    }
}
