package com.saiton.ccs.base;

import com.saiton.ccs.basedao.CustomerRegistrationDAO;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.InputDialog;
import com.saiton.ccs.validations.CustomListViewValidationImpl;
import com.saiton.ccs.validations.CustomTextAreaValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.CustomValidatorImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

public class CustomerRegistrationController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

    //<editor-fold defaultstate="collapsed" desc="InitComponents">
    @FXML
    private Button btnBack;

    @FXML
    private Label lblSearch;

    @FXML
    private Button btnRemoveMobile;

    @FXML
    private Button btnRemoveEmail;

    @FXML
    private Button btnRemoveVehicleNo;

    @FXML
    private Button btnAddTelephone;

    @FXML
    private Button btnAddMobile;

    @FXML
    private Button btnAddEmail;

    @FXML
    private Button btnAddVehicleNo;

    @FXML
    private Button btnRemoveTelephone;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnClose;

    @FXML
    private ComboBox<String> cmbTitle;

    @FXML
    private ImageView imgNext;

    @FXML
    private ListView<String> lstEmail;

    @FXML
    private ListView<String> lstVehicleNo;

    @FXML
    private ListView<String> lstMobile;

    @FXML
    private ListView<String> lstTelephone;

    @FXML
    private TableView<Item> tblContactDetails;

    @FXML
    private TableColumn tcCusType;

    @FXML
    private TableColumn tcAddress;

    @FXML
    private TableColumn tcCustomerId;

    @FXML
    private TableColumn tcName;

    @FXML
    private TextArea txtAddress;

    @FXML
    public TextField txtCustomerId;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> cmbCusType;

    @FXML
    private TextField txtVehicleNo;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAdd;
    @FXML
    private ListView<String> lstDrivers;
    @FXML
    private Button btnRemoveDrivers;
    @FXML
    private TextField txtDriver;
    @FXML
    private Button btnAddDrivers;

    @FXML
    private TextField txtTelephone;
    @FXML
    private Insets x1;
    @FXML
    private Insets x3;
    @FXML
    private Insets x11;
    @FXML
    private Insets x12;
    @FXML
    private Insets x2;
    @FXML
    private Button btnSearchCustomer;

    @FXML
    private Insets x441;
    @FXML
    private Insets x551;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabPaneCustomerDetails;

    @FXML
    private Tab tabPaneContactInformation;

    @FXML
    private Button btnDelete;

    private ObservableList data;

    private Stage stage;

    private String userId;
    private String userName;
    private String userCategory;

//</editor-fold>
    //Class initializations
    private final ValidationSupport validationSupport = new ValidationSupport();
    private final ValidationSupport validationSupportListView
            = new ValidationSupport();

    private final ValidationSupport validationSupportEmail
            = new ValidationSupport();
    private final ValidationSupport validationSupportMobile
            = new ValidationSupport();
    private final ValidationSupport validationSupportTelephone
            = new ValidationSupport();

    private final ValidationSupport validationSupportListViewEmail
            = new ValidationSupport();
    private final ValidationSupport validationSupportListViewMoblie
            = new ValidationSupport();
    private final ValidationSupport validationSupportListViewTelephone
            = new ValidationSupport();

    private final ValidationSupport validateTelephone = new ValidationSupport();
    private final ValidationSupport validateMobile = new ValidationSupport();
    private ValidationSupport validateEmail = new ValidationSupport();
    private final ValidationSupport validateFax = new ValidationSupport();
    private final ValidationSupport validateDriver = new ValidationSupport();

    private final CustomerRegistrationDAO customerRegistrationDAO
            = new CustomerRegistrationDAO();
    private final FormatAndValidate fav = FormatAndValidate.getInstance();
    private CustomValidatorImpl customValidator;
    private MessageBox mb;

    private Item item = new Item();
    private int profession;
    boolean isCustomerSaved = false;
    boolean isTelephoneNoSaved = false;
    boolean isMobileNoSaved = false;
    boolean isEmailSaved = false;
    boolean isFaxNoSaved = false;
    boolean isDriverSaved = false;

    private ComponentControl componentControl = new ComponentControl();

    LocalDate date = null;

    //<editor-fold defaultstate="collapsed" desc="ObservableList">
    private ObservableList<String> customerTypeData;

    ObservableList<String> idTypesList = FXCollections.observableArrayList(
            "NIC", "Passport"
    );

    ObservableList<String> titleList = FXCollections.observableArrayList(
            "Mr.", "Mrs.", "Ms.", "Prof.", "Rev.", "Dr.", "Esq.", "Hon.", "Jr.",
            "Sr."
    );

    ObservableList<String> NationalityList = FXCollections.observableArrayList(
            "Afghans", "Albanians", "Algerians", "Americans", "Andorrans",
            "Angolans", "Argentines", "Armenians", "Aromanians",
            "Arubans", "Australians", "Austrians", "Azeris", "Bahamians",
            "Bahrainis", "Bangladeshis", "Barbadians", "Belarusians",
            "Belgians", "Belizeans", "Bermudians", "Boers", "Bosnians",
            "Brazilians", "Bretons", "British", "British Virgin Islanders",
            "Bulgarians", "Macedonian Bulgarians", "Burkinabès", "Burundians",
            "Baltic Russians", "Cambodians", "Cameroonians",
            "Canadians", "Catalans", "Cape Verdeans", "Chadians", "Chileans",
            "Chinese", "Comorians", "Congolese", "Croatians",
            "Cubans", "Cypriots", "Turkish Cypriots", "Czechs", "Danes",
            "Dominicans (Republic)", "Dominicans (Commonwealth)",
            "Dutch", "East Timorese", "Ecuadorians", "Egyptians", "Emiratis",
            "English", "Eritreans", "Estonians", "Ethiopians", "Faroese",
            "Finns", "Finnish Swedish", "Fijians", "Filipinos",
            "French citizens", "Georgians", "Germans", "Ghanaians", "Gibraltar",
            "Greeks",
            "Grenadians", "Guatemalans", "Guianese (French)", "Guineans",
            "Guinea-Bissau nationals", "Guyanese", "Haitians", "Hondurans",
            "Hong Kong", "Hungarians", "Icelanders", "Indians", "Indonesians",
            "Iranians (Persians)", "Arabs", "Irish", "Israelis", "Italians",
            "Ivoirians", "Jamaicans", "Japanese", "Jordanians", "Kazakhs",
            "Kenyans", "Koreans", "Kosovo Albanians", "Kurds", "Kuwaitis",
            "Lao", "Latvians", "Lebanese", "Liberians", "Libyans",
            "Liechtensteiners", "Lithuanians", "Luxembourgers", "Macedonians",
            "Malaysians", "Malawians", "Maldivians", "Malians", "Maltese",
            "Manx", "Mauritians", "Mexicans", "Moldovans", "Moroccans",
            "Mongolians", "Montenegrins", "Namibians", "Nepalese",
            "New Zealanders", "Nicaraguans", "Nigeriens", "Nigerians",
            "Norwegians",
            "Pakistanis", "Palauans", "Palestinians", "Panamanians",
            "Papua New Guineans", "Paraguayans", "Peruvians", "Poles",
            "Portuguese",
            "Puerto Ricans", "Quebecers", "Réunionnais", "Romanians", "Russians",
            "Rwandans", "Salvadorans", "São Tomé and Príncipe",
            "Saudis", "Scots", "Senegalese", "Serbs", "Sierra Leoneans", "Sikhs",
            "Singaporeans", "Slovaks", "Slovenes", "Somalis",
            "South Africans", "Spaniards", "Sri Lankans", "St Lucians",
            "Sudanese", "Surinamese", "Swedes", "Swiss", "Syrians",
            "Taiwanese", "Tanzanians", "Thais", "Tibetans", "Tobagonians",
            "Trinidadians", "Tunisians", "Turks", "Tuvaluans",
            "Ugandans", "Ukrainians", "Uruguayans", "Uzbeks", "Vanuatuans",
            "Venezuelans", "Vietnamese", "Welsh", "Yemenis",
            "Zambians", "Zimbabweans"
    );
