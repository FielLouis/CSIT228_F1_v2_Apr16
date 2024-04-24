package com.example.csit228_f1_v2;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ButtonEditTableCell<S> extends TableCell<S, Void> {
    private final Button button;
    public ButtonEditTableCell(String buttonText) {
        this.button = new Button(buttonText);
        this.button.setOnAction(event -> {
            Events event1 = (Events) getTableView().getItems().get(getIndex());
//            deleteEventFromDatabase(event1);


        });
    }
}
