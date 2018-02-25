package com.saiton.ccs.base;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class UserNotification {

    private final SimpleStringProperty type;
    private final SimpleBooleanProperty show;

    public UserNotification() {
        this.type = new SimpleStringProperty();
        this.show = new SimpleBooleanProperty();
    }

    public UserNotification(String description,
            Boolean show) {
        this();
        type.set(description);
        this.show.set(show);
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleBooleanProperty showProperty() {
        return show;
    }

    public void setDescription(String description) {
        this.type.set(description);
    }

    public void setShow(Boolean show) {
        this.show.set(show);
    }

    public String getType() {
        return type.get();
    }

    public Boolean getShow() {
        return show.get();
    }

    public String[] toStringArray() {
        return new String[]{typeProperty().get(),
            showProperty().get() ? "1" : "0"};
    }

    public void setFromStringArray(String[] arr) {
        setDescription(arr[0]);
        setShow(arr[1].equals("1"));
    }
}
