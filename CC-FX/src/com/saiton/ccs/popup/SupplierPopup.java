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
 * @author Isu
 */
public class SupplierPopup {
    public SimpleStringProperty colSupId = new SimpleStringProperty("tcSupId");
    public SimpleStringProperty colSupName = new SimpleStringProperty("tcSupName");
    public SimpleStringProperty colNic = new SimpleStringProperty("tcNic");
    public SimpleStringProperty colAddress = new SimpleStringProperty("tcAddress");

    public String getColSupId() {
        return colSupId.get();
    }

    public String getColSupName() {
        return colSupName.get();
    }

    public String getColNic() {
        return colNic.get();
    }

    public String getColAddress() {
        return colAddress.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcSupId = new TableColumn("Supplied ID");
        tcSupId .setMinWidth(100);
        tcSupId .setCellValueFactory(
                new PropertyValueFactory<>("colSupId"));
        
        TableColumn tcSupName = new TableColumn("Supplier Name");
        tcSupName.setMinWidth(100);
        tcSupName.setCellValueFactory(
                new PropertyValueFactory<>("colSupName"));
        
        TableColumn tcNic = new TableColumn("NIC");
        tcNic.setMinWidth(100);
        tcNic.setCellValueFactory(
                new PropertyValueFactory<>("colNic"));
        
        TableColumn tcAddress = new TableColumn("Rate");
        tcAddress.setMinWidth(100);
        tcAddress.setCellValueFactory(
                new PropertyValueFactory<>("colAddress"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcSupId , tcSupName , tcNic , tcAddress);

        return tableView;
    }
}
