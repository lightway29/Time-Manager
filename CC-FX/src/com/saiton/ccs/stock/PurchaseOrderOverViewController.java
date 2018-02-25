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

import com.saiton.ccs.stockdao.PurchaseOrderDAO;
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
import javafx.scene.control.Label;
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

/**
 * FXML Controller class
 *
 * @author nadeesha
 */
public class PurchaseOrderOverViewController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

    @FXML
    private Label userNameLbl;
    @FXML
    private TextField txtUsername;
    @FXML
    private Insets x1;
    @FXML
    private Label dateLbl;
    @FXML
    private TextField txtDate;
    @FXML
    private CheckBox checkReceived;
    @FXML
    private Label fromLbl;
    @FXML
    private DatePicker dtpFromDate;
    @FXML
    private Label toLbl;
    @FXML
    private DatePicker dtpToDate;
    @FXML
    private Label searchLbl;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label purchOderLstLbl;
    @FXML
    private TableView<Item> tblPurchasOrderList;
    @FXML
    private TableColumn<Item, String> tcId;
    @FXML
    private TableColumn<Item, String> tcPurchaseOrderID;
    @FXML
    private TableColumn<Item, String> tcPurchaseOrderDate;
    @FXML
    private TableColumn<Item, String> tcSupplierId;
    @FXML
    private TableColumn<Item, String> tcStatus;
    @FXML
    private Button btnApprove;
    @FXML
    private Button btnClose;
    @FXML
    private Label purchOrderOvrLbl;

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

    private PurchaseOrderDAO purchaseOrderDao = new PurchaseOrderDAO();

