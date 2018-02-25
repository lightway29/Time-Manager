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
import com.saiton.ccs.stockdao.ExternalReturnNoteDAO;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class ExternalReturnNoteOverViewController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="localize">
    @FXML
    private Label extNoteOvrLbl;
    @FXML
    private Label userNameLbl;
    @FXML
    private Label dateLbl;
    @FXML
    private Label extRetOvrLstLbl;
    @FXML
    private Label fromLbl;
    @FXML
    private Label toLbl;
    @FXML
    private Label searchLbl;

    //btn texts
    private String btnApproveTxt;
    private String btnCloseTxt;

    //prompt texts
    private String txtUsernamePt;
    private String txtDatePt;
    private String dtpFromDatePt;
    private String dtpToDatePt;
    private String txtSearchPt;

    //check box txt
    private String checkFilterByApprovedTxt;

    //table column texts
    private String tcIdTc;
    private String tcReturnNoteIdTc;
    private String tcDescriptionTc;
    private String tcDateTc;
    private String tcStatusTc;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private CheckBox checkFilterByApproved;

    @FXML
    private TableColumn<Item, String> tcStatus;

    @FXML
    private Button btnClose;

//    private TableColumn<Item, String> tcType;
    @FXML
    private TextField txtDate;

    @FXML
    private Button btnApprove;

    @FXML
    private DatePicker dtpFromDate;

    @FXML
    private TableView<Item> tblReturnNoteList;

    @FXML
    private TextField txtUsername;

    @FXML
    private TableColumn<Item, String> tcReturnNoteId;

    @FXML
    private DatePicker dtpToDate;

    @FXML
    private TableColumn<Item, String> tcDate;

    @FXML
    private Insets x1;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Item, String> tcId;

    @FXML
    private TableColumn<Item, String> tcDescription;

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
    private ObservableList TableItemData = FXCollections.observableArrayList();
    private MessageBox mb;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    ExternalReturnNoteDAO returnNoteDAO = new ExternalReturnNoteDAO();
    private DateFormatter dateFormatter = new DateFormatter();
    private final ValidationSupport validationSupportDateRange
            = new ValidationSupport();
    private final ValidationSupport validationSupport
            = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcId.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colId"));
        tcReturnNoteId.setCellValueFactory(
                new PropertyValueFactory<Item, String>("colReturnNoteId"));
        tcDescription.setCellValueFactory(
                new PropertyValueFactory<Item, String>("colDescription"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colDate"));
        tcStatus.setCellValueFactory(
                new PropertyValueFactory<Item, String>("colStatus"));
        tblReturnNoteList.setItems(TableItemData);
//        tcType.setCellValueFactory(
//                new PropertyValueFactory<Item, String>("colType"));
        tblReturnNoteList.setItems(TableItemData);

        mb = SimpleMessageBoxFactory.createMessageBox();
        txtUsername.setText(userId);
        txtDate.setText(dateFormat.format(date));

        dateFormatter.format("yyyy-MM-dd", dtpToDate);
        dateFormatter.format("yyyy-MM-dd", dtpFromDate);
        dtpFromDate.setValue(LocalDate.now());
        dtpToDate.setValue(LocalDate.now());
        loadExistingAdditionalItem();
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
        if (checkFilterByApproved.isSelected() == true) {
            btnApprove.setVisible(false);
        } else {
            btnApprove.setVisible(true);
        }
        existingAdditionalItemToTable();
    }

    @FXML
    void btnApproveOnAction(ActionEvent event) {
        validatorInitialization();
        String id = null;
        boolean isApproved = false;
        boolean validationSupportResult = false;
        boolean isNotSelected = false;
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
                    for (int i = 0; i < tblReturnNoteList.getItems().size(); i++) {
                        if (tblReturnNoteList.getSelectionModel().isSelected(i)) {
                            id = tblReturnNoteList.getSelectionModel().
                                    getSelectedItem().getColReturnNoteId();
                            if (tblReturnNoteList.
                                    getSelectionModel().getSelectedItem()
                                    != null) {
                                isApproved = returnNoteDAO.ApproveRequestnote(
                                        tblReturnNoteList.getSelectionModel().
                                        getSelectedItem().colId.
                                        getValue(),
                                        tblReturnNoteList.getSelectionModel().
                                        getSelectedItem().colReturnNoteId.
                                        getValue(), userId, txtDate.getText());

                                existingAdditionalItemToTable();

                                if (isApproved == true) {
                                    mb.ShowMessage(stage,
                                            ErrorMessages.RecordApproved,
                                            "Success Information",
                                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                            MessageBox.MessageType.MSG_OK);

                                    HashMap param = new HashMap();
                                    param.put("returnId", id);

                                    File fil = new File(".//Reports//ExternalReturnNote.jasper");
                                    String img = fil.getAbsolutePath();
                                    ReportGenerator r = new ReportGenerator(img, param);

                                    r.setVisible(true);

                                } else {
                                    mb.ShowMessage(stage,
                                            ErrorMessages.RecordNotApproved,
                                            MessageBoxTitle.ERROR.toString(),
                                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                                            MessageBox.MessageType.MSG_OK);
                                }
                            }
                        } else {
                            isNotSelected = true;
                        }
                    }
                    if (isNotSelected == true) {
                        mb.ShowMessage(stage,
                                ErrorMessages.InvalidRecord,
                                MessageBoxTitle.ERROR.toString(),
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }

                } else {
                    mb.ShowMessage(stage, ErrorMessages.AccessDenied,
                            MessageBoxTitle.ERROR.toString(),
                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
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
    void dtpToDateOnAction(ActionEvent event) {
        existingAdditionalItemToTable();
    }

    @FXML
    void dtpFromDateOnAction(ActionEvent event) {
        existingAdditionalItemToTable();
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="KeyEvents">
    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {
        existingAdditionalItemToTable();
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="MouseEvents">
    @FXML
    void tblReturnNoteListOnMouseclicked(
            javafx.scene.input.MouseEvent mouseEvent) {
        try {

            if (mouseEvent.getClickCount() == 2) {

                boolean model = tblReturnNoteList.getSelectionModel().isEmpty();
                if (model == false) {
                    Object[] id = {tblReturnNoteList.getSelectionModel().
                        getSelectedItem().getColReturnNoteId()};
                  
                    FxmlUiLauncher.launchOnNewStage(
                            "/com/saiton/ccs/stock/ExternalReturnNote.fxml",
                            "External Return Note",
                            id);
                }

            }
        } catch (Exception e) {

        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">
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

        }

        validationSupport.registerValidator(tblReturnNoteList,
                new CustomTableViewValidationImpl(tblReturnNoteList,
                        !fav.validTableView(tblReturnNoteList),
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
                disableUi(false);
                btnClose.setDisable(false);
                btnClose.setVisible(true);
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
                System.out.println("Reached");
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

    private void disableUi(boolean state) {

        btnClose.setDisable(state);
        btnClose.setVisible(!state);
        btnApprove.setDisable(state);
        btnApprove.setVisible(!state);

        txtUsername.setDisable(state);
        txtUsername.setVisible(!state);
        txtDate.setDisable(state);
        txtDate.setVisible(!state);
        checkFilterByApproved.setDisable(state);
        checkFilterByApproved.setVisible(!state);
        dtpFromDate.setDisable(state);
        dtpFromDate.setVisible(!state);
        dtpToDate.setDisable(state);
        dtpToDate.setVisible(!state);
        txtSearch.setDisable(state);
        txtSearch.setVisible(!state);
        tblReturnNoteList.setVisible(!state);
        tblReturnNoteList.setDisable(state);

    }

    private void loadExistingAdditionalItem() {

        TableItemData.clear();

        ArrayList<ArrayList<String>> additionalItemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = returnNoteDAO.getTableApproveInfo(
                checkFilterByApproved.isSelected(), txtSearch.getText(),
                dtpFromDate.getValue().toString(), dtpToDate.
                getValue().toString());

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                additionalItemInfo.add(list.get(i));

            }

            if (additionalItemInfo != null && additionalItemInfo.size() > 0) {
                for (int i = 0; i < additionalItemInfo.size(); i++) {

                    item = new Item();

                    item.colId.setValue(additionalItemInfo.get(i).get(0));
                    item.colReturnNoteId.setValue(additionalItemInfo.get(
                            i).
                            get(1));
                    item.colDescription.setValue(
                            additionalItemInfo.get(i).
                            get(2));
                    item.colDate.setValue(
                            additionalItemInfo.get(i).get(3));
                    item.colStatus.setValue(additionalItemInfo.get(
                            i).
                            get(4));
//                    item.colType.setValue(additionalItemInfo.get(
//                            i).
//                            get(5));
                    TableItemData.add(item);
                }
            }
        } else {
            mb.ShowMessage(stage, ErrorMessages.InvalidEvent, MessageBoxTitle.ERROR.toString(),
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

    }

    private void existingAdditionalItemToTable() {

        validatorInitialization();
        boolean validationSupportResult = false;
        ValidationResult ResultValidationSupport
                = validationSupportDateRange.getValidationResult();

        if (ResultValidationSupport != null) {
            validationSupportResult = validationSupportDateRange.isInvalid();
            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                loadExistingAdditionalItem();
            }
            validatorInitialization();
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Classes">
    public class Item {

        public SimpleStringProperty colId = new SimpleStringProperty("tcId");
        public SimpleStringProperty colReturnNoteId = new SimpleStringProperty(
                "tcReturnNoteId");
        public SimpleStringProperty colDescription
                = new SimpleStringProperty("tcDescription");
        public SimpleStringProperty colDate = new SimpleStringProperty(
                "tcDate");
        public SimpleStringProperty colStatus = new SimpleStringProperty(
                "tcStatus");
//        public SimpleStringProperty colType = new SimpleStringProperty(
//                "tcType");

        public String getColId() {
            return colId.get();
        }

        public String getColReturnNoteId() {
            return colReturnNoteId.get();
        }

        public String getColDescription() {
            return colDescription.get();
        }

        public String getColDate() {
            return colDate.get();
        }

        public String getColStatus() {
            return colStatus.get();
        }

//        public String getColType() {
//            return colType.get();
//        }
    }
//</editor-fold>

}
