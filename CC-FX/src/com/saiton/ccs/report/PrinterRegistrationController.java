package com.saiton.ccs.report;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.validations.ErrorMessages;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Saitonya
 */
public class PrinterRegistrationController extends AnchorPane implements
        Initializable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private Label lblItemId;

    @FXML
    private Button btnClose;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblItemId1;

    @FXML
    private ComboBox<String> cmbPrinterType;

    @FXML
    private Insets x11;

    @FXML
    private Insets x2;

    @FXML
    private TextField txtPrinterId;

    @FXML
    private Button btnPrinterIdSearch;

    @FXML
    private TextField txtTitle;

//</editor-fold>
    private Stage stage;
    private PrinterReportFacade prdao;
    private boolean isUpdate = false;
    private final ValidationSupport vbase = new ValidationSupport();
    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();
    private PopOver printerPop;
    private TableView<Printer> tvPrinters;
    private ObservableList<Printer> printersData;
    private ObservableList<String> typeData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ignore
    }

    private void setUpdateMode() {
        isUpdate = true;
        btnDelete.setVisible(true);
    }

    private void setRegisterMode() {
        isUpdate = false;
        btnDelete.setVisible(false);
    }

    private void clearInput() {
        txtAreaDescription.setText("");
        txtPrinterId.setText(prdao.generatePrinterId());
        txtTitle.setText("");
        cmbPrinterType.getSelectionModel().selectFirst();
        setRegisterMode();

    }

    private void createPop() {
        tvPrinters = new TableView<>();
        TableColumn<Printer, String> pid = new TableColumn<>("Printer ID");
        pid.setMinWidth(50.0);
        TableColumn<Printer, String> title = new TableColumn<>("Title");
        title.setMinWidth(50.0);
        TableColumn<Printer, String> type = new TableColumn<>("Type");
        type.setMinWidth(50.0);

        pid.setCellValueFactory(
                new PropertyValueFactory("pid"));

        title.setCellValueFactory(
                new PropertyValueFactory("name"));

        type.setCellValueFactory(
                new PropertyValueFactory("type"));

        tvPrinters.getColumns().addAll(pid, title, type);

        printersData = tvPrinters.getItems();
        printersData.clear();
        printerPop = new PopOver(tvPrinters);

        tvPrinters.setOnMouseClicked(e -> {

            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                printerPop.hide();

            } else if (e.getClickCount() == 2) {
                try {
                    Printer printer = tvPrinters.getSelectionModel().getSelectedItem();

                    if (printer != null) {
                        setUpdateMode();
                        txtPrinterId.setText(printer.getPid());
                        txtTitle.setText(printer.getName());
                        txtAreaDescription.setText(printer.getDescription());
                        cmbPrinterType.getSelectionModel().select(printer.getType());
                    }

                } catch (Exception n) {

                }

                printerPop.hide();
                validate();
            }
        });
    }

    private void validate() {
        vbase.registerValidator(txtTitle, Validator.createEmptyValidator(
                ErrorMessages.Empty));
        vbase.registerValidator(txtAreaDescription, Validator.
                createEmptyValidator(ErrorMessages.Empty));
        vbase.registerValidator(txtPrinterId, Validator.createEmptyValidator(
                ErrorMessages.Empty));
        vbase.registerValidator(cmbPrinterType, Validator.createEmptyValidator(
                ErrorMessages.Empty));
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        prdao = new PrinterReportFacade();
        createPop();
        stage.setOnCloseRequest(event -> {
            if (printerPop.isShowing()) {
                event.consume();
                printerPop.hide();
            }
        });
        typeData = cmbPrinterType.getItems();
        typeData.clear();
        List<String> types = prdao.selectAllPrinterTypes();
        if (types != null) {
            typeData.addAll(types);
        }
        clearInput();
        validate();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!isUpdate) {
            return;
        }

        if (vbase.isInvalid()) {
            mb.ShowMessage(stage, "Please enter valid data.",
                    "Printer Registration");
            return;
        }

        if (prdao.deletePrinter(txtPrinterId.getText())) {
            mb.ShowMessage(stage, "Success",
                    "Printer Registration");
        } else {
            mb.ShowMessage(stage, "Failed",
                    "Printer Registration");
        }

        clearInput();
    }

    @FXML
    void btnPrinterIdSearchOnAction(ActionEvent event) {
        printersData.clear();

        List<Printer> prints = prdao.selectAllPrinters();

        printersData.addAll(prints);
        if (!printerPop.isShowing()) {
            printerPop.show(btnPrinterIdSearch);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (vbase.isInvalid()) {
            mb.ShowMessage(stage, "Please enter valid data.",
                    "Printer Registration");
            return;
        }

        boolean b;
        if (isUpdate) {
            b = (prdao.updatePrinter(txtPrinterId.getText(), txtTitle.getText(),
                    cmbPrinterType.
                    getValue(),
                    txtAreaDescription.getText()));

        } else {
            b = (prdao.insertPrinter(txtTitle.getText(), cmbPrinterType.
                    getValue(),
                    txtAreaDescription.getText()));
        }

        if (b) {
            mb.ShowMessage(stage, "Success",
                    "Printer Registration");
        } else {
            mb.ShowMessage(stage, "Failed",
                    "Printer Registration");
        }
        clearInput();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        if (printerPop.isShowing()) {
            printerPop.hide();
        } else {
            stage.close();
        }
    }

    @FXML
    void cmbPrinterTypeOnAction(ActionEvent event) {
        validate();
    }

    @FXML
    void txtAreaDescriptionOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtTitleOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtPrinterIdOnKeyReleased(KeyEvent event) {
        validate();
    }

}
