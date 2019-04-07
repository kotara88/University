package com.foxminded.university.controller;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.LecturerDao;
import com.foxminded.university.dao.LessonDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.domain.Lecturer;
import com.foxminded.university.domain.Lesson;
import com.foxminded.university.domain.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Student> students = new StudentDao().getAll();
            List<Lecturer> lecturers = new LecturerDao().getAll();
            List<Lesson> lessons = new LessonDao().getAll();
            request.setAttribute("liststudents", students);
            request.setAttribute("listlecturers", lecturers);
            request.setAttribute("listlessons", lessons);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
            dispatcher.forward(request, response);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