//    private PrinterRegistry printerRegistry = new PrinterRegistry();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDate.setText(LocalDate.now().toString());

        date.format("yyyy-MM-dd", dtpToDate);
        date.format("yyyy-MM-dd", dtpFromDate);
        dtpFromDate.setValue(LocalDate.now());
        dtpToDate.setValue(LocalDate.now());

        tcId.setCellValueFactory(new PropertyValueFactory<>("colId"));

        tcSupplierId.setCellValueFactory(new PropertyValueFactory<>(
                "colSupplierId"));
        tcPurchaseOrderID.setCellValueFactory(new PropertyValueFactory<>(
                "colPurchaseId"));
        tcPurchaseOrderDate.setCellValueFactory(new PropertyValueFactory<>(
                "colDate"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>(
                "colStatus"));

        tblPurchasOrderList.setItems(tableItemData);
        loadPurchaseData();
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
//        localize();
        setUserAccessLevel();
        validatorInitialization();
    }

    @FXML
    private void checkFilterByApprovedOnAction(ActionEvent event) {
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
    private void txtSearchOnKeyReleased(KeyEvent event) {
        internalGrnTableLoader();
    }

    @FXML
    private void tblPurchasOrderListOnMouseClicked(MouseEvent event) {
        validatorInitialization();
        try {
            if (event.getClickCount() == 2) {
                Object[] obj = {
                    tblPurchasOrderList.getSelectionModel().getSelectedItem().getColPurchaseId(),
                    tblPurchasOrderList.getSelectionModel().getSelectedItem().getColSupplierId()};

                FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/PurchaseOrder.fxml",
                        "Purchase Order", obj);
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void btnApproveOnAction(ActionEvent event) {
        validatorInitialization();
        boolean validationSupportResult = false;
        ValidationResult ResultValidationSupport
                = validationSupport.getValidationResult();

        if (ResultValidationSupport != null) {
            validationSupportResult = validationSupport.isInvalid();
            if (validationSupportResult == true) {

                mb.ShowMessage(stage,
                        ErrorMessages.MandatoryError, MessageBoxTitle.ERROR.toString(),
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false) {
                if (update == true) {
                    if (tblPurchasOrderList.
                            getSelectionModel().getSelectedItem() != null) {
                        updateStock(tblPurchasOrderList.
                                getSelectionModel().getSelectedItem().
                                getColPurchaseId());
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.InvalidRecord, MessageBoxTitle.ERROR.toString(),
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }

                } else {
                    mb.ShowMessage(stage, ErrorMessages.AccessDenied,
                            MessageBoxTitle.ERROR.toString(), MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
                }
            }

        }

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }

//<editor-fold defaultstate="collapsed" desc="Methods">
    private void internalGrnTableLoader() {

        validatorInitialization();
        boolean validationSupportResult = false;
        ValidationResult ResultValidationSupport
                = validationSupportDateRange.getValidationResult();

        if (ResultValidationSupport != null) {
            validationSupportResult = validationSupportDateRange.isInvalid();
            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                loadPurchaseData();

            }
            validatorInitialization();
        }

    }

    private void updateStock(String purchasId) {

        //Approve GRN
        boolean isApproved = purchaseOrderDao.approvePurchaseOrder(
                tblPurchasOrderList.
                getSelectionModel().getSelectedItem().getColId(),
                tblPurchasOrderList.getSelectionModel().getSelectedItem().
                getColPurchaseId(),
                userId, txtDate.getText());

        if (isApproved == true) {
            mb.ShowMessage(stage, ErrorMessages.RecordApproved,
                    "Success Information",
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);

            //=====================Print out===============================
            HashMap param = new HashMap();
            param.put("purchaseOrderNo", tblPurchasOrderList.getSelectionModel().
                    getSelectedItem().getColPurchaseId());

            File fil = new File(".//Reports//PurchaseOrderSheet.jasper");
            String img = fil.getAbsolutePath();
            ReportGenerator r = new ReportGenerator(img, param);
            r.setVisible(true);
            loadPurchaseData();
        } else {
            mb.ShowMessage(stage, ErrorMessages.RecordNotApproved,
                    MessageBoxTitle.ERROR.toString(), MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

    }

    private void loadPurchaseData() {

        tableItemData.clear();

        ArrayList<ArrayList<String>> additionalItemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = purchaseOrderDao.
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
                    item.colSupplierId.setValue(additionalItemInfo.get(
                            i).
                            get(1));
                    item.colPurchaseId.setValue(additionalItemInfo.get(i).
                            get(2));
                    item.colDate.setValue(additionalItemInfo.get(i).
                            get(3));
                    item.colStatus.setValue(
                            additionalItemInfo.get(i).get(4));
                    tableItemData.add(item);
                }
            }
        } else {
            mb.ShowMessage(stage, ErrorMessages.InvalidEvent, MessageBoxTitle.ERROR.toString(),
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

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

        validationSupport.registerValidator(tblPurchasOrderList,
                new CustomTableViewValidationImpl(tblPurchasOrderList,
                        !fav.validTableView(tblPurchasOrderList),
                        ErrorMessages.EmptyListView));

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                break;

            case DELETE:
                disableUi(true);

                break;

            case READ_ONLY:
                btnClose.setDisable(false);
                btnClose.setVisible(true);

                dtpFromDate.setDisable(false);
                dtpFromDate.setVisible(true);

                dtpToDate.setDisable(false);
                dtpToDate.setVisible(true);

                txtDate.setDisable(false);
                txtDate.setVisible(true);

                btnApprove.setDisable(true);
                btnApprove.setVisible(false);
                checkReceived.setDisable(false);
                checkReceived.setVisible(true);
                txtUsername.setDisable(false);
                txtUsername.setVisible(true);

                txtSearch.setDisable(false);
                txtSearch.setVisible(true);
                tblPurchasOrderList.setVisible(true);
                tblPurchasOrderList.setDisable(false);

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
        tblPurchasOrderList.setVisible(!state);
        tblPurchasOrderList.setDisable(state);

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
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Classes">
    public class Item {

        public SimpleStringProperty colId = new SimpleStringProperty("tcId");
        public SimpleStringProperty colSupplierId = new SimpleStringProperty(
                "tcIssueNoteId");
        public SimpleStringProperty colPurchaseId = new SimpleStringProperty(
                "tcGrnId");
        public SimpleStringProperty colDate = new SimpleStringProperty(
                "tcGrnDate");
        public SimpleStringProperty colStatus = new SimpleStringProperty(
                "tcGrnStatus");

        public String getColId() {
            return colId.get();
        }

        public String getColSupplierId() {
            return colSupplierId.get();
        }

        public String getColPurchaseId() {
            return colPurchaseId.get();
        }

        public String getColDate() {
            return colDate.get();
        }

        public String getColStatus() {
            return colStatus.get();
        }

    }
//</editor-fold>

}
