package com.saiton.ccs.base;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.StagePassable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class NotifyController implements StagePassable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    //-------------------------------------------------------
    //Search Table
    @FXML
    private TableColumn<Notification, String> tcsType;

    @FXML
    private TableColumn<Notification, String> tcsDesc;

    @FXML
    private TableColumn<Notification, String> tcsDate;

    @FXML
    private TableColumn<Notification, String> tcsUi;

    @FXML
    private TableColumn<Notification, Boolean> tcsResolved;

    @FXML
    private TableColumn<Notification, String> tcsResolvedDate;

    @FXML
    private TableView<Notification> tvSearch;

    ObservableList<Notification> dataSearch;
    //---------------------------------------------
    @FXML
    private TableColumn<Notification, String> tcuUi;

    @FXML
    private TableColumn<Notification, String> tcuType;

    @FXML
    private TableColumn<Notification, String> tcuDesc;

    @FXML
    private TableColumn<Notification, String> tcuDate;

    @FXML
    private TableView<Notification> tvUnresolved;

    ObservableList<Notification> dataUnresolved;
    //------------------------------------------------------

    @FXML
    private DatePicker dpFrom;

    @FXML
    private DatePicker dpTo;

    @FXML
    private TextField txtKey;

    private final StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd");

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
    };
    //----------------------------------------
    private Stage stage;

    private final NotificationFacade notify = NotificationFacade.getInstance();

    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();

    private void loadUnresolved() {
        dataUnresolved.clear();
        dataUnresolved.addAll(notify.selectUnresolvedNotifications());
    }

    private void loadSearch() {
        String from = converter.toString(dpFrom.getValue());
        String to = converter.toString(dpTo.getValue());

        dataSearch.clear();
        dataSearch.addAll(notify.searchNotifications(txtKey.getText() == null
                ? "" : txtKey.getText(), from, to));
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        loadUnresolved();
    }

    @FXML
    void btnResolveOnAction(ActionEvent event) {
        Notification n = tvUnresolved.getSelectionModel().getSelectedItem();
        if (n == null) {
            mb.ShowMessage(stage,
                    "You need to select a notification to mark it as resolved",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }
        if (notify.resolve(n.getNid(), UserSession.getInstance().getUserInfo().
                getUsername())) {
            mb.ShowMessage(stage,
                    "Success",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);
        } else {
            mb.ShowMessage(stage,
                    "Failed",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);
        }
        loadUnresolved();
    }

    @FXML
    void btnSearchResolveOnAction(ActionEvent event) {
        Notification n = tvSearch.getSelectionModel().getSelectedItem();
        if (n == null) {
            mb.ShowMessage(stage,
                    "You need to select a notification to mark it as resolved",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }
        
        if (n.getResolved()) {
            mb.ShowMessage(stage,
                    "Already resolved",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (notify.resolve(n.getNid(), UserSession.getInstance().getUserInfo().
                getUsername())) {
            mb.ShowMessage(stage,
                    "Success",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);
        } else {
            mb.ShowMessage(stage,
                    "Failed",
                    "Notifications",
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);
        }
        loadSearch();
    }

    @FXML
    void btnSearchRefreshOnAction(ActionEvent event) {
        loadSearch();
    }

    @FXML
    void initialize() {
        //========================
        //SEARCH

        tcsType.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        tcsDesc.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        tcsDate.setCellValueFactory(
                new PropertyValueFactory<>("dateAdded"));
        tcsUi.setCellValueFactory(
                new PropertyValueFactory<>("ui"));
        tcsResolved.setCellValueFactory(
                new PropertyValueFactory<>("resolved"));
        tcsResolved.setCellFactory((column) -> new CheckBoxTableCell<>());
        tcsResolvedDate.setCellValueFactory(
                new PropertyValueFactory<>("dateResolved"));
        dataSearch = tvSearch.getItems();

        //===========================
        //UNRESOLVED
        tcuUi.setCellValueFactory(
                new PropertyValueFactory<>("ui"));
        tcuType.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        tcuDesc.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        tcuDate.setCellValueFactory(
                new PropertyValueFactory<>("dateAdded"));
        dataUnresolved = tvUnresolved.getItems();
        //===================================
        dpTo.setValue(LocalDate.now());
        dpTo.setConverter(converter);
        dpFrom.setValue(LocalDate.now());
        dpFrom.setConverter(converter);
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
    }

}
