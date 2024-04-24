package com.example.csit228_f1_v2;

import java.sql.Time;
import java.util.Date;
import javafx.beans.property.*;

public class Events {
    public StringProperty eventTitle;
    public StringProperty eventDescription;
    public StringProperty eventType;
    public ObjectProperty<Date> eventDate;

    public Events(String title, String desc, String type, Date date) {
        eventTitle = new SimpleStringProperty(title);
        eventDescription = new SimpleStringProperty(desc);
        eventType = new SimpleStringProperty(type);
        eventDate = new SimpleObjectProperty<>(date);
    }

    public StringProperty titleProperty() {
        return eventTitle;
    }

    public StringProperty descProperty() {
        return eventDescription;
    }

    public StringProperty typeProperty() {
        return eventType;
    }

    public ObjectProperty<Date> dateProperty() {
        return eventDate;
    }

    public String getEventTitle() {
        return eventTitle.get();
    }
}
