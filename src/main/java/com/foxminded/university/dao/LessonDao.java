package com.foxminded.university.dao;

import com.foxminded.university.domain.Lesson;
import com.foxminded.university.domain.Student;
import com.foxminded.university.domain.TimePeriod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class LessonDao {
    private static Logger log = LogManager.getLogger(LessonDao.class.getName());

    public Lesson insert(Lesson lesson) throws DaoException {
        log.info("Insert lesson {} {}", lesson.getSubject(), lesson.getTimePeriod().getStartTime());
        String query = "INSERT INTO lessons (subject, classroom, lecturer_id, start_time, end_time) VALUES (?, ?, ?, ?, ?);";

        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp startTime = new Timestamp(lesson.getTimePeriod().getStartTime().getTime());
            Timestamp endTime = new Timestamp(lesson.getTimePeriod().getEndTime().getTime());
            log.trace("Set statement");
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getClassroom());
            statement.setLong(3, lesson.getLecturer().getId());
            statement.setTimestamp(4, startTime);
            statement.setTimestamp(5, endTime);
            log.debug("Execute statement");
            statement.executeUpdate();
            log.trace("Get generated key");
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                log.debug("Set inserted lesson id");
                lesson.setId(resultSet.getLong("id"));
                insertAllStudents(lesson);
                log.trace("Return inserted lesson");
                return lesson;
            }
        } catch (SQLException e) {
            log.error("Couldn't insert lesson {} {}", lesson.getSubject(), lesson.getTimePeriod().getStartTime(), e);
            throw new DaoException("Could't insert lesson", e);
        }
        return null;
    }

    private void insertAllStudents(Lesson lesson) throws DaoException {
        log.info("Insert lesson's list of students");
        String query = "INSERT INTO lesson_student (lesson_id, student_id) VALUES (?, ?);";

        for (Student student : lesson.getStudents()) {
            log.trace("Open connection and create prepare statement");

            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                log.trace("Set statement");
                statement.setLong(1, lesson.getId());
                statement.setLong(2, student.getId());
                log.debug("Execute statement");
                statement.executeUpdate();
            } catch (SQLException e) {
                log.error("Could't insert students id = " + student.getId(), e);
                throw new DaoException("Could't insert students id = " + student.getId(), e);
            }
        }
    }

    public Lesson update(Lesson lesson) throws DaoException {
        log.info("update lesson id = {}", lesson.getId());
        String query = "UPDATE lessons SET subject = ?, classroom = ?, lecturer_id = ?, start_time = ?, end_time = ? WHERE id = ?;";

        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.trace("Set statement");
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getClassroom());
            statement.setLong(3, lesson.getLecturer().getId());

            Timestamp startTime = new Timestamp(lesson.getTimePeriod().getStartTime().getTime());
            Timestamp endTime = new Timestamp(lesson.getTimePeriod().getEndTime().getTime());
            statement.setTimestamp(4, startTime);
            statement.setTimestamp(5, endTime);
            statement.setLong(6, lesson.getId());
            log.debug("Execute statement");
            statement.executeUpdate();
            updateAllStudents(lesson);
            log.trace("Return updated lesson");
            return lesson;
        } catch (SQLException e) {
            log.error("Could't update lesson", e);
            throw new DaoException("Could't update lesson", e);
        }

    }

    private void updateAllStudents(Lesson lesson) throws DaoException {
        log.info("Update  lesson's list of students");
        String query = "UPDATE lesson_student SET student_id = ? WHERE lesson_id = ?";

        log.trace("Open connection and create prepare statement");
        for (Student student : lesson.getStudents()) {
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                log.trace("Set statement");
                statement.setLong(1, student.getId());
                statement.setLong(2, lesson.getId());
                log.debug("Execute statement");
                statement.executeUpdate();
            } catch (SQLException e) {
                log.error("Could't insert students id = " + student.getId(), e);
                throw new DaoException("Could't update all students for lesson", e);
            }
        }
    }

    public void delete(Lesson lesson) throws DaoException {
        log.info("Delete  lesson id = {}", lesson.getId());
        String query = "DELETE FROM lessons WHERE id = ?;";
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            deleteAllStudents(lesson);
            log.trace("Set statement");
            statement.setLong(1, lesson.getId());
            log.debug("Execute statement");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Could't delete  lesson id = {}", lesson.getId(), e);
            throw new DaoException("Could't delete lesson", e);
        }
    }

    private void deleteAllStudents(Lesson lesson) throws DaoException {
        log.info("Delete  lesson's list of students");
        String query = "DELETE FROM lesson_student WHERE lesson_id = ?;";

        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.trace("Set statement");
            statement.setLong(1, lesson.getId());
            log.debug("Execute statement");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Could't delete lesson's list of students", e);
            throw new DaoException("Can't delete all students for lesson", e);
        }
    }

    public Lesson getLessonById(long id) throws DaoException {
        log.info("Get lesson");
        String query = "SELECT * FROM lessons WHERE id = ?;";
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.trace("Set statement");
            statement.setLong(1, id);
            log.trace("Execute statement and get result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    Lesson lesson = new Lesson();
                    LecturerDao lecturerDao = new LecturerDao();
                    log.debug("Set lesson");
                    lesson.setId(resultSet.getLong("id"));
                    lesson.setSubject(resultSet.getString("subject"));
                    lesson.setClassroom(resultSet.getString("classroom"));
                    lesson.setLecturer(lecturerDao.getById(resultSet.getLong("lecturer_id")));
                    lesson.setStudents(getAllStudents(lesson));

                    Date startTime = resultSet.getTimestamp("start_time");
                    Date endTime = resultSet.getTimestamp("end_time");
                    lesson.setTimePeriod(new TimePeriod(startTime, endTime));
                    log.trace("Return lesson");
                    return lesson;
                }
            }
        } catch (SQLException e) {
            log.error("Could't get lesson id = {}", id, e);
            throw new DaoException("Could't get lesson id = " + id, e);
        }
        return null;
    }

    private ArrayList<Student> getAllStudents(Lesson lesson) throws DaoException {
        log.info("Get lesson's list of students");
        String query = "SELECT student_id FROM lesson_student WHERE lesson_id = ?;";
        ArrayList<Student> students = new ArrayList<>();
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.trace("Set statement");
            statement.setLong(1, lesson.getId());
            log.debug("Executing statement and getting result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                StudentDao studentDao = new StudentDao();
                while (resultSet.next()) {
                    log.trace("Set student");
                    Student student = studentDao.getById(resultSet.getLong("student_id"));
                    students.add(student);
                }
            }
            log.trace("Return student");
            return students;
        } catch (SQLException e) {
            log.error("Could't get lesson's list of students", e);
            throw new DaoException("Could't get all students for lesson", e);
        }
    }

    public ArrayList<Lesson> getAll() throws DaoException {
        log.info("Get list of all lessons");
        String query = "SELECT * FROM lessons;";
        ArrayList<Lesson> lessons = new ArrayList<>();
        log.trace("Open connection and create prepare statement");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            log.debug("Executing statement and getting result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                LecturerDao lecturerDao = new LecturerDao();
                while (resultSet.next()) {
                    log.debug("Set lesson");
                    Lesson lesson = new Lesson();
                    lesson.setId(resultSet.getLong("id"));
                    lesson.setSubject(resultSet.getString("subject"));
                    lesson.setClassroom(resultSet.getString("classroom"));
                    lesson.setLecturer(lecturerDao.getById(resultSet.getLong("lecturer_id")));
                    lesson.setStudents(getAllStudents(lesson));

                    Date startTime = resultSet.getTimestamp("start_time");
                    Date endTime = resultSet.getTimestamp("end_time");
                    lesson.setTimePeriod(new TimePeriod(startTime, endTime));
                    lessons.add(lesson);
                }
                log.trace("Return lesson");
                return lessons;
            }
        } catch (SQLException e) {
            log.error("Could't get list of all lessons", e);
            throw new DaoException("Can't get all lesson", e);
        }
    }
}
