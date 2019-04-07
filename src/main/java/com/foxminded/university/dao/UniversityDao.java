package com.foxminded.university.dao;

import com.foxminded.university.domain.Lecturer;
import com.foxminded.university.domain.Lesson;
import com.foxminded.university.domain.Student;
import com.foxminded.university.domain.University;
import org.apache.logging.log4j.LogManager;


public class UniversityDao {
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(StudentDao.class.getName());

    public University insert(University university) throws DaoException {
        log.info("Insert university");
        LecturerDao lecturerDao = new LecturerDao();

        log.trace("Insert all lecturers");
        for (Lecturer lecturer : university.getLecturers()) {
            lecturerDao.insert(lecturer);
        }

        log.trace("Insert all students");
        StudentDao studentDao = new StudentDao();
        for (Student student : university.getStudents()) {
            studentDao.insert(student);
        }

        log.trace("Insert all lessons");
        LessonDao lessonDao = new LessonDao();
        for (Lesson lesson : university.getLessons()) {
            lessonDao.insert(lesson);
        }

        log.info("Return university");
        return university;
    }

    public University upadte(University university) throws DaoException {
        log.info("Update university");
        LecturerDao lecturerDao = new LecturerDao();

        log.trace("Update all lecturers");
        for (Lecturer lecturer : university.getLecturers()) {
            lecturerDao.update(lecturer);
        }

        log.trace("Update all students");
        StudentDao studentDao = new StudentDao();
        for (Student student : university.getStudents()) {
            studentDao.update(student);
        }

        log.trace("Update all lessons");
        LessonDao lessonDao = new LessonDao();
        for (Lesson lesson : university.getLessons()) {
            lessonDao.update(lesson);
        }

        log.info("Return university");
        return university;
    }

    public void delete(University university) throws DaoException {
        log.info("Delete university");
        LecturerDao lecturerDao = new LecturerDao();

        log.trace("Delete all lecturers");
        for (Lecturer lecturer : university.getLecturers()) {
            lecturerDao.delete(lecturer);
        }

        log.trace("Delete all students");
        StudentDao studentDao = new StudentDao();
        for (Student student : university.getStudents()) {
            studentDao.delete(student);
        }

        log.trace("Delete all lesson");
        LessonDao lessonDao = new LessonDao();
        for (Lesson lesson : university.getLessons()) {
            lessonDao.delete(lesson);
        }
    }

    public University getUniversity() throws DaoException {
        log.info("Get university");
        University university = new University();

        LecturerDao lecturerDao = new LecturerDao();
        university.setLecturers(lecturerDao.getAll());

        StudentDao studentDao = new StudentDao();
        university.setStudents(studentDao.getAll());

        LessonDao lessonDao = new LessonDao();
        university.setLessons(lessonDao.getAll());

        log.info("Return university");
        return university;
    }


}
