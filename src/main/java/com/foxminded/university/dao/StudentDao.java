package com.foxminded.university.dao;

import com.foxminded.university.domain.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;


public class StudentDao {
    private static Logger log = LogManager.getLogger(StudentDao.class.getName());

    public Student insert(Student student) throws DaoException {
        log.info("Insert student {} {}", student.getName(), student.getLastName());
        String query = "INSERT INTO students (first_name, last_Name, age, student_group) values (?, ?, ?, ?);";

        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            log.trace("Set statement");
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getStudentGroup());
            log.debug("Execute statement");
            statement.execute();
            log.trace("Get generated key");
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                log.debug("Set student id");
                student.setId(resultSet.getLong("id"));
                log.trace("Return inserted student");
                return student;
            }
        } catch (SQLException e) {
            log.error("Couldn't insert student", e);
            throw new DaoException("Couldn't insert student", e);
        }
        return null;
    }

    public Student update(Student student) throws DaoException {
        log.info("Update student id = {}", student.getId());
        String query = "UPDATE students SET first_name = ?, last_name = ?, age = ?, student_group = ? WHERE id = ?;";
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.trace("Set statement");
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getStudentGroup());
            statement.setLong(5, student.getId());
            log.debug("Execute statement");
            statement.executeUpdate();
            log.trace("Return updated student");
            return student;
        } catch (SQLException e) {
            log.error("Couldn't update student", e);
            throw new DaoException("Couldn't update student", e);
        }
    }

    public void delete(Student student) throws DaoException {
        log.info("Delete student id = {}", student.getId());
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?;")) {
            statement.setLong(1, student.getId());
            log.debug("Execute statement");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't delete student", e);
            throw new DaoException("Can't delete student", e);
        }
    }

    // to do
    public Student getById(long id) throws DaoException {
        log.info("Getting student by id = {}", id);
        log.trace("Opening connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ?;")) {
            statement.setLong(1, id);
            log.debug("Execute statement and get result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                Student student = extractStudentFromResultSet(resultSet);
                return student;
            }
        } catch (SQLException e) {
            log.error("Couldn't get student {}", id, e);
            throw new DaoException("Couldn't get student " + id, e);
        }
    }

    public ArrayList<Student> getAll() throws DaoException {
        log.info("Get list of all students");
        ArrayList<Student> students = new ArrayList<>();
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            log.debug("Executing statement and getting result set");
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM students;")) {
                while (resultSet.next()) {
                    Student student = extractStudentFromResultSet(resultSet);
                    students.add(student);
                }
            }
            log.trace("Return list of students");
            return students;
        } catch (SQLException e) {
            log.error("Couldn't get list of students", e);
            throw new DaoException("Couldn't get list of students", e);
        }
    }

    private Student extractStudentFromResultSet(ResultSet resultSet) throws DaoException {
        log.info("Extract student from result set");
        try {
            if (resultSet.next()){
                Student student = new Student();
                log.debug("Set student");
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setAge(resultSet.getInt("age"));
                student.setStudentGroup(resultSet.getString("student_group"));
                log.trace("Return extracted student");
                return student;
            }
        } catch (SQLException e) {
            log.error("Couldn't extract student from result set", e);
            throw new DaoException("Couldn't extract student from resultSet", e);
        }
        return null;
    }
}