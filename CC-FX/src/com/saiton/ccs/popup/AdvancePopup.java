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
public class AdvancePopup {

    public SimpleStringProperty colAdvanceId = new SimpleStringProperty("tcAdvanceId");
    public SimpleStringProperty colAdvanceDate = new SimpleStringProperty("tcAdvanceDate");
    public SimpleStringProperty colAdvanceAmount = new SimpleStringProperty("tcAdvanceAmount");

    public String getColAdvanceId() {
        return colAdvanceId.get();
    }

    public String getColAdvanceDate() {
        return colAdvanceDate.get();
    }

    public String getColAdvanceAmount() {
        return colAdvanceAmount.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcAdvanceId = new TableColumn("Advance Id");
        tcAdvanceId.setMinWidth(100);
        tcAdvanceId.setCellValueFactory(
                new PropertyValueFactory<>("colAdvanceId"));
        
        TableColumn tcAdvanceDate = new TableColumn("Advance Date");
        tcAdvanceDate.setMinWidth(100);
        tcAdvanceDate.setCellValueFactory(
                new PropertyValueFactory<>("colAdvanceDate"));
        
        TableColumn tcAdvanceAmount = new TableColumn("Advance Amount");
        tcAdvanceAmount.setMinWidth(100);
        tcAdvanceAmount.setCellValueFactory(
                new PropertyValueFactory<>("colAdvanceAmount"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcAdvanceId, tcAdvanceDate, tcAdvanceAmount);

        return tableView;
    }

}
