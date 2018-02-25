package com.saiton.ccs.base;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class UserPermission {

    private SimpleStringProperty type;
    private SimpleBooleanProperty insert;
    private SimpleBooleanProperty update;
    private SimpleBooleanProperty delete;
    private SimpleBooleanProperty view;

    public UserPermission() {
        type = new SimpleStringProperty("");
        insert = new SimpleBooleanProperty(false);
        update = new SimpleBooleanProperty(false);
        delete = new SimpleBooleanProperty(false);
        view = new SimpleBooleanProperty(false);
    }

    public UserPermission(String description) {
        this();
        type.set(description);
    }

    public void setType(String type) {
        typeProperty().set(type);
    }

    public void setCanInsert(Boolean insert) {
        insertProperty().set(insert);
    }

    public void setCanUpdate(Boolean update) {
        updateProperty().set(update);
    }

    public void setCanDelete(Boolean delete) {
        deleteProperty().set(delete);
    }

    public void setCanView(Boolean view) {
        viewProperty().set(view);
    }

    public String[] toStringArray() {
        return new String[]{typeProperty().get(),
            insertProperty().get() ? "1" : "0",
            updateProperty().get() ? "1" : "0",
            deleteProperty().get() ? "1" : "0",
            viewProperty().get() ? "1" : "0"
        };
    }

    public void setFromStringArray(String[] arr) {
        setType(arr[0]);
        setCanInsert(arr[1].equals("1"));
        setCanUpdate(arr[2].equals("1"));
        setCanDelete(arr[3].equals("1"));
        setCanView(arr[4].equals("1"));
    }

    public String getType() {
        return type.get();
    }

    public Boolean canInsert() {
        return insert.get();
    }

    public Boolean canUpdate() {
        return update.get();
    }

    public Boolean canDelete() {
        return delete.get();
    }

    public Boolean canView() {
        return view.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleBooleanProperty insertProperty() {
        return insert;
    }

    public SimpleBooleanProperty updateProperty() {
        return update;
    }

    public SimpleBooleanProperty deleteProperty() {
        return delete;
    }

    public SimpleBooleanProperty viewProperty() {
        return view;
    }
}
