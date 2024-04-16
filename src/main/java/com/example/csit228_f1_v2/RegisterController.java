package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {
    public GridPane pnRegister;
    public AnchorPane pnMain;

    public TextField txtusername, txtupass;
    @FXML
    protected void onRegisterClick() throws IOException {


        Parent homeview = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("home-view.fxml")));
        AnchorPane p = (AnchorPane) pnRegister.getParent();
        p.getChildren().remove(pnRegister);
        p.getChildren().add(homeview);
    }
}
