/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.stock;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.stockdao.ReturnNoteDAO;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.util.DateFormatter;
import com.saiton.ccs.validations.CustomDatePickerValidationImpl;
import com.saiton.ccs.validations.CustomTableViewValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author MewanZ
 */
public class ReturnOverViewController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

//<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private TextField txtName;

    @FXML
    private Button btnApprove;

    @FXML
    private TableView<Item> tblReturnNoteList;

    @FXML
    private RadioButton radioDestroy;

    @FXML
    private RadioButton radioAddToMainStock;

    @FXML
    private DatePicker dpkTo;

    @FXML
    private RadioButton radioReturnToSupplier;

    @FXML
    private RadioButton radioInternalReturnNote;

    @FXML
    private TextField txtItemId;

    @FXML
    private TableColumn<Item, String> tcItemId;

    @FXML
    private TextField txtBatchNo;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtReturnNoteNo;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtDate2;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtResolveId;

    @FXML
    private TextField txtUsername;

    @FXML
    private TableColumn<Item, String> tcType;

    @FXML
    private RadioButton radioExternalReturnNote;

    @FXML
    private TableColumn<Item, String> tcReturnNoteId;

    @FXML
    private TableColumn<Item, String> tcDate;

    @FXML
    private Insets x1;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Item, String> tcName;

    @FXML
    private DatePicker dpkFrom;

    @FXML
    private TableColumn<Item, String> tcBatchNo;

    @FXML
    private TableColumn<Item, String> tcQty;

    @FXML
    private Label dpkFrom1;
