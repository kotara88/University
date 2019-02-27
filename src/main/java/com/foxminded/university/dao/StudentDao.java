package com.foxminded.university.dao;

import com.foxminded.university.domain.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentDao {

    public Student insert(Student student) {
        String query = "INSERT INTO students (first_name, last_Name, age, student_group) values (?, ?, ?, ?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getStudentGroup());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                student.setId(resultSet.getLong("id"));
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student update(Student student) {
        String query = "UPDATE students SET first_name = ?, last_name = ?, age = ?, student_group = ? WHERE id = ?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getStudentGroup());
            statement.setLong(5, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void delete(Student student) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?;")) {
            statement.setLong(1, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getById(long id) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ?;")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = extractStudentFromResultSet(resultSet);
                    return student;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Student> getAll() {
        ArrayList<Student> students = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM students;")) {
                while (resultSet.next()) {
                    Student student = extractStudentFromResultSet(resultSet);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    private Student extractStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setAge(resultSet.getInt("age"));
        student.setStudentGroup(resultSet.getString("student_group"));
        return student;
    }
}