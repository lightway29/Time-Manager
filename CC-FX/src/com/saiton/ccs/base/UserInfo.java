package com.saiton.ccs.base;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class UserInfo {

    private final SimpleStringProperty eid;
    private final SimpleStringProperty title;
    private final SimpleStringProperty name;
    private final SimpleStringProperty username;
    private final SimpleStringProperty flag;
    private final SimpleBooleanProperty finger;
    private final SimpleStringProperty category;

    public UserInfo() {
        title = new SimpleStringProperty("");
        name = new SimpleStringProperty("");
        username = new SimpleStringProperty("");
        flag = new SimpleStringProperty("");
        finger = new SimpleBooleanProperty(false);
        category = new SimpleStringProperty("");
        eid = new SimpleStringProperty("");
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }
    public SimpleStringProperty eidProperty() {
        return eid;
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty flagProperty() {
        return flag;
    }

    public SimpleBooleanProperty fingerProperty() {
        return finger;
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public void setFlag(String flag) {
        flagProperty().set(flag);
    }

    public void setFinger(Boolean finger) {
        fingerProperty().set(finger);
    }

    public void setCategory(String category) {
        categoryProperty().set(category);
    }
    public void setEid(String eid) {
        eidProperty().set(eid);
    }
    public void setFromStringArray(String[] arr) {
                 setEid(arr[0]);
        setTitle(arr[1]);
        setName(arr[2]);
        setUsername(arr[3]);
        setFlag(arr[4]);
        setCategory(arr[5]);
    }

    public String getEid() {
        return eid.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getName() {
        return name.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getFlag() {
        return flag.get();
    }

    public Boolean getFinger() {
        return finger.get();
    }

    public String getCategory() {
        return category.get();
    }
    
    
}
