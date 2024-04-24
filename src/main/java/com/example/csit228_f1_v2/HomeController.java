package com.example.csit228_f1_v2;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    public Button btnLogOut, btnEditProfile;
    public Label lblUsername;
    public AnchorPane apHome;

    public void initialize() {
        //initializing current username display
        lblUsername.setText(CurrentUser.getCurrentUser());
    }

    public void onToEditProfileClick() throws IOException {
        Parent edit = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("edit-profile.fxml")));
        AnchorPane p = (AnchorPane) apHome.getParent();
        p.getChildren().remove(apHome);
        p.getChildren().add(edit);
    }

    public void onLogOutClick() throws IOException {
        // setting current user to null
        CurrentUser.logoutCurrentUser();

        Parent login = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login-view.fxml")));
        AnchorPane p = (AnchorPane) apHome.getParent();
        p.getChildren().remove(apHome);
        p.getChildren().add(login);
    }
}
