package com.saiton.ccs.stock;

import com.saiton.ccs.base.UserPermission;
import com.saiton.ccs.base.UserSession;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.stockdao.PurchaseOrderDAO;
import com.saiton.ccs.stockdao.SupplierDAO;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.validations.CustomComboboxValidationImplT;
import com.saiton.ccs.validations.CustomTableViewValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;

/**
 * Purchase Order
 *
 * @author Saitonya
 */
public class PurchaseOrderController implements Initializable, StagePassable {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPurchaseOrderId;
    @FXML
    private Button btnPurchaseOrderIdSearch;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtSupplier;
    @FXML
    private Button btnSupplierSearch;

    @FXML
    private Label lblPurchaseOrder;

    @FXML
    private TextField txtItemName;
    @FXML
    private TextField txtItemId;
    @FXML
    private Button btnItemSearch;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtReOrder;

    @FXML
    private TableView<PurchaseItem> tvItems;
    @FXML
    private TableColumn<PurchaseItem, String> tcItemId;
    @FXML
    private TableColumn<PurchaseItem, String> tcBatchNo;
    @FXML
    private TableColumn<PurchaseItem, String> tcQty;
    @FXML
    private TableColumn<PurchaseItem, String> tcReOrder;
    @FXML
    private TableColumn<PurchaseItem, String> tcRemark;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnItemRefresh;
//    @FXML
//    private TextField txtItemUnit;
    @FXML
    private TextField txtItemTotalQty;

    @FXML
    private ComboBox<Batch> cmbBatchNo;

    @FXML
    private TextField txtBatchPrice;
    @FXML
    private TextField txtPurchaseRemark;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRefresh;
    //<editor-fold defaultstate="collapsed" desc="insets">
    @FXML
    private Insets x1;
    @FXML
    private Insets x3;
    @FXML
    private Insets x2;
    @FXML
    private Insets x211;
    @FXML
    private Insets x2111;
    @FXML
    private Insets x2112;
    //</editor-fold>

    private Stage stage;

    private ValidationSupport vsBase, vsAdd;

    private boolean isUpdate = false;

    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

    private final PurchaseOrderFacade purchaseFacade = new PurchaseOrderFacade();
    private final FormatAndValidate fav = new FormatAndValidate();
    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();
    PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();
    SupplierFacade supplierDAO = new SupplierFacade();

    //============
    ObservableList<Batch> batchData;
    ObservableList<PurchaseItem> purchaseItemData;
    //============
    private PopOver suppliersPop;
    private ObservableList<Supplier> suppliersData;
    private TableView<Supplier> tvSuppliers;

    private PopOver purchasePop;
    private ObservableList<Purchase> purchasePopData;
    private TableView<Purchase> tvPurchasePop;

    private PopOver itemsPop;
    private ObservableList<Item> itemsData;
    private TableView<Item> tvPopItems;

