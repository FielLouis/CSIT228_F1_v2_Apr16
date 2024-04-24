package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class EditEvent {
    public AnchorPane apEditEvent;
    public TextField tfEventTitle;
    public TextArea taEventDesc;
    public TextField tfEventType;
    public DatePicker dpDate;
    public Label lblFeedback;

    public void initialize() {

    }

    public void onToHomeClick(ActionEvent actionEvent) throws IOException {
        Parent homeview = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("homepage.fxml")));
        AnchorPane p = (AnchorPane) apEditEvent.getParent();

        p.getChildren().remove(apEditEvent);
        p.getChildren().add(homeview);
    }

    public void onEditEventClick(ActionEvent event) {
        lblFeedback.setText("Event Update is not implemented (yet allegedly)... WOMP WOMP :(");
        lblFeedback.setTextFill(Color.RED);
    }
}
