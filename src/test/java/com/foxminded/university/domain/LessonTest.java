package com.foxminded.university.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LessonTest {
    private Student firstStudent;
    private Student secondStudent;
    private Lesson lesson;

    @Before
    public void initialize(){
        firstStudent = new Student("Patrick", "Kluivert", 22, "VM-41");
        secondStudent = new Student("Patrick", "Kluivert", 22, "VM-41");
        lesson = new Lesson();
        lesson.getStudents().add(secondStudent);
    }

    @Test
    public void mustAddStudentIntoStudentList(){
        lesson.addStudent(firstStudent);
        assertTrue(lesson.getStudents().contains(firstStudent));
    }

    @Test
    public void mustRemoveStudentFromStudentList(){
        lesson.removeStudent(secondStudent);
        assertFalse(lesson.getStudents().contains(secondStudent));
    }
}
