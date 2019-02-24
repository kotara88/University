package com.foxminded.university.dao;

import com.foxminded.university.domain.TimePeriod;

import java.sql.*;
import java.util.Date;

public class TimePeriodDao {

    protected TimePeriod insert(TimePeriod timePeriod) {
        String query = "INSERT INTO time_period (start_time, end_time) VALUES (?, ?)";

        Timestamp startTime = new Timestamp(timePeriod.getStartTime().getTime());
        Timestamp endTime = new Timestamp(timePeriod.getEndTime().getTime());

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, startTime);
            statement.setTimestamp(2, endTime);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                timePeriod.setId(resultSet.getLong("id"));
                return timePeriod;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected TimePeriod update(TimePeriod timePeriod) {
        String query = "UPDATE time_period SET start_time = ?, end_time = ? WHERE id = ?";

        Timestamp startTime = new Timestamp(timePeriod.getStartTime().getTime());
        Timestamp endTime = new Timestamp(timePeriod.getEndTime().getTime());

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, startTime);
            statement.setTimestamp(2, endTime);
            statement.setLong(3, timePeriod.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timePeriod;
    }

    protected void delete(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM time_period WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected TimePeriod getById(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM time_period WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Date startTime = resultSet.getTimestamp("start_time");
                Date endTime = resultSet.getTimestamp("end_time");
                TimePeriod timePeriod = new TimePeriod(id, startTime, endTime);
                return timePeriod;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
