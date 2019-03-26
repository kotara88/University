package com.foxminded.university.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Logger log = LogManager.getLogger(StudentDao.class.getName());

    private static final String DB_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/university";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws DaoException {
        try {
            log.debug("Load database driver");
            Class.forName(DB_DRIVER_CLASS);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            log.error("Couldn't load database driver", e);
            throw new DaoException("Couldn't load database driver", e);
        } catch (SQLException e) {
            log.error("Couldn't get connection to database", e);
            throw new DaoException("Couldn't get connection to database", e);
        }
    }
}
