package com.saiton.ccs.stock;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class Supplier {

    private final SimpleStringProperty sid;
    private final SimpleStringProperty name;
    private final SimpleStringProperty regNo;
    private final SimpleStringProperty address;

    public Supplier() {
        sid = new SimpleStringProperty();
        name = new SimpleStringProperty();
        regNo = new SimpleStringProperty();
        address = new SimpleStringProperty();
    }

    public SimpleStringProperty sidProperty() {
        return sid;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty regNoProperty() {
        return regNo;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getSid() {
        return sid.get();
    }

    public void setSid(String sid) {
        this.sid.set(sid);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRegNo() {
        return regNo.get();
    }

    public void setRegNo(String regNo) {
        this.regNo.set(regNo);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setSid(data[0]);
        setName(data[1]);
        setRegNo(data[2]);
        setAddress(data[3]);
    }
}
