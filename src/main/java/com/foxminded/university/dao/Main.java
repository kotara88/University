package com.foxminded.university.dao;

import com.foxminded.university.domain.*;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        StudentDao studentDao = new StudentDao();
        Student firstStudent = new Student("Patrick", "Kluivert", 19, "vm-41");
        Student secondStudent = new Student("Jack", "Nicholson", 17, "vm-21");
        Student thirdStudent = new Student("Marlon", "Brando", 18, "vm-31");
        firstStudent = studentDao.insert(firstStudent);
        secondStudent = studentDao.insert(secondStudent);
        thirdStudent = studentDao.insert(thirdStudent);

        LecturerDao lecturerDao = new LecturerDao();
        Lecturer mathLecturer = new Lecturer("Tom", "Hanks", 56, "computer engineering");
        Lecturer programmingLecturer = new Lecturer("Denzel!!!", "Washington", 43, "computer engineering");
        Lecturer foreignLanguagesLecturer = new Lecturer("Morgan", "Freeman", 39, "foreign languages");
        mathLecturer = lecturerDao.insert(mathLecturer);
        programmingLecturer =  lecturerDao.insert(programmingLecturer);
        foreignLanguagesLecturer = lecturerDao.insert(foreignLanguagesLecturer);

        String datePattern = "dd-MM-yyyy hh:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        TimePeriod mathLessonTimePeriod = new TimePeriod(dateFormat.parse("04-01-2019 08:00"), dateFormat.parse("04-01-2019 09:35"));
        Lesson mathLesson = new Lesson("Mathematics", "1", mathLecturer, mathLessonTimePeriod);
        mathLesson.addStudent(firstStudent);
        mathLesson.addStudent(secondStudent);

        TimePeriod programmingLessonTimePeriod = new TimePeriod(dateFormat.parse("04-01-2019 09:45"), dateFormat.parse("04-01-2019 11:20"));
        Lesson programmingLesson = new Lesson("Programming Languages", "2", programmingLecturer, programmingLessonTimePeriod);
        programmingLesson.addStudent(firstStudent);
        programmingLesson.addStudent(secondStudent);

        TimePeriod englishLanguagesLessonTimePeriod = new TimePeriod(dateFormat.parse("04-01-2019 11:30"), dateFormat.parse("04-01-2019 13:05"));
        Lesson englishLanguagesLesson = new Lesson("English", "3", foreignLanguagesLecturer, englishLanguagesLessonTimePeriod);
        englishLanguagesLesson.addStudent(secondStudent);
        englishLanguagesLesson.addStudent(thirdStudent);

        TimePeriod discreteMathLessonTimePeriod = new TimePeriod(dateFormat.parse("05-01-2019 08:00"), dateFormat.parse("05-01-2019 09:35"));
        Lesson discreteMathLesson = new Lesson("Discrete mathematics", "2", mathLecturer, discreteMathLessonTimePeriod);
        discreteMathLesson.addStudent(firstStudent);
        discreteMathLesson.addStudent(secondStudent);
        discreteMathLesson.addStudent(thirdStudent);

        LessonDao lessonDao = new LessonDao();
        lessonDao.insert(mathLesson);
    }

}
