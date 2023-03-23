package com.gproject.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {

    private static String url = "jdbc:postgresql://localhost:5433/GP01";
    private static String user = "postgres";
    private static String password = "root";
    private static final Logger LOGGER =
            Logger.getLogger(JdbcConnection.class.getName());
    private static JdbcConnection connection;

    private JdbcConnection() {
//        if (connection.isEmpty()) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
            LOGGER.log(Level.SEVERE, null, e);
        }

//            try {
//                connection = Optional.ofNullable(
//                        DriverManager.getConnection(url, user, password));
//            } catch (SQLException ex) {
//                LOGGER.log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return connection;
    }


    public static JdbcConnection getInstance() {
        if (connection == null) {
            connection = new JdbcConnection();
        }
        return connection;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}