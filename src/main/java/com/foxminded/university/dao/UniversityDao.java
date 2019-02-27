package com.foxminded.university.dao;

import com.foxminded.university.domain.Lecturer;
import com.foxminded.university.domain.Lesson;
import com.foxminded.university.domain.Student;
import com.foxminded.university.domain.University;

public class UniversityDao {

    public University insert(University university) {
        LecturerDao lecturerDao = new LecturerDao();
        for (Lecturer lecturer : university.getLecturers()) {
            lecturerDao.insert(lecturer);
        }

        StudentDao studentDao = new StudentDao();
        for (Student student : university.getStudents()) {
            studentDao.insert(student);
        }

        LessonDao lessonDao = new LessonDao();
        for (Lesson lesson : university.getLessons()) {
            lessonDao.insert(lesson);
        }

        return university;
    }

    public University upadte(University university) {
        LecturerDao lecturerDao = new LecturerDao();
        for (Lecturer lecturer : university.getLecturers()) {
            lecturerDao.update(lecturer);
        }

        StudentDao studentDao = new StudentDao();
        for (Student student : university.getStudents()) {
            studentDao.update(student);
        }

        LessonDao lessonDao = new LessonDao();
        for (Lesson lesson : university.getLessons()) {
            lessonDao.update(lesson);
        }
        return university;
    }

    public void delete(University university) {
        LecturerDao lecturerDao = new LecturerDao();
        for (Lecturer lecturer : university.getLecturers()) {
            lecturerDao.delete(lecturer);
        }

        StudentDao studentDao = new StudentDao();
        for (Student student : university.getStudents()) {
            studentDao.delete(student);
        }

        LessonDao lessonDao = new LessonDao();
        for (Lesson lesson : university.getLessons()) {
            lessonDao.delete(lesson);
        }
    }

    public University getUniversity() {
        University university = new University();

        LecturerDao lecturerDao = new LecturerDao();
        university.setLecturers(lecturerDao.getAll());

        StudentDao studentDao = new StudentDao();
        university.setStudents(studentDao.getAll());

        LessonDao lessonDao = new LessonDao();
        university.setLessons(lessonDao.getAllLessons());
        return university;
    }


}
