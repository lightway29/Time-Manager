package com.saiton.ccs.base;

import java.util.Objects;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class Notification {

    private final SimpleStringProperty nid;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;
    private final SimpleStringProperty ui;
    private final SimpleStringProperty addedUser;
    private final SimpleStringProperty dateAdded;
    private final SimpleStringProperty dateResolved;
    private final SimpleBooleanProperty resolved;
    private final SimpleStringProperty resolvedUser;

    public Notification() {
        this.nid = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.ui = new SimpleStringProperty();
        this.addedUser = new SimpleStringProperty();
        this.dateAdded = new SimpleStringProperty();
        this.dateResolved = new SimpleStringProperty();
        this.resolved = new SimpleBooleanProperty();
        this.resolvedUser = new SimpleStringProperty();
    }

    public SimpleStringProperty nidProperty() {
        return nid;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public SimpleStringProperty uiProperty() {
        return ui;
    }

    public SimpleStringProperty addedUserProperty() {
        return addedUser;
    }

    public SimpleStringProperty dateAddedProperty() {
        return dateAdded;
    }

    public SimpleStringProperty dateResolvedProperty() {
        return dateResolved;
    }

    public SimpleBooleanProperty resolvedProperty() {
        return resolved;
    }

    public SimpleStringProperty resolvedUserProperty() {
        return resolvedUser;
    }

    public void setNid(String nid) {
        this.nid.set(nid);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setUi(String ui) {
        this.ui.set(ui);
    }

    public void setAddedUser(String addedUser) {
        this.addedUser.set(addedUser);
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded.set(dateAdded);
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved.set(dateResolved);
    }

    public void setResolved(Boolean resolved) {
        this.resolved.set(resolved);
    }

    public void setResolvedUser(String resolvedUser) {
        this.resolvedUser.set(resolvedUser);
    }

    public String getNid() {
        return nid.get();
    }

    public String getType() {
        return type.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getUi() {
        return ui.get();
    }

    public String getAddedUser() {
        return addedUser.get();
    }

    public String getDateAdded() {
        return dateAdded.get();
    }

    public String getDateResolved() {
        return dateResolved.get();
    }

    public Boolean getResolved() {
        return resolved.get();
    }

    public String getResolvedUser() {
        return resolvedUser.get();
    }

    public void setFromStringArray(String[] data){
        setNid(data[0]);
        setType(data[1]);
        setDescription(data[2]);
        setUi(data[3]);
        setAddedUser(data[4]);
        setDateAdded(data[5]);
        setDateResolved(data[6]);
        setResolved(data[7].equals("1"));
        setResolvedUser(data[8]);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nid.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notification other = (Notification) obj;
        return Objects.equals(this.nid.get(), other.nid.get());
    }
    
}
