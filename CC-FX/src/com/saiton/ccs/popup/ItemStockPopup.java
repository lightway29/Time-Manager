/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.popup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Polypackaging-A1
 */
public class ItemStockPopup {

    public SimpleStringProperty colItemID = new SimpleStringProperty("tcItemID");
    public SimpleStringProperty colBatchNo = new SimpleStringProperty(
            "tcBatchNo");
    public SimpleStringProperty colItemName = new SimpleStringProperty(
            "tcItemName");
    public SimpleStringProperty colQty = new SimpleStringProperty("tcQty");

    public String getColItemID() {
        return colItemID.get();
    }

    public String getColBatchNo() {
        return colBatchNo.get();
    }

    public String getColItemName() {
        return colItemName.get();
    }

    public String getColQty() {
        return colQty.get();
    }
    
    

   
    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcItemID = new TableColumn("Item ID");
        tcItemID.setMinWidth(100);
        tcItemID.setCellValueFactory(
                new PropertyValueFactory<>("colItemID"));

        TableColumn tcBatchNo = new TableColumn("Batch No");
        tcBatchNo.setMinWidth(100);
        tcBatchNo.setCellValueFactory(
                new PropertyValueFactory<>("colBatchNo"));

        TableColumn tcItemName = new TableColumn("Item Name");
        tcItemName.setMinWidth(100);
        tcItemName.setCellValueFactory(
                new PropertyValueFactory<>("colItemName"));

        TableColumn tcQty = new TableColumn("Qty");
        tcQty.setMinWidth(100);
        tcQty.setCellValueFactory(
                new PropertyValueFactory<>("colQty"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcItemID, tcBatchNo, tcItemName, tcQty);

        return tableView;
    }

}
