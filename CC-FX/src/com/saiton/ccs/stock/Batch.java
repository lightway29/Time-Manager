package com.saiton.ccs.stock;

import javafx.beans.property.*;

/**
 * Batch
 *
 * @author Saitonya
 */
public class Batch {

    private final SimpleStringProperty batch;
    private final SimpleStringProperty qty;
    private final SimpleStringProperty price;
    private final SimpleStringProperty reorder;

    public Batch() {
        batch = new SimpleStringProperty();
        qty = new SimpleStringProperty();
        price = new SimpleStringProperty();
        reorder = new SimpleStringProperty();

    }

    public String getBatch() {
        return batch.get();
    }

    public void setBatch(String batch) {
        this.batch.set(batch);
    }

    public SimpleStringProperty batchProperty() {
        return batch;
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

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public SimpleStringProperty priceProperty() {
        return price;
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

    @Override
    public String toString() {
        return batch.get();
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setBatch(data[0]);
        setQty(data[1]);
        setPrice(data[2]);
        setReorder(data[3]);
    }
}
