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
import com.saiton.ccs.popup.ItemInfoPopup;
import com.saiton.ccs.stockdao.ItemDAO;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.InputDialog;
import com.saiton.ccs.validations.CustomComboboxValidationImpl;
import com.saiton.ccs.validations.CustomTableViewValidationImpl;
import com.saiton.ccs.validations.CustomTextAreaValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author
 */
public class ItemsController extends AnchorPane implements Initializable,
        Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    
    //<editor-fold defaultstate="collapsed" desc="TextFields">
    @FXML
    private TextField txtItemId;

    @FXML
    private TextArea txtItemName;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtPartNo;
    @FXML
    private TextField txtBuyingPrice;
    @FXML
    private TextArea txtItemDescription;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSellingPrice;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Comboboxes">
    @FXML
    private ComboBox<String> cmbBatchNo;

    @FXML
    private ComboBox<String> cmbMainCategory;

    @FXML
    private ComboBox<String> cmbUnitQty;
    
    @FXML
    private ComboBox<String> cmbUnit;
   

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Labels">
    @FXML
    private Label lblItemId;
    private Label lblItemId1;
    @FXML
    private Label lblItemName;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Buttons">
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnItemNameSearch;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnBatchNo;


//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Table Components">
    @FXML
    private TableColumn<Item, String> tcPartNo;
    @FXML
    private TableColumn<Item, String> tcItemId;
    @FXML
    private TableColumn<Item, String> tcItemName;
    @FXML
    private TableColumn<Item, String> tcBatchNo;
    @FXML
    private TableColumn<Item, String> tcBuyingPrice;

    @FXML
    private TableColumn<Item, String> tcSellingPrice;
    @FXML
    private TableColumn<Item, String> tcQty;
    @FXML
    private TableColumn<Item, String> tcMainCategory;
    @FXML
    private TableColumn<Item, String> tcSubCategory;
     @FXML
    private TableColumn<Item, String> tcItemDescripton;
    @FXML
    private TableColumn<Item, String> tcUnit;
    @FXML
    private TableColumn<Item, String> tcUnitQty;
    @FXML
    private TableView<Item> tblItemList;

//</editor-fold>
    
    //</editor-fold> 
    
    private final ValidationSupport validationSupportTableData
            = new ValidationSupport();
    private final ValidationSupport validationSupportTable
            = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();
    private MessageBox mb;
    private Stage stage;

    Item item = new Item();
    private ObservableList TableItemData = FXCollections.observableArrayList();
    ItemDAO itemDAO = new ItemDAO();
    boolean isupdate = false;
    int no = 1;

    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

    private ComponentControl componentControl = new ComponentControl();

    //item info popup---------------------------------------
    private TableView itemTable = new TableView();
    private PopOver itemPop;
    private ObservableList<ItemInfoPopup> itemData = FXCollections.
            observableArrayList();
    private ItemInfoPopup itemPopup = new ItemInfoPopup();
    private ObservableList<String> batchNoList;
    private ObservableList<String> subCategoryData;
    private ObservableList<String> mainCategoryData;
    private ObservableList<String> unitData;
    private ObservableList<String> unitQtyData;
    @FXML
    private ComboBox<String> cmbSubCategory;
    @FXML
    private Label lblItemDescription;
    @FXML
    private Label lblPartNo;
    @FXML
    private Label lblBatchNo;
    @FXML
    private Label lblMainCategory;
    @FXML
    private Button btnMainCategory;
    @FXML
    private Label lblSubCategory;
    @FXML
    private Button btnSubCategory;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblUnit;
    @FXML
    private Button btnUnit;
    @FXML
    private Label lblUnitQty;
    @FXML
    private Button btnUnitQty;
    @FXML
    private Label lblQty;
    @FXML
    private Label lblBuyingPrice;
    @FXML
    private Label lblSellingPrice;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
               

        tcItemId.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colItemId"));
        tcItemName.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colItemName"));
        tcItemDescripton.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colItemDescripton"));
        tcPartNo.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colPartNo"));
        tcBatchNo.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colBatchNo"));
        tcMainCategory.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colMainCategory"));
        tcSubCategory.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colSubCategory"));
        tcUnit.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colUnit"));
        tcUnitQty.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colUnitQty"));
        tcQty.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colQty"));
        tcBuyingPrice.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colBuyingPrice"));
        tcSellingPrice.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colSellingPrice"));
        
        tblItemList.setItems(TableItemData);

        mb = SimpleMessageBoxFactory.createMessageBox();
        txtItemId.setText(itemDAO.generateID());
