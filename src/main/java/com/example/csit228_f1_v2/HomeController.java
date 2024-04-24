package com.example.csit228_f1_v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    public Button btnLogOut, btnEditProfile;
    public Label lblUsername;
    public AnchorPane apHome;
    public Button btnAddEvent;
    public TableColumn btnDelColumn;
    public TableColumn btnEditColumn;
    @FXML
    private TableView<Events> tblEvents;
    @FXML
    private TableColumn<Events, String> titleColumn;
    @FXML
    private TableColumn<Events, String> descColumn;
    @FXML
    private TableColumn<Events, String> typeColumn;
    @FXML
    private TableColumn<Events, Date> dateColumn;
    
    private final ObservableList<Events> eventList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing current username display
        lblUsername.setText(CurrentUser.getCurrentUser());

        titleColumn.setCellValueFactory(eventsStringCellDataFeatures -> eventsStringCellDataFeatures.getValue().titleProperty());
        descColumn.setCellValueFactory(eventsStringCellDataFeatures -> eventsStringCellDataFeatures.getValue().descProperty());
        typeColumn.setCellValueFactory(eventsStringCellDataFeatures -> eventsStringCellDataFeatures.getValue().typeProperty());
        dateColumn.setCellValueFactory(eventsDateCellDataFeatures -> eventsDateCellDataFeatures.getValue().dateProperty());
        btnDelColumn.setCellFactory(param -> new ButtonDeleteTableCell("Delete") {
            private final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    Events eventToDelete = (Events) getTableView().getItems().get(getIndex());
                    deleteEvent(eventToDelete.getEventTitle());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        btnEditColumn.setCellFactory(param -> new ButtonEditTableCell<>("Edit") {
            private final Button updateButton = new Button("Edit");
            {
                updateButton.setOnAction(event -> {
                    Events eventToUpdate = (Events) getTableView().getItems().get(getIndex());

                    try {
                        Parent edit = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("edit-event.fxml")));
                        AnchorPane p = (AnchorPane) apHome.getParent();
                        p.getChildren().remove(apHome);
                        p.getChildren().add(edit);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });


        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblevents WHERE uid=?";
            PreparedStatement preparedStatement = c.prepareStatement(query);

            preparedStatement.setInt(1, CurrentUser.getCurrentUserID());
            ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                String title = res.getString("eventTitle");
                String desc = res.getString("eventDescription");
                String type = res.getString("eventType");
                Date date = res.getDate("eventDate");

                Events e = new Events(title, desc, type, date);
                eventList.add(e);
            }

            tblEvents.setItems(eventList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEvent(String title) {
        String query = "DELETE FROM tblevents WHERE uid=? AND eventTitle=?";
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(query)) {
            c.setAutoCommit(false);
            preparedStatement.setInt(1, CurrentUser.getCurrentUserID());
            preparedStatement.setString(2, title);
            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows > 0) {
                eventList.removeIf(event -> event.getEventTitle().equals(title));
            }
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void onAddEventClick() throws IOException {
        Parent add = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("add-event.fxml")));
        AnchorPane p = (AnchorPane) apHome.getParent();
        p.getChildren().remove(apHome);
        p.getChildren().add(add);
    }
}
