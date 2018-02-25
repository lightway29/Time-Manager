package com.saiton.ccs.stock;

import com.saiton.ccs.base.UserPermission;
import com.saiton.ccs.base.UserSession;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.InputDialog;
import com.saiton.ccs.validations.CustomListViewValidationImpl;
import com.saiton.ccs.validations.CustomTextAreaValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Saitonya
 */
public class SupplierRegistrationController implements Initializable,
        StagePassable {

    @FXML
    private TextField txtName;

    @FXML
    private Insets x1;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private Button btnSupplierIdSearch;

    @FXML
    private Insets x2;

    @FXML
    private TextField txtItemId;

    @FXML
    private Button btnItemSearch;

    @FXML
    private TableView<Item> tblItemList;

    @FXML
    private TableColumn<Item, String> tcItemId;

    @FXML
    private TableColumn<Item, String> tcItemName;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtTitle;

    @FXML
    private ComboBox<String> cmbPhone;
    @FXML
    private Button btnPlus;
    @FXML
    private Button btnMinus;
    @FXML
    private Insets x11;

    ComponentControl componentControl = new ComponentControl();
    //User Access Components
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;
    private String userName;
    private String userCategory;
    String userId;

    @FXML
    private TextArea txtAreaAddress;

    private Stage stage;

    private ValidationSupport vsBase;
    private ValidationSupport vsItem;

    private final FormatAndValidate fav = new FormatAndValidate();

    private ObservableList<Item> supplierItemsData;

    private ObservableList<String> telephonesData;

    private final SupplierFacade supplierDao = new SupplierFacade();

    private boolean isUpdate = false;

    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();

    //=================
    //POPUP
    private PopOver suppliersPop;
    private ObservableList<Supplier> suppliersData;
    private TableView<Supplier> tvSuppliers;

    private PopOver itemsPop;
    private ObservableList<Item> itemsData;
    private TableView<Item> tvPopItems;
    @FXML
    private Button btnSave;

    @FXML
    private Button btnClose;

    @FXML
    private TextField txtRegNo;

    @FXML
    private Label lblSupplierId;
    @FXML
    private Button btnRefresh;

    //=================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //IGNORE
    }

    private void clear() {
        txtAreaAddress.clear();
//        txtCategory.clear();
        txtItemId.clear();
        txtName.clear();
        txtRegNo.clear();
        txtSupplierId.setText(supplierDao.generateSupplierId());
        txtTitle.clear();
//        txtUnit.clear();
        supplierItemsData.clear();
        telephonesData.clear();
        isUpdate = false;
        toggleUpdate();
        validate();
    }

    private void toggleUpdate() {
        if (isUpdate) {
            btnDelete.setVisible(true);
        } else {
            btnDelete.setVisible(false);
        }
    }

    private void createPopItems() {

        tvPopItems = new TableView<>();
        TableColumn<Item, String> itemId = new TableColumn<>("Item ID");
        itemId.setMinWidth(50.0);
        TableColumn<Item, String> name = new TableColumn<>("Name");
        name.setMinWidth(50.0);
//        TableColumn<Item, String> cat = new TableColumn<>("Category");
//        cat.setMinWidth(50.0);
////        TableColumn<Item, String> unit = new TableColumn<>("Unit");
//        unit.setMinWidth(50.0);

//        cat.setCellValueFactory(
//                new PropertyValueFactory("category"));
        itemId.setCellValueFactory(
                new PropertyValueFactory("itemId"));

        name.setCellValueFactory(
                new PropertyValueFactory("name"));

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
                        txtTitle.setText(atom.getName());
//                        txtUnit.setText(atom.getUnit());
//                        txtCategory.setText(atom.getCategory());

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
                        String supId = atom.getSid();
                        txtName.setText(atom.getName());
                        txtSupplierId.setText(supId);
                        txtRegNo.setText(atom.getRegNo());
                        txtAreaAddress.setText(atom.getAddress());
                        telephonesData.clear();
                        List<String> dataTelephones
                                = supplierDao.selectTelephoneNumbers(supId);
                        if (dataTelephones != null) {
                            telephonesData.addAll(dataTelephones);
                        }
                        cmbPhone.getSelectionModel().selectFirst();

                        supplierItemsData.clear();
                        List<Item> dataItems = supplierDao.selectItems(supId);
                        if (dataItems != null) {
                            supplierItemsData.addAll(dataItems);
                        }
                        isUpdate = true;
                        toggleUpdate();
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
    }

    private void validate() {
        //validate
        vsBase.registerValidator(txtRegNo, new CustomTextFieldValidationImpl(
                txtRegNo, !fav.validNumber(txtRegNo.getText()),
                ErrorMessages.InvalidRegNo));

        vsBase.registerValidator(txtAreaAddress,
                new CustomTextAreaValidationImpl(
                        txtAreaAddress, !fav.validAddress(txtAreaAddress.
                                getText()),
                        ErrorMessages.InvalidAddress));

        vsBase.registerValidator(txtName, new CustomTextFieldValidationImpl(
                txtName, !fav.validName(txtName.getText()),
                ErrorMessages.InvalidName));

        vsBase.registerValidator(txtSupplierId, Validator.createEmptyValidator(
                ErrorMessages.Empty));

        vsBase.registerValidator(cmbPhone, Validator.createEmptyValidator(
                ErrorMessages.Empty));
        vsBase.registerValidator(tblItemList, new CustomListViewValidationImpl(
                tblItemList, supplierItemsData.size() <= 0, ErrorMessages.Empty));

        vsItem.registerValidator(txtItemId, Validator.createEmptyValidator(
                ErrorMessages.Empty));
        //    txtAreaAddress

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                componentControl.deactivateSearch(lblSupplierId,
                        txtSupplierId, btnSupplierIdSearch,
                        220.0, USE_PREF_SIZE);
                btnDelete.setDisable(true);
                btnDelete.setVisible(false);
                break;

            case DELETE:
                disableUi(false);

                btnSave.setDisable(true);
                btnSave.setVisible(false);

                break;

            case READ_ONLY:
                btnClose.setDisable(false);
                btnClose.setVisible(true);
                btnDelete.setDisable(true);
                btnDelete.setVisible(false);
                btnSave.setDisable(false);
                btnSave.setVisible(true);

                btnPlus.setDisable(false);
                btnPlus.setVisible(true);

                btnMinus.setDisable(false);
                btnMinus.setVisible(true);

                cmbPhone.setDisable(false);
                cmbPhone.setVisible(true);

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

        btnClose.setDisable(state);
        btnClose.setVisible(!state);

        btnSave.setDisable(state);
        btnSave.setVisible(!state);

        btnDelete.setDisable(state);
        btnDelete.setVisible(!state);

        txtSupplierId.setDisable(state);
        txtSupplierId.setVisible(!state);

        txtName.setDisable(state);
        txtName.setVisible(!state);

        cmbPhone.setDisable(state);
        cmbPhone.setVisible(!state);

        txtAreaAddress.setDisable(state);
        txtAreaAddress.setVisible(!state);

        txtItemId.setDisable(state);
        txtItemId.setVisible(!state);

        txtTitle.setDisable(state);
        txtTitle.setVisible(!state);

        btnPlus.setDisable(state);
        btnPlus.setVisible(!state);

        btnMinus.setDisable(state);
        btnMinus.setVisible(!state);

        tblItemList.setDisable(state);
        tblItemList.setVisible(!state);

    }

    private void setUserAccessLevel() {

        userId = UserSession.getInstance().getUserInfo().getEid();
        userName = UserSession.getInstance().getUserInfo().getName();
        userCategory = UserSession.getInstance().getUserInfo().getCategory();

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

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        clear();
        toggleUpdate();

    }

    @FXML
    private void txtNameOnkeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    private void txtSupplierIdOnKeyReleased(KeyEvent event) {
        suppliersData.clear();
        if (txtSupplierId.getText().length() >= 3) {

            List<Supplier> supliers = supplierDao.searchAllSuppliers(
                    txtSupplierId.getText());
            if (supliers != null) {
                suppliersData.addAll(supliers);
            }
            if (!suppliersData.isEmpty()) {
                suppliersPop.show(btnSupplierIdSearch);
            } else {
                suppliersPop.hide();
            }
        }
        validate();
    }

    @FXML
    private void btnSupplierIdSearchOnAction(ActionEvent event) {
        suppliersData.clear();
        List<Supplier> supliers = supplierDao.searchAllSuppliers(txtSupplierId.
                getText());
        if (supliers != null) {
            suppliersData.addAll(supliers);
        }
        if (!suppliersPop.isShowing()) {
            suppliersPop.show(btnSupplierIdSearch);
        }
    }

    @FXML
    private void txtItemIdOnKeyReleased(KeyEvent event) {
        if (txtItemId.getText().length() >= 3) {
            itemsData.clear();
            List<Item> items = supplierDao.selectAllItems();
            if (items != null) {
                itemsData.addAll(items);
            }
            if (!itemsData.isEmpty()) {
                itemsPop.show(btnItemSearch);
            } else {
                itemsPop.hide();
            }
            validate();
        }
    }

    @FXML
    private void btnItemSearchOnAction(ActionEvent event) {
        itemsData.clear();
        List<Item> items = supplierDao.selectAllItems();
        if (items != null) {
            itemsData.addAll(items);
        }
        if (!itemsData.isEmpty()) {
            itemsPop.show(btnItemSearch);
        }
        validate();
    }

    private void btnMinusItemOnAction(ActionEvent event) {
        Item item = tblItemList.getSelectionModel().getSelectedItem();
        if (item == null) {
            mb.ShowMessage(stage, "Need to select an item to delete",
                    "Supplier",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        } else {
            supplierItemsData.remove(item);
        }
    }

    private void failMessage() {
        mb.ShowMessage(stage, "Failed", "Supplier",
                MessageBox.MessageIcon.MSG_ICON_FAIL,
                MessageBox.MessageType.MSG_OK);
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        boolean insertSuccess = false;
        boolean UpdateSuccess = false;
        try {
            validate();
            if (vsBase.isInvalid()) {
                mb.ShowMessage(stage, "Please fill the required data",
                        "Supplier",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
                return;
            }

            String sid = null;
            if (isUpdate) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        ErrorMessages.Update, MessageBoxTitle.INFORMATION.
                        toString(),
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {
                    UpdateSuccess = supplierDao.updateSupplier(txtSupplierId.
                            getText(), txtName.
                            getText(), txtRegNo.getText(), txtAreaAddress.
                            getText());
                    sid = txtSupplierId.getText();
                    boolean success = supplierDao.setItems(sid,
                            supplierItemsData)
                            && supplierDao.setTelephoneNumbers(sid,
                                    telephonesData);
                    if (!UpdateSuccess) {
                        failMessage();
                        return;
                    }
                    if (success && UpdateSuccess) {
                        mb.ShowMessage(stage, ErrorMessages.SuccesfullyUpdated,
                                "Supplier",
                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                MessageBox.MessageType.MSG_OK);
                        clear();
                    } else {
                        failMessage();
                    }
                }
            } else {
                sid = supplierDao.insertSupplier(txtName.getText(),
                        txtRegNo.getText(), txtAreaAddress.getText());
                insertSuccess = sid != null;
                boolean success = supplierDao.setItems(sid, supplierItemsData)
                        && supplierDao.setTelephoneNumbers(sid, telephonesData);
                if (!insertSuccess) {
                    failMessage();
                    return;
                }
                if (success && insertSuccess) {
                    mb.ShowMessage(stage, ErrorMessages.SuccesfullyCreated,
                            "Supplier",
                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                            MessageBox.MessageType.MSG_OK);
                    clear();
                } else {
                    failMessage();
                }
            }

        } catch (Exception e) {
            failMessage();
            e.printStackTrace();
        }

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        if (itemsPop.isShowing()) {
            itemsPop.hide();
            return;
        }
        if (suppliersPop.isShowing()) {
            suppliersPop.hide();
            return;
        }

        stage.close();
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        if (isUpdate) {
            MessageBox.MessageOutput option = mb.ShowMessage(stage,
                    ErrorMessages.Delete, MessageBoxTitle.INFORMATION.toString(),
                    MessageBox.MessageIcon.MSG_ICON_NONE,
                    MessageBox.MessageType.MSG_YESNO);
            if (option.equals(MessageBox.MessageOutput.MSG_YES)) {
                String sid = txtSupplierId.getText();
                if (supplierDao.deleteSupplier(sid)) {
                    mb.ShowMessage(stage, ErrorMessages.SuccesfullyDeleted,
                            "Supplier",
                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                            MessageBox.MessageType.MSG_OK);

                } else {
                    mb.ShowMessage(stage, ErrorMessages.NotSuccesfullyDeleted,
                            "Supplier",
                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
                }
                clear();
            }
        }
    }

    @FXML
    private void txtNicOnkeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    private void cmbPhoneOnAction(ActionEvent event) {
        validate();
    }

    @FXML
    private void btnPlusOnAction(ActionEvent event) {
        String telephone = InputDialog.inputForAddNew("Telephone");
        if (telephone == null) {
            return;
        }
        if (!fav.validTelephoneNumber(telephone)) {
            mb.ShowMessage(stage, "Invalid Phone Number", "Supplier",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (telephonesData.contains(telephone)) {
            mb.ShowMessage(stage, "Duplicate Phone Number", "Supplier",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }
        //success
        telephonesData.add(telephone);
        cmbPhone.getSelectionModel().selectFirst();
        validate();
    }

    @FXML
    private void btnMinusOnAction(ActionEvent event) {
        String telephone = cmbPhone.getValue();
        if (telephone == null) {
            return;
        }
        telephonesData.remove(telephone);
        cmbPhone.getSelectionModel().selectFirst();
        validate();
    }

    @FXML
    private void txtAreaAddressOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        validate();
        if (vsItem.isInvalid()) {
            mb.ShowMessage(stage, "Please select an item.", "Supplier",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }
        String itemId = txtItemId.getText();

        for (Item atom : supplierItemsData) {
            if (atom.getItemId().equals(itemId)) {
                mb.ShowMessage(stage, "Cannot add same item.", "Supplier",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
                return;

            }
        }

        Item item = new Item();
        item.setItemId(itemId);
        item.setName(txtTitle.getText());
//        item.setUnit(txtUnit.getText());
//        item.setCategory(txtCategory.getText());

        supplierItemsData.add(item);

        validate();
        txtItemId.clear();
        txtTitle.clear();

    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;

        setUserAccessLevel();

        vsBase = new ValidationSupport();
        vsItem = new ValidationSupport();
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
        });
        supplierItemsData = tblItemList.getItems();

//        tcCategory.setCellValueFactory(
//                new PropertyValueFactory("category"));
        tcItemId.setCellValueFactory(
                new PropertyValueFactory("itemId"));

        tcItemName.setCellValueFactory(
                new PropertyValueFactory("name"));

//        tcUnit.setCellValueFactory(
//                new PropertyValueFactory("unit"));
        telephonesData = cmbPhone.getItems();
        createPop();
        clear();

    }

    @FXML
    private void tblItemListOnMouseClicked(MouseEvent event) {

        if (event.getClickCount() == 2) {

            Item item = tblItemList.getSelectionModel().getSelectedItem();
            if (item == null) {
                mb.ShowMessage(stage, "Need to select an item to delete",
                        "Supplier",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
            } else {
                supplierItemsData.remove(item);
            }
        }

    }

}