//        userId = "EM0001";
//        txtUserId.setText(userId);
        btnDelete.setVisible(false);
        batchNoList = cmbBatchNo.getItems();
        batchNoList.clear();
        batchNoList = FXCollections.observableArrayList(
                itemDAO.generateBatchID(txtItemId.getText()));
        cmbBatchNo.setItems(batchNoList);
        cmbBatchNo.getSelectionModel().selectFirst();
//        btnDelete.setVisible(false);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {

        TableItemData.clear();
        no = 1;
        txtUserId.setText(userName);
        btnSave.setVisible(true);
        btnDelete.setVisible(false);
        update = false;
        isupdate = false;
        txtItemId.setText(itemDAO.generateID());
        txtItemName.clear();
        txtPartNo.clear();
        txtQty.clear();
        txtSellingPrice.clear();
        txtBuyingPrice.clear();
        cmbBatchNo.getSelectionModel().selectFirst();
        cmbMainCategory.getSelectionModel().selectFirst();
        cmbSubCategory.getSelectionModel().selectFirst();
        cmbUnit.getSelectionModel().selectFirst();
        cmbUnitQty.getSelectionModel().selectFirst();
        txtItemDescription.clear();
        loadBatch();
        validatorInitialization();
    }

    @Override
    public void clearValidations() {
        no = 1;
        txtItemName.clear();

        batchNoList.clear();

//        txtPrice.clear();
        isupdate = true;

        int count = TableItemData.size();
        if (count == 0) {

//            btnSave.setVisible(false);
            btnDelete.setVisible(true);

        }

        validatorInitialization();
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {

        this.stage = stage;
        setUserAccessLevel();
        //item popup------------------------
        itemTable = itemPopup.tableViewLoader(itemData);

        itemTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    btnDelete.setVisible(true);
                    ItemInfoPopup p = null;
                    p = (ItemInfoPopup) itemTable.getSelectionModel().
                            getSelectedItem();
                    if (p.getColItemID() != null) {
                        clearValidations();

                        txtItemId.setText(p.getColItemID());
                        txtItemName.setText(p.getColItemName());
                        txtUserId.setText(itemDAO.getUserName(
                                txtItemId.getText()));
                        
                        loadItemData(txtItemId.getText());
                        
                        batchNoList.clear();
                        loadBatchNoToCombobox(p.getColItemID());
                    }

                } catch (NullPointerException n) {

                }

                itemPop.hide();
                validatorInitialization();

            }

        });

        itemTable.setOnMousePressed(e -> {

            if (e.getButton() == MouseButton.SECONDARY) {

                itemPop.hide();
                validatorInitialization();

            }

        });

        itemPop = new PopOver(itemTable);

        stage.setOnCloseRequest(e -> {

            if (itemPop.isShowing()) {
                e.consume();
                itemPop.hide();

            }
        });

        mainCategoryData = cmbMainCategory.getItems();
        subCategoryData = cmbUnitQty.getItems();
        unitData = cmbUnit.getItems();
        unitQtyData = cmbUnitQty.getItems();
        
        loadMainCategoryToCombobox();
        loadSubCategoryToCombobox();
        loadUnitToCombobox();
        loadUnitQtyToCombobox();

        validatorInitialization();
    }

    //<editor-fold defaultstate="collapsed" desc="Action Events">
    @FXML
    void btnItemNameSearchOnAction(ActionEvent event) {
        itemTableDataLoader(txtItemName.getText());
        itemTable.setItems(itemData);
        if (!itemData.isEmpty()) {
            itemPop.show(btnItemNameSearch);
        }
        validatorInitialization();
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        clearInput();
    }

    @FXML
    void btnBatchNoOnAction(ActionEvent event) {
        loadBatch();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        validatorInitialization();
        boolean validationSupportTableResult = false;
        boolean isTableContentSaved = false;
        Item itemTable;

        ValidationResult v = validationSupportTable.getValidationResult();

        if (v != null) {
            validationSupportTableResult = validationSupportTable.isInvalid();

            if (validationSupportTableResult == true) {

                mb.ShowMessage(stage, ErrorMessages.MandatoryError,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);

            } else if (validationSupportTableResult == false) {
                if (isupdate == false) {

                    if (tblItemList.getItems().size() != 0) {
                        for (int i = 0; i < tblItemList.getItems().size(); i++) {
                            itemTable = (Item) tblItemList.getItems().get(i);

                            
                            isTableContentSaved = itemDAO.additem(
                                    itemTable.getColItemId(),
                                    itemTable.getColItemName(),
                                    Double.parseDouble(itemTable.getColQty()),
                                    userId,
                                    itemTable.getColItemDescripton(),
                                    itemTable.getColPartNo(),
                                    itemDAO.getMainCategoryId(itemTable.getColMainCategory()),
                                    itemDAO.getSubCategoryId(itemTable.getColSubCategory(),
                                            Integer.parseInt(itemDAO.getMainCategoryId(itemTable.getColMainCategory()))),
                                    itemTable.getColBatchNo(),
                                    Double.parseDouble(itemTable.getColBuyingPrice()),
                                    10,//this needs to be updated in the interface
                                    Double.parseDouble(itemTable.getColSellingPrice()),
                                    itemDAO.getUnitQtyId(itemTable.getColUnitQty(),
                                            itemTable.getColUnit()));

                        }
                    }

                    if (isTableContentSaved == true) {

                        mb.ShowMessage(stage,
                                ErrorMessages.SuccesfullyCreated, "Information",
                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                MessageBox.MessageType.MSG_OK);
                        clearInput();

                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyCreated, "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                    //Save Action Event
                } else {

                    MessageBox.MessageOutput option = mb.ShowMessage(stage,
                            ErrorMessages.Update, "Information",
                            MessageBox.MessageIcon.MSG_ICON_NONE,
                            MessageBox.MessageType.MSG_YESNO);
                    if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                        if (tblItemList.getItems().size() != 0) {
                            for (int i = 0; i < tblItemList.getItems().size();
                                    i++) {
                                itemTable = (Item) tblItemList.getItems().get(i);

                                isTableContentSaved = itemDAO.additem(
                                    itemTable.getColItemId(),
                                    itemTable.getColItemName(),
                                    Double.parseDouble(itemTable.getColQty()),
                                    userId,
                                    itemTable.getColItemDescripton(),
                                    itemTable.getColPartNo(),
                                    itemDAO.getMainCategoryId(itemTable.getColMainCategory()),
                                    itemDAO.getSubCategoryId(itemTable.getColSubCategory(),
                                            Integer.parseInt(itemDAO.getMainCategoryId(itemTable.getColMainCategory()))),
                                    itemTable.getColBatchNo(),
                                    Double.parseDouble(itemTable.getColBuyingPrice()),
                                    10,//this needs to be updated in the interface
                                    Double.parseDouble(itemTable.getColSellingPrice()),
                                    itemDAO.getUnitQtyId(itemTable.getColUnitQty(),
                                            itemTable.getColUnit()));

                            }
                        }
                        if (isTableContentSaved == true) {

                            mb.ShowMessage(this.stage,
                                    ErrorMessages.SuccesfullyUpdated,
                                    "Information",
                                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                    MessageBox.MessageType.MSG_OK);
                            clearInput();

                        } else {
                            mb.ShowMessage(stage,
                                    ErrorMessages.NotSuccesfullyUpdated, "Error",
                                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                                    MessageBox.MessageType.MSG_OK);
                        }

                    }

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
    void btnDeleteOnAction(ActionEvent event) {

        boolean isDeleted = false;
        Item itemTable;
        validatorInitialization();
        boolean validationSupportResult = false;

        ValidationResult v = validationSupportTable.getValidationResult();

        if (v != null) {
            validationSupportResult = validationSupportTable.isInvalid();

            if (validationSupportResult == true) {

                mb.ShowMessage(stage, ErrorMessages.NoData, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false) {

                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        ErrorMessages.Delete, "Information",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    if (tblItemList.getItems().size() != 0) {
                        for (int i = 0; i < tblItemList.getItems().size(); i++) {
                            itemTable = (Item) tblItemList.getItems().get(i);
                            if (itemDAO.checkingItemAvailability(
                                    itemTable.getColItemId())) {
                                
                                isDeleted = itemDAO.deleteItem(
                                        itemTable.getColItemId());
                                
                            } else {
                                mb.ShowMessage(stage,
                                        ErrorMessages.InvalidId, "Error",
                                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                                        MessageBox.MessageType.MSG_OK);
                            }

                        }

                        if (isDeleted == true) {

                            mb.ShowMessage(stage,
                                    ErrorMessages.SuccesfullyDeleted,
                                    "Information",
                                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                    MessageBox.MessageType.MSG_OK);
                            clearInput();

                        } else {
                            mb.ShowMessage(stage, ErrorMessages.Error,
                                    "Error",
                                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                                    MessageBox.MessageType.MSG_OK);
                        }

                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NoData, "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }

                }

            }
        }
    }

    @FXML
    void cmbBatchNoOnAction(ActionEvent event) {

//        try {
//            if (itemDAO.getPrice(txtItemId.getText(),
//                    cmbBatchNo.getValue().toString()) != null) {
//                txtPrice.setText(itemDAO.getPrice(txtItemId.getText(),
//                        cmbBatchNo.getValue().toString()));
//            } else {
//                txtPrice.clear();
//            }
//        } catch (Exception e) {
//            txtPrice.clear();
//        }
        
    }

    @FXML
    private void cmbMainCategoryNoOnAction(ActionEvent event) {
                
            loadSubCategoryToCombobox();
              
    }

    @FXML
    private void btnMainCategoryOnAction(ActionEvent event) {

        String mainCategory = InputDialog.inputForAddNew("Main Category");
        boolean isSaved = false;
        if (mainCategory == null) {
            return;
        }
        if (!fav.validAddress(mainCategory)) {
            mb.ShowMessage(stage, "Invalid Main Category", "Main Category",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (mainCategoryData.contains(mainCategory)) {
            mb.ShowMessage(stage, "Duplicate Main Category", "Main Category",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = itemDAO.insertMainCategory(mainCategory);

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Main Category",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        mainCategoryData.add(mainCategory);
        loadMainCategoryToCombobox();
        cmbMainCategory.getSelectionModel().select(mainCategory);

        validatorInitialization();

    }


    @FXML
    private void btnSubCategoryOnAction(ActionEvent event) {
        
         String subCategory = InputDialog.inputForAddNew("Sub Category");
        boolean isSaved = false;
        if (subCategory == null) {
            return;
        }
        if (!fav.validAddress(subCategory)) {
            mb.ShowMessage(stage, "Invalid Sub Category", "Sub Category",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (subCategoryData.contains(subCategory)) {
            mb.ShowMessage(stage, "Duplicate Sub Category", "Sub Category",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = itemDAO.insertSubCategory(subCategory,
                Integer.parseInt(itemDAO.getMainCategoryId(cmbMainCategory.getValue())));

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Sub Category",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        subCategoryData.add(subCategory);
        loadSubCategoryToCombobox();
        cmbSubCategory.getSelectionModel().select(subCategory);

        validatorInitialization();
        
    }

    @FXML
    private void cmbBUnitOnAction(ActionEvent event) {
        
        loadUnitQtyToCombobox();
    }

    @FXML
    private void btnUnitOnAction(ActionEvent event) {
        
        String unit = InputDialog.inputForAddNew("Unit");
        boolean isSaved = false;
        if (unit == null) {
            return;
        }
        if (!fav.validName(unit)) {
            mb.ShowMessage(stage, "Invalid Unit", "Unit",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (unitData.contains(unit)) {
            mb.ShowMessage(stage, "Duplicate Unit", "Unit",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = itemDAO.insertUnit(unit);

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Unit",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        unitData.add(unit);
        loadUnitToCombobox();
        cmbUnit.getSelectionModel().select(unit);

        validatorInitialization();
        
    }


    @FXML
    private void btnUnitQtyOnAction(ActionEvent event) {
        
         String unitQty = InputDialog.inputForAddNew("Unit-Qty");
        boolean isSaved = false;
        if (unitQty == null) {
            return;
        }
        if (!fav.validName(unitQty)) {
            mb.ShowMessage(stage, "Invalid Unit qty", "Unit-Qty",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (unitQtyData.contains(unitQty)) {
            mb.ShowMessage(stage, "Duplicate Unit qty", "Unit-Qty",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = itemDAO.insertUnitQty(unitQty,
                Integer.parseInt(itemDAO.getUnitId(cmbUnit.getValue())));

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Unit-Qty",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        unitQtyData.add(unitQty);
        loadUnitQtyToCombobox();
        cmbUnitQty.getSelectionModel().select(unitQty);

        validatorInitialization();
        
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Key Events">
    @FXML
    private void txtItemNameOnKeyReleased(KeyEvent event) {
        validatorInitialization();
    }

    @FXML
    private void txtSellingPriceOnKeyReleased(KeyEvent event) {
        
        validatorInitialization();
        boolean validationSupportResult = false;
        boolean isAvalible = false;
        
        if (event.getCode() == KeyCode.ENTER) {

            ValidationResult v = validationSupportTableData.
                    getValidationResult();
            if (v != null) {

                validationSupportResult = validationSupportTableData.isInvalid();
                if (validationSupportResult == true) {
                    mb.ShowMessage(stage, ErrorMessages.MandatoryError,
                            "Error",
                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);

                } else if (validationSupportResult == false) {
                    
                    if (isupdate == false) {

                        isAvalible = itemDAO.checkingItemNameAvailability(
                                txtItemName.getText()
                        );

                        if (isAvalible == false) {
                            if (tblItemList.getItems().size() != 0) {
                                int n = tblItemList.getItems().size();
                                for (int s = 0; s < n; s++) {
                                    
                                    item = (Item) tblItemList.getItems().get(s);
                                    
                                    if ((txtItemId.getText() + cmbBatchNo.
                                            getValue()).equals(
                                                    (item.getColItemId() + item.
                                                    getColBatchNo()))
                                            && tblItemList.getItems().size() > 0) {
                                        TableItemData.remove(s);
                                        n--;
                                        
                                    }

                                    if (txtItemId.getText().equals(
                                            item.getColItemId())
                                            && tblItemList.getItems().size() > 0) {
                                        if (!item.getColItemName().equals(
                                                txtItemName.getText())) {
                                            item.setColItemName(
                                                    txtItemName.getText());
                                        }
                                    }

                                }
                            }

                            item = new Item();
                            
                            item.colItemId.setValue(txtItemId.getText());
                            item.colItemName.setValue(txtItemName.getText());
                            item.colItemDescripton.setValue(txtItemDescription.getText());
                            item.colPartNo.setValue(txtPartNo.getText());
                            item.colMainCategory.setValue(cmbMainCategory.getValue());
                            item.colSubCategory.setValue(cmbSubCategory.getValue());
                            item.colUnit.setValue(cmbUnit.getValue());
                            item.colUnitQty.setValue(cmbUnitQty.getValue());
                            item.colQty.setValue(txtQty.getText());
                            item.colBuyingPrice.setValue(txtBuyingPrice.getText());
                            item.colSellingPrice.setValue(txtSellingPrice.getText());
                            item.colBatchNo.setValue(cmbBatchNo.getValue());
                            
                            TableItemData.add(item);
                            
                            no = no + 1;
                            txtItemId.setText(itemDAO.generateIDOOnDemand(no));
                            loadBatchNoToCombobox(txtItemId.getText());
                            
                            //Clear item components for new entry
                            clearComponentsForNewEntry();
//                            txtItemId.setText(itemDAO.generateID());

                        } else {
                            mb.ShowMessage(stage,
                                    ErrorMessages.InvalidItemName,
                                    "Error",
                                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                                    MessageBox.MessageType.MSG_OK);
                        }

                    } else {
                        if (tblItemList.getItems().size() != 0) {
                            //Removing existing item for update or new addition
                            int n = tblItemList.getItems().size();
                            for (int s = 0; s < n; s++) {
                                item = (Item) tblItemList.getItems().get(s);
                                if ((txtItemId.getText() + cmbBatchNo.getValue()).
                                        equals(
                                                (item.getColItemId() + item.
                                                getColBatchNo()))
                                        && tblItemList.getItems().size() > 0) {
                                    TableItemData.remove(s);
                                    n--;
                                }

                                if (txtItemId.getText().equals(
                                        item.getColItemId())
                                        && tblItemList.getItems().size() > 0) {
                                    if (!item.getColItemName().equals(
                                            txtItemName.getText())) {
                                        item.colItemName.setValue(
                                                txtItemName.getText());
                                    }
                                }
                            }
                        }
                        //Adding items to the table
                        item = new Item();
                        item.colItemId.setValue(txtItemId.getText());
                        item.colItemName.setValue(txtItemName.getText());
                        item.colItemDescripton.setValue(txtItemDescription.getText());
                        item.colPartNo.setValue(txtPartNo.getText());
                        item.colMainCategory.setValue(cmbMainCategory.getValue());
                        item.colSubCategory.setValue(cmbUnitQty.getValue());
                        item.colUnit.setValue(cmbUnit.getValue());
                        item.colUnitQty.setValue(cmbUnitQty.getValue());
                        item.colQty.setValue(txtQty.getText());
                        item.colBuyingPrice.setValue(txtBuyingPrice.getText());
                        item.colSellingPrice.setValue(txtSellingPrice.getText());
                                        
                        TableItemData.add(item);

                        //Resetting fields for next item
                        txtItemId.setText(itemDAO.generateID());
                        loadBatchNoToCombobox(txtItemId.getText());
                        
                        clearComponentsForNewEntry();

                        update = true;
                        btnSave.setVisible(true);

                    }

                }
            }
        }
        validatorInitialization();

        
        
    }


    @FXML
    private void cmbMainCategoryOnKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.DELETE | event.getCode()
                == KeyCode.BACK_SPACE) {

            if (cmbMainCategory.getValue() != null) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        "Are you sure you want \nto remove the main category ? ",
                        "Main Category",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    boolean isRemoved = itemDAO.deleteMainCategory(
                            cmbMainCategory.getValue());

                    if (isRemoved == true) {
                        loadMainCategoryToCombobox();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyDeleted,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                }
            }
            loadMainCategoryToCombobox();
            validatorInitialization();
        }

    }
    
    @FXML
    private void cmbSubCategoryNoOnKeyReleased(KeyEvent event) {
        
         if (event.getCode() == KeyCode.DELETE | event.getCode()
                == KeyCode.BACK_SPACE) {

            if (cmbUnitQty.getValue() != null) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        "Are you sure you want \nto remove the sub category ? ",
                        "Sub Category",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    boolean isRemoved = itemDAO.deleteSubCategory(
                            cmbUnitQty.getValue(),
                            Integer.parseInt(itemDAO.getMainCategoryId(
                                            cmbMainCategory.getValue()))
                    
                    
                    );

                    if (isRemoved == true) {
                        loadSubCategoryToCombobox();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyDeleted,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                }
            }
            loadSubCategoryToCombobox();
            validatorInitialization();
        }

        
    }
    
    @FXML
    private void cmbUnitOnKeyReleased(KeyEvent event) {
        
          if (event.getCode() == KeyCode.DELETE | event.getCode()
                == KeyCode.BACK_SPACE) {

            if (cmbUnit.getValue() != null) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        "Are you sure you want \nto remove the unit ? ",
                        "Unit",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    boolean isRemoved = itemDAO.deleteUnit(
                            cmbUnit.getValue());

                    if (isRemoved == true) {
                        loadUnitToCombobox();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyDeleted,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                }
            }
            loadUnitToCombobox();
            validatorInitialization();
        }       
        
    }
    
    @FXML
    private void cmbUnitQtyOnKeyReleased(KeyEvent event) {
        
        if (event.getCode() == KeyCode.DELETE | event.getCode()
                == KeyCode.BACK_SPACE) {

            if (cmbUnitQty.getValue() != null) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        "Are you sure you want \nto remove the unit qty ? ",
                        "Unit Qty",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    boolean isRemoved = itemDAO.deleteUnitQty(
                            cmbUnitQty.getValue(),
                            Integer.parseInt(itemDAO.getUnitId(
                                            cmbUnit.getValue()))
                    
                    
                    );

                    if (isRemoved == true) {
                        loadUnitQtyToCombobox();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyDeleted,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                }
            }
            loadUnitQtyToCombobox();
            validatorInitialization();
        }
        
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Mouse Events">
    @FXML
    void tblRequestNoteListOnMouseClicked(
            javafx.scene.input.MouseEvent mouseEvent) {
        try {

            if (mouseEvent.getClickCount() == 2) {

                boolean model = tblItemList.getSelectionModel().isEmpty();

                if (model == false) {
                    TableItemData.remove(
                            tblItemList.getSelectionModel().getSelectedIndex());

                }
                validatorInitialization();

            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void txtItemDescriptionKeyReleased(KeyEvent event) {
        
       
        
    }

    @FXML
    private void txtPartNoOnKeyReleased(KeyEvent event) {
    }

    @FXML
    private void cmbMainCategoryOnMouseReleased(MouseEvent event) {
    }

    @FXML
    private void cmbSubCategoryNoOnAction(ActionEvent event) {
    }

    @FXML
    private void cmbBUnitQtyOnAction(ActionEvent event) {
    }

    @FXML
    private void txtQtyOnKeyReleased(KeyEvent event) {
    }

    @FXML
    private void txtSuppliersPriceOnKeyReleased(KeyEvent event) {
    }

  

   

    

//</editor-fold>
    
    public class Item {

        public SimpleStringProperty colItemId = new SimpleStringProperty(
                "tcItemId");
        public SimpleStringProperty colItemName = new SimpleStringProperty(
                "tcItemName");
        public SimpleStringProperty colItemDescripton
                = new SimpleStringProperty(
                        "tcItemDescripton");
        public SimpleStringProperty colPartNo = new SimpleStringProperty(
                "tcPartNo");
        public SimpleStringProperty colBatchNo = new SimpleStringProperty(
                "tcBatchNo");
        public SimpleStringProperty colMainCategory = new SimpleStringProperty(
                "tcMainCategory");
        public SimpleStringProperty colSubCategory = new SimpleStringProperty(
                "tcSubCategory");
        public SimpleStringProperty colUnit = new SimpleStringProperty(
                "tcUnit");
        public SimpleStringProperty colUnitQty = new SimpleStringProperty(
                "tcUnitQty");
        public SimpleStringProperty colQty = new SimpleStringProperty(
                "tcQty");
        public SimpleStringProperty colBuyingPrice = new SimpleStringProperty(
                "tcBuyingPrice");
        public SimpleStringProperty colSellingPrice
                = new SimpleStringProperty(
                        "tcItSellingPrice");

        public String getColItemId() {
            return colItemId.get();
        }

        public String getColItemName() {
            return colItemName.get();
        }

        public String getColItemDescripton() {
            return colItemDescripton.get();
        }

        public String getColPartNo() {
            return colPartNo.get();
        }

        public String getColBatchNo() {
            return colBatchNo.get();
        }

        public String getColMainCategory() {
            return colMainCategory.get();
        }

        public String getColSubCategory() {
            return colSubCategory.get();
        }

        public String getColUnit() {
            return colUnit.get();
        }

        public String getColUnitQty() {
            return colUnitQty.get();
        }

        public String getColQty() {
            return colQty.get();
        }

        public String getColBuyingPrice() {
            return colBuyingPrice.get();
        }

        public String getColSellingPrice() {
            return colSellingPrice.get();
        }
        
        public void setColItemName(String itemName) {
            colItemName.setValue(itemName);
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Methods">
    private void loadMainCategoryToCombobox() {

        cmbMainCategory.setItems(null);
        ArrayList<String> mainCategoryList = null;
        mainCategoryList = itemDAO.loadMainCategory();
        if (mainCategoryList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        mainCategoryList);
                cmbMainCategory.setItems(List);
                cmbMainCategory.setValue(List.get(0));
            } catch (Exception e) {

            }

        }
                loadSubCategoryToCombobox();

    }
    
    private void loadSubCategoryToCombobox() {

        cmbSubCategory.setItems(null);
        ArrayList<String> subCategoryList = null;
        
        try {
            subCategoryList = itemDAO.loadSubCategory
        (Integer.parseInt(itemDAO.getMainCategoryId(cmbMainCategory.getValue())));
        } catch (NumberFormatException e) {
        }
        
        if (subCategoryList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        subCategoryList);
                cmbSubCategory.setItems(List);
                cmbSubCategory.setValue(List.get(0));
            } catch (Exception e) {

            }

        }

    }

    private void validatorInitialization() {
        
         validationSupportTableData.registerValidator(txtItemName,
                new CustomTextAreaValidationImpl(txtItemName,
                        !fav.validName(txtItemName.getText()),
                        ErrorMessages.Error));

        validationSupportTableData.registerValidator(txtBuyingPrice,
                new CustomTextFieldValidationImpl(txtBuyingPrice,
                        !fav.chkPrice(txtBuyingPrice.getText()),
                        ErrorMessages.InvalidPrice));
        
        validationSupportTableData.registerValidator(txtQty,
                new CustomTextFieldValidationImpl(txtQty,
                        !fav.chkPrice(txtQty.getText()),
                        ErrorMessages.InvalidQty));
        
        validationSupportTableData.registerValidator(txtQty,
                new CustomTextFieldValidationImpl(txtQty,
                        !fav.chkPrice(txtQty.getText()),
                        ErrorMessages.InvalidQty));
        
        validationSupportTableData.registerValidator(txtSellingPrice,
                new CustomTextFieldValidationImpl(txtSellingPrice,
                        !fav.chkPrice(txtSellingPrice.getText()),
                        ErrorMessages.InvalidPrice));

        validationSupportTable.registerValidator(tblItemList,
                new CustomTableViewValidationImpl(tblItemList,
                        !fav.validTableView(tblItemList),
                        ErrorMessages.EmptyListView));
        
    }

    private void itemTableDataLoader(String keyword) {

        itemData.clear();
        ArrayList<ArrayList<String>> itemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = itemDAO.searchItemDetails(keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemInfo.add(list.get(i));
            }

            if (itemInfo != null && itemInfo.size() > 0) {
                for (int i = 0; i < itemInfo.size(); i++) {

                    itemPopup = new ItemInfoPopup();
                    itemPopup.colItemID.setValue(itemInfo.get(i).get(0));
                    itemPopup.colItemName.setValue(itemInfo.get(i).get(1));
                    
                    itemData.add(itemPopup);
                }
            }

        }

    }

    private void loadBatchNoToCombobox(String keyword) {

        ArrayList<ArrayList<String>> itemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = itemDAO.searchItemBatchDetails(
                keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemInfo.add(list.get(i));
            }

            if (itemInfo != null && itemInfo.size() > 0) {
                for (int i = 0; i < itemInfo.size(); i++) {
                    batchNoList.add(itemInfo.get(i).get(0));
                }
            }
            cmbBatchNo.setItems(batchNoList);
            cmbBatchNo.getSelectionModel().selectFirst();

        }
        try {
            
            ArrayList<String> batchList = null;
            ArrayList<String> unitList = null;
        batchList = itemDAO.getPrice(txtItemId.getText(),
                        cmbBatchNo.getValue().toString());
        
            if (batchList!=null) {
                
                 ObservableList<String> List = FXCollections.observableArrayList(
                        batchList);
                           
                unitList = itemDAO.loadUnitValue(List.get(2));
                 ObservableList<String> unitValueList = FXCollections.observableArrayList(
                        unitList);
                
                txtBuyingPrice.setText(List.get(0));
                txtSellingPrice.setText(List.get(1));
                cmbUnit.getSelectionModel().select(unitValueList.get(0));
                cmbUnitQty.getSelectionModel().select(unitValueList.get(1));
                txtQty.setText(List.get(3));
                
            
            }
       
            
        } catch (Exception e) {

        }

    }

    private void loadBatch() {
        boolean value = false;
        
        
        if (isupdate == false) {
            batchNoList.clear();
        }
        String batch = itemDAO.generateBatchID(txtItemId.getText());
        for (int i = 0; i < batchNoList.size(); i++) {
            if (batchNoList.get(i).equals(batch)) {
                value = true;
            }
        }
        if (value == false) {
            batchNoList.add(itemDAO.generateBatchID(txtItemId.getText()));
            cmbBatchNo.getSelectionModel().selectLast();
        }

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                deactivateSearch();

                btnDelete.setVisible(false);

                break;

            case DELETE:
                disableUi(false);

                btnSave.setDisable(true);
                btnSave.setVisible(false);

                deactivateCombo();

                break;

            case READ_ONLY:
                disableUi(false);
                deactivateCombo();
                btnDelete.setVisible(false);

                btnSave.setDisable(true);
                btnSave.setVisible(false);

                break;

            case ALL_BUT_DELETE:
                disableUi(false);

                btnDelete.setVisible(false);

                break;

            case FULL_ACCESS:
                disableUi(false);
                btnDelete.setVisible(false);
                break;

            case NO_ACCESS:
                disableUi(true);

                break;

        }

    }

    private void disableUi(boolean state) {

        txtItemId.setDisable(state);
        txtItemId.setVisible(!state);

        txtItemName.setDisable(state);
        txtItemName.setVisible(!state);

        btnItemNameSearch.setDisable(state);
        btnItemNameSearch.setVisible(!state);

        txtUserId.setDisable(state);
        txtUserId.setVisible(!state);

        cmbBatchNo.setDisable(state);
        cmbBatchNo.setVisible(!state);

        btnBatchNo.setDisable(state);
        btnBatchNo.setVisible(!state);

        tblItemList.setDisable(state);
        tblItemList.setVisible(!state);

        btnDelete.setDisable(state);
        btnDelete.setVisible(!state);

        btnSave.setDisable(state);
        btnSave.setVisible(!state);

        btnClose.setDisable(state);
        btnClose.setVisible(!state);
    }

    private void setUserAccessLevel() {

        userId = UserSession.getInstance().getUserInfo().getEid();

        userName = UserSession.getInstance().getUserInfo().getName();
        userCategory = UserSession.getInstance().getUserInfo().getCategory();
        txtUserId.setText(userName);

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

        componentControl.deactivateSearch(lblItemName, txtItemName,
                btnItemNameSearch,
                220.00, 0.00);

    }

    private void deactivateCombo() {
        componentControl.controlCComboBox(lblItemId1, cmbBatchNo, btnBatchNo,
                220.00, 0.0, true);
    }
    
    private void clearComponentsForNewEntry(){
    
        txtItemName.clear();
        txtItemDescription.clear();
        txtPartNo.clear();
        cmbBatchNo.getSelectionModel().selectFirst();
        cmbMainCategory.getSelectionModel().selectFirst();
        cmbUnitQty.getSelectionModel().selectFirst();
        cmbUnit.getSelectionModel().selectFirst();
        cmbUnitQty.getSelectionModel().selectFirst();
        txtQty.clear();
        txtBuyingPrice.clear();
        txtSellingPrice.clear();
        
        
    }
    
    private void loadUnitToCombobox() {

        cmbUnit.setItems(null);
        ArrayList<String> unitList = null;
        unitList = itemDAO.loadUnit();
        if (unitList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        unitList);
                cmbUnit.setItems(List);
                cmbUnit.setValue(List.get(0));
            } catch (Exception e) {

            }

        }
               

    }
    
    private void loadUnitQtyToCombobox() {

        cmbUnitQty.setItems(null);
        ArrayList<String> unitQtyList = null;
        
        try {
            unitQtyList = itemDAO.loadUnitQty
        (Integer.parseInt(itemDAO.getUnitId(cmbUnit.getValue())));
        } catch (NumberFormatException e) {
        }
        
        if (unitQtyList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        unitQtyList);
                cmbUnitQty.setItems(List);
                cmbUnitQty.setValue(List.get(0));
            } catch (Exception e) {

            }

        }

    }
    
    private void loadItemData(String itemId) {

        ArrayList<String> itemList = null;
        itemList = itemDAO.loadItemData(itemId);
        if (itemList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        itemList);
                
                txtItemDescription.setText(List.get(0));
                txtPartNo.setText(List.get(1));
                cmbMainCategory.getSelectionModel().
                        select(itemDAO.getMainCategory(List.get(2)));
                
                cmbSubCategory.setValue(itemDAO.getSubCategory(
                        List.get(2),
                        List.get(3)
                ));
                
                txtItemDescription.setText(List.get(0));
                
            } catch (Exception e) {

            }

        }
               

    }

//</editor-fold>
}
