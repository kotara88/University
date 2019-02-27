package com.foxminded.university.dao;

import com.foxminded.university.domain.Lecturer;

import java.sql.*;
import java.util.ArrayList;

public class LecturerDao {

    public Lecturer insert(Lecturer lecturer) {
        String query = "INSERT INTO lecturers (first_name, last_Name, age, department) values (?, ?, ?, ?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, lecturer.getName());
            statement.setString(2, lecturer.getLastName());
            statement.setInt(3, lecturer.getAge());
            statement.setString(4, lecturer.getDepartment());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                lecturer.setId(resultSet.getLong("id"));
                return lecturer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Lecturer update(Lecturer lecturer) {
        String query = "UPDATE lecturers SET first_name = ?, last_name = ?, age = ?, department = ? WHERE id = ?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lecturer.getName());
            statement.setString(2, lecturer.getLastName());
            statement.setInt(3, lecturer.getAge());
            statement.setString(4, lecturer.getDepartment());
            statement.setLong(5, lecturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturer;
    }

    public void delete(Lecturer lecturer) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM lecturers WHERE id = ?;")) {
            statement.setLong(1, lecturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lecturer getById(long id) {
        Lecturer lecturer = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lecturers WHERE id = ?;")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lecturer = extractLecturerFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturer;
    }

    public ArrayList<Lecturer> getAll() {
        ArrayList<Lecturer> lecturers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM lecturers;")) {
                while (resultSet.next()) {
                    Lecturer lecturer = extractLecturerFromResultSet(resultSet);
                    lecturers.add(lecturer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

    private Lecturer extractLecturerFromResultSet(ResultSet resultSet) throws SQLException {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(resultSet.getLong("id"));
        lecturer.setName(resultSet.getString("first_name"));
        lecturer.setLastName(resultSet.getString("last_name"));
        lecturer.setAge(resultSet.getInt("age"));
        lecturer.setDepartment(resultSet.getString("student_group"));
        return lecturer;
    }
}
