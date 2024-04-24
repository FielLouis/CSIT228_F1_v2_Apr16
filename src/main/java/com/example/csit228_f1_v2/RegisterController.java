package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterController {
    public AnchorPane pnRegister;
    public TextField tfUsername;
    public TextField tfTmpPass, tfConfirmPass;
    public PasswordField pfUserPass, pfConfirmPass;
    public Label lblFeedback;
    public GridPane pnLogin;

    @FXML
    protected void onRegisterClick() {
        //insert data
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblusers (uname, upassword) VALUES (?, ?)"
             )) {

            String username = tfUsername.getText();
            String userpassword = String.valueOf(pfUserPass.getText().hashCode());
            String confirmpassword = String.valueOf(pfConfirmPass.getText().hashCode());
            boolean usernameAlreadyExists = false;

            if(!confirmpassword.equals(userpassword)) {
                lblFeedback.setText("Password does not match!");
                lblFeedback.setTextFill(Color.RED);
                return;
            }

            //read existing data
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                int id = res.getInt("id");
                String name = res.getString("uname");

                //checks if inputted username already exists or not
                if(name.equals(username)) {
                    usernameAlreadyExists = true;
                    break;
                }
            }

            //when inputted username already exists, dont insert, if not, then insert
            if(usernameAlreadyExists) {
                lblFeedback.setText("Username already exists!");
                lblFeedback.setTextFill(Color.RED);
            } else {
                statement.setString(1, username);
                statement.setString(2, userpassword);

                statement.executeUpdate();
                lblFeedback.setText("User successfully registered!");
                lblFeedback.setTextFill(Color.GREEN);
            }

            //set all fields into no characters after button click
            tfUsername.setText("");
            pfUserPass.setText("");
            pfConfirmPass.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onToLogInClick() throws IOException{
        Parent login = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login-view.fxml")));
        AnchorPane p = (AnchorPane) pnRegister.getParent();
        p.getChildren().remove(pnRegister);
        p.getChildren().add(login);
    }

    public void onShowPassword(MouseEvent mouseEvent) {
        tfTmpPass.setText(pfUserPass.getText());
        tfTmpPass.setVisible(true);
        pfUserPass.setVisible(false);
    }

    public void onUnshowPassword(MouseEvent mouseEvent) {
        pfUserPass.setVisible(true);
        pfUserPass.setText(tfTmpPass.getText());
        tfTmpPass.setVisible(false);
    }

    public void onShowPassword1(MouseEvent mouseEvent) {
        tfConfirmPass.setText(pfConfirmPass.getText());
        tfConfirmPass.setVisible(true);
        pfConfirmPass.setVisible(false);
    }

    public void onUnshowPassword1(MouseEvent mouseEvent) {
        pfConfirmPass.setVisible(true);
        pfConfirmPass.setText(tfConfirmPass.getText());
        tfConfirmPass.setVisible(false);
    }
}
