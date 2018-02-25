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
public class InternalGrnPopup {

    public SimpleStringProperty colGrnId = new SimpleStringProperty("tcGrnId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colDesc = new SimpleStringProperty("tcDesc");

    public String getColGrnId() {
        return colGrnId.get();
    }

    public String getColDate() {
        return colDate.get();
    }

    public String getColDesc() {
        return colDesc.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcGrnId = new TableColumn("Internal GRN ID");
        tcGrnId.setMinWidth(120);
        tcGrnId.setCellValueFactory(
                new PropertyValueFactory<>("colGrnId"));

        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));

        TableColumn tcDesc = new TableColumn("Description");
        tcDesc.setMinWidth(100);
        tcDesc.setCellValueFactory(
                new PropertyValueFactory<>("colDesc"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcGrnId, tcDesc, tcDate);

        return tableView;
    }

}
