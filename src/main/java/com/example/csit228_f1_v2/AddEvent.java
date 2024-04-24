package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Currency;
import java.util.Objects;

public class AddEvent {
    public GridPane pnAddEvent;
    public Label lblFeedback;
    public TextField tfEventTitle;
    public TextArea taEventDesc;
    public TextField tfEventType;
    public AnchorPane apAddEvent;
    public DatePicker dpDate;

    public void onToHomeClick(ActionEvent actionEvent) throws IOException {
        Parent homeview = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("homepage.fxml")));
        AnchorPane p = (AnchorPane) apAddEvent.getParent();

        p.getChildren().remove(apAddEvent);
        p.getChildren().add(homeview);
    }

    public void onAddClick(ActionEvent actionEvent) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblevents (eventTitle, eventDescription, eventType, eventDate, uid) VALUES (?, ?, ?, ?, ?)"
             )) {
            c.setAutoCommit(false);

            String title = tfEventTitle.getText();
            String desc = taEventDesc.getText();
            String type = tfEventType.getText();
            LocalDate date = dpDate.getValue();
            Date sqlDate = Date.valueOf(date);

            statement.setString(1, title);
            statement.setString(2, desc);
            statement.setString(3, type);
            statement.setDate(4, sqlDate);
            statement.setInt(5, CurrentUser.getCurrentUserID());

            statement.executeUpdate();
            lblFeedback.setText("Event successfully registered!");
            lblFeedback.setTextFill(Color.GREEN);

            c.commit();

            tfEventTitle.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
