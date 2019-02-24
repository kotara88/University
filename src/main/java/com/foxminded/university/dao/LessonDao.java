package com.foxminded.university.dao;

import com.foxminded.university.domain.Lesson;
import com.foxminded.university.domain.Student;

import java.sql.*;
import java.util.ArrayList;

public class LessonDao {
    public Lesson insert(Lesson lesson) {
        String query = "INSERT INTO lessons (subject, classroom, lecturer_id, time_period_id) VALUES (?, ?, ?, ?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            TimePeriodDao timePeriodDao = new TimePeriodDao();
            lesson.setTimePeriod(timePeriodDao.insert(lesson.getTimePeriod()));
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getClassroom());
            statement.setLong(3, lesson.getLecturer().getId());
            statement.setLong(4, lesson.getTimePeriod().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                lesson.setId(resultSet.getLong("id"));
            }
            insertAllStudents(lesson);
            return lesson;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertAllStudents(Lesson lesson) {
        String query = "INSERT INTO lesson_student (lesson_id, student_id) VALUES (?, ?);";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Student student : lesson.getStudents()) {
                setStatementForStudent(statement, lesson.getId(), student.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setStatementForStudent(PreparedStatement statement, long id, long secondId) throws SQLException {
        statement.setLong(1, id);
        statement.setLong(2, secondId);
        statement.executeUpdate();
    }

    public Lesson update(Lesson lesson) {
        String query = "UPDATE lessons SET subject = ?, classroom = ?, lecturer_id = ?, time_period_id = ? WHERE id = ?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            TimePeriodDao timePeriodDao = new TimePeriodDao();
            timePeriodDao.update(lesson.getTimePeriod());
            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getClassroom());
            statement.setLong(3, lesson.getLecturer().getId());
            statement.setLong(4, lesson.getTimePeriod().getId());
            statement.setLong(5, lesson.getId());
            statement.executeUpdate();
            updateAllStudents(lesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }

    private void updateAllStudents(Lesson lesson) {
        String query = "UPDATE lesson_student SET student_id = ? WHERE lesson_id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Student student : lesson.getStudents()) {
                setStatementForStudent(statement, student.getId(), lesson.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Lesson lesson) {
        String query = "DELETE FROM lessons WHERE id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            deleteAllStudents(lesson);
            statement.setLong(1, lesson.getId());
            statement.executeUpdate();
            TimePeriodDao timePeriodDao = new TimePeriodDao();
            timePeriodDao.delete(lesson.getTimePeriod().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllStudents(Lesson lesson) {
        String query = "DELETE FROM lesson_student WHERE lesson_id = ? AND student_id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Student student : lesson.getStudents()) {
                setStatementForStudent(statement, lesson.getId(),student.getId() );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lesson getLessonById(long id) {
        String query = "SELECT * FROM lessons WHERE id = ?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Lesson lesson = new Lesson();
                LecturerDao lecturerDao = new LecturerDao();
                TimePeriodDao timePeriodDao = new TimePeriodDao();
                if (resultSet.next()) {
                    lesson.setId(resultSet.getLong(1));
                    lesson.setSubject(resultSet.getString(2));
                    lesson.setClassroom(resultSet.getString(3));
                    lesson.setLecturer(lecturerDao.getById(resultSet.getLong(4)));
                    lesson.setStudents(getAllStudents(lesson));
                    lesson.setTimePeriod(timePeriodDao.getById(id));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Student> getAllStudents(Lesson lesson) {
        String query = "SELECT student_id FROM lesson_student WHERE lesson_id = ?;";
        ArrayList<Student> students = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, lesson.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                StudentDao studentDao = new StudentDao();
                while (resultSet.next()) {
                    Student student = studentDao.getById(resultSet.getLong(1));
                    students.add(student);
                }
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