//</editor-fold>

    private Stage stage;
    private String userId;
    Item item = new Item();
    private ObservableList tableItemData = FXCollections.observableArrayList();
    private final ValidationSupport validationSupport
            = new ValidationSupport();
    private final ValidationSupport validationSupportDateRange
            = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();
    private MessageBox mb;
    private DateFormatter date = new DateFormatter();
    private ReturnNoteDAO returnNoteDAO = new ReturnNoteDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtDate.setText(LocalDate.now().toString());

        date.format("yyyy-MM-dd", dpkTo);
        date.format("yyyy-MM-dd", dpkFrom);
        dpkTo.setValue(LocalDate.now());
        dpkFrom.setValue(LocalDate.now());
        radioInternalReturnNote.setSelected(true);
        radioReturnToSupplier.setSelected(true);
        userId = "EM0001";
        mb = SimpleMessageBoxFactory.createMessageBox();
        txtUsername.setText(userId);

        tcItemId.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colItemId"));

        tcReturnNoteId.setCellValueFactory(
                new PropertyValueFactory<Item, String>(
                        "colReturnNoteId"));
        tcBatchNo.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colBatchNo"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colDate"));
        tcName.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colName"));
        tcType.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colType"));
        tcQty.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colQty"));

        tblReturnNoteList.setItems(tableItemData);

        txtResolveId.setText(returnNoteDAO.generateID());

        TableLoader();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {
        txtReturnNoteNo.clear();
        txtItemId.clear();
        txtDate2.clear();
        txtDescription.clear();
        txtBatchNo.clear();
        txtItemId.clear();
        txtName.clear();
        txtType.clear();
        txtQty.clear();
        txtResolveId.setText(returnNoteDAO.generateID());
        dpkTo.setValue(LocalDate.now());
        dpkFrom.setValue(LocalDate.now());
        txtDate.setText(LocalDate.now().toString());
        TableLoader();
        validatorInitialization();
        
    }

    @Override
    public void clearValidations() {

    }

    @FXML
    void tblReturnNoteListOnMouseclicked(
            javafx.scene.input.MouseEvent mouseEvent) {

        
        try {
            if (mouseEvent.getClickCount() == 2) {

                txtItemId.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColItemId());
                txtReturnNoteNo.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColReturnNoteId());
                txtBatchNo.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColBatchNo());
                txtName.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColName());
                txtDate2.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColDate());
                txtType.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColType());
                txtQty.setText(
                        tblReturnNoteList.getSelectionModel().getSelectedItem().
                        getColQty());

            }
        } catch (Exception e) {
        }
        validatorInitialization();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        TableLoader();
    }

    @FXML
    void btnApproveOnAction(ActionEvent event) {
        boolean isProceed = false;
        validatorInitialization();
        boolean validationSupportResult = false;
        boolean validationSupportTableResult = false;

        ValidationResult v = validationSupport.getValidationResult();

        if (v != null) {
            validationSupportResult = validationSupport.isInvalid();

            if (validationSupportResult == true || validationSupportTableResult == true) {
                mb.ShowMessage(stage, ErrorMessages.MandatoryError, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false && validationSupportTableResult == false) {

                isProceed = returnNoteDAO.updateData(
                        txtResolveId.getText(),txtReturnNoteNo.getText(),
                        txtDate2.getText(),txtItemId.getText(),txtBatchNo.getText(),
                        txtDate.getText(),txtType.getText(),Double.parseDouble(
                        txtQty.getText()),userId,radioAddToMainStock.isSelected());

                if (isProceed == true) {

                    mb.ShowMessage(stage,
                            ErrorMessages.SuccesfullyCreated,
                            "Information",
                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                            MessageBox.MessageType.MSG_OK);
                    clearInput();

                } else {
                    mb.ShowMessage(stage, ErrorMessages.NotSuccesfullyCreated, "Error",
                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
                }
            }
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void radioAddToMainStockOnAction(ActionEvent event) {

    }

    @FXML
    void radioInternalReturnNoteOnAction(ActionEvent event) {
        TableLoader();
    }

    @FXML
    void radioExternalReturnNoteOnAction(ActionEvent event) {
        TableLoader();
    }

    @FXML
    void radioReturnToSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void radioDestroyOnAction(ActionEvent event) {

    }

    @FXML
    void dpkFromOnAction(ActionEvent event) {
        TableLoader();
    }

    @FXML
    void dpkToOnAction(ActionEvent event) {
        TableLoader();
    }

    private void TableLoader() {

        validatorInitialization();
        boolean validationSupportResult = false;
        ValidationResult ResultValidationSupport
                = validationSupportDateRange.getValidationResult();

        if (ResultValidationSupport != null) {
            validationSupportResult = validationSupportDateRange.isInvalid();
            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                loadGrnData();

            }
            validatorInitialization();
        }

    }

    private void loadGrnData() {

        tableItemData.clear();

        ArrayList<ArrayList<String>> additionalItemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = returnNoteDAO.
                getTableInfo(
                        radioInternalReturnNote.isSelected(),
                        txtSearch.getText(),
                        dpkFrom.getValue().toString(), dpkTo.
                        getValue().toString());

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                additionalItemInfo.add(list.get(i));
            }

            if (additionalItemInfo != null && additionalItemInfo.size()
                    > 0) {
                for (int i = 0; i < additionalItemInfo.size(); i++) {

                    item = new Item();

                    item.colItemId.
                            setValue(additionalItemInfo.get(i).get(0));
                    item.colReturnNoteId.setValue(additionalItemInfo.get(
                            i).
                            get(1));
                    item.colBatchNo.setValue(additionalItemInfo.get(i).
                            get(2));
                    item.colDate.setValue(additionalItemInfo.
                            get(i).
                            get(3));
                    item.colName.setValue(additionalItemInfo.get(i).
                            get(4));
                    item.colType.setValue(
                            additionalItemInfo.get(i).get(5));
                    item.colQty.setValue(
                            additionalItemInfo.get(i).get(6));
                    tableItemData.add(item);
                }
            }
        } else {
            mb.ShowMessage(stage, ErrorMessages.InvalidEvent, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

    }

    @FXML
    void txtItemIdOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtBatchNoOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDescriptionKeyReleased(KeyEvent event) {
        validatorInitialization();
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtReturnNoteNoOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDate2OnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtTypeOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtResolveIdOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {
        TableLoader();
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        validatorInitialization();

    }

    private void validatorInitialization() {

        System.gc();

        try {
            validationSupportDateRange.registerValidator(dpkFrom,
                    new CustomDatePickerValidationImpl(dpkFrom,
                            dpkFrom.getValue().toString().isEmpty(),
                            ErrorMessages.Empty));
            validationSupportDateRange.registerValidator(dpkTo,
                    new CustomDatePickerValidationImpl(dpkTo,
                            dpkTo.getValue().toString().isEmpty(),
                            ErrorMessages.Empty));
        } catch (Exception e) {
            //This is a JavaFX component Exception Hope this will be handled in future versions of JavaFx Exception
        }

        validationSupport.registerValidator(txtReturnNoteNo,
                new CustomTextFieldValidationImpl(txtReturnNoteNo,
                        !fav.validName(txtReturnNoteNo.getText()),
                        ErrorMessages.InvalidId));

        validationSupport.registerValidator(txtItemId,
                new CustomTextFieldValidationImpl(txtItemId,
                        !fav.validName(txtItemId.getText()),
                        ErrorMessages.InvalidId));

        validationSupport.registerValidator(txtDate2,
                new CustomTextFieldValidationImpl(txtDate2,
                        !fav.validName(txtDate2.getText()),
                        ErrorMessages.InvalidId));

        validationSupport.registerValidator(txtDescription,
                new CustomTextFieldValidationImpl(txtDescription,
                        !fav.validName(txtDescription.getText()),
                        ErrorMessages.InvalidId));

        validationSupport.registerValidator(txtBatchNo,
                new CustomTextFieldValidationImpl(txtBatchNo,
                        !fav.validName(txtBatchNo.getText()),
                        ErrorMessages.InvalidId));

        validationSupport.registerValidator(txtItemId,
                new CustomTextFieldValidationImpl(txtItemId,
                        !fav.validName(txtItemId.getText()), ErrorMessages.Error));

        validationSupport.registerValidator(txtName,
                new CustomTextFieldValidationImpl(txtName,
                        !fav.validName(txtName.getText()), ErrorMessages.Error));

        validationSupport.registerValidator(txtType,
                new CustomTextFieldValidationImpl(txtType,
                        !fav.validName(txtType.getText()), ErrorMessages.Error));

        validationSupport.registerValidator(txtQty,
                new CustomTextFieldValidationImpl(txtQty,
                        !fav.isDouble(txtQty.getText()),
                        ErrorMessages.InvalidQty));

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Classes">
    public class Item {

        public SimpleStringProperty colItemId = new SimpleStringProperty(
                "tcItemId");
        public SimpleStringProperty colReturnNoteId = new SimpleStringProperty(
                "tcReturnNoteId");
        public SimpleStringProperty colBatchNo = new SimpleStringProperty(
                "tcBatchNo");
        public SimpleStringProperty colName
                = new SimpleStringProperty("tcName");
        public SimpleStringProperty colDate = new SimpleStringProperty(
                "tcDate");
        public SimpleStringProperty colType = new SimpleStringProperty(
                "tcType");
        public SimpleStringProperty colQty = new SimpleStringProperty(
                "tcQty");

        public String getColItemId() {
            return colItemId.get();
        }

        public String getColReturnNoteId() {
            return colReturnNoteId.get();
        }

        public String getColBatchNo() {
            return colBatchNo.get();
        }

        public String getColName() {
            return colName.get();
        }

        public String getColDate() {
            return colDate.get();
        }

        public String getColType() {
            return colType.get();
        }

        public String getColQty() {
            return colQty.get();
        }

    }
//</editor-fold>

}
