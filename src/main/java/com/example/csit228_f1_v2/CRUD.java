package com.example.csit228_f1_v2;

import java.sql.*;

public class CRUD {
    public static void insertData() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblusers (name, email) VALUES (?, ?)"
             )) {

            String name = "Karylle de los Reyes";
            String email = "karylle@gmail.com";

            statement.setString(1, name);
            statement.setString(2, email);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows Inserted " + rowsInserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void readData() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String email = res.getString("email");

                System.out.println("ID: " + id + "\nName: " + name + "\nEmail: " + email + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upadateData() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET name=? WHERE id=?"
             )) {
            String new_name = "Dechie de los Reyes";
            int id = 2;

            statement.setString(1, new_name);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteData() {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM tblusers WHERE id>=? AND id<=?"
             )){

            int startingID = 2;
            int endingID = 4;
            statement.setInt(1, startingID);
            statement.setInt(2, endingID);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows Deleted: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