//</editor-fold>

    // initialize-------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mb = SimpleMessageBoxFactory.createMessageBox();
        tcCustomerId.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colCustomerId"));
//        tcTitle.setCellValueFactory(new PropertyValueFactory<Item, String>(
//                "colTitle"));
        tcName.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colName"));

        tcAddress.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colAddress"));

        tcCusType.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colVAT"));

        hidePrinterRemark();

        data = FXCollections.observableArrayList();
        tblContactDetails.setItems(data);

        // Component initializing--------------------
        //loadProfessionToCombobox();
        validatorInitialization();

        this.reloadCustomerID();

        tableDataLoader(txtSearch.getText().trim());

    }

    void reloadCustomerID() {

        String id = customerRegistrationDAO.generateId();//working code do not erase.

        if (id != null) {
            txtCustomerId.setText(id);

        } else if (id == null) {

            mb.ShowMessage((Stage) txtCustomerId.getScene().getWindow(),
                    ErrorMessages.IdNotGenerated, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        }
    }

    void hidePrinterRemark() {

        btnBack.setVisible(false);

        cmbTitle.setItems(titleList);
        cmbTitle.setValue("Mr.");
    }
//==============================================================================================================================
//==============================================================================================================================
//  Button events ----------------------------------------------

    @FXML
    private void btnRemoveDriversOnAction(ActionEvent event) {

        try {
            validatorInitialization();
            boolean model = lstDrivers.getSelectionModel().isEmpty();
            if (model == false) {

                int removeIndex = lstDrivers.getSelectionModel().
                        getSelectedIndex();
                lstDrivers.getItems().remove(removeIndex);
            }

        } catch (Exception e) {
        }
    }

    @FXML
    private void btnAddDriversNoOnAction(ActionEvent event) {

        validateDriver.registerValidator(txtDriver,
                new CustomTextFieldValidationImpl(
                        txtDriver,
                        !fav.isValidVehicleNo(lstDrivers, txtDriver.getText().
                                trim()), ErrorMessages.InvalidDriverName));

        boolean validationSupportResult = false;
        ValidationResult v = validateDriver.getValidationResult();

        if (v != null) {
            validationSupportResult = validateDriver.isInvalid();

            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                ObservableList<String> driverList = lstDrivers.getItems();
                driverList.add(txtDriver.getText());
                lstDrivers.setItems(FXCollections.observableList(driverList));
                txtDriver.clear();

            }
            //           validatorInitialization();
        }

    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        hidePrinterRemark();
        reloadCustomerID();
        clearInput();
    }

    @FXML
    private void btnAddEmailOnAction(ActionEvent event) {

//        validateEmail.registerValidator(txtEmail,
//                new CustomTextFieldValidationImpl(
//                        txtEmail,
//                        !fav.isValidUniqueEmail(lstEmail, txtEmail.
//                                getText().
//                                trim()),
//                        ErrorMessages.InvalidEmailAddressOrDuplicate));
//
//        boolean validationSupportResult = false;
//        ValidationResult v = validateEmail.getValidationResult();
//
//        if (v != null) {
//            validationSupportResult = validateEmail.isInvalid();
//
//            if (validationSupportResult == true) {
//
//            } else if (validationSupportResult == false) {
//
//                ObservableList<String> faxList = lstEmail.getItems();
//                faxList.add(txtEmail.getText());
//                lstEmail.setItems(FXCollections.observableList(faxList));
//                txtEmail.clear();
//
//            }
//            validatorInitializationEmailList();
//        }
        boolean validationSupportResult = false;
        ValidationResult v = validateEmail.getValidationResult();

        if (v != null) {
            validationSupportResult = validateEmail.isInvalid();

            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                ObservableList<String> emailList = lstEmail.getItems();
                emailList.add(txtEmail.getText());
                lstEmail.setItems(FXCollections.observableList(emailList));
                txtEmail.clear();
                validatorInitialization();
            }

        }
    }

    @FXML
    private void btnAddVehicleNoOnAction(ActionEvent event) {

        validateFax.registerValidator(txtVehicleNo,
                new CustomTextFieldValidationImpl(
                        txtVehicleNo,
                        !fav.isValidVehicleNo(lstVehicleNo, txtVehicleNo.
                                getText().
                                trim()), ErrorMessages.InvalidVehicleNo));

        boolean validationSupportResult = false;
        ValidationResult v = validateFax.getValidationResult();

        if (v != null) {
            validationSupportResult = validateFax.isInvalid();

            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                ObservableList<String> faxList = lstVehicleNo.getItems();
                faxList.add(txtVehicleNo.getText());
                lstVehicleNo.setItems(FXCollections.observableList(faxList));
                txtVehicleNo.clear();

            }
            //           validatorInitialization();
        }

    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = false;
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
                if (customerRegistrationDAO.checkingCustomerAvailability(
                        txtCustomerId.getText())) {
                    MessageBox.MessageOutput option = mb.ShowMessage(stage,
                            ErrorMessages.Delete, "Information",
                            MessageBox.MessageIcon.MSG_ICON_NONE,
                            MessageBox.MessageType.MSG_YESNO);
                    if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                        isDeleted = customerRegistrationDAO.
                                deleteCustomerTelephoneForUpdate(
                                        txtCustomerId.getText());

                        isDeleted = customerRegistrationDAO.
                                deleteCustomerMobileForUpdate(
                                        txtCustomerId.getText());

                        isDeleted = customerRegistrationDAO.
                                deleteCustomerEmailForUpdate(
                                        txtCustomerId.getText());

                        isDeleted = customerRegistrationDAO.
                                deleteCustomerFaxForUpdate(
                                        txtCustomerId.getText());

                        isDeleted = customerRegistrationDAO.
                                deleteCustomerInfoForUpdate(
                                        txtCustomerId.getText());

                        if (isDeleted == true) {

                            mb.ShowMessage(stage,
                                    ErrorMessages.SuccesfullyDeleted,
                                    "Information",
                                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                    MessageBox.MessageType.MSG_OK);
                            clearInput();

                        } else {
                            mb.ShowMessage(stage, ErrorMessages.Error,
                                    "Error 01",
                                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                                    MessageBox.MessageType.MSG_OK);
                        }
                    }
                } else {
                    mb.ShowMessage(stage, ErrorMessages.InvalidId, "Error",
                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
                }
            }
        }
    }

    @FXML
    private void btnAddMobileOnAction(ActionEvent event) {

        validateMobile.registerValidator(txtMobile,
                new CustomTextFieldValidationImpl(
                        txtMobile,
                        !fav.isValidUniqueTelephoneNumber(lstMobile, txtMobile.
                                getText().
                                trim()), ErrorMessages.InvalidFaxOrDuplicate));

        boolean validationSupportResult = false;
        ValidationResult v = validateMobile.getValidationResult();

        if (v != null) {
            validationSupportResult = validateMobile.isInvalid();

            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                ObservableList<String> faxList = lstMobile.getItems();
                faxList.add(txtMobile.getText());
                lstMobile.setItems(FXCollections.observableList(faxList));
                txtMobile.clear();

            }
            validatorInitializationMobileList();
        }

    }

    @FXML
    private void btnAddTelephoneOnAction(ActionEvent event) {

        validateTelephone.registerValidator(txtTelephone,
                new CustomTextFieldValidationImpl(
                        txtTelephone,
                        !fav.isValidUniqueTelephoneNumber(lstTelephone,
                                txtTelephone.getText().
                                trim()), ErrorMessages.InvalidFaxOrDuplicate));

        boolean validationSupportResult = false;
        ValidationResult v = validateTelephone.getValidationResult();

        if (v != null) {
            validationSupportResult = validateTelephone.isInvalid();

            if (validationSupportResult == true) {

            } else if (validationSupportResult == false) {

                ObservableList<String> faxList = lstTelephone.getItems();
                faxList.add(txtTelephone.getText());
                lstTelephone.setItems(FXCollections.observableList(faxList));
                txtTelephone.clear();

            }
            validatorInitializationTelephoneList();
//            validatorInitialization();
        }

    }

    @FXML
    private void btnBackOnAction(ActionEvent event) {

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

        selectionModel.select(0); //select by index starting with 0
        btnNext.setText("Next");
        btnBack.setVisible(false);

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {

        Stage stage = (Stage) btnNext.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void btnNextOnAction(ActionEvent event) {

        validatorInitialization();
        validatorInitializationTelephoneList();
        validatorInitializationMobileList();
        validatorInitializationEmailList();

        boolean validationSupportResult = false;
        boolean validateListResult = false;

        ValidationResult ResultValidationSupport = validationSupport.
                getValidationResult();
//        ValidationResult ResultValidationListSupport
//                = validationSupportListView.getValidationResult();

        if (ResultValidationSupport != null) {

            validationSupportResult = validationSupport.isInvalid();

            if (validationSupportResult == true) {

                mb.ShowMessage((Stage) txtCustomerId.getScene().getWindow(),
                        ErrorMessages.MandatoryError, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else if (validationSupportResult == false) {

                SingleSelectionModel<Tab> selectionModel = tabPane.
                        getSelectionModel();

                if (selectionModel.getSelectedIndex() == 0) {

                    // Tab Handling
//===============================================================================================================  
                    btnNext.setText("Finish");
                    btnBack.setVisible(true);
                    selectionModel.select(1); //Select by index starting with 0

                } else if (selectionModel.getSelectedIndex() == 1) {

                    boolean validateListResultTelephone = false;

                    boolean validateListResultMobile = false;

                    boolean validateListResultEmail = false;

                    ValidationResult ResultValidationListSupportTelephone
                            = validationSupportListViewTelephone.
                            getValidationResult();

                    ValidationResult ResultValidationListSupportMobile
                            = validationSupportListViewMoblie.
                            getValidationResult();

                    ValidationResult ResultValidationListSupportEmail
                            = validationSupportListViewEmail.
                            getValidationResult();
//                    validateListResult = validationSupportListView.
//                            isInvalid();
                    if (validateListResult == true) {

                        //                                    validatorInitialization();
                        mb.ShowMessage(stage,
                                ErrorMessages.MandatoryError,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);

                    } else if ((ResultValidationListSupportTelephone
                            != null)
                            || (ResultValidationListSupportMobile
                            != null)
                            || (ResultValidationListSupportEmail != null)) {

                        validateListResultTelephone
                                = validationSupportListViewTelephone.
                                isInvalid();
                        validateListResultMobile
                                = validationSupportListViewMoblie.
                                isInvalid();
                        validateListResultEmail
                                = validationSupportListViewEmail.
                                isInvalid();

                        if ((validateListResultTelephone == false)
                                || (validateListResultMobile == false)
                                || (validateListResultEmail == false)) {

                            boolean isDataAvailable
                                    = customerRegistrationDAO.
                                    checkingCustomerAvailability(
                                            txtCustomerId.
                                            getText()); // Checking customer availability     

                            if (selectionModel.
                                    getSelectedIndex() == 1
                                    && (isDataAvailable == false)) {
                                txtCustomerId.setText(
                                        customerRegistrationDAO.
                                        generateId());//  To handle concurrency

                                isCustomerSaved
                                        = customerRegistrationDAO.
                                        insertCustomerDetails(
                                                txtCustomerId.getText(),
                                                txtName.getText(),
                                                txtAddress.getText().
                                                trim(),
                                                cmbTitle.getValue().
                                                toString(),
                                                cmbCusType.getValue().
                                                toString()
                                        );

                                saveContactInformation();

                                if (validateListResultTelephone == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == true
                                            && isMobileNoSaved == false
                                            && isEmailSaved == false
                                            && isFaxNoSaved == true) {
                                        mb.ShowMessage(
                                                (Stage) txtCustomerId.
                                                getScene().
                                                getWindow(),
                                                ErrorMessages.CustomerSuccesfullyCreated,
                                                "Information",
                                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                                MessageBox.MessageType.MSG_OK);

                                        clearInput();
                                        txtCustomerId.setText(
                                                customerRegistrationDAO.
                                                generateId());
                                        btnNext.setText("Next");
                                        btnBack.
                                                setVisible(false);
                                        selectionModel.select(0); //Select by index starting with 0

                                    }
                                }
                                if (validateListResultMobile == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == false
                                            && isMobileNoSaved == true
                                            && isEmailSaved == false
                                            && isFaxNoSaved == true) {

                                        mb.ShowMessage(
                                                (Stage) txtCustomerId.
                                                getScene().
                                                getWindow(),
                                                ErrorMessages.CustomerSuccesfullyCreated,
                                                "Information 5",
                                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                                MessageBox.MessageType.MSG_OK);
                                        clearInput();
                                        txtCustomerId.setText(
                                                customerRegistrationDAO.
                                                generateId());
                                        btnNext.setText("Next");
                                        btnBack.setVisible(false);
                                        selectionModel.select(0); //Select by index starting with 0

                                    }
                                }
//done                
                                if (validateListResultEmail == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == false
                                            && isMobileNoSaved == false
                                            && isEmailSaved == true
                                            && isFaxNoSaved == true) {

                                        mb.ShowMessage(
                                                (Stage) txtCustomerId.
                                                getScene().
                                                getWindow(),
                                                ErrorMessages.CustomerSuccesfullyCreated,
                                                "Information 6",
                                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                                MessageBox.MessageType.MSG_OK);
                                        clearInput();
                                        txtCustomerId.setText(
                                                customerRegistrationDAO.
                                                generateId());
                                        btnNext.setText("Next");
                                        btnBack.
                                                setVisible(false);
                                        selectionModel.select(0); //Select by index starting with 0

                                    }
                                }
                                if (validateListResultEmail == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == false
                                            && isMobileNoSaved == false
                                            && isEmailSaved == false
                                            && isFaxNoSaved == true) {
                                        mb.ShowMessage(stage,
                                                ErrorMessages.MandatoryError,
                                                "Error Not any one",
                                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                                MessageBox.MessageType.MSG_OK);

                                    }
                                }

                                mb.ShowMessage(stage,
                                        ErrorMessages.SuccesfullyRegistered,
                                        "Information",
                                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                        MessageBox.MessageType.MSG_OK);

                                clearInput();
                                this.reloadCustomerID();
                                btnNext.setText("Next");
                                btnBack.setVisible(false);
                                selectionModel.select(0);

                                //================================// update.....//========================//
                            } else if (isDataAvailable == true) {

                                boolean isCustomerSaved
                                        = customerRegistrationDAO.
                                        updateCustomerInfo(
                                                txtCustomerId.getText(),
                                                txtName.getText(),
                                                txtAddress.getText().
                                                trim(),
                                                cmbTitle.getValue().
                                                toString(),
                                                cmbCusType.getValue().
                                                toString()
                                        );

                                boolean isTelephoneNoDeleted
                                        = customerRegistrationDAO.
                                        deleteCustomerTelephoneForUpdate(
                                                txtCustomerId.getText());

                                boolean isMobileNoDeleted
                                        = customerRegistrationDAO.
                                        deleteCustomerMobileForUpdate(
                                                txtCustomerId.getText());

                                boolean isEmailDeleted
                                        = customerRegistrationDAO.
                                        deleteCustomerEmailForUpdate(
                                                txtCustomerId.getText());

                                boolean isFaxDeleted
                                        = customerRegistrationDAO.
                                        deleteCustomerFaxForUpdate(
                                                txtCustomerId.getText());
                                
                                boolean isDriverDeleted
                                        = customerRegistrationDAO.
                                        deleteCustomerDriverForUpdate(
                                                txtCustomerId.getText());

                                saveContactInformation();

                                // Done
                                if (validateListResultTelephone == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == true
                                            && isMobileNoSaved == false
                                            && isEmailSaved == false
                                            && isFaxNoSaved == true) {

                                        mb.ShowMessage(
                                                (Stage) txtCustomerId.
                                                getScene().
                                                getWindow(),
                                                ErrorMessages.CustomerSuccesfullyCreated,
                                                "Information 4",
                                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                                MessageBox.MessageType.MSG_OK);
                                        clearInput();
                                        txtCustomerId.setText(
                                                customerRegistrationDAO.
                                                generateId());
                                        btnNext.setText("Next");
                                        btnBack.
                                                setVisible(false);
                                        selectionModel.select(0); //Select by index starting with 0

                                    }
                                }
                                if (validateListResultMobile == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == false
                                            && isMobileNoSaved == true
                                            && isEmailSaved == false
                                            && isFaxNoSaved == true) {

                                        mb.ShowMessage(
                                                (Stage) txtCustomerId.
                                                getScene().
                                                getWindow(),
                                                ErrorMessages.CustomerSuccesfullyCreated,
                                                "Information 5",
                                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                                MessageBox.MessageType.MSG_OK);
                                        clearInput();
                                        txtCustomerId.setText(
                                                customerRegistrationDAO.
                                                generateId());
                                        btnNext.setText("Next");
                                        btnBack.setVisible(false);
                                        selectionModel.select(0); //Select by index starting with 0

                                    }
                                }
//done                
                                if (validateListResultEmail == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == false
                                            && isMobileNoSaved == false
                                            && isEmailSaved == true
                                            && isFaxNoSaved == true) {

                                        mb.ShowMessage(
                                                (Stage) txtCustomerId.
                                                getScene().
                                                getWindow(),
                                                ErrorMessages.CustomerSuccesfullyCreated,
                                                "Information 6",
                                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                                MessageBox.MessageType.MSG_OK);
                                        clearInput();
                                        txtCustomerId.setText(
                                                customerRegistrationDAO.
                                                generateId());
                                        btnNext.setText("Next");
                                        btnBack.
                                                setVisible(false);
                                        selectionModel.select(0); //Select by index starting with 0

                                    }
                                }
                                if (validateListResultEmail == true) {
                                    if (isCustomerSaved == true
                                            && isTelephoneNoSaved == false
                                            && isMobileNoSaved == false
                                            && isEmailSaved == false
                                            && isFaxNoSaved == true) {
                                        mb.ShowMessage(stage,
                                                ErrorMessages.MandatoryError,
                                                "Error Not any one",
                                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                                MessageBox.MessageType.MSG_OK);

                                    }
                                }

                                mb.ShowMessage(stage,
                                        ErrorMessages.SuccesfullyUpdated,
                                        "Information",
                                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                        MessageBox.MessageType.MSG_OK);

                                clearInput();
                                this.reloadCustomerID();
                                btnNext.setText("Next");
                                btnBack.setVisible(false);
                                selectionModel.select(0);

                            } else {
                                mb.ShowMessage(stage,
                                        ErrorMessages.MandatoryError,
                                        "At least one Component Should initialize",
                                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                                        MessageBox.MessageType.MSG_OK);
                            }
                        } else {
                            mb.ShowMessage(stage,
                                    ErrorMessages.MandatoryError,
                                    "Error",
                                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                                    MessageBox.MessageType.MSG_OK);
                        }

                    }

                }
            }

        } else {
            mb.ShowMessage(stage,
                    ErrorMessages.ComponentNotInitialized, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        }
    }

    @FXML
    private void btnRemoveEmailOnAction(ActionEvent event
    ) {

        try {
            validatorInitializationEmailList();
            boolean model = lstEmail.getSelectionModel().isEmpty();
            if (model == false) {

                int removeIndex = lstEmail.getSelectionModel().
                        getSelectedIndex();
                lstEmail.getItems().remove(removeIndex);
                //            validatorInitializationEmailList();
            }

        } catch (Exception e) {
        }

    }

    @FXML
    private void btnRemoveVehicleNoxOnAction(ActionEvent event
    ) {

        try {
            validatorInitialization();
            boolean model = lstVehicleNo.getSelectionModel().isEmpty();
            if (model == false) {

                int removeIndex = lstVehicleNo.getSelectionModel().
                        getSelectedIndex();
                lstVehicleNo.getItems().remove(removeIndex);
            }

        } catch (Exception e) {
        }

    }

    @FXML
    private void btnRemoveMobileOnAction(ActionEvent event
    ) {

        try {
            validatorInitializationMobileList();
            boolean model = lstMobile.getSelectionModel().isEmpty();
            if (model == false) {

                int removeIndex = lstMobile.getSelectionModel().
                        getSelectedIndex();
                lstMobile.getItems().remove(removeIndex);
                //        validatorInitializationMobileList();
            }

        } catch (Exception e) {
        }

    }

    @FXML
    private void btnRemoveTelephoneOnAction(ActionEvent event
    ) {

        try {
            validatorInitializationTelephoneList();
            boolean model = lstTelephone.getSelectionModel().isEmpty();
            if (model == false) {

                int removeIndex = lstTelephone.getSelectionModel().
                        getSelectedIndex();
                lstTelephone.getItems().remove(removeIndex);
                //        validatorInitializationTelephoneList();
            }

        } catch (Exception e) {
        }

    }

    @FXML
    private void btnSearchCustomerOnAction(ActionEvent event
    ) {
        tableDataLoader(txtSearch.getText().trim());

    }

    private void btnAddProfessionOnAction(ActionEvent event) {

        Optional<String> response = null;
        try {
            response = Dialogs.create().title("New profession").masthead(
                    "Add new profession").message("Profession Title").
                    showTextInput();

            if (response != null) {
                if (!response.get().isEmpty()) {

                    boolean valid = fav.validProfession(response.get().trim());
                    if (valid == true) {

                        boolean isDataAvailable = customerRegistrationDAO.
                                checkingProfessionAvailability(response.get());// Checking profession availability
                        boolean professionAck = customerRegistrationDAO.
                                insertProfession(response.get());

                        if (professionAck == true && isDataAvailable == false) {

                            //loadProfessionToCombobox();
                        } else if (professionAck == false && isDataAvailable
                                == false) {
                            mb.ShowMessage((Stage) txtCustomerId.getScene().
                                    getWindow(),
                                    ErrorMessages.UnsavedData, "Error",
                                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                                    MessageBox.MessageType.MSG_OK);
                        }
                    }

                }
            }
        } catch (NoSuchElementException e) {

            if (e instanceof NoSuchElementException) {

                mb.ShowMessage((Stage) txtCustomerId.getScene().getWindow(),
                        ErrorMessages.UnsavedData, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            }

        } catch (Exception e) {

            mb.ShowMessage((Stage) txtCustomerId.getScene().getWindow(),
                    ErrorMessages.Error, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

    }

//  TextField Events-------------------------------------------------
    @FXML
    private void txtNameKeyReleased(KeyEvent event
    ) {

        validatorInitialization();

    }

    @FXML
    private void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event
    ) {

        tableDataLoader(txtSearch.getText().trim());

    }

    private void txtIdentificationOnKeyReleased(KeyEvent event
    ) {

        validatorInitialization();

    }

    @FXML
    private void txtAddressOnKeyReleased(KeyEvent event
    ) {

        validatorInitialization();

    }

    @FXML
    private void txtTelephoneOnKeyReleased(KeyEvent event
    ) {

        //    validatorInitialization();
        validateTelephone.registerValidator(txtTelephone,
                new CustomTextFieldValidationImpl(
                        txtTelephone,
                        !fav.isValidUniqueTelephoneNumber(lstTelephone,
                                txtTelephone.getText().
                                trim()),
                        ErrorMessages.InvalidTelephoneOrDuplicate));
    }

    @FXML
    private void txtMobileOnKeyReleased(KeyEvent event
    ) {

        //    validatorInitialization();
        validateMobile.registerValidator(txtMobile,
                new CustomTextFieldValidationImpl(
                        txtMobile,
                        !fav.isValidUniqueTelephoneNumber(lstMobile, txtMobile.
                                getText().
                                trim()), ErrorMessages.InvalidMobileOrDuplicate));

    }

    @FXML
    private void txtEmailOnKeyReleased(KeyEvent event
    ) {

        //    validatorInitialization();
        validateEmail.registerValidator(txtEmail,
                new CustomTextFieldValidationImpl(
                        txtEmail,
                        !fav.isValidUniqueEmail(lstEmail, txtEmail.
                                getText().
                                trim()),
                        ErrorMessages.InvalidEmailAddressOrDuplicate));

    }

    @FXML
    private void txtVehicleNoOnKeyReleased(KeyEvent event
    ) {

        validateFax.registerValidator(txtVehicleNo,
                new CustomTextFieldValidationImpl(
                        txtVehicleNo,
                        fav.isValidVehicleNo(lstVehicleNo, txtVehicleNo.
                                getText().
                                trim()), ErrorMessages.InvalidVehicleNo));

    }

    private void dtpDateOfBirthOnAction(ActionEvent event
    ) {

        validatorInitialization();

    }

    @FXML
    private void cmbTitleOnAction(ActionEvent event
    ) {

        Tooltip tooltip = new Tooltip();

        if (cmbTitle.getValue().equals("Mr.")) {
            tooltip.setText("Mister");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Mrs.")) {
            tooltip.setText("Missus");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Ms.")) {
            tooltip.setText("Miss");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Prof.")) {
            tooltip.setText("Professor");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Rev.")) {
            tooltip.setText("Reverend");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Dr.")) {
            tooltip.setText("Doctor");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Esq.")) {
            tooltip.setText("Esquire");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Hon.")) {
            tooltip.setText("Honorable");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Jr.")) {
            tooltip.setText("Junior");
            cmbTitle.setTooltip(tooltip);
        } else if (cmbTitle.getValue().equals("Sr.")) {
            tooltip.setText("Sir");
            cmbTitle.setTooltip(tooltip);
        } else {
            tooltip.setText("");
            cmbTitle.setTooltip(tooltip);
        }

    }

//    Mouse events-------------------------------------------------
    @FXML
    private void tableViewOnMouseClick(javafx.scene.input.MouseEvent mouseEvent
    ) {

        if (mouseEvent.getClickCount() == 2) {
            Item itemData = (Item) tblContactDetails.getSelectionModel().
                    getSelectedItem();
            String customerId = itemData.colCustomerId.get();

            if (customerId != null) {
                customerInformationLoader(customerId);
            }

        }

    }

    @FXML
    private void lstTelephoneOnMouseClicked(
            javafx.scene.input.MouseEvent mouseEvent
    ) {

        try {

            validatorInitializationTelephoneList();
            if (mouseEvent.getClickCount() == 2) {

                boolean model = lstTelephone.getSelectionModel().isEmpty();
                if (model == false) {

                    txtTelephone.setText(lstTelephone.getSelectionModel().
                            getSelectedItem());
                    int removeIndex = lstTelephone.getSelectionModel().
                            getSelectedIndex();

                    lstTelephone.getItems().remove(removeIndex);
                    txtTelephone.requestFocus();
                    txtTelephone.selectAll();

                }

            }
        } catch (Exception e) {

        }

    }

    @FXML
    private void lstMobileOnMouseClicked(
            javafx.scene.input.MouseEvent mouseEvent
    ) {
        try {
            validatorInitializationMobileList();
            if (mouseEvent.getClickCount() == 2) {

                boolean model = lstMobile.getSelectionModel().isEmpty();
                if (model == false) {

                    txtMobile.setText(lstMobile.getSelectionModel().
                            getSelectedItem());
                    int removeIndex = lstMobile.getSelectionModel().
                            getSelectedIndex();
                    lstMobile.getItems().remove(removeIndex);
                    txtMobile.requestFocus();
                    txtMobile.selectAll();
                }
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void lstEmailOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent
    ) {
        try {

            validatorInitializationEmailList();
            if (mouseEvent.getClickCount() == 2) {

                boolean model = lstEmail.getSelectionModel().isEmpty();
                if (model == false) {

                    txtEmail.setText(lstEmail.getSelectionModel().
                            getSelectedItem());
                    int removeIndex = lstEmail.getSelectionModel().
                            getSelectedIndex();
                    lstEmail.getItems().remove(removeIndex);
                    txtEmail.requestFocus();
                    txtEmail.selectAll();
                }
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void lstFaxOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent
    ) {
        try {
            if (mouseEvent.getClickCount() == 2) {

                boolean model = lstVehicleNo.getSelectionModel().isEmpty();
                if (model == false) {

                    txtVehicleNo.setText(lstVehicleNo.getSelectionModel().
                            getSelectedItem());
                    int removeIndex = lstVehicleNo.getSelectionModel().
                            getSelectedIndex();
                    lstVehicleNo.getItems().remove(removeIndex);
                    txtVehicleNo.requestFocus();
                    txtVehicleNo.selectAll();
                }
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void onMouseReleasedTelephoneAdd(
            javafx.scene.input.MouseEvent mouseEvent) {
        validatorInitializationTelephoneList();
    }

    @FXML
    private void onMouseReleasedMobieAdd(
            javafx.scene.input.MouseEvent mouseEvent) {
        validatorInitializationMobileList();
    }

    @FXML
    private void onMouseReleasedEmailAdd(
            javafx.scene.input.MouseEvent mouseEvent) {
        validatorInitializationEmailList();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {

        reloadCustomerID();
        txtAddress.clear();
        cmbCusType.setValue("");
        txtEmail.clear();
        txtVehicleNo.clear();
        //txtIdentification.setText("");
        txtMobile.clear();
        txtName.clear();
        txtSearch.clear();
        txtTelephone.clear();
        //cmbCustomerIdType.setValue("NIC");
        cmbTitle.setValue("Mr.");

//        if (cmbProfession.getItems().size() != 0) {
//            cmbProfession.setValue(cmbProfession.getItems().get(0));
//        }
//        cmbNationality.setValue("Sri Lankans");
//        dtpDateOfBirth.setValue(LocalDate.now());
        lstEmail.getItems().removeAll(lstEmail.getItems());
        lstVehicleNo.getItems().removeAll(lstVehicleNo.getItems());
        lstMobile.getItems().removeAll(lstMobile.getItems());
        lstTelephone.getItems().removeAll(lstTelephone.getItems());
        lstDrivers.getItems().removeAll(lstDrivers.getItems());
        validatorInitialization();
        tableDataLoader(txtSearch.getText().trim());

    }

    @Override
    public void clearValidations() {

    }

//Methods-----------------------------------------------------------------------------------------------------------
    private void tableDataLoader(String keyword) {
        tableCleanner();

        ArrayList<ArrayList<String>> custInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = customerRegistrationDAO.
                searchItemDetails(keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                custInfo.add(list.get(i));
            }

            if (custInfo != null && custInfo.size() > 0) {
                for (int i = 0; i < custInfo.size(); i++) {

                    item = new Item();

                    item.colCustomerId.setValue(custInfo.get(i).get(0));
                    item.colTitle.setValue(custInfo.get(i).get(1));
                    item.colName.setValue(custInfo.get(i).get(2));
//                    item.colIdType.setValue(custInfo.get(i).get(3));
                    item.colAddress.setValue(custInfo.get(i).get(3));
                    item.colVAT.setValue(custInfo.get(i).get(4));

                    data.add(item);
                }
            }

        }

    }

    private void tableCleanner() {

        int count = data.size();

        if (count > 0) {

            for (int i = 0; i < count; i++) {

                data.remove(0);
            }

        }

    }

//    private void loadProfessionToCombobox() {
//
//        cmbProfession.setItems(null);
//        ArrayList<String> professionList = null;
//        professionList = customerRegistrationDAO.loadingProfession();
//        if (professionList != null) {
//
//            ObservableList<String> List = FXCollections.observableArrayList(
//                    professionList);
//            cmbProfession.setItems(List);
//            cmbProfession.setValue(List.get(0));
//
//        }
//
//    }
    private void dateFormatter(String pattern, DatePicker dtp) {

        dtp.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(
                    pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    private void customerInformationLoader(String customerId) {

        ArrayList<String> customerDataList = null;
        ArrayList<String> customerTelephoneList = null;
        ArrayList<String> customerMobileList = null;
        ArrayList<String> customerEmailList = null;
        ArrayList<String> customerFaxList = null;
        ArrayList<String> customerDriverList = null;
        ArrayList<String> customerVatList = null;

        if (customerId != null) {

            customerDataList = customerRegistrationDAO.customerInformation(
                    customerId);
            customerTelephoneList = customerRegistrationDAO.
                    customerTelephoneLoading(customerId);
            customerMobileList = customerRegistrationDAO.customerMobileLoading(
                    customerId);
            customerEmailList = customerRegistrationDAO.customerEmailLoading(
                    customerId);
            customerFaxList = customerRegistrationDAO.customerFaxLoading(
                    customerId);

            customerDriverList = customerRegistrationDAO.customerDriverLoading(
                    customerId);

            if (customerDataList != null) {

                txtCustomerId.setText(customerId);
                cmbTitle.setValue(customerDataList.get(1));
                txtName.setText(customerDataList.get(2));

                txtAddress.setText(customerDataList.get(3));

                cmbCusType.setValue(customerDataList.get(4));

                if (customerTelephoneList != null) {
                    lstTelephone.setItems(FXCollections.observableList(
                            customerTelephoneList));
                }
                if (customerMobileList != null) {
                    lstMobile.setItems(FXCollections.observableList(
                            customerMobileList));
                }
                if (customerEmailList != null) {
                    lstEmail.setItems(FXCollections.observableList(
                            customerEmailList));
                }
                if (customerFaxList != null) {
                    lstVehicleNo.setItems(FXCollections.
                            observableList(customerFaxList));
                }
                if (customerDriverList != null) {
                    lstDrivers.setItems(FXCollections.
                            observableList(customerDriverList));
                }

                validatorInitialization();

            }
        }

    }

    private void saveContactInformation() {

        ObservableList<String> listTelephone = lstTelephone.getItems();
        ObservableList<String> listMobile = lstMobile.getItems();
        ObservableList<String> listEmail = lstEmail.getItems();
        ObservableList<String> listFax = lstVehicleNo.getItems();
        ObservableList<String> listDriver = lstDrivers.getItems();

//// Loading to db
////=============================================================================================================== 
        if (listTelephone.size() != 0) {
            for (int i = 0; i < listTelephone.size(); i++) {

                isTelephoneNoSaved = customerRegistrationDAO.
                        insertCustomerTelephoneDetails(txtCustomerId.getText(),
                                listTelephone.get(i).toString());
            }
        }

        if (listMobile.size() != 0) {
            for (int i = 0; i < listMobile.size(); i++) {

                isMobileNoSaved = customerRegistrationDAO.
                        insertCustomerMobileDetails(txtCustomerId.getText(),
                                listMobile.get(i).toString());
            }
        }

        if (listEmail.size() != 0) {
            for (int i = 0; i < listEmail.size(); i++) {
                isEmailSaved = customerRegistrationDAO.
                        insertCustomerEmailDetails(txtCustomerId.getText(),
                                listEmail.get(i).toString());
            }
        }

        if (listFax.size() != 0) {
            for (int i = 0; i < listFax.size(); i++) {
                isFaxNoSaved = customerRegistrationDAO.insertCustomerFaxDetails(
                        txtCustomerId.getText(), listFax.get(i).toString());
            }
        } else if (listFax.size() == 0) {
            isFaxNoSaved = true;
        }

        if (listDriver.size() != 0) {
            for (int i = 0; i < listDriver.size(); i++) {
                isDriverSaved = customerRegistrationDAO.insertDriverDetails(
                        txtCustomerId.getText(), listDriver.get(i).toString());
            }
        } else if (listDriver.size() == 0) {
            isDriverSaved = true;
        }

    }

    private void validatorInitialization() {

        System.gc();
        // Validator Initializing-----------------------------
//        validationSupport.registerValidator(txtAddress,
//                new CustomTextAreaValidationImpl(txtAddress,
//                        !fav.validAddress(txtAddress.getText()),
//                        ErrorMessages.InvalidAddress));

//       
        validationSupport.registerValidator(txtName,
                new CustomTextFieldValidationImpl(txtName,
                        !fav.validName(txtName.getText()),
                        ErrorMessages.InvalidName));

        validationSupport.registerValidator(txtName,
                new CustomTextFieldValidationImpl(txtName,
                        !fav.validName(txtName.getText()),
                        ErrorMessages.InvalidName));

        // list validations
        validationSupportListView.registerValidator(lstMobile,
                new CustomListViewValidationImpl(lstMobile,
                        !fav.validListView(lstMobile),
                        ErrorMessages.EmptyListView));

        validationSupportListView.registerValidator(lstEmail,
                new CustomListViewValidationImpl(lstEmail,
                        !fav.validListView(lstEmail),
                        ErrorMessages.EmptyListView));

        validationSupportListView.registerValidator(lstTelephone,
                new CustomListViewValidationImpl(lstTelephone,
                        !fav.validListView(lstTelephone),
                        ErrorMessages.EmptyListView));

        validateEmail.registerValidator(txtEmail,
                new CustomTextFieldValidationImpl(txtEmail,
                        !fav.isValidUniqueEmail(lstEmail, txtEmail.getText().
                                trim()),
                        ErrorMessages.InvalidEmailAddressOrDuplicate));

//        validationSupportListViewTelephone.registerValidator(lstTelephone,
//                new CustomListViewValidationImpl(lstTelephone,
//                        !fav.validListView(lstTelephone),
//                        ErrorMessages.EmptyListView));
//
//        validationSupportListViewMoblie.registerValidator(lstMobile,
//                new CustomListViewValidationImpl(lstMobile,
//                        !fav.validListView(lstMobile),
//                        ErrorMessages.EmptyListView));
//
//        validationSupportListViewEmail.registerValidator(lstEmail,
//                new CustomListViewValidationImpl(lstEmail,
//                        !fav.validListView(lstEmail),
//                        ErrorMessages.EmptyListView));
        /////////// text fields validations
//        validationSupport.registerValidator(cmbProfession,
//                new CustomComboboxValidationImpl(cmbProfession,
//                        !fav.validCombobox(cmbProfession),
//                        ErrorMessages.EmptyListView));
    }

    private void validatorInitializationTelephoneList() {

        validationSupportListViewTelephone.registerValidator(lstTelephone,
                new CustomListViewValidationImpl(lstTelephone,
                        !fav.validListView(lstTelephone),
                        ErrorMessages.EmptyListView));
    }

    private void validatorInitializationMobileList() {
        validationSupportListViewMoblie.registerValidator(lstMobile,
                new CustomListViewValidationImpl(lstMobile,
                        !fav.validListView(lstMobile),
                        ErrorMessages.EmptyListView));
    }

    private void validatorInitializationEmailList() {

        validationSupportListViewEmail.registerValidator(lstEmail,
                new CustomListViewValidationImpl(lstEmail,
                        !fav.validListView(lstEmail),
                        ErrorMessages.EmptyListView));
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        btnDelete.setVisible(false);
        this.hidePrinterRemark();

        stage.setHeight(575.0);
        stage.setWidth(1015.0);

        setUserAccessLevel();
//        customerTypeData.clear();
        customerTypeData = cmbCusType.getItems();
        loadCustomerTypeToCombobox();

    }

    @FXML
    private void cmbCusTypeOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {

        String customerType = InputDialog.inputForAddNew("Customer Type");
        boolean isSaved = false;
        if (customerType == null) {
            return;
        }
        if (!fav.validAddress(customerType)) {
            mb.ShowMessage(stage, "Invalid Customer Type", "Customer",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (customerTypeData.contains(customerType)) {
            mb.ShowMessage(stage, "Duplicate Customer", "Customer",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = customerRegistrationDAO.insertCustomerType(customerType);

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Customer",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        customerTypeData.add(customerType);
        loadCustomerTypeToCombobox();
        cmbCusType.getSelectionModel().select(customerType);

        validatorInitialization();

    }

    @FXML
    private void cmbCusTypeOnKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.DELETE | event.getCode()
                == KeyCode.BACK_SPACE) {

            if (cmbCusType.getValue() != null) {
                MessageBox.MessageOutput option = mb.ShowMessage(stage,
                        "Are you sure you want \nto remove the customer type ? ",
                        "Customer Type",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO);
                if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                    boolean isRemoved = customerRegistrationDAO.
                            deleteCustomerType(
                                    cmbCusType.getValue());

                    if (isRemoved == true) {
                        loadCustomerTypeToCombobox();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyDeleted,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                }
            }
            loadCustomerTypeToCombobox();
            validatorInitialization();
        }
    }

    @FXML
    private void txtDriverOnKeyReleased(KeyEvent event) {
        
//            validatorInitialization();
        validateDriver.registerValidator(txtDriver,
                new CustomTextFieldValidationImpl(
                        txtDriver,
                        fav.isValidVehicleNo(lstDrivers, txtDriver.
                                getText().
                                trim()), ErrorMessages.InvalidDriverName));
        
    }

//----------------------------------------------------------------------------------------------------------------------    
//Classes------------------------------------------------------------------------------------------------------------
    public class Item {

        public SimpleStringProperty colCustomerId = new SimpleStringProperty(
                "tcCustomerId");
        public SimpleStringProperty colTitle = new SimpleStringProperty(
                "tcTitle");
        public SimpleStringProperty colName = new SimpleStringProperty("tcName");
        public SimpleStringProperty colIdType = new SimpleStringProperty(
                "tcPassport");
        public SimpleStringProperty colAddress = new SimpleStringProperty(
                "tcAddress");
        public SimpleStringProperty colVAT = new SimpleStringProperty(
                "tcVAT");

        public String getColCustomerId() {
            return colCustomerId.get();
        }

        public String getColTitle() {
            return colTitle.get();
        }

        public String getColName() {
            return colName.get();
        }

        public String getColIdType() {
            return colIdType.get();
        }

        public String getColAddress() {
            return colAddress.get();
        }

        public String getColVAT() {
            return colVAT.get();
        }

    }

//----------------------------------------------------------------------------------------------------------------------    
//<editor-fold defaultstate="collapsed" desc="Methods">
    private void loadCustomerTypeToCombobox() {

        cmbCusType.setItems(null);
        ArrayList<String> customerTypeList = null;
        customerTypeList = customerRegistrationDAO.loadCustomerType();
        if (customerTypeList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        customerTypeList);
                cmbCusType.setItems(List);
                cmbCusType.setValue(List.get(0));
            } catch (Exception e) {

            }

        }

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);

                deactivateSearch();

//                btnDelete.setDisable(true);
                btnDelete.setVisible(false);
                btnRemoveTelephone.setDisable(true);
                btnRemoveTelephone.setVisible(false);

                btnRemoveMobile.setDisable(true);
                btnRemoveMobile.setVisible(false);

                btnRemoveEmail.setDisable(true);
                btnRemoveEmail.setVisible(false);

                btnRemoveVehicleNo.setDisable(true);
                btnRemoveVehicleNo.setVisible(false);

                btnBack.setDisable(true);
                btnBack.setVisible(false);

                break;

            case DELETE:
                disableUi(false);

                btnNext.setDisable(true);
                btnNext.setVisible(false);

                btnBack.setDisable(true);
                btnBack.setVisible(false);

                break;

            case READ_ONLY:
                disableUi(false);

                btnNext.setDisable(true);
                btnNext.setVisible(false);

                btnBack.setDisable(true);
                btnBack.setVisible(false);

//                btnDelete.setDisable(true);
                btnDelete.setVisible(false);
                break;

            case ALL_BUT_DELETE:
                disableUi(false);

//                btnDelete.setDisable(true);
                btnDelete.setVisible(false);
                btnRemoveTelephone.setDisable(true);
                btnRemoveTelephone.setVisible(false);

                btnRemoveMobile.setDisable(true);
                btnRemoveMobile.setVisible(false);

                btnRemoveEmail.setDisable(true);
                btnRemoveEmail.setVisible(false);

                btnRemoveVehicleNo.setDisable(true);
                btnRemoveVehicleNo.setVisible(false);

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

    private void disableUi(boolean state) {

        txtCustomerId.setDisable(state);
        txtCustomerId.setVisible(!state);

        cmbTitle.setDisable(state);
        cmbTitle.setVisible(!state);

        txtName.setDisable(state);
        txtName.setVisible(!state);

        txtAddress.setDisable(state);
        txtAddress.setVisible(!state);

        txtSearch.setDisable(state);
        txtSearch.setVisible(!state);

        btnSearchCustomer.setDisable(state);
        btnSearchCustomer.setVisible(!state);

        tblContactDetails.setDisable(state);
        tblContactDetails.setVisible(!state);

        txtTelephone.setDisable(state);
        txtTelephone.setVisible(!state);

        txtMobile.setDisable(state);
        txtMobile.setVisible(!state);

        txtEmail.setDisable(state);
        txtEmail.setVisible(!state);

        txtVehicleNo.setDisable(state);
        txtVehicleNo.setVisible(!state);

        btnRemoveTelephone.setDisable(state);
        btnRemoveTelephone.setVisible(!state);

        btnRemoveMobile.setDisable(state);
        btnRemoveMobile.setVisible(!state);

        btnRemoveEmail.setDisable(state);
        btnRemoveEmail.setVisible(!state);

        btnRemoveVehicleNo.setDisable(state);
        btnRemoveVehicleNo.setVisible(!state);

        btnAddTelephone.setDisable(state);
        btnAddTelephone.setVisible(!state);

        btnAddMobile.setDisable(state);
        btnAddMobile.setVisible(!state);

        btnAddEmail.setDisable(state);
        btnAddEmail.setVisible(!state);

        btnAddVehicleNo.setDisable(state);
        btnAddVehicleNo.setVisible(!state);

        btnDelete.setDisable(state);
        btnDelete.setVisible(!state);

        btnNext.setDisable(state);
        btnNext.setVisible(!state);

        btnClose.setDisable(state);
        btnClose.setVisible(!state);

        cmbCusType.setDisable(state);
        cmbCusType.setVisible(!state);

    }

    private void deactivateSearch() {
        componentControl.deactivateSearch(lblSearch, txtSearch,
                btnSearchCustomer,
                180.00, 0.00);

    }

//</editor-fold>
}
