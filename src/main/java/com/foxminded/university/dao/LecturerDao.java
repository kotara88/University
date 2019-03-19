package com.foxminded.university.dao;

import com.foxminded.university.domain.Lecturer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class LecturerDao {
    private static Logger log = LogManager.getLogger(LecturerDao.class.getName());

    public Lecturer insert(Lecturer lecturer) throws DaoException {
        log.info("Insert lecturer {} {}", lecturer.getName(), lecturer.getLastName());
        String query = "INSERT INTO lecturers (first_name, last_Name, age, department) values (?, ?, ?, ?);";
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            log.trace("Set statement");
            statement.setString(1, lecturer.getName());
            statement.setString(2, lecturer.getLastName());
            statement.setInt(3, lecturer.getAge());
            statement.setString(4, lecturer.getDepartment());
            log.debug("Execute statement");
            statement.executeUpdate();
            log.trace("Get generated key");
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            lecturer.setId(resultSet.getLong("id"));
            log.trace("Return inserted lecturer");
            return lecturer;
        } catch (SQLException e) {
            log.error("Couldn't insert student");
            throw new DaoException("Couldn't insert lecturer", e);
        }
    }

    public Lecturer update(Lecturer lecturer) throws DaoException {
        log.info("Update student id = {}", lecturer.getId());
        String query = "UPDATE lecturers SET first_name = ?, last_name = ?, age = ?, department = ? WHERE id = ?;";
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.trace("Set statement");
            statement.setString(1, lecturer.getName());
            statement.setString(2, lecturer.getLastName());
            statement.setInt(3, lecturer.getAge());
            statement.setString(4, lecturer.getDepartment());
            statement.setLong(5, lecturer.getId());
            log.debug("Execute statement");
            statement.executeUpdate();
            log.trace("Return inserted lecturer");
            return lecturer;
        } catch (SQLException e) {
            log.error("Couldn't update lecturer");
            throw new DaoException("Can't update lecturer", e);
        }
    }

    public void delete(Lecturer lecturer) throws DaoException {
        log.info("Delete lecturer id = {}", lecturer.getId());
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM lecturers WHERE id = ?;")) {
            log.trace("Set statement");
            statement.setLong(1, lecturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Couldn't update lecturer");
            throw new DaoException("Can't delete lecturer", e);
        }
    }


    // to do
    public Lecturer getById(long id) throws DaoException {
        log.info("Get lecturer by id = {}", id);
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM lecturers WHERE id = ?;")) {
            log.trace("Set statement");
            statement.setLong(1, id);
            log.debug("Execute statement and get result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                Lecturer lecturer = extractLecturerFromResultSet(resultSet);
                return lecturer;
            }
        } catch (SQLException e) {
            log.error("Couldn't get lecturer {}", id);
            throw new DaoException("Couldn't get lecturer", e);
        }
    }

    public ArrayList<Lecturer> getAll() throws DaoException {
        log.info("Get list of all lecturers");
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            log.debug("Executing statement and getting result set");
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM lecturers;")) {
                while (resultSet.next()) {
                    Lecturer lecturer = extractLecturerFromResultSet(resultSet);
                    lecturers.add(lecturer);
                }
                log.trace("Return list of lecturers");
                return lecturers;
            }
        } catch (SQLException e) {
            log.error("Couldn't get list of students");
            throw new DaoException("Can't get lecturers", e);
        }

    }

    private Lecturer extractLecturerFromResultSet(ResultSet resultSet) throws DaoException {
        log.info("Extract lecturer from result set");
        try {
            Lecturer lecturer = new Lecturer();
            log.trace("Set lecturer");
            lecturer.setId(resultSet.getLong("id"));
            lecturer.setName(resultSet.getString("first_name"));
            lecturer.setLastName(resultSet.getString("last_name"));
            lecturer.setAge(resultSet.getInt("age"));
            lecturer.setDepartment(resultSet.getString("department"));
            log.trace("Return extracted lecturer");
            return lecturer;
        } catch (SQLException e) {
            log.error("Couldn't extract student from result set");
            throw new DaoException("Couldn't extract lecturer from result set", e);
        }
    }
}
