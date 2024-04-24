package com.example.csit228_f1_v2;

import java.sql.*;

public class CREATE {
    public static void createTableUser() {
        try (Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS tblusers" +
                            "( id INTEGER(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                            "uname VARCHAR(50) NOT NULL," +
                            "upassword VARCHAR(50) NOT NULL )"
            )) {
            c.setAutoCommit(false);
            statement.execute();
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableEvent() {
        try (Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS tblevents" +
                            "( eventid INTEGER(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                            "eventTitle VARCHAR(50) NOT NULL," +
                            "eventDescription VARCHAR(255) NOT NULL," +
                            "eventType VARCHAR(50) NOT NULL," +
                            "eventDate DATE NOT NULL," +
                            "uid INTEGER(11) NOT NULL," +
                            "FOREIGN KEY (uid) REFERENCES tblusers(id))"
            )) {
            c.setAutoCommit(false);
            statement.execute();
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
