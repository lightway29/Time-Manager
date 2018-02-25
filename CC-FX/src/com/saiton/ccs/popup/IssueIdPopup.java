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
public class IssueIdPopup {

    public SimpleStringProperty colIssueId = new SimpleStringProperty("tcIssueId");
    public SimpleStringProperty colRequesrId = new SimpleStringProperty("tcRequesrId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");

    public String getColIssueId() {
        return colIssueId.get();
    }
    
    public String getColRequesrId() {
        return colRequesrId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcIssueId = new TableColumn("Issue Note Id");
        tcIssueId.setMinWidth(100);
        tcIssueId.setCellValueFactory(
                new PropertyValueFactory<>("colIssueId"));
        
        TableColumn tcRequesrId = new TableColumn("Request Note Id");
        tcRequesrId.setMinWidth(100);
        tcRequesrId.setCellValueFactory(
                new PropertyValueFactory<>("colRequesrId"));
        
        TableColumn tcDate = new TableColumn("Issue Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));     

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcIssueId, tcRequesrId, tcDate);

        return tableView;
    }

}
