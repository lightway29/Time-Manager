package com.saiton.ccs.report;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class PrinterReport {

    private final SimpleStringProperty pid;
    private final SimpleStringProperty pname;
    private final SimpleStringProperty ptype;
    private final SimpleStringProperty pdescription;
    private final SimpleStringProperty rid;
    private final SimpleStringProperty rname;
    private final SimpleStringProperty rtype;

    public PrinterReport() {
        pid = new SimpleStringProperty();
        pname = new SimpleStringProperty();
        ptype = new SimpleStringProperty();
        pdescription = new SimpleStringProperty();
        rid = new SimpleStringProperty();
        rname = new SimpleStringProperty();
        rtype = new SimpleStringProperty();
    }

    public SimpleStringProperty pidProperty() {
        return pid;
    }

    public SimpleStringProperty pnameProperty() {
        return pname;
    }

    public SimpleStringProperty ptypeProperty() {
        return ptype;
    }

    public SimpleStringProperty pdescriptionProperty() {
        return pdescription;
    }

    public SimpleStringProperty ridProperty() {
        return rid;
    }

    public SimpleStringProperty rnameProperty() {
        return rname;
    }

    public SimpleStringProperty rtypeProperty() {
        return rtype;
    }

    public String getPid() {
        return pid.get();
    }

    public String getPname() {
        return pname.get();
    }

    public String getPtype() {
        return ptype.get();
    }

    public String getPdescription() {
        return pdescription.get();
    }

    public String getRid() {
        return rid.get();
    }

    public String getRname() {
        return rname.get();
    }

    public String getRtype() {
        return rtype.get();
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    public void setPname(String name) {
        this.pname.set(name);
    }

    public void setPtype(String type) {
        this.ptype.set(type);
    }

    public void setPdescription(String description) {
        this.pdescription.set(description);
    }

    public void setRid(String rid) {
        this.rid.set(rid);
    }

    public void setRname(String name) {
        this.rname.set(name);
    }

    public void setRtype(String type) {
        this.rtype.set(type);
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setPid(data[0]);
        setPname(data[1]);
        setPtype(data[2]);
        setPdescription(data[3]);
        setRid(data[4]);
        setRname(data[5]);
        setRtype(data[6]);
    }

    public String[] toStringArray() {
        return new String[]{getPid(), getRid()};
    }
}
