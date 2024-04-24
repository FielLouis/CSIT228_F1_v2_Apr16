package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

public class EditProfile {
    public AnchorPane apEdit;
    public TextField tfEditUsername;
    public Label lblFeedback;
    public GridPane pnEdit;
    public AnchorPane pnMain;

    public void initialize() {
        tfEditUsername.setText(CurrentUser.getCurrentUser());
    }

    public void onConfirmClick(ActionEvent actionEvent) throws IOException {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET uname=? WHERE id=?"
             )) {
            c.setAutoCommit(false);
            String new_name = tfEditUsername.getText();

            statement.setString(1, new_name);
            statement.setInt(2, CurrentUser.getCurrentUserID());

            statement.executeUpdate();
            CurrentUser.setCurrentUser(new_name);
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Parent homeview = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("homepage.fxml")));
        AnchorPane p = (AnchorPane) apEdit.getParent();

        p.getChildren().remove(apEdit);
        p.getChildren().add(homeview);
    }

    public void onGoBackClick(ActionEvent actionEvent) throws IOException {
        Parent homeview = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("homepage.fxml")));
        AnchorPane p = (AnchorPane) apEdit.getParent();

        p.getChildren().remove(apEdit);
        p.getChildren().add(homeview);
    }

    public void onDeleteClick(ActionEvent actionEvent) throws IOException {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM tblusers WHERE id=?"
             )){
            c.setAutoCommit(false);
            statement.setInt(1, CurrentUser.getCurrentUserID());
            statement.executeUpdate();
            c.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Cant delete user when user still has some events");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // setting current user to null
        CurrentUser.logoutCurrentUser();

        Parent login = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login-view.fxml")));
        AnchorPane p = (AnchorPane) apEdit.getParent();
        p.getChildren().remove(apEdit);
        p.getChildren().add(login);
    }
}
