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
public class BotPopup {

    public SimpleStringProperty colBotId = new SimpleStringProperty("tcBotId");
    public SimpleStringProperty colCusId = new SimpleStringProperty("tcCusId");
    public SimpleStringProperty colEvtId = new SimpleStringProperty("tcEvtId");
    public SimpleStringProperty colEvtDate = new SimpleStringProperty("tcEvtDate");

    public String getColBotId() {
        return colBotId.get();
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

        TableColumn tcBotId = new TableColumn("Bot Id");
        tcBotId.setMinWidth(100);
        tcBotId.setCellValueFactory(
                new PropertyValueFactory<>("colBotId"));
        
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
        tableView.getColumns().addAll(tcBotId, tcCusId, tcEvtId, tcEvtDate);

        return tableView;
    }

}
