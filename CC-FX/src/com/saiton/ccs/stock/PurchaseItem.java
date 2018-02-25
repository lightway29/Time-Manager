package com.saiton.ccs.stock;

import javafx.beans.property.*;

/**
 * PurchaseItem
 *
 * @author Saitonya
 */
public class PurchaseItem {

    private final SimpleStringProperty itemId;
    private final SimpleStringProperty batchNo;
    private final SimpleStringProperty qty;
    private final SimpleStringProperty reorder;
    private final SimpleStringProperty remarks;

    public PurchaseItem() {
        itemId = new SimpleStringProperty();
        batchNo = new SimpleStringProperty();
        qty = new SimpleStringProperty();
        reorder = new SimpleStringProperty();
        remarks = new SimpleStringProperty();

    }

    public String getItemId() {
        return itemId.get();
    }

    public void setItemId(String itemId) {
        this.itemId.set(itemId);
    }

    public SimpleStringProperty itemIdProperty() {
        return itemId;
    }

    public String getBatchNo() {
        return batchNo.get();
    }

    public void setBatchNo(String batchNo) {
        this.batchNo.set(batchNo);
    }

    public SimpleStringProperty batchNoProperty() {
        return batchNo;
    }

    public String getQty() {
        return qty.get();
    }

    public void setQty(String qty) {
        this.qty.set(qty);
    }

    public SimpleStringProperty qtyProperty() {
        return qty;
    }

    public String getReorder() {
        return reorder.get();
    }

    public void setReorder(String reorder) {
        this.reorder.set(reorder);
    }

    public SimpleStringProperty reorderProperty() {
        return reorder;
    }

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public SimpleStringProperty remarksProperty() {
        return remarks;
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setItemId(data[0]);
        setBatchNo(data[1]);
        setQty(data[2]);
        setReorder(data[3]);
        setRemarks(data[4]);
    }

    public String[] toStringArray() {
        return new String[]{getItemId(), getBatchNo(), getQty(), getReorder(),
            getRemarks()};

    }
}
