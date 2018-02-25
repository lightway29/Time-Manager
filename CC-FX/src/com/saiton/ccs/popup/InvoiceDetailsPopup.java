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
public class InvoiceDetailsPopup {

    public SimpleStringProperty colInvoiceNo = new SimpleStringProperty(
            "tcInvoiceNo");
    public SimpleStringProperty colIsTaxInvoiced = new SimpleStringProperty(
            "tcIsTaxInvoice");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colPoNo = new SimpleStringProperty("tcPoNo");
    public SimpleStringProperty colPoDate = new SimpleStringProperty("tcPoDate");

    public String getColInvoiceNo() {
        return colInvoiceNo.get();
    }

    public String getColIsTaxInvoiced() {
        return colIsTaxInvoiced.get();
    }

    public String getColDate() {
        return colDate.get();
    }

    public String getColPoNo() {
        return colPoNo.get();
    }

    public String getColPoDate() {
        return colPoDate.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcInvoiceNo = new TableColumn("Invoice No");
        tcInvoiceNo.setMinWidth(100);
        tcInvoiceNo.setCellValueFactory(
                new PropertyValueFactory<>("colInvoiceNo"));

        TableColumn tcIsTaxInvoice = new TableColumn("Salse Executive");
        tcIsTaxInvoice.setMinWidth(150);
        tcIsTaxInvoice.setCellValueFactory(
                new PropertyValueFactory<>("colIsTaxInvoiced"));

        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));

        TableColumn tcPoNo = new TableColumn("Customer Type");
        tcPoNo.setMinWidth(150);
        tcPoNo.setCellValueFactory(
                new PropertyValueFactory<>("colPoNo"));

        TableColumn tcPoDate = new TableColumn("Vehicle No");
        tcPoDate.setMinWidth(150);
        tcPoDate.setCellValueFactory(
                new PropertyValueFactory<>("colPoDate"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcInvoiceNo, tcIsTaxInvoice, tcDate, tcPoNo,tcPoDate);

        return tableView;
    }

}
