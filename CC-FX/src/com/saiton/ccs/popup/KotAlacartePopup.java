/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.popup;

import com.saiton.ccs.base.CustomerRegistrationController;
import com.saiton.ccs.basedao.CustomerRegistrationDAO;
import com.saiton.ccs.uihandle.StagePassable;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Polypackaging-A1
 */
public class KotAlacartePopup {

    public SimpleStringProperty colKotId = new SimpleStringProperty("tcKotId");
    public SimpleStringProperty colSelectionID = new SimpleStringProperty("tcSelectionID");
    public SimpleStringProperty colTableNo = new SimpleStringProperty("tcTableNo");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");

    public String getColKotId() {
        return colKotId.get();
    }

    public String getColSelectionID() {
        return colSelectionID.get();
    }

    public String getColTableNo() {
        return colTableNo.get();
    }

    public String getColCustomerName() {
        return colCustomerName.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcKotId = new TableColumn("Kot ID");
        tcKotId.setMinWidth(100);
        tcKotId.setCellValueFactory(
                new PropertyValueFactory<>("colKotId"));

        TableColumn tcSelectionID = new TableColumn("Selection ID");
        tcSelectionID.setMinWidth(100);
        tcSelectionID.setCellValueFactory(
                new PropertyValueFactory<>("colSelectionID"));

        TableColumn tcTableNo = new TableColumn("Table No");
        tcTableNo.setMinWidth(100);
        tcTableNo.setCellValueFactory(
                new PropertyValueFactory<>("colTableNo"));

        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcKotId, tcSelectionID, tcTableNo, tcCustomerName);

        return tableView;
    }

}
