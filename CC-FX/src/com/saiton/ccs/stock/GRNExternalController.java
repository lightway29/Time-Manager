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
import com.saiton.ccs.popup.InternalGrnPopup;
import com.saiton.ccs.popup.PurchaseOrderPopup;
import com.saiton.ccs.stockdao.ExternalGrnDAO;

import com.saiton.ccs.stockdao.IssueNoteDAO;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.validations.CustomTableViewValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

public class GRNExternalController extends AnchorPane implements Initializable,
        Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private Button btnClose;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnSearchGRNID;

    @FXML
    private TextField txtDate;

    @FXML
    private TableColumn<?, ?> tcBatchNo;

    @FXML
    private TableColumn<?, ?> tcItemId;

    @FXML
    private TableColumn<?, ?> tcItemName;

//    @FXML
//    private TextField txtPurchaseOrderID;
    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtGRNDescription;

    @FXML
    private TableColumn<?, ?> tcQty;

    @FXML
    private Insets x1;

//    @FXML
//    private Label lblIssueNoteId;
    @FXML
    private Insets x2;

    @FXML
    private TextField txtGRNID;

    @FXML
    private TableColumn<?, ?> tcItemDescription;

    @FXML
    private TextField txtPurchaseOrderDate;

    @FXML
    private TableView<?> tblGRNItems;

    @FXML
    private Label lblGRNID;
    @FXML
    private Button btnRefresh;
    @FXML
    private Label lblPurchaseOrderId;
    @FXML
    private TextField txtPurchaseOrderId;
    @FXML
    private Button btnSearchPurchaseOrderId;

//</editor-fold>
    private Stage stage;

    //ExternalGrn Id popup---------------------------------------
    private TableView grnIdTable = new TableView();
    private PopOver grnIdPop;
    private ObservableList<InternalGrnPopup> grnIdData = FXCollections.
            observableArrayList();
    private InternalGrnPopup grnIdPopup = new InternalGrnPopup();
    private ExternalGrnDAO ExternalGrnDao = new ExternalGrnDAO();
    //------------------------------------------------------------

    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;
    Item item = new Item();

    GRNItem grnItem = new GRNItem();
    //Issue Note popup---------------------------------------
    private TableView purchaseOrderIdTable = new TableView();
    private PopOver purchaseOrderIdPop;
    private ObservableList<PurchaseOrderPopup> purchaseOrderData
            = FXCollections.
            observableArrayList();
    ComponentControl component = new ComponentControl();
    private PurchaseOrderPopup PurchaseOrderPopupIdPopup
            = new PurchaseOrderPopup();
    IssueNoteDAO issueNoteDAO = new IssueNoteDAO();

    private ObservableList TableItemData = FXCollections.observableArrayList();
    private MessageBox mb;

    private ComponentControl componentControl = new ComponentControl();

    private final ValidationSupport validationSupport = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();
    boolean isTableContentSaved = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtDate.setText(LocalDate.now().toString());
        txtUserName.setText(userId);
        mb = SimpleMessageBoxFactory.createMessageBox();

        tcItemId.setCellValueFactory(new PropertyValueFactory<>(
                "colItemId"));
        tcItemName.setCellValueFactory(new PropertyValueFactory<>(
                "colItemName"));
        tcItemDescription.setCellValueFactory(
                new PropertyValueFactory<>("colItemDescription"));
