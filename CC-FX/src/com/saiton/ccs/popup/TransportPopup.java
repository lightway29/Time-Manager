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
public class TransportPopup {

    public SimpleStringProperty colTransportID = new SimpleStringProperty("tcTransportID");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colFrom = new SimpleStringProperty("tcFrom");
    public SimpleStringProperty colTo = new SimpleStringProperty("tcTo");
    

    public String getColTransportID() {
        return colTransportID.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }
    
    public String getColFrom() {
        return colFrom.get();
    }
    
    public String getColTo() {
        return colTo.get();
    }
    
    

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcTransportID = new TableColumn("Transport ID");
        tcTransportID.setMinWidth(100);
        tcTransportID.setCellValueFactory(
                new PropertyValueFactory<>("colTransportID"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));
        
        TableColumn tcFrom = new TableColumn("From");
        tcFrom.setMinWidth(100);
        tcFrom.setCellValueFactory(
                new PropertyValueFactory<>("colFrom"));
        
        TableColumn tcTo = new TableColumn("To");
        tcTo.setMinWidth(100);
        tcTo.setCellValueFactory(
                new PropertyValueFactory<>("colTo"));
        
        

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcTransportID, tcDate, tcFrom, tcTo);

        return tableView;
    }

}
