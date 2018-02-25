package com.saiton.ccs.report;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class Printer {

    private final SimpleStringProperty pid;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;

    public Printer() {
        pid = new SimpleStringProperty();
        name = new SimpleStringProperty();
        type = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public SimpleStringProperty pidProperty() {
        return pid;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getPid() {
        return pid.get();
    }

    public String getName() {
        return name.get();
    }

    public String getType() {
        return type.get();
    }

    public String getDescription() {
        return description.get();
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setPid(data[0]);
        setName(data[1]);
        setType(data[2]);
        setDescription(data[3]);
    }
}
