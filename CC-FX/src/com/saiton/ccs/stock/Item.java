package com.saiton.ccs.stock;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Saitonya
 */
public class Item {

    private final SimpleStringProperty itemId;
    private final SimpleStringProperty name;
//    private final SimpleStringProperty category;
//    private final SimpleStringProperty unit;
    private final SimpleStringProperty quantity;

    public Item() {
        itemId = new SimpleStringProperty();
        name = new SimpleStringProperty();
//        category = new SimpleStringProperty();
//        unit = new SimpleStringProperty();
        quantity = new SimpleStringProperty();
    }

    public SimpleStringProperty itemIdProperty() {
        return itemId;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

//    public SimpleStringProperty gcategoryProperty() {
//        return category;
//    }
//
//    public SimpleStringProperty unitProperty() {
//        return unit;
//    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public String getItemId() {
        return itemId.get();
    }

    public void setItemId(String itemId) {
        this.itemId.set(itemId);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

//    public String getCategory() {
//        return category.get();
//    }
//
//    public void setCategory(String category) {
//        this.category.set(category);
//    }
//
//    public String getUnit() {
//        return unit.get();
//    }
//
//    public void setUnit(String unit) {
//        this.unit.set(unit);
//    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public void setFromStringArray(String[] data) {
        if (data == null) {
            return;
        }
        setItemId(data[0]);
        setName(data[1]);
//        setCategory(data[2]);
  //      setUnit(data[2]);
    }
    public void setFromStringArrayQty(String[] data) {
        if (data == null) {
            return;
        }
        setItemId(data[0]);
        setName(data[1]);
//        setCategory(data[2]);
//        setUnit(data[3]);
        setQuantity(data[2]);
    }
}
