package com.saiton.ccs.stock;

import javafx.beans.property.*;

/**
 * Purchase
 *
 * @author Saitonya
 */
public class Purchase {

    private final SimpleStringProperty pid;
    private final SimpleStringProperty sid;
    private final SimpleStringProperty name;
    private final SimpleStringProperty date;
    private final SimpleStringProperty user;

    public Purchase() {
        pid = new SimpleStringProperty();
        sid = new SimpleStringProperty();
        name = new SimpleStringProperty();
        date = new SimpleStringProperty();
        user = new SimpleStringProperty();

    }

    public String getPid() {
        return pid.get();
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    public SimpleStringProperty pidProperty() {
        return pid;
    }

    public String getSid() {
        return sid.get();
    }

    public void setSid(String sid) {
        this.sid.set(sid);
    }

    public SimpleStringProperty sidProperty() {
        return sid;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setPid(data[0]);
        setSid(data[1]);
        setName(data[2]);
        setDate(data[3]);
        setUser(data[4]);
    }
}
