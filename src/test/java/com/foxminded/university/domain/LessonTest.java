package com.foxminded.university.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LessonTest {
    private Student student;
    private Lesson lesson;

    @Before
    public void initialize(){
        student = new Student("Patrick", "Kluivert", 22, "VM-41");
        lesson = new Lesson();
    }

    @Test
    public void mustAddStudentIntoStudentList(){
        lesson.addStudent(student);
        int expected = 1;
        int actual = lesson.getStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    public void mustRemoveStudentFromStudentList(){
        lesson.addStudent(student);
        lesson.removeStudent(student);
        int expected = 0;
        int actual = lesson.getStudents().size();
        assertEquals(expected, actual);
    }
}