    private ComponentControl componentControl = new ComponentControl();

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    ComponentControl component = new ComponentControl();
    @FXML
    private Label itemIdLbl;
    @FXML
    private Label lblRemark;
    @FXML
    private Label supplierLbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //IGNORE
    }

    private void createPopPurchase() {

        tvPurchasePop = new TableView<>();
        TableColumn<Purchase, String> pid = new TableColumn<>("Purchase Order");
        pid.setMinWidth(50.0);
        TableColumn<Purchase, String> sid = new TableColumn<>("Supplier ID");
        sid.setMinWidth(50.0);
        TableColumn<Purchase, String> sname = new TableColumn<>("Supplier Name");
        sname.setMinWidth(50.0);
        TableColumn<Purchase, String> date = new TableColumn<>("Date");
        date.setMinWidth(50.0);

        pid.setCellValueFactory(
                new PropertyValueFactory("pid"));

        sid.setCellValueFactory(
                new PropertyValueFactory("sid"));

        sname.setCellValueFactory(
                new PropertyValueFactory("name"));
        date.setCellValueFactory(
                new PropertyValueFactory("date"));

        tvPurchasePop.getColumns().addAll(pid, sid, sname, date);

        purchasePopData = tvPurchasePop.getItems();
        purchasePopData.clear();
        purchasePop = new PopOver(tvPurchasePop);

        tvPurchasePop.setOnMouseClicked(e -> {

            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                purchasePop.hide();

            } else if (e.getClickCount() == 2) {
                try {
                    Purchase atom = tvPurchasePop.getSelectionModel().
                            getSelectedItem();

                    if (atom != null) {
                        clear();
                        isUpdate = true;
                        toggleUpdate();
                        txtDate.setText(atom.getDate());
                        txtSupplier.setText(atom.getSid());
                        txtPurchaseOrderId.setText(atom.getPid());
                        List<PurchaseItem> data = purchaseFacade.
                                selectPurchaseItems(atom.getPid());
                        if (data != null) {
                            purchaseItemData.addAll(data);
                        }

                    }

                } catch (Exception n) {

                }

                purchasePop.hide();
                validate();
            }
        });
    }

    private void createPopItems() {

        tvPopItems = new TableView<>();
        TableColumn<Item, String> itemId = new TableColumn<>("Item ID");
        itemId.setMinWidth(50.0);
        TableColumn<Item, String> name = new TableColumn<>("Name");
        name.setMinWidth(50.0);
//        TableColumn<Item, String> cat = new TableColumn<>("Category");
//        cat.setMinWidth(50.0);
//        TableColumn<Item, String> unit = new TableColumn<>("Unit");
//        unit.setMinWidth(50.0);

//        cat.setCellValueFactory(
//                new PropertyValueFactory("category"));
        itemId.setCellValueFactory(
                new PropertyValueFactory("itemId"));

        name.setCellValueFactory(
                new PropertyValueFactory("name"));
//
//        unit.setCellValueFactory(
//                new PropertyValueFactory("unit"));

        tvPopItems.getColumns().addAll(itemId, name);

        itemsData = tvPopItems.getItems();
        itemsData.clear();
        itemsPop = new PopOver(tvPopItems);

        tvPopItems.setOnMouseClicked(e -> {

            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                itemsPop.hide();

            } else if (e.getClickCount() == 2) {
                try {
                    Item atom = tvPopItems.getSelectionModel().
                            getSelectedItem();

                    if (atom != null) {
                        txtItemId.setText(atom.getItemId());
                        txtItemName.setText(atom.getName());
                        txtItemTotalQty.setText(atom.getQuantity());
                        batchData.clear();
                        batchData.addAll(purchaseFacade.selectAllBatch(atom.
                                getItemId()));
                        cmbBatchNo.getSelectionModel().selectFirst();
                        setBatchData();
                    }

                } catch (Exception n) {

                }

                itemsPop.hide();
                validate();
            }
        });
    }

    private void createPopSuppliers() {

        tvSuppliers = new TableView<>();
        TableColumn<Supplier, String> sid = new TableColumn<>("Supplier ID");
        sid.setMinWidth(50.0);
        TableColumn<Supplier, String> name = new TableColumn<>("Name");
        name.setMinWidth(50.0);
        TableColumn<Supplier, String> regNo = new TableColumn<>("Reg No");
        regNo.setMinWidth(50.0);

        sid.setCellValueFactory(
                new PropertyValueFactory("sid"));

        name.setCellValueFactory(
                new PropertyValueFactory("name"));

        regNo.setCellValueFactory(
                new PropertyValueFactory("regNo"));

        tvSuppliers.getColumns().addAll(sid, name, regNo);

        suppliersData = tvSuppliers.getItems();
        suppliersData.clear();
        suppliersPop = new PopOver(tvSuppliers);

        tvSuppliers.setOnMouseClicked(e -> {

            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                suppliersPop.hide();

            } else if (e.getClickCount() == 2) {
                try {
                    Supplier atom = tvSuppliers.getSelectionModel().
                            getSelectedItem();

                    if (atom != null) {
                        txtSupplier.setText(atom.getSid());
                    }

                } catch (Exception n) {

                }

                suppliersPop.hide();
                validate();
            }
        });
    }

    private void createPop() {
        createPopItems();
        createPopSuppliers();
        createPopPurchase();
    }

    private void setUser() {
        //get and set the user id
        //txtUsername.setText(UserSession.getInstance().getUserInfo().getEid());

        //hardcoded as requested
        txtUsername.setText("EM0001");
    }

    private void validate() {
        vsBase.registerValidator(txtSupplier, new CustomTextFieldValidationImpl(
                txtSupplier, txtSupplier.getText().isEmpty(),
                ErrorMessages.Empty));

        vsBase.registerValidator(tvItems, new CustomTableViewValidationImpl(
                tvItems, !fav.validTableView(tvItems), ErrorMessages.Empty));

        vsAdd.registerValidator(txtItemId, new CustomTextFieldValidationImpl(
                txtItemId, txtItemId.getText().isEmpty(),
                ErrorMessages.Empty));

        vsAdd.registerValidator(txtQty, new CustomTextFieldValidationImpl(
                txtQty, !fav.validMinInt(txtQty.getText(), 1),
                ErrorMessages.InvalidQty));

        vsAdd.registerValidator(txtReOrder, new CustomTextFieldValidationImpl(
                txtReOrder, !fav.validMinInt(txtReOrder.getText(), 0),
                ErrorMessages.InvalidReOrderLevel));

        vsAdd.registerValidator(cmbBatchNo,
                new CustomComboboxValidationImplT<Batch>(cmbBatchNo, cmbBatchNo.
                        getValue() == null, ErrorMessages.Empty));
    }

    private void clear() {
        txtDate.setText(format.format(new Date()));
        txtPurchaseOrderId.setText(purchaseFacade.generateId());
        txtItemId.clear();
        txtBatchPrice.clear();
        txtItemName.clear();
        txtItemTotalQty.clear();
//        txtItemUnit.clear();
        txtPurchaseRemark.clear();
        txtQty.clear();
        txtReOrder.clear();
        txtSupplier.clear();
        batchData.clear();
        purchaseItemData.clear();
        setUser();
        isUpdate = false;
        toggleUpdate();
        validate();
    }

    private void clearItems() {
        txtItemId.clear();
        txtBatchPrice.clear();
        txtItemName.clear();
        txtItemTotalQty.clear();
        txtPurchaseRemark.clear();
        txtQty.clear();
        txtReOrder.clear();
        batchData.clear();

        validate();
    }

    private void toggleUpdate() {
        if (isUpdate) {
            btnDelete.setVisible(true);
        } else {
            btnDelete.setVisible(false);
        }
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        clear();
        clearItems();
        toggleUpdate();
    }

    @FXML
    private void btnItemRefreshOnAction(ActionEvent event) {
        clearItems();
    }

    @FXML
    private void txtPurchaseOrderIdOnKeyReleased(KeyEvent event) {
        purchasePopData.clear();
        if (txtPurchaseOrderId.getText().length() >= 3) {

            List<Purchase> allPurchases = purchaseFacade.searchPurchases(txtPurchaseOrderId.getText());
            if (allPurchases != null) {
                purchasePopData.addAll(allPurchases);
            }
            if (!purchasePopData.isEmpty()) {
                purchasePop.show(btnPurchaseOrderIdSearch);
            } else {
                purchasePop.hide();
            }
        }
        validate();
    }

    @FXML
    private void btnPurchaseOrderIdSearchOnAction(ActionEvent event) {
        purchasePopData.clear();
        List<Purchase> allPurchases = purchaseFacade.searchPurchases(txtPurchaseOrderId.getText());
        if (allPurchases != null) {
            purchasePopData.addAll(allPurchases);
        }
        if (!purchasePop.isShowing()) {
            purchasePop.show(btnPurchaseOrderIdSearch);
        }
        validate();
    }

    @FXML
    private void txtSupplierOnKeyReleased(KeyEvent event) {
        suppliersData.clear();
        if (txtSupplier.getText().length() >= 3) {

            List<Supplier> supliers = supplierDAO.searchAllSuppliers(txtSupplier.getText());
            if (supliers != null) {
                suppliersData.addAll(supliers);
            }
            if (!suppliersData.isEmpty()) {
                suppliersPop.show(btnSupplierSearch);
            } else {
                suppliersPop.hide();
            }
        }
        validate();
    }

    @FXML
    private void btnSupplierSearchOnAction(ActionEvent event) {
        suppliersData.clear();
        List<Supplier> supliers = supplierDAO.searchAllSuppliers(txtSupplier.getText());
        if (supliers != null) {
            suppliersData.addAll(supliers);
        }
        if (!suppliersData.isEmpty()) {
            suppliersPop.show(btnSupplierSearch);
        }
    }

    @FXML
    private void txtItemIdOnKeyReleased(KeyEvent event) {

        String sup = txtSupplier.getText();
        String search = txtItemName.getText();
        if (sup.isEmpty()) {
            return;
        }

        if (txtItemId.getText().length() >= 3) {
            itemsData.clear();
            List<Item> items = purchaseFacade.searchItemsBySupplier(sup, search);
            if (items != null) {
                itemsData.addAll(items);
            }
            if (!itemsData.isEmpty()) {
                itemsPop.show(btnItemSearch);
            } else {
                itemsPop.hide();
            }
        }
        validate();
    }

    @FXML
    private void btnItemSearchOnAction(ActionEvent event) {

        String sup = txtSupplier.getText();
        String search = txtItemName.getText();
        if (sup.isEmpty()) {
            return;
        }
        itemsData.clear();
        List<Item> items = purchaseFacade.searchItemsBySupplier(sup, search);
        if (items != null) {
            itemsData.addAll(items);
        }
        if (!itemsData.isEmpty()) {
            itemsPop.show(btnItemSearch);
        }
    }

    @FXML
    private void txtQtyOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    private void txtReOrderOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        validate();

        if (vsBase.isInvalid()) {
            mb.ShowMessage(stage, "Please fill the required data",
                    "Purchase Order",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }
        Boolean updateSuccess = false;
        Boolean insertSuccess = false;
        if (isUpdate) {
            MessageBox.MessageOutput option = mb.ShowMessage(stage,
                    ErrorMessages.Update, MessageBoxTitle.INFORMATION.toString(),
                    MessageBox.MessageIcon.MSG_ICON_NONE,
                    MessageBox.MessageType.MSG_YESNO);
            if (option.equals(MessageBox.MessageOutput.MSG_YES)) {
                updateSuccess = purchaseFacade.updatePurchaseOrder(txtPurchaseOrderId.
                        getText(), txtSupplier.getText(), txtUsername.getText(),
                        purchaseItemData);
            }
            if (updateSuccess == true) {
                mb.ShowMessage(stage, ErrorMessages.SuccesfullyUpdated,
                        "Purchase Order",
                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                        MessageBox.MessageType.MSG_OK);
                clear();
            } else {
                mb.ShowMessage(stage, "Failed",
                        "Purchase Order",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
            }
        } else {
            insertSuccess = purchaseFacade.insertPurchaseOrder(txtSupplier.getText(),
                    txtUsername.getText(),
                    purchaseItemData);

            if (insertSuccess == true) {
                mb.ShowMessage(stage, ErrorMessages.SuccesfullyCreated,
                        "Purchase Order",
                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                        MessageBox.MessageType.MSG_OK);
                clear();
            } else {
                mb.ShowMessage(stage, "Failed",
                        "Purchase Order",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
            }
        }

    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        if (itemsPop.isShowing()) {
            itemsPop.hide();
            return;
        }
        if (suppliersPop.isShowing()) {
            suppliersPop.hide();
            return;
        }

        if (purchasePop.isShowing()) {
            purchasePop.hide();
            return;
        }
        stage.close();
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        validate();

        if (isUpdate) {
            MessageBox.MessageOutput option = mb.ShowMessage(stage,
                    ErrorMessages.Delete, MessageBoxTitle.INFORMATION.toString(),
                    MessageBox.MessageIcon.MSG_ICON_NONE,
                    MessageBox.MessageType.MSG_YESNO);
            if (option.equals(MessageBox.MessageOutput.MSG_YES)) {
                String sid = txtPurchaseOrderId.getText();
                if (purchaseFacade.deletePurchaseOrder(sid)) {
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
                clear();
            }
        }
        validate();
    }

    private void setBatchData() {
        Batch b = cmbBatchNo.getValue();
        if (b == null) {
            return;
        }
        txtBatchPrice.setText(b.getPrice());
        txtReOrder.setText(b.getReorder());
        validate();
    }

    @FXML
    private void cmbBatchNoOnAction(ActionEvent event) {
        validate();
        setBatchData();
    }

    @FXML
    private void txtPurchaseRemarkOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {

        validate();
        if (vsAdd.isInvalid()) {
            mb.ShowMessage(stage, "Please Fill Data", "Purchase Order",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        PurchaseItem item = new PurchaseItem();
        item.setBatchNo(cmbBatchNo.getValue().getBatch());
        item.setItemId(txtItemId.getText());
        item.setQty(txtQty.getText());
        item.setRemarks(txtPurchaseRemark.getText());
        item.setReorder(txtReOrder.getText());
        purchaseItemData.add(item);
        clearItems();
        validate();
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;

        setUserAccessLevel();

        vsBase = new ValidationSupport();
        vsAdd = new ValidationSupport();
        batchData = cmbBatchNo.getItems();
        purchaseItemData = tvItems.getItems();
        stage.setOnCloseRequest(event -> {
            //HANDLE POPUPS
            if (itemsPop.isShowing()) {
                itemsPop.hide();
                event.consume();
            }
            if (suppliersPop.isShowing()) {
                suppliersPop.hide();
                event.consume();
            }
            if (purchasePop.isShowing()) {
                purchasePop.hide();
                event.consume();
            }
        });
        //===
        tcBatchNo.setCellValueFactory(
                new PropertyValueFactory("batchNo"));
        tcItemId.setCellValueFactory(
                new PropertyValueFactory("itemId"));
        tcQty.setCellValueFactory(
                new PropertyValueFactory("qty"));
        tcReOrder.setCellValueFactory(
                new PropertyValueFactory("reorder"));
        tcRemark.setCellValueFactory(
                new PropertyValueFactory("remarks"));
        //=======
        createPop();

        clear();
        if (obj != null) {
            DataFromApprove(obj[0].toString(), obj[1].toString());
        }
    }

    @FXML
    private void tvItemsOnMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            PurchaseItem p = tvItems.getSelectionModel().getSelectedItem();
            if (p != null) {
                purchaseItemData.remove(p);
            }
        }
        validate();
    }

    //<editor-fold defaultstate="collapsed" desc="Methods">
    private void DataFromApprove(String purchaseOrderId, String supplierId) {

        btnSave.setVisible(false);
        btnDelete.setVisible(false);

        txtPurchaseOrderId.setVisible(true);
        txtPurchaseOrderId.setDisable(true);
        txtQty.setVisible(true);
        txtQty.setDisable(true);
        txtItemName.setVisible(true);
        txtItemName.setDisable(true);
        txtItemId.setVisible(true);
        txtItemId.setDisable(true);
        txtItemTotalQty.setVisible(true);
        txtItemTotalQty.setDisable(true);
        cmbBatchNo.setVisible(true);
        cmbBatchNo.setDisable(true);
        txtBatchPrice.setVisible(true);
        txtBatchPrice.setDisable(true);
        btnItemSearch.setVisible(true);
        txtReOrder.setDisable(true);

        component.deactivateSearch(supplierLbl, txtSupplier, btnSupplierSearch, 220.0, 0.0);

        component.deactivateSearch(lblRemark, txtPurchaseRemark, btnAdd, 220.0, 0.0);

        component.deactivateTextFieldWithTwoButtons(lblPurchaseOrder, txtPurchaseOrderId,
                btnPurchaseOrderIdSearch, btnRefresh, 220.0, 0.0, 0.0, 5.0, true);
        component.deactivateTextFieldWithTwoButtons(itemIdLbl, txtItemId,
                btnItemSearch, btnItemRefresh, 220.0, 0.0, 0.0, 5.0, true);

        existingAdditionalItemToTable(purchaseOrderId, supplierId);
    }

    private void existingAdditionalItemToTable(String purchaseOrderId, String suplierId) {
        purchaseItemData.clear();

        ArrayList<String> purchaseData = purchaseOrderDAO.searchPurchaseDetails(purchaseOrderId);
        List<PurchaseItem> data = purchaseFacade.
                selectPurchaseItems(purchaseOrderId);

        txtPurchaseOrderId.setText(purchaseOrderId);
        txtSupplier.setText(purchaseData.get(0));
        txtDate.setText(purchaseData.get(1));
        txtUsername.setText(purchaseData.get(2));

        if (data != null) {

            purchaseItemData.addAll(data);
            tvItems.setItems(purchaseItemData);
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

                btnSave.setDisable(true);
                btnSave.setVisible(false);

                break;

            case READ_ONLY:

                disableUi(false);
                btnDelete.setDisable(true);
                btnDelete.setVisible(false);

                btnSave.setDisable(true);
                btnSave.setVisible(false);

                break;

            case ALL_BUT_DELETE:
                disableUi(false);

                btnDelete.setDisable(true);
                btnDelete.setVisible(false);

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

        txtPurchaseOrderId.setDisable(state);
        txtPurchaseOrderId.setVisible(!state);

        txtSupplier.setDisable(state);
        txtSupplier.setVisible(!state);

        txtDate.setDisable(state);
        txtDate.setVisible(!state);

        txtUsername.setDisable(state);
        txtUsername.setVisible(!state);

        txtItemId.setDisable(state);
        txtItemId.setVisible(!state);

        txtItemName.setDisable(state);
        txtItemName.setVisible(!state);

        txtItemTotalQty.setDisable(state);
        txtItemTotalQty.setVisible(!state);

        cmbBatchNo.setDisable(state);
        cmbBatchNo.setVisible(!state);

        txtBatchPrice.setDisable(state);
        txtBatchPrice.setVisible(!state);

        txtQty.setDisable(state);
        txtQty.setVisible(!state);

        txtReOrder.setDisable(state);
        txtReOrder.setVisible(!state);

        txtPurchaseRemark.setDisable(state);
        txtPurchaseRemark.setVisible(!state);

        tvItems.setDisable(state);
        tvItems.setVisible(!state);

        btnDelete.setDisable(state);
        btnDelete.setVisible(!state);

        btnSave.setDisable(state);
        btnSave.setVisible(!state);

        btnCancel.setDisable(state);
        btnCancel.setVisible(!state);

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

    private void deactivateSearch() {
        componentControl.deactivateSearch(lblPurchaseOrder, txtPurchaseOrderId,
                btnPurchaseOrderIdSearch,
                220.00, 0.00);

    }

//</editor-fold>
}