//        tcUnit.setCellValueFactory(new PropertyValueFactory<>(
//                "colUnit"));
        tcQty.setCellValueFactory(new PropertyValueFactory<>(
                "colQty"));

        tcBatchNo.setCellValueFactory(new PropertyValueFactory<>(
                "colBatchNo"));
        tblGRNItems.setItems(TableItemData);
        btnDelete.setVisible(false);
        refreshId();
        validatorInitialization();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {

        txtGRNDescription.clear();
        txtGRNID.clear();
        txtPurchaseOrderDate.clear();

        txtPurchaseOrderId.clear();
        btnDelete.setVisible(false);
        btnSave.setVisible(true);
        itemTableCleanner();
        refreshId();
    }

    private void itemTableCleanner() {
        TableItemData.clear();
    }

    @Override
    public void clearValidations() {

    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;

        setUserAccessLevel();

        if (obj != null) {
            DataFromApprove(obj[0].toString(), obj[1].toString());
        }

//        if (obj != null) {
//
//            setUiMode(UiMode.READ_ONLY);
//            grnDataLoader(obj[0].toString());
//
//        }
        //Internal Grn popup------------------------
        grnIdTable = grnIdPopup.tableViewLoader(grnIdData);

        grnIdTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    btnDelete.setVisible(false);
                    InternalGrnPopup p = null;
                    p = (InternalGrnPopup) grnIdTable.getSelectionModel().
                            getSelectedItem();
                    clearInput();

                    if (p.getColGrnId() != null) {

                        txtGRNID.setText(p.getColGrnId());
                        setUiMode(UiMode.DELETE);
                        grnDataLoader(txtGRNID.getText());

                    }

                } catch (NullPointerException n) {

                }

                grnIdPop.hide();
                validatorInitialization();

            }

        });

        grnIdTable.setOnMousePressed(e -> {

            if (e.getButton() == MouseButton.SECONDARY) {

                grnIdPop.hide();
                validatorInitialization();

            }

        });

        //Issue Note popup------------------------
        purchaseOrderIdTable = PurchaseOrderPopupIdPopup.tableViewLoader(
                purchaseOrderData);

        purchaseOrderIdTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {

                    PurchaseOrderPopup p = null;
                    p = (PurchaseOrderPopup) purchaseOrderIdTable.
                            getSelectionModel().
                            getSelectedItem();
                    clearInput();

                    if (p.colPurchaseOrderId != null) {

                        String purchaseOrderId = p.getColPurchaseOrderId();
                        txtPurchaseOrderId.setText(purchaseOrderId);
                        txtPurchaseOrderDate.setText(ExternalGrnDao.
                                loadingPurchaseOrderDate(txtPurchaseOrderId.
                                        getText()));
                        addIssueNoteItemToTable();
                    }

                } catch (NullPointerException n) {

                }

                purchaseOrderIdPop.hide();

                validatorInitialization();
            }

        });

        purchaseOrderIdTable.setOnMousePressed(e -> {

            if (e.getButton() == MouseButton.SECONDARY) {

                purchaseOrderIdPop.hide();
                validatorInitialization();

            }

        });

        grnIdPop = new PopOver(grnIdTable);
        purchaseOrderIdPop = new PopOver(purchaseOrderIdTable);

        stage.setOnCloseRequest(e -> {

            if (grnIdPop.isShowing() | purchaseOrderIdPop.isShowing()) {
                e.consume();
                grnIdPop.hide();
                purchaseOrderIdPop.hide();
            }
        });
    }

    private void DataFromApprove(String grnId, String purchaseOrderId) {

        txtGRNID.setText(grnId);
        txtPurchaseOrderId.setText(purchaseOrderId);

        btnDelete.setVisible(false);
        btnSave.setVisible(false);

        component.deactivateSearch(lblGRNID, txtGRNID,
                btnSearchGRNID, 220.0, 0.0);
        component.deactivateSearch(lblGRNID, txtGRNID,
                btnRefresh, 220.0, 0.0);
        component.deactivateSearch(lblPurchaseOrderId, txtPurchaseOrderId,
                btnSearchPurchaseOrderId, 220.0, 0.0);

        grnIdData.clear();

        ArrayList<String> DataList = null;

        DataList = ExternalGrnDao.loadGrnInfo(txtGRNID.getText());
//
        txtGRNDescription.setText(DataList.get(0));
        txtDate.setText(DataList.get(1));
        txtPurchaseOrderId.setText(DataList.get(2));
        txtPurchaseOrderDate.setText(DataList.get(3));
        txtUserName.setText(DataList.get(4));
        txtGRNDescription.setDisable(true);

        ArrayList<ArrayList<String>> additionalItemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = ExternalGrnDao.
                searchPurchaseOrderItems(
                        txtPurchaseOrderId.getText());
        ArrayList<ArrayList<String>> additionalItemInfo1
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list1 = ExternalGrnDao.searchGrnDetails(
                txtGRNID.getText());

        if (DataList != null && list != null && list1 != null) {

            for (int i = 0; i < list.size(); i++) {

                additionalItemInfo.add(list.get(i));
            }

            if (additionalItemInfo != null && additionalItemInfo.size() > 0) {
                for (int i = 0; i < additionalItemInfo.size(); i++) {

                    item = new Item();

                    item.colItemId.setValue(additionalItemInfo.get(i).get(0));
                    item.colItemName.setValue(additionalItemInfo.get(i).get(1));
                    item.colItemDescription.setValue(additionalItemInfo.get(i).
                            get(2));
                    item.colQty.setValue(additionalItemInfo.get(i).get(3));
                    item.colUnit.setValue(additionalItemInfo.get(i).get(4));

                    TableItemData.add(item);
                }
            }

        } else {
            mb.ShowMessage(stage, ErrorMessages.MandatoryError, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

        validatorInitialization();
    }

    //<editor-fold defaultstate="collapsed" desc="ActionEvents">
    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
    }

    @FXML
    private void btnSearchPurchaseOrderIdOnAction(ActionEvent event) {
        PurchaseOrderPopupTableDataLoader(txtPurchaseOrderId.getText());
        purchaseOrderIdTable.setItems(purchaseOrderData);

        if (!purchaseOrderData.isEmpty()) {
            purchaseOrderIdPop.show(btnSearchPurchaseOrderId);
        }
        validatorInitialization();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isGrnSaved = false;
        boolean isTableContentSaved = false;
        validatorInitialization();
        boolean validationSupportResult = false;

        ValidationResult v = validationSupport.getValidationResult();

        if (v != null) {
            validationSupportResult = validationSupport.isInvalid();

            if (validationSupportResult == true) {

                mb.ShowMessage(stage, ErrorMessages.MandatoryError, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false) {
                String id = ExternalGrnDao.generateId();
                isGrnSaved = ExternalGrnDao.insertExternalGrn(id,
                        txtPurchaseOrderId.
                        getText(), txtDate.getText(),
                        txtGRNDescription.getText(), userId);

                isTableContentSaved = saveTableContent(id);

                if (isGrnSaved == true && isTableContentSaved == true) {

                    mb.ShowMessage(stage, ErrorMessages.SuccesfullyCreated,
                            "Information",
                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                            MessageBox.MessageType.MSG_OK);

                    clearInput();

                } else {
                    mb.ShowMessage(stage, ErrorMessages.NotSuccesfullyCreated,
                            "Error", MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
                }
                //Save Action Event
            }
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSearchGRNIDOnAction(ActionEvent event) {
        grnPopupDataLoader(txtGRNID.getText());
        grnIdTable.setItems(grnIdData);
        if (!grnIdData.isEmpty()) {
            grnIdPop.show(btnSearchGRNID);
        }
        validatorInitialization();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean sDeleted = false;
        validatorInitialization();
        boolean validationSupportResult = false;

        ValidationResult v = validationSupport.getValidationResult();

        if (v != null) {
            validationSupportResult = validationSupport.isInvalid();

            if (validationSupportResult == true) {

                mb.ShowMessage(stage, ErrorMessages.MandatoryError, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        ErrorMessages.Delete, MessageBoxTitle.INFORMATION.toString(),
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    sDeleted = ExternalGrnDao.deleteGrnNote(txtGRNID.getText());
                    if (sDeleted) {
                        mb.ShowMessage(stage, ErrorMessages.SuccesfullyDeleted,
                                "Purchase Order",
                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                MessageBox.MessageType.MSG_OK);

                    } else {
                        mb.ShowMessage(stage, ErrorMessages.NotSuccesfullyDeleted,
                                "Purchase Order",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                    clearInput();
                }
            }

        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="KeyEvents">
    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtIssueNoteIDOnKeyReleased(KeyEvent event) {
        if (txtPurchaseOrderId.getText().length() >= 3) {
            PurchaseOrderPopupTableDataLoader(txtPurchaseOrderId.getText());
            purchaseOrderIdTable.setItems(purchaseOrderData);

            if (!purchaseOrderData.isEmpty()) {
                purchaseOrderIdPop.show(btnSearchPurchaseOrderId);
            } else {
                purchaseOrderIdPop.hide();
            }
            validatorInitialization();
        }

    }

    @FXML
    void txtIssueDateOnKeyReleased(KeyEvent event) {

    }

    void txtRequestTypeOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {

    }

    void txtIssueDescriptionOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtGRNIDOnKeyReleased(KeyEvent event) {
        if (txtGRNID.getText().length() >= 3) {
            grnPopupDataLoader(txtGRNID.getText());
            grnIdTable.setItems(grnIdData);
            if (!grnIdData.isEmpty()) {
                grnIdPop.show(btnSearchGRNID);
            } else {
                grnIdPop.hide();
            }
            validatorInitialization();
        }
    }

    @FXML
    void txtGRNDescriptionOnKeyReleased(KeyEvent event) {
        validatorInitialization();
    }

    @FXML
    void tblGRNItemsOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {

    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="MouseEvents">
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">

    private void PurchaseOrderPopupTableDataLoader(String keyword) {

        purchaseOrderData.clear();

        ArrayList<ArrayList<String>> itemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = ExternalGrnDao.
                searchPurchaseOrderDetails(keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemInfo.add(list.get(i));
            }

            if (itemInfo != null && itemInfo.size() > 0) {
                for (int i = 0; i < itemInfo.size(); i++) {

                    PurchaseOrderPopupIdPopup = new PurchaseOrderPopup();
                    PurchaseOrderPopupIdPopup.colPurchaseOrderId.setValue(
                            itemInfo.get(i).get(0));
                    PurchaseOrderPopupIdPopup.colSupplierId.setValue(itemInfo.
                            get(i).get(1));
                    PurchaseOrderPopupIdPopup.colDate.setValue(itemInfo.get(i).
                            get(2));
                    PurchaseOrderPopupIdPopup.colSupplierName.setValue(itemInfo.
                            get(i).get(3));
                    purchaseOrderData.add(PurchaseOrderPopupIdPopup);
                }
            }

        }

    }

    private boolean saveTableContent(String grnId) {

        boolean isTableContentSaved = false;

        Item itemTable;

//// Loading table item to db
////=============================================================================================================== 
        if (tblGRNItems.getItems().size() != 0) {
            for (int i = 0; i < tblGRNItems.getItems().size(); i++) {
                itemTable = (Item) tblGRNItems.getItems().get(i);

                isTableContentSaved = ExternalGrnDao.insertGrnItems(
                        grnId,
                        itemTable.getColItemId(),
                        itemTable.getColItemDescription(),
                        Double.parseDouble(itemTable.getColQty()),
                        itemTable.getColBatchNo());

            }
        }
        return isTableContentSaved;
    }

    private void grnPopupDataLoader(String keyword) {

        grnIdData.clear();
        ArrayList<ArrayList<String>> itemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = ExternalGrnDao.searchGrnDetails(
                keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemInfo.add(list.get(i));
            }

            if (itemInfo != null && itemInfo.size() > 0) {
                for (int i = 0; i < itemInfo.size(); i++) {

                    grnIdPopup = new InternalGrnPopup();
                    grnIdPopup.colGrnId.setValue(itemInfo.get(i).get(0));
                    grnIdPopup.colDate.setValue(itemInfo.get(i).get(1));
                    grnIdPopup.colDesc.setValue(itemInfo.get(i).get(2));
                    grnIdData.add(grnIdPopup);
                }
            }

        }

    }

    private void validatorInitialization() {

        System.gc();

//        validationSupport.registerValidator(txtPurchaseOrderID,
//                new CustomTextFieldValidationImpl(txtPurchaseOrderID,
//                        !fav.validName(txtPurchaseOrderID.getText()),
//                        ErrorMessages.InvalidId));
        validationSupport.registerValidator(tblGRNItems,
                new CustomTableViewValidationImpl(tblGRNItems,
                        !fav.validTableView(tblGRNItems),
                        ErrorMessages.EmptyListView));

        validationSupport.registerValidator(txtGRNDescription,
                new CustomTextFieldValidationImpl(txtGRNDescription,
                        !fav.validName(txtGRNDescription.getText()),
                        ErrorMessages.InvalidDescription));

    }

    private void addIssueNoteItemToTable() {

        itemTableCleanner();

        ArrayList<ArrayList<String>> itemDetails
                = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> list = ExternalGrnDao.
                searchPurchaseOrderItems(
                        txtPurchaseOrderId.getText());

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemDetails.add(list.get(i));
            }

            if (itemDetails != null && itemDetails.size() > 0) {
                for (int i = 0; i < itemDetails.size(); i++) {

                    Item item = new Item();

                    item.colItemId.setValue(itemDetails.get(i).get(0));
                    item.colItemName.setValue(itemDetails.get(i).get(1));
                    item.colItemDescription.setValue(itemDetails.get(i).
                            get(2));
//                    item.colUnit.setValue(itemDetails.get(i).get(4));
                    item.colQty.setValue(itemDetails.get(i).get(3));
                    item.colBatchNo.setValue(itemDetails.get(i).get(4));

                    TableItemData.add(item);
                }
            } else {
                mb.ShowMessage(stage, ErrorMessages.InvalidEvent,
                        "Error", MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            }

        }
    }

    private void refreshId() {

        String id = ExternalGrnDao.generateId();

        if (id != null) {
            txtGRNID.setText(id);

        }

    }

    private void grnDataLoader(String grnId) {

        ArrayList<String> dataList = null;

        dataList = ExternalGrnDao.loadingGrnInfo(grnId);

        if (dataList != null) {
            txtGRNDescription.setText(dataList.get(0));
            txtDate.setText(dataList.get(1));
            txtPurchaseOrderId.setText(dataList.get(2));
//            txtUserName.setText(internalGrnDao.resolveUserId(dataList.get(3)));
            txtPurchaseOrderDate.setText(ExternalGrnDao.
                    loadingPurchaseOrderDate(txtPurchaseOrderId.getText()));
            grnTableDataLoader(grnId);

        }

    }

    private void grnTableDataLoader(String grnId) {

        itemTableCleanner();

        ArrayList<ArrayList<String>> itemDetails
                = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> list = ExternalGrnDao.searchGrnItemDetails(
                grnId);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemDetails.add(list.get(i));
            }

            if (itemDetails != null && itemDetails.size() > 0) {
                for (int i = 0; i < itemDetails.size(); i++) {

                    Item item = new Item();

                    item.colItemId.setValue(itemDetails.get(i).get(0));
                    item.colItemName.setValue(itemDetails.get(i).get(1));
                    item.colItemDescription.setValue(itemDetails.get(i).
                            get(2));
                    item.colQty.setValue(itemDetails.get(i).get(3));
                    item.colBatchNo.setValue(itemDetails.get(i).get(4));

                    TableItemData.add(item);
                }
            } else {
                mb.ShowMessage(stage, ErrorMessages.InvalidEvent,
                        "Error", MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            }

        }

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                deactivateSearch();

                btnDelete.setDisable(true);
                btnDelete.setVisible(false);

                break;

            case DELETE:
                disableUi(false);

                btnSave.setVisible(false);
                break;

            case READ_ONLY:
                disableUi(false);

                btnSave.setVisible(false);

                btnDelete.setDisable(true);
                btnDelete.setVisible(false);

                break;

            case FULL_ACCESS:
                disableUi(false);
                btnDelete.setDisable(true);
                btnDelete.setVisible(false);
                break;

            case NO_ACCESS:
                disableUi(true);

                break;

        }

    }

    private void disableUi(boolean state) {

        txtGRNID.setDisable(state);
        txtGRNID.setVisible(!state);

        btnSearchGRNID.setDisable(state);
        btnSearchGRNID.setVisible(!state);

        btnSearchGRNID.setDisable(state);
        btnSearchGRNID.setVisible(!state);

        btnSearchPurchaseOrderId.setDisable(state);
        btnSearchPurchaseOrderId.setVisible(!state);

        txtGRNDescription.setDisable(state);
        txtGRNDescription.setVisible(!state);

        txtPurchaseOrderDate.setDisable(state);
        txtPurchaseOrderDate.setVisible(!state);

        txtUserName.setDisable(state);
        txtUserName.setVisible(!state);

        txtDate.setDisable(state);
        txtDate.setVisible(!state);

        tblGRNItems.setDisable(state);
        tblGRNItems.setVisible(!state);

        btnDelete.setDisable(state);
        btnDelete.setVisible(!state);

        btnSave.setDisable(state);
        btnSave.setVisible(!state);

        btnClose.setDisable(state);
        btnClose.setVisible(!state);

        btnRefresh.setDisable(state);
        btnRefresh.setVisible(!state);

    }

    private void setUserAccessLevel() {

        userId = UserSession.getInstance().getUserInfo().getEid();
        userName = UserSession.getInstance().getUserInfo().getName();
        userCategory = UserSession.getInstance().getUserInfo().getCategory();
        txtUserName.setText(userName);

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

    private void deactivateSearch() {
        componentControl.deactivateSearch(lblGRNID, txtGRNID,
                btnSearchGRNID,
                220.00, 0.00);

    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Classes">
    public class Item {

        public SimpleStringProperty colItemId = new SimpleStringProperty(
                "tcItemId");
        public SimpleStringProperty colItemName = new SimpleStringProperty(
                "tcItemName");
        public SimpleStringProperty colItemDescription
                = new SimpleStringProperty("tcItemDescription");
        public SimpleStringProperty colUnit = new SimpleStringProperty("tcUnit");
        public SimpleStringProperty colQty = new SimpleStringProperty("tcQty");

        public SimpleStringProperty colBatchNo = new SimpleStringProperty(
                "tcBatchNo");

        public String getColItemId() {
            return colItemId.get();
        }

        public String getColItemName() {
            return colItemName.get();
        }

        public String getColItemDescription() {
            return colItemDescription.get();
        }

        public String getColUnit() {
            return colUnit.get();
        }

        public String getColQty() {
            return colQty.get();
        }

        public String getColBatchNo() {
            return colBatchNo.get();
        }

    }

    public class GRNItem {

        public SimpleStringProperty colGRNItemId = new SimpleStringProperty(
                "tcGRNItemId");
        public SimpleStringProperty colGRNItemName = new SimpleStringProperty(
                "tcGRNItemName");
        public SimpleStringProperty colGRNItemDescription
                = new SimpleStringProperty("tcGRNItemDescription");
        public SimpleStringProperty colGRNUnit = new SimpleStringProperty(
                "tcGRNUnit");
        public SimpleStringProperty colGRNQty = new SimpleStringProperty(
                "tcGRNQty");

        public SimpleStringProperty colGRNBatchNo = new SimpleStringProperty(
                "tcGRNBatchNo");

        public SimpleStringProperty colPrice = new SimpleStringProperty(
                "tcPrice");

        public SimpleStringProperty colVAT = new SimpleStringProperty(
                "tcVAT");

        public String getColGRNItemId() {
            return colGRNItemId.get();
        }

        public String getColGRNItemName() {
            return colGRNItemName.get();
        }

        public String getColGRNItemDescription() {
            return colGRNItemDescription.get();
        }

        public String getColGRNUnit() {
            return colGRNUnit.get();
        }

        public String getColGRNQty() {
            return colGRNQty.get();
        }

        public String getColGRNBatchNo() {
            return colGRNBatchNo.get();
        }

        public String getColPrice() {
            return colPrice.get();
        }

        public String getColVAT() {
            return colVAT.get();
        }

    }

//</editor-fold>
}
