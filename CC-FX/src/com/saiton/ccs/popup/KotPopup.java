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
public class KotPopup {

    public SimpleStringProperty colKotId = new SimpleStringProperty("tcKotId");
    public SimpleStringProperty colCusId = new SimpleStringProperty("tcCusId");
    public SimpleStringProperty colEvtId = new SimpleStringProperty("tcEvtId");
    public SimpleStringProperty colEvtDate = new SimpleStringProperty(
            "tcEvtDate");

    public String getColKotId() {
        return colKotId.get();
    }

    public String getColCusId() {
        return colCusId.get();
    }

    public String getColEvtId() {
        return colEvtId.get();
    }

    public String getColEvtDate() {
        return colEvtDate.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcKotId = new TableColumn("Kot Id");
        tcKotId.setMinWidth(100);
        tcKotId.setCellValueFactory(
                new PropertyValueFactory<>("colKotId"));

        TableColumn tcCusId = new TableColumn("Customer Id");
        tcCusId.setMinWidth(100);
        tcCusId.setCellValueFactory(
                new PropertyValueFactory<>("colCusId"));

        TableColumn tcEvtId = new TableColumn("Event Id");
        tcEvtId.setMinWidth(100);
        tcEvtId.setCellValueFactory(
                new PropertyValueFactory<>("colEvtId"));

        TableColumn tcEvtDate = new TableColumn("Event Date");
        tcEvtDate.setMinWidth(100);
        tcEvtDate.setCellValueFactory(
                new PropertyValueFactory<>("colEvtDate"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcKotId, tcCusId, tcEvtId, tcEvtDate);

        return tableView;
    }

}
