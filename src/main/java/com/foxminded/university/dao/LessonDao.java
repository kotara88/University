package com.foxminded.university.dao;

import com.foxminded.university.domain.Lesson;
import com.foxminded.university.domain.Student;
import com.foxminded.university.domain.TimePeriod;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class LessonDao {
    public Lesson insert(Lesson lesson) {
        String query = "INSERT INTO lessons (subject, classroom, lecturer_id, start_time, end_time) VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp startTime = new Timestamp(lesson.getTimePeriod().getStartTime().getTime());
            Timestamp endTime = new Timestamp(lesson.getTimePeriod().getEndTime().getTime());

            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getClassroom());
            statement.setLong(3, lesson.getLecturer().getId());
            statement.setTimestamp(4, startTime);
            statement.setTimestamp(5, endTime);
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
                statement.setLong(1, lesson.getId());
                statement.setLong(2, student.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lesson update(Lesson lesson) {
        String query = "UPDATE lessons SET subject = ?, classroom = ?, lecturer_id = ?, start_time = ?, end_time = ? WHERE id = ?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lesson.getSubject());
            statement.setString(2, lesson.getClassroom());
            statement.setLong(3, lesson.getLecturer().getId());

            Timestamp startTime = new Timestamp(lesson.getTimePeriod().getStartTime().getTime());
            Timestamp endTime = new Timestamp(lesson.getTimePeriod().getEndTime().getTime());
            statement.setTimestamp(4, startTime);
            statement.setTimestamp(5, endTime);
            statement.setLong(6, lesson.getId());
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
                statement.setLong(1, student.getId());
                statement.setLong(2, lesson.getId());
                statement.executeUpdate();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllStudents(Lesson lesson) {
        String query = "DELETE FROM lesson_student WHERE lesson_id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, lesson.getId());
                statement.executeUpdate();
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

                if (resultSet.next()) {
                    lesson.setId(resultSet.getLong("id"));
                    lesson.setSubject(resultSet.getString("subject"));
                    lesson.setClassroom(resultSet.getString("classroom"));
                    lesson.setLecturer(lecturerDao.getById(resultSet.getLong("lecturer_id")));
                    lesson.setStudents(getAllStudents(lesson));

                    Date startTime = resultSet.getTimestamp("start_time");
                    Date endTime = resultSet.getTimestamp("end_time");
                    lesson.setTimePeriod(new TimePeriod(startTime, endTime));
                    return lesson;
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
                    Student student = studentDao.getById(resultSet.getLong("student_id"));
                    students.add(student);
                }
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Lesson> getAllLessons() {
        String query = "SELECT * FROM lessons;";
        ArrayList<Lesson> lessons = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                LecturerDao lecturerDao = new LecturerDao();
                while (resultSet.next()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }
}
