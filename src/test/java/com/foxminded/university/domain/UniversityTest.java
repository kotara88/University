package com.foxminded.university.domain;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UniversityTest {
    private University university;

    private Student firstStudent;
    private Student secondStudent;
    private Student thirdStudent;
    private Student searchingStudent;
    private Student notExistedStudent;

    private Lecturer mathLecturer;
    private Lecturer programmingLecturer;
    private Lecturer foreignLanguagesLecturer;
    private Lecturer searchingLecturer;
    private Lecturer notExistedLecturer;
    private Lecturer lecturerThatHaveNoScheduledLessons;

    private Lesson mathLesson;
    private Lesson programmingLesson;
    private Lesson englishLanguagesLesson;
    private Lesson discreteMathLesson;
    private Lesson algorithmsLesson;
    private Lesson spanishLesson;
    private Lesson geometryLesson;
    private Lesson databasesLesson;
    private Lesson frenchLesson;

    private TimePeriod mathLessonTimePeriod;
    private TimePeriod programmingLessonTimePeriod;
    private TimePeriod englishLanguagesLessonTimePeriod;
    private TimePeriod discreteMathLessonTimePeriod;
    private TimePeriod algorithmsLessonTimePeriod;
    private TimePeriod spanishLessonTimePeriod;
    private TimePeriod geometryLessonTimePeriod;
    private TimePeriod databasesLessonTimePeriod;
    private TimePeriod frenchLessonTimePeriod;

    private String datePattern;
    private String dayDatePattern;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dayDateFormat;

    private Lesson lessonThatHasSameTimeAndSameClassroomWithMathLesson;
    private Lesson lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson;

    @Before
    public void initialize() {
        university = new University();

        firstStudent = new Student("Patrick", "Kluivert", 19, "vm-41");
        secondStudent = new Student("Jack", "Nicholson", 17, "vm-21");
        thirdStudent = new Student("Marlon", "Brando", 18, "vm-31");
        university.getStudents().add(secondStudent);
        university.getStudents().add(thirdStudent);

        mathLecturer = new Lecturer("Tom", "Hanks", 56, "computer engineering");
        programmingLecturer = new Lecturer("Denzel", "Washington", 43, "computer engineering");
        foreignLanguagesLecturer = new Lecturer("Morgan", "Freeman", 39, "foreign languages");
        university.getLecturers().add(programmingLecturer);
        university.getLecturers().add(foreignLanguagesLecturer);

        datePattern = "dd-MM-yyyy hh:mm";
        dayDatePattern = "dd-MM-yyyy";
        dateFormat = new SimpleDateFormat(datePattern);
        dayDateFormat = new SimpleDateFormat(dayDatePattern);

        try {
            mathLessonTimePeriod = new TimePeriod(dateFormat.parse("04-01-2019 08:00"), dateFormat.parse("04-01-2019 09:35"));
            mathLesson = new Lesson("Mathematics", "1", mathLecturer, mathLessonTimePeriod);
            mathLesson.addStudent(firstStudent);
            mathLesson.addStudent(secondStudent);

            programmingLessonTimePeriod = new TimePeriod(dateFormat.parse("04-01-2019 09:45"), dateFormat.parse("04-01-2019 11:20"));
            programmingLesson = new Lesson("Programming Languages", "2", programmingLecturer, programmingLessonTimePeriod);
            programmingLesson.addStudent(firstStudent);
            programmingLesson.addStudent(secondStudent);

            englishLanguagesLessonTimePeriod = new TimePeriod(dateFormat.parse("04-01-2019 11:30"), dateFormat.parse("04-01-2019 13:05"));
            englishLanguagesLesson = new Lesson("English", "3", foreignLanguagesLecturer, englishLanguagesLessonTimePeriod);
            englishLanguagesLesson.addStudent(secondStudent);
            englishLanguagesLesson.addStudent(thirdStudent);

            discreteMathLessonTimePeriod = new TimePeriod(dateFormat.parse("05-01-2019 08:00"), dateFormat.parse("05-01-2019 09:35"));
            discreteMathLesson = new Lesson("Discrete mathematics", "2", mathLecturer, discreteMathLessonTimePeriod);
            discreteMathLesson.addStudent(firstStudent);
            discreteMathLesson.addStudent(secondStudent);
            discreteMathLesson.addStudent(thirdStudent);

            algorithmsLessonTimePeriod = new TimePeriod(dateFormat.parse("05-01-2019 09:45"), dateFormat.parse("05-01-2019 11:20"));
            algorithmsLesson = new Lesson("Algorithms", "2", programmingLecturer, algorithmsLessonTimePeriod);
            algorithmsLesson.addStudent(firstStudent);
            algorithmsLesson.addStudent(thirdStudent);

            spanishLessonTimePeriod = new TimePeriod(dateFormat.parse("05-01-2019 11:30"), dateFormat.parse("05-01-2019 13:05"));
            spanishLesson = new Lesson("Spanish", "3", foreignLanguagesLecturer, spanishLessonTimePeriod);
            spanishLesson.addStudent(thirdStudent);
            spanishLesson.addStudent(secondStudent);

            geometryLessonTimePeriod = new TimePeriod(dateFormat.parse("06-01-2019 08:00"), dateFormat.parse("05-01-2019 09:35"));
            geometryLesson = new Lesson("Geometry", "2", mathLecturer, geometryLessonTimePeriod);
            geometryLesson.addStudent(firstStudent);
            geometryLesson.addStudent(secondStudent);

            databasesLessonTimePeriod = new TimePeriod(dateFormat.parse("06-01-2019 09:45"), dateFormat.parse("05-01-2019 11:20"));
            databasesLesson = new Lesson("Databases", "2", programmingLecturer, databasesLessonTimePeriod);
            databasesLesson.addStudent(firstStudent);
            databasesLesson.addStudent(secondStudent);
            databasesLesson.addStudent(thirdStudent);

            frenchLessonTimePeriod = new TimePeriod(dateFormat.parse("06-01-2019 11:30"), dateFormat.parse("05-01-2019 13:05"));
            frenchLesson = new Lesson("French", "3", foreignLanguagesLecturer, frenchLessonTimePeriod);
            frenchLesson.addStudent(secondStudent);
            frenchLesson.addStudent(thirdStudent);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }

        university.getLessons().add(mathLesson);
        university.getLessons().add(programmingLesson);
        university.getLessons().add(englishLanguagesLesson);
        university.getLessons().add(discreteMathLesson);
        university.getLessons().add(algorithmsLesson);
        university.getLessons().add(spanishLesson);
        university.getLessons().add(geometryLesson);
        university.getLessons().add(databasesLesson);

        searchingStudent = new Student("Marlon", "Brando", 18, "vm-31");
        notExistedStudent = new Student("Kotov", "Mikhail", 30, "vm-51");

        searchingLecturer = new Lecturer("Denzel", "Washington", 43, "computer engineering");
        notExistedLecturer = new Lecturer("Tom", "Washington", 48, "computer engineering");

        lessonThatHasSameTimeAndSameClassroomWithMathLesson = new Lesson("English", "1", foreignLanguagesLecturer, mathLessonTimePeriod);
        lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson = new Lesson("Spanish", "V-4", foreignLanguagesLecturer, englishLanguagesLessonTimePeriod);
    }

    @Test
    public void mustAddStudentIntoStudentList() {
        university.acceptStudent(firstStudent);
        assertTrue(university.getStudents().contains(firstStudent));
    }

    @Test
    public void mustRemoveStudentFromStudentList() {
        university.expelStudent(secondStudent);
        assertFalse(university.getStudents().contains(secondStudent));
    }

    @Test
    public void mustFindStudentInStudentList_whenStudentListContainsSearchingStudent() {
        Student expected = thirdStudent;
        Student actual = university.findStudent(searchingStudent);
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnNull_whenStudentListDoesNotContainSearchingStudent() {
        assertNull(university.findStudent(notExistedStudent));
    }

    @Test
    public void mustAddLecturerIntoLecturerList() {
        university.employLecturer(mathLecturer);
        assertTrue(university.getLecturers().contains(mathLecturer));
    }

    @Test
    public void mustRemoveLecturerFromLecturerList_whenLecturerHasNoScheduledLessons() {
        university.sackLecturer(lecturerThatHaveNoScheduledLessons);
        assertFalse(university.getLecturers().contains(lecturerThatHaveNoScheduledLessons));
    }

    @Test(expected = IllegalArgumentException.class)
    public void mustThrowIllegalArgumentException_WhenLecturerHasScheduledLessons() {
        university.sackLecturer(mathLecturer);
    }

    @Test
    public void mustFindLecturerInLecturerList_whenLecturerListContainsSearchingLecturer() {
        Lecturer expected = programmingLecturer;
        Lecturer actual = university.findLecturer(searchingLecturer);
        assertEquals(expected, actual);
    }

    @Test
    public void mustReturnNull_whenLecturerListDoesNotContainSearchingLecturer() {
        assertNull(university.findLecturer(notExistedLecturer));
    }

    @Test
    public void mustAddLessonsIntoLessonsList() {
        university.addLesson(frenchLesson);
        assertTrue(university.getLessons().contains(frenchLesson));
    }

    @Test
    public void mustNotAddLessonIntoLessonList_WhenAddingLessonHasSameClassroomAndTimeWithExistingLessons() {
        university.addLesson(lessonThatHasSameTimeAndSameClassroomWithMathLesson);
        assertFalse(university.getLessons().contains(lessonThatHasSameTimeAndSameClassroomWithMathLesson));
    }

    @Test
    public void mustNotAddLessonIntoLessonList__WhenAddingLessonHasSameLecturerAndTimeWithExistingLessons() {
        university.addLesson(lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson);
        assertFalse(university.getLessons().contains(lessonThatHasSameTimeAndSameLecturerWithEnglishLanguagesLesson));
    }

    @Test
    public void mustRemoveLessonFromLessonList() {
        university.removeLesson(mathLesson);
        assertFalse(university.getLessons().contains(mathLecturer));
    }

    @Test
    public void mustReturnDayScheduleForStudent() {
        try {
            Schedule schedule = university.getSchedule(dayDateFormat.parse("04-01-2019"), firstStudent);
            int expected = 2;
            int actual = schedule.getAllLesson().size();
            assertEquals(expected, actual);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    @Test
    public void mustReturnDayScheduleForLecturer() {
        try {
            Schedule schedule = university.getSchedule(dayDateFormat.parse("04-01-2019"), mathLecturer);
            int expected = 1;
            int actual = schedule.getAllLesson().size();
            assertEquals(expected, actual);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    @Test
    public void mustReturnScheduleForStudent() {
        try {
            TimePeriod searchingTimePeriod = new TimePeriod(dayDateFormat.parse("04-01-2019"), dayDateFormat.parse("05-01-2019"));
            Schedule schedule = university.getSchedule(searchingTimePeriod, firstStudent);
            Lesson[] expectedLessons = {mathLesson, programmingLesson, discreteMathLesson, algorithmsLesson};
            List<Lesson> expected = Arrays.asList(expectedLessons);
            List<Lesson> actual = schedule.getAllLesson();
            assertEquals(expected, actual);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }

    }

    @Test
    public void mustReturnScheduleForLecturer() {
        try {
            TimePeriod searchingTimePeriod = new TimePeriod(dayDateFormat.parse("05-01-2019"), dayDateFormat.parse("06-01-2019"));
            Schedule schedule = university.getSchedule(searchingTimePeriod, mathLecturer);
            Lesson[] expectedLessons = {discreteMathLesson, geometryLesson};
            List<Lesson> expected = Arrays.asList(expectedLessons);
            List<Lesson> actual = schedule.getAllLesson();
            assertEquals(expected, actual);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }
}
