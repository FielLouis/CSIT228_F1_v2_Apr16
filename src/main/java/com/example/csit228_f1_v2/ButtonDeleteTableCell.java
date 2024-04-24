package com.example.csit228_f1_v2;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ButtonDeleteTableCell<S> extends TableCell<S, Void> {
    private final Button button;
    public ButtonDeleteTableCell(String buttonText) {
        this.button = new Button(buttonText);
        this.button.setOnAction(event -> {
            Events event1 = (Events) getTableView().getItems().get(getIndex());
            deleteEventFromDatabase(event1);
            getTableView().getItems().remove(event1);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }

    private void deleteEventFromDatabase(Events event) {
        String query = "DELETE FROM tblevents WHERE eventTitle=?";
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(query)) {
            preparedStatement.setString(1, event.getEventTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
