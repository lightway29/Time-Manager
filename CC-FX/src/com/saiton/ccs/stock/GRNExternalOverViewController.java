/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.stock;

import com.saiton.ccs.base.UserPermission;
import com.saiton.ccs.base.UserSession;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.stockdao.ExternalGrnDAO;

import com.saiton.ccs.uihandle.FxmlUiLauncher;
import com.saiton.ccs.uihandle.ReportGenerator;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.DateFormatter;
import com.saiton.ccs.validations.CustomDatePickerValidationImpl;
import com.saiton.ccs.validations.CustomTableViewValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import com.saiton.ccs.validations.Validatable;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

public class GRNExternalOverViewController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private Button btnClose;

    @FXML
    private TextField txtDate;

    @FXML
    private Button btnApprove;

    @FXML
    private DatePicker dtpFromDate;

    @FXML
    private TableColumn<Item, String> tcGrnDate;

    @FXML
    private TableColumn<Item, String> tcGrnDescription;

    @FXML
    private TableView<Item> tblGRNList;

    @FXML
    private TableColumn<Item, String> tcGrnStatus;

    @FXML
    private TableColumn<Item, String> tcGrnId;

    @FXML
    private CheckBox checkReceived;

    @FXML
    private TextField txtUsername;

    @FXML
    private DatePicker dtpToDate;

    @FXML
    private TableColumn<Item, String> tcPurchaseOrderId;

    @FXML
    private Insets x1;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Item, String> tcId;
