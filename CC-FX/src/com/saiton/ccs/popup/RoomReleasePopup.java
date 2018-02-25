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
public class RoomReleasePopup {

    public SimpleStringProperty colReleaseCardNo = new SimpleStringProperty("tcReleaseCardNo");
    public SimpleStringProperty colSummaryId = new SimpleStringProperty("tcSummaryId");
    public SimpleStringProperty colMiniBarNo = new SimpleStringProperty("tcMiniBarNo");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");

    public String getColReleaseCardNo() {
        return colReleaseCardNo.get();
    }
    
    public String getColSummaryId() {
        return colSummaryId.get();
    }
    
    public String getColMiniBarNo() {
        return colMiniBarNo.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcReleaseCardNo = new TableColumn("Release Card No");
        tcReleaseCardNo.setMinWidth(100);
        tcReleaseCardNo.setCellValueFactory(
                new PropertyValueFactory<>("colReleaseCardNo"));
        
        TableColumn tcSummaryId = new TableColumn("Summary ID");
        tcSummaryId.setMinWidth(100);
        tcSummaryId.setCellValueFactory(
                new PropertyValueFactory<>("colSummaryId"));
        
        TableColumn tcMiniBarNo = new TableColumn("MiniBar No");
        tcMiniBarNo.setMinWidth(100);
        tcMiniBarNo.setCellValueFactory(
                new PropertyValueFactory<>("colMiniBarNo"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcReleaseCardNo, tcSummaryId, tcMiniBarNo, tcDate);

        return tableView;
    }

}
