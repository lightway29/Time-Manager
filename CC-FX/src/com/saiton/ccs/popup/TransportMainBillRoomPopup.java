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
public class TransportMainBillRoomPopup {

    public SimpleStringProperty colTransportID = new SimpleStringProperty("tcTransportID");
    public SimpleStringProperty colFrom = new SimpleStringProperty("tcFrom");
    public SimpleStringProperty colTo = new SimpleStringProperty("tcTo");
    public SimpleStringProperty colCharge = new SimpleStringProperty("tcCharge");
    

    public String getColTransportID() {
        return colTransportID.get();
    }
    
    public String getColFrom() {
        return colFrom.get();
    }
    
    public String getColTo() {
        return colTo.get();
    }

    public String getColCharge() {
        return colCharge.get();
    }
    
    

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcTransportID = new TableColumn("Transport ID");
        tcTransportID.setMinWidth(100);
        tcTransportID.setCellValueFactory(
                new PropertyValueFactory<>("colTransportID"));
        
        TableColumn tcFrom = new TableColumn("From");
        tcFrom.setMinWidth(100);
        tcFrom.setCellValueFactory(
                new PropertyValueFactory<>("colFrom"));
        
        TableColumn tcTo = new TableColumn("To");
        tcTo.setMinWidth(100);
        tcTo.setCellValueFactory(
                new PropertyValueFactory<>("colTo"));   
        
        TableColumn tcCharge = new TableColumn("Charge");
        tcCharge.setMinWidth(100);
        tcCharge.setCellValueFactory(
                new PropertyValueFactory<>("colCharge"));
        
        

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcTransportID, tcFrom, tcTo, tcCharge);

        return tableView;
    }

}