//</editor-fold>

    private Stage stage;
    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;
    Item item = new Item();
    private ObservableList tableItemData = FXCollections.observableArrayList();
    private final ValidationSupport validationSupport
            = new ValidationSupport();
    private final ValidationSupport validationSupportDateRange
            = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();
    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();
    private DateFormatter date = new DateFormatter();

    private ExternalGrnDAO externalGrnDao = new ExternalGrnDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtDate.setText(LocalDate.now().toString());

        date.format("yyyy-MM-dd", dtpToDate);
        date.format("yyyy-MM-dd", dtpFromDate);
        dtpFromDate.setValue(LocalDate.now());
        dtpToDate.setValue(LocalDate.now());

        tcId.setCellValueFactory(new PropertyValueFactory<>("colId"));

        tcPurchaseOrderId.setCellValueFactory(new PropertyValueFactory<>(
                "colPurchaseOrderId"));
        tcGrnId.setCellValueFactory(new PropertyValueFactory<>(
                "colGrnId"));
        tcGrnDescription.setCellValueFactory(new PropertyValueFactory<>(
                "colGrnDescription"));
        tcGrnDate.setCellValueFactory(new PropertyValueFactory<>(
                "colGrnDate"));
        tcGrnStatus.setCellValueFactory(new PropertyValueFactory<>(
                "colGrnStatus"));

        tblGRNList.setItems(tableItemData);
        loadGrnData();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {

    }

    @Override
    public void clearValidations() {
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        setUserAccessLevel();
        validatorInitialization();
    }

    //<editor-fold defaultstate="collapsed" desc="ActionEvents">
    @FXML
    void checkFilterByApprovedOnAction(ActionEvent event) {
        if (checkReceived.isSelected() == true) {
            btnApprove.setVisible(false);
        } else {
            btnApprove.setVisible(true);
        }
        internalGrnTableLoader();
    }

    @FXML
    private void dtpFromDateOnAction(ActionEvent event) {
        internalGrnTableLoader();
    }

    @FXML
    private void dtpToDateOnAction(ActionEvent event) {
        internalGrnTableLoader();
    }

    @FXML
    void btnApproveOnAction(ActionEvent event) {
        validatorInitialization();
        boolean isSelected = false;
        String id = null;
        boolean validationSupportResult = false;
        ValidationResult ResultValidationSupport
                = validationSupport.getValidationResult();

        if (ResultValidationSupport != null) {
            validationSupportResult = validationSupport.isInvalid();
            if (validationSupportResult == true) {

                mb.ShowMessage(stage,
                        ErrorMessages.MandatoryError, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false) {
                for (int i = 0; i < tblGRNList.getItems().size(); i++) {
                    if (tblGRNList.getSelectionModel().isSelected(i)) {
                        id = tblGRNList.getSelectionModel().
                                getSelectedItem().getColGrnId();
                        updateStock(tblGRNList.
                                getSelectionModel().getSelectedItem().getColGrnId());
//                tableItemData.clear();
                        HashMap param = new HashMap();
                        param.put("grn_no", id);

                        File fil = new File(".//Reports//ExternalGoodReceivedNote.jasper");
                        String img = fil.getAbsolutePath();
                        ReportGenerator r = new ReportGenerator(img, param);
                        r.setVisible(true);

                        loadGrnData();
                    } else {
                        isSelected = true;
                    }
                }
                if (isSelected) {
                    mb.ShowMessage(stage,
                            "please selects a valid record", MessageBoxTitle.ERROR.toString(),
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="KeyEvents">
    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {
        internalGrnTableLoader();
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="MouseEvents">

    @FXML
    private void tblGRNListOnMouseclicked(MouseEvent event) {
        validatorInitialization();
        try {
            if (event.getClickCount() == 2) {

                Object[] obj = {
                    tblGRNList.getSelectionModel().getSelectedItem().
                    getColGrnId(),
                    tblGRNList.getSelectionModel().getSelectedItem().
                    getColPurchaseOrderId()};

                FxmlUiLauncher.
                        launchOnNewStage(
                                "/com/saiton/ccs/stock/GRNExternal.fxml",
                                "External GRN", obj);
            }
        } catch (Exception e) {
        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">
    private void loadGrnData() {

        tableItemData.clear();

        ArrayList<ArrayList<String>> additionalItemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = externalGrnDao.
                getTableApproveInfo(
                        checkReceived.isSelected(), txtSearch.getText(),
                        dtpFromDate.getValue().toString(), dtpToDate.
                        getValue().toString());

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                additionalItemInfo.add(list.get(i));
            }

            if (additionalItemInfo != null && additionalItemInfo.size()
                    > 0) {
                for (int i = 0; i < additionalItemInfo.size(); i++) {

                    item = new Item();

                    item.colId.
                            setValue(additionalItemInfo.get(i).get(0));
                    item.colPurchaseOrderId.setValue(additionalItemInfo.get(
                            i).
                            get(1));
                    item.colGrnId.setValue(additionalItemInfo.get(i).
                            get(2));
                    item.colGrnDescription.setValue(additionalItemInfo.
                            get(i).
                            get(3));
                    item.colGrnDate.setValue(additionalItemInfo.get(i).
                            get(4));
                    item.colGrnStatus.setValue(
                            additionalItemInfo.get(i).get(5));
                    tableItemData.add(item);

                }
            }
        } else {
            mb.ShowMessage(stage, ErrorMessages.InvalidEvent, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

    }

    private void updateStock(String grnId) {

        //Load items from GRN
        ArrayList<ArrayList<String>> grnItems
                = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> list = externalGrnDao.loadingGrn(
                grnId);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                grnItems.add(list.get(i));

            }

            if (grnItems != null && grnItems.size() > 0) {

                //Approve GRN
                boolean isApproved = externalGrnDao.approveGrn(tblGRNList.
                        getSelectionModel().getSelectedItem().getColId(),
                        tblGRNList.getSelectionModel().getSelectedItem().
                        getColGrnId(), userId, txtDate.getText());

                boolean isStatusUpdated = externalGrnDao.updateGRNStatus(
                        tblGRNList.getSelectionModel().getSelectedItem().getColPurchaseOrderId(), 1);

                if (isApproved == true) {

                    //Update stock
                    for (int i = 0; i < grnItems.size(); i++) {

                        String itemId = grnItems.get(i).get(0);
                        String batchNo = grnItems.get(i).get(1);
                        String qty = grnItems.get(i).get(2);
                        String date = txtDate.getText();

                        //Add item qty to main stock
                        boolean isMainStockItemUpdated
                                = externalGrnDao.
                                updateMainStockItems(itemId,
                                        batchNo,
                                        Double.parseDouble(
                                                qty));

                        boolean isMainStockItemTotalUpdated
                                = externalGrnDao.
                                updateItemTotal(
                                        itemId, Double.
                                        parseDouble(
                                                qty));

                        if (isMainStockItemUpdated == true
                                && isMainStockItemTotalUpdated
                                == true) {

//                            mb.ShowMessage(stage,
//                                    ErrorMessages.SuccesfullyUpdated,
//                                    "Information",
//                                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
//                                    MessageBox.MessageType.MSG_OK);
                        }

                    }
                    load();

                }

            }
        }

    }

    public void load() {

        //    MainBillAlacart_Event.jasper
        mb.ShowMessage(stage,
                ErrorMessages.SuccesfullyCreated,
                "Information",
                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                MessageBox.MessageType.MSG_OK);

        //=====================Print out===============================
        HashMap param = new HashMap();
        param.put("grn_no", tblGRNList.getSelectionModel().getSelectedItem().
                getColGrnId());

        File fil
                = new File(
                        ".//Reports//GoodReceivedNote.jasper");
        String img = fil.getAbsolutePath();
        ReportGenerator r = new ReportGenerator(img, param);
        r.setVisible(true);
        loadGrnData();
        //=============================================================
//        FxmlUiLauncher.launchOnNewStage(
//                UserInterfacePath.RESTAURANT_EVENT_BILL_PATH.toString(),
//                UserInterfaceTitle.RESTAURANT_EVENT_BILL_TITLE.toString());
//        Stage stage = (Stage) btnClose.getScene().
//                getWindow();
//        stage.close();
    }

    private void validatorInitialization() {

        System.gc();

        try {
            validationSupportDateRange.registerValidator(dtpFromDate,
                    new CustomDatePickerValidationImpl(dtpFromDate,
                            dtpFromDate.getValue().toString().isEmpty(),
                            ErrorMessages.Empty));
            validationSupportDateRange.registerValidator(dtpToDate,
                    new CustomDatePickerValidationImpl(dtpToDate,
                            dtpToDate.getValue().toString().isEmpty(),
                            ErrorMessages.Empty));
        } catch (Exception e) {
            //This is a JavaFX component Exception Hope this will be handled in future versions of JavaFx Exception
        }

        validationSupport.registerValidator(tblGRNList,
                new CustomTableViewValidationImpl(tblGRNList,
                        !fav.validTableView(tblGRNList),
                        ErrorMessages.EmptyListView));

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                break;

            case DELETE:
                disableUi(false);

                break;

            case READ_ONLY:
                disableUi(false);

                btnApprove.setDisable(true);
                btnApprove.setVisible(false);

                break;

            case ALL_BUT_DELETE:
                disableUi(false);

                break;

            case FULL_ACCESS:
                disableUi(false);

                break;

            case NO_ACCESS:
                disableUi(true);

                break;

        }

    }

    private void disableUi(boolean state) {

        btnClose.setDisable(state);
        btnClose.setVisible(!state);

        dtpFromDate.setDisable(state);
        dtpFromDate.setVisible(!state);

        dtpToDate.setDisable(state);
        dtpToDate.setVisible(!state);

        txtDate.setDisable(state);
        txtDate.setVisible(!state);

        btnApprove.setDisable(state);
        btnApprove.setVisible(!state);

        checkReceived.setDisable(state);
        checkReceived.setVisible(!state);

        txtUsername.setDisable(state);
        txtUsername.setVisible(!state);

        txtSearch.setDisable(state);
        txtSearch.setVisible(!state);

        tblGRNList.setDisable(state);

        tblGRNList.setVisible(!state);

    }

    private void setUserAccessLevel() {

        userId = UserSession.getInstance().getUserInfo().getEid();
        userName = UserSession.getInstance().getUserInfo().getName();
        userCategory = UserSession.getInstance().getUserInfo().getCategory();
        txtUsername.setText(userName);

        String title = stage.getTitle();

        if (!title.isEmpty()) {

            UserPermission userPermission = UserSession.getInstance().
                    findPermission(title);

            if (userPermission.canInsert() == true) {
                insert = true;
            }

            if (userPermission.canDelete() == true) {
                delete = true;
            }

            if (userPermission.canUpdate() == true) {
                update = true;
            }

            if (userPermission.canView() == true) {
                view = true;
            }

            if (insert == true && delete == true && update == true && view
                    == true) {
                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == false && delete == true && update == true
                    && view
                    == true) {
                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == true && delete == false && update == true
                    && view
                    == true) {
                setUiMode(UiMode.ALL_BUT_DELETE);

            } else if (insert == true && delete == true && update == false
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == true && delete == true && update == true
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == true
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == false && delete == true && update == false
                    && view
                    == true) {
                setUiMode(UiMode.DELETE);

            } else if (insert == false && delete == true && update == true
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == true && delete == false && update == false
                    && view
                    == true) {

                setUiMode(UiMode.ALL_BUT_DELETE);

            } else if (insert == true && delete == false && update == true
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == true && delete == true && update == false
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == false
                    && view
                    == true) {

                setUiMode(UiMode.READ_ONLY);

            } else if (insert == false && delete == false && update == true
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == false && delete == true && update == false
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == true && delete == false && update == false
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == false
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            }
        } else {

            setUiMode(UiMode.NO_ACCESS);

        }

    }

    private void internalGrnTableLoader() {

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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Classes">
    public class Item {

        public SimpleStringProperty colId = new SimpleStringProperty("tcId");
        public SimpleStringProperty colPurchaseOrderId
                = new SimpleStringProperty(
                        "tcPurchaseOrderId");
        public SimpleStringProperty colGrnId = new SimpleStringProperty(
                "tcGrnId");
        public SimpleStringProperty colGrnDescription
                = new SimpleStringProperty("tcGrnDescription");
        public SimpleStringProperty colGrnDate = new SimpleStringProperty(
                "tcGrnDate");
        public SimpleStringProperty colGrnStatus = new SimpleStringProperty(
                "tcGrnStatus");

        public String getColId() {
            return colId.get();
        }

        public String getColPurchaseOrderId() {
            return colPurchaseOrderId.get();
        }

        public String getColGrnId() {
            return colGrnId.get();
        }

        public String getColGrnDescription() {
            return colGrnDescription.get();
        }

        public String getColGrnDate() {
            return colGrnDate.get();
        }

        public String getColGrnStatus() {
            return colGrnStatus.get();
        }

    }

//</editor-fold>
}
