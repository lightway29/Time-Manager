package com.saiton.ccs.base;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.FxmlUiLauncher;
import com.saiton.ccs.uihandle.ReportGenerator;
import com.saiton.ccs.ui.fxhome.ConcreteUiPageManager;
import com.saiton.ccs.ui.fxhome.ConcreteUiTitle;
import com.saiton.ccs.ui.fxhome.FxHome;
import com.saiton.ccs.ui.fxhome.TileColors;
import com.saiton.ccs.ui.fxhome.UiLink;
import com.saiton.ccs.ui.fxhome.UiLinkFactory;
import com.saiton.ccs.ui.fxhome.UiPageManager;
import com.saiton.ccs.database.Starter;
import com.saiton.ccs.printerservice.PrinterServiceServer;
import com.saiton.ccs.ui.fxhome.TileDescription;
import com.saiton.ccs.util.XMLFileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class CCSHome extends Application implements HomeCallback {

    private final Logger log = Logger.getLogger(this.getClass());

    private FxHome home;

    private Node login;

    private Stage stage;

    private LoginAccess loginAccess = null;

    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();
    private XMLFileReader xmlFileReader = new XMLFileReader();

    @Override
    public void start(Stage stage) {

    //<editor-fold defaultstate="collapsed" desc="Printer Service">
        //Server configuration
//        File file = new File("serverConfig.xml");
//
//        if (file.exists()) {
//
//            if (xmlFileReader.isIsServer() == true) {
//
//                PrinterServiceServer printerServiceServer
//                        = new PrinterServiceServer();
//                printerServiceServer.startPrintJobScanner();
//
//            }
//
//        }
//</editor-fold>
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        
        this.stage = stage;

        if (ApplicationProperties.getInstance().isInvalid()) {
            FxmlUiLauncher.launchOnNewStageWait(
                    "/com/saiton/ccs/base/DatabaseCon.fxml",
                    "Database Connection", null);
        }

        if (ApplicationProperties.getInstance().isInvalid() || !Starter.start()) {
            mb.ShowMessage(stage,
                    "Database connection failed.\n"
                    + "Connection details will now reset.\n"
                    + "Application will now close.",
                    "Start",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            ApplicationProperties.getInstance().reset();
            Platform.exit();
            System.exit(0);
        }

//        log.info("CCS Initiated. " + (ApplicationProperties.getInstance().
//                isFingerAvailable() ? "Finger Print Present" : "No Finger Print"));

        try {

            createHomeUi();
            createLoginUi();
            NotificationFacade.start(); //nothing happens unless user is logged in
            startLoginUi(); //disable this to hide login
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createHomeUi() {
        home = new FxHome(new ConcreteUiTitle());

        //<editor-fold defaultstate="collapsed" desc="Home">
        home.createTileButton("Teacher \nRegistration",
                TileColors.TILE_COLOR_CORAL,
                "/com/saiton/ccs/res/img-customer.png", FxHome.HOME_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/base/CustomerRegistration.fxml",
                        "Customer Registration"));

        home.createTileButton("Subject \nRegistration",
                TileColors.TILE_COLOR_BLUEVIOLET,
                "/com/saiton/ccs/res/img-user.png", FxHome.HOME_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/base/UserRegistration.fxml",
                        "User Registration"));
        
        home.createTileButton("Class \nRegistration" ,
                TileColors.TILE_COLOR_DARKCYAN,
                "/com/saiton/ccs/res/img-external-return-note.png",
                FxHome.HOME_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/report/ReportGenerator.fxml",
                        "Report Generator"));
        
        home.createTileButton("Stock Report",
                TileColors.TILE_COLOR_AQUAMARINE,
                "/com/saiton/ccs/res/img-external-grn.png",
                FxHome.HOME_PAGE,
                e -> {
            HashMap param = new HashMap();
            File fil
                    = new File(".//Reports//Stock.jasper");
            String img = fil.getAbsolutePath();
            ReportGenerator r = new ReportGenerator(img, param);
            r.setVisible(true);
        });

//         home.createTileButton("Printer\nRegistration",
//         TileColors.TILE_COLOR_CORAL,
//         "/com/saiton/ccs/res/img-printer-registration.png",
//         FxHome.HOME_PAGE,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ccs/report/PrinterRegistration.fxml",
//         "Printer Registration"));
//         home.createTileButton("Report\nRegistration",
//         TileColors.TILE_COLOR_CYAN,
//         "/com/saiton/ccs/res/img-report-registration.png",
//         FxHome.HOME_PAGE,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ccs/report/ReportRegistration.fxml",
//         "Report Registration"));
//         home.createTileButton("Report\nSettings",
//         TileColors.TILE_COLOR_DARKCYAN,
//         "/com/saiton/ccs/res/img-report-settings.png", FxHome.HOME_PAGE,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ccs/report/ReportSettings.fxml",
//         "Report Settings"));
//
//         home.createTileButton("Currency\nSettings",
//         TileColors.TILE_COLOR_DARKORANGE,
//         "/com/saiton/ccs/res/img-currency-settings.png",
//         FxHome.HOME_PAGE,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ccs/base/SetCurrencyRate.fxml",
//         "Currency Settings"));
         //-----------------------------------------------------------------
        //sample code
        //add a new notification
        //        home.createTileButton("Add \nNotifications",
        //                TileColors.TILE_COLOR_CORAL,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                NotificationFacade nf = NotificationFacade.getInstance();
        //                nf.insert("Room", "Room 10 needs fixing",
        //                        "Room UI", "saiton");
        //                nf.insert("Room", "Room 20 needs fixing",
        //                        "Room UI", "saiton");
        //                nf.insert("Room", "Room 11 needs fixing",
        //                        "Room UI", "saiton");
        //                nf.insert("Room", "Room 13 needs fixing",
        //                        "Room UI", "Add Notifications");
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //
        //        home.createTileButton("Grid Demo",
        //                TileColors.TILE_COLOR_SPRINGGREEN,
        //                "/com/saiton/ihm/res/img-extraKot.png", FxHome.HOME_PAGE,
        //                e -> {
        //            Stage stag = new Stage();
        //            stag.initStyle(StageStyle.TRANSPARENT);
        //            FxmlUiLauncher.launchContentReplacement(new ButtonGridDemo(stag),
        //                    "Demo", stag);
        //            stag.show();
        //        });
        //sample code
        //-----------------------------------------------------------------
        //        //-----------------------------------------------------------------
        //        //sample code
        //        //put a string to the event field as "event" if there is no real event
        //        home.createTileButton("Banquet \nMenu\nSelection",
        //                TileColors.TILE_COLOR_CORAL,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                MenuUiFacade menu = new MenuUiFacade(MenuType.BANQUET,
        //                        "CUS0001", "event");
        //                menu.launchMenuSelection(stage);
        //                List<OrmSelectionItem> osil = menu.getMenuDao().
        //                        selectSelection(
        //                                "CUS0001",
        //                                "event");
        //               
        //                for (OrmSelectionItem o : osil) {
        //                    System.out.println(o);
        //
        //                }
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //            //sample code
        //        //-----------------------------------------------------------------
        //
        //        //-----------------------------------------------------------------
        //        //select hall prices from menu Id
        //        home.createTileButton("Hall",
        //                TileColors.TILE_COLOR_CYAN,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                MenuDao m = new MenuDao(MenuType.BANQUET); //must be banquet
        //                List<OrmHallPrice> mm = m.selectAllHallPrices("BNM0001");
        //                for (OrmHallPrice hp : mm) {
        //                    System.out.println(hp.toPrintableString());
        //                }
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //            //-----------------------------------------------------------------
        //
        //        //-----------------------------------------------------------------
        //        //select a food from it's id
        //        home.createTileButton("Food",
        //                TileColors.TILE_COLOR_DARKCYAN,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                //omly ala carte give you service and price
        //                MenuDao m = new MenuDao(MenuType.ALA_CARTE);
        //                System.out.println(m.selectFood("ALF0002").
        //                        toPrintableString());
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //            //-----------------------------------------------------------------   
        //
        //        //-----------------------------------------------------------------
        //        //select a menu from it's id
        //        home.createTileButton("Menu\nSelect",
        //                TileColors.TILE_COLOR_DARKORANGE,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                MenuDao m = new MenuDao(MenuType.RESERVATION);
        //                //only reservation gives you service and price
        //                System.out.println(m.selectMenu("RSM0001").
        //                        toPrintableString());
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //            //-----------------------------------------------------------------  
        //
        //        //-----------------------------------------------------------------
        //        //ala carte menu selection test
        //        home.createTileButton("Ala Carte\nMenu\nSelect",
        //                TileColors.TILE_COLOR_DEEPPINK,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                MenuUiFacade menu = new MenuUiFacade(MenuType.ALA_CARTE,
        //                        "SID0001", "event");
        //                menu.launchMenuSelection(stage);
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //            //-----------------------------------------------------------------  
        //
        //        //-----------------------------------------------------------------
        //        //select food's ingredients + amounts
        //        home.createTileButton("Food\nIngredients",
        //                TileColors.TILE_COLOR_TURQUOISE,
        //                "/com/saiton/ihm/res/img-user.png", FxHome.HOME_PAGE,
        //                e -> {
        //            try {
        //                MenuDao m = new MenuDao(MenuType.ALA_CARTE);
        //                List<OrmIngredient> i = m.selectAllIngredients(
        //                        "ALF0001");
        //                for (OrmIngredient o : i) {
        //                    System.out.println(o.toPrintableString());
        //                }
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //        //-----------------------------------------------------------------  
        //            [used]home.createTileButton("UI Title Here", TileColors.TILE_COLOR_CYAN, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            [used]home.createTileButton("UI Title Here", TileColors.TILE_COLOR_DARKCYAN, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            [used]home.createTileButton("UI Title Here", TileColors.TILE_COLOR_DARKORANGE, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_DEEPPINK, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_DODGERBLUE, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_GOLD, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_LAWNGREEN, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_MAGENTA, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_SPRINGGREEN, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_YELLOW, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_PLUM, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_TURQUOISE, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_PALETURQUOISE, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_PALEGREEN, "/com/saiton/ihm/res/img-hall.png", FxHome.HOME_PAGE, null);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Registration">
        home.createTileButton("Invoice",
                TileColors.TILE_COLOR_AQUAMARINE,
                "/com/saiton/ccs/res/img-external-return-note-overview.png", FxHome.SALES,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/sales/Invoice.fxml",
                        "Invoice"));
        
         home.createTileButton("Service \nRegistration",
         TileColors.TILE_COLOR_BLUEVIOLET,
         "/com/saiton/ccs/res/img-stock_approve.png", FxHome.SALES,
         e -> FxmlUiLauncher.launchOnNewStage(
         "/com/saiton/ccs/sales/Services.fxml",
                 "Service Registration"));
         
//         home.createTileButton("Banquet\nKitchen \nOrder Ticket",
//         TileColors.TILE_COLOR_CORAL,
//         "/com/saiton/ihm/res/img-kot.png", FxHome.SALES,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ihm/banquet/KOT.fxml",
//         "Kitchen Order Ticket"));
//         home.createTileButton("Extra \nKitchen \nOrder Ticket",
//         TileColors.TILE_COLOR_CYAN,
//         "/com/saiton/ihm/res/img-extraKot.png", FxHome.SALES,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ihm/banquet/ExtraKOT.fxml",
//         "Extra Kitchen Order Ticket"));
//         home.createTileButton("Extra \nBar \nOrder Ticket",
//         TileColors.TILE_COLOR_DARKCYAN,
//         "/com/saiton/ihm/res/img-extraBot.png", FxHome.SALES,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ihm/banquet/ExtraBOT.fxml",
//         "Extra Bar Order Ticket"));
//         home.createTileButton("Function \nRegistration",
//         TileColors.TILE_COLOR_DARKORANGE,
//         "/com/saiton/ihm/res/img-functionRegistration.png",
//         FxHome.SALES,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ihm/banquet/FunctionRegistration.fxml",
//         "Function Registration"));
//         home.createTileButton("Function \nSheet",
//         TileColors.TILE_COLOR_DEEPPINK,
//         "/com/saiton/ihm/res/img-function-sheet.png",
//         FxHome.SALES,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ihm/banquet/FunctionSheet.fxml",
//         "Function Sheet"));
//         home.createTileButton("Banquet \nMaster Bill",
//         TileColors.TILE_COLOR_DODGERBLUE,
//         "/com/saiton/ihm/res/img-banquet-master-bill.png",
//         FxHome.SALES,
//         e -> FxmlUiLauncher.launchOnNewStage(
//         "/com/saiton/ihm/banquet/MasterBill.fxml",
//         "Banquet Master Bill"));

         //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_DODGERBLUE, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_GOLD, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_LAWNGREEN, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_MAGENTA, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_SPRINGGREEN, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_YELLOW, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_PLUM, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_TURQUOISE, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_PALETURQUOISE, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //            home.createTileButton("UI Title Here", TileColors.TILE_COLOR_PALEGREEN, "/com/saiton/ihm/res/img-hall.png", FxHome.SALES, null);
        //</editor-fold>  
        //<editor-fold defaultstate="collapsed" desc="Settings">
        home.createTileButton("Invoice \nSettings",
                TileColors.TILE_COLOR_CYAN,
                "/com/saiton/ccs/res/img-invoice-settings.png",
                FxHome.SETTINGS_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/settings/InvoiceSettings.fxml",
                        "Invoice Settings"));
         //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Stock">


        home.createTileButton("Item \nRegistration",
                TileColors.TILE_COLOR_DARKCYAN,
                "/com/saiton/ccs/res/img-item.png",
                FxHome.STOCK_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/Items.fxml", "Item Registration"));


        home.createTileButton("Supplier\nRegistration",
                TileColors.TILE_COLOR_DODGERBLUE,
                "/com/saiton/ccs/res/img-supplier-registration.png",
                FxHome.STOCK_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/SupplierRegistration.fxml",
                        "Supplier Registration"));

         home.createTileButton("Purchase \nOrder \nOverview",
         TileColors.TILE_COLOR_LAWNGREEN,
         "/com/saiton/ccs/res/img-internal-return-note-overview.png",
         FxHome.STOCK_PAGE,
         e -> FxmlUiLauncher.launchOnNewStage(
         "/com/saiton/ccs/stock/PurchaseOrderOverView.fxml",
         "Purchase Order Overview"));
         
        home.createTileButton("External\nGRN",
                TileColors.TILE_COLOR_MAGENTA,
                "/com/saiton/ccs/res/img-external-grn.png",
                FxHome.STOCK_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/GRNExternal.fxml",
                        "External GRN"));

        home.createTileButton("External\nGRN\nOverview",
                TileColors.TILE_COLOR_PALEGREEN,
                "/com/saiton/ccs/res/img-external-grn-overview.png",
                FxHome.STOCK_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/GRNExternalOverView.fxml",
                        "External GRN Overview"));

        home.createTileButton("Purchase\nOrder",
                TileColors.TILE_COLOR_PALETURQUOISE,
                "/com/saiton/ccs/res/img-external-grn-overview.png",
                FxHome.STOCK_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/PurchaseOrder.fxml",
                        "Purchase Order"));

        home.createTileButton("External\nReturn Note",
                TileColors.TILE_COLOR_PLUM,
                "/com/saiton/ccs/res/img-external-return-note.png",
                FxHome.STOCK_PAGE,
                e -> FxmlUiLauncher.launchOnNewStage(
                        "/com/saiton/ccs/stock/ExternalReturnNote.fxml",
                        "External Return Note"));
         
         home.createTileButton("External \nReturn Note \nOverview",
         TileColors.TILE_COLOR_SPRINGGREEN,
         "/com/saiton/ccs/res/img-external-return-note-overview.png",
         FxHome.STOCK_PAGE,
         e -> FxmlUiLauncher.launchOnNewStage(
         "/com/saiton/ccs/stock/ExternalReturnNoteOverView.fxml",
         "External Return Note Overview"));


        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Reports">
    
          



//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Left Bar">
        UiPageManager manager = home.getPageManager();
        manager.registerUiPage(ConcreteUiPageManager.buildUiPage(FxmlUiLauncher.
                FxmlToNode("/com/saiton/ccs/base/Notify.fxml", stage), "notify"));

        ArrayList<UiLink> links = new ArrayList<>();
        links.add(UiLinkFactory.createUiLink("Home",
                "/com/saiton/ccs/res/img-leftnav-home.png", fxHome -> {
            fxHome.getPageManager().navigateTo(FxHome.HOME_PAGE);
        }));

        links.add(UiLinkFactory.createUiLink("Registration",
                "/com/saiton/ccs/res/img-banquet.png", fxHome -> {
            fxHome.getPageManager().navigateTo(FxHome.SALES);
        }));



//        links.add(UiLinkFactory.createUiLink("Time Management",
//                "/com/saiton/ccs/res/img-stock-navi.png", fxHome -> {
//            fxHome.getPageManager().navigateTo(FxHome.STOCK_PAGE);
//        }));
        
                links.add(UiLinkFactory.createUiLink("Settings",
                "/com/saiton/ccs/res/img-alacarte.png", fxHome -> {
            fxHome.getPageManager().navigateTo(FxHome.SETTINGS_PAGE);
        }));
        
//        links.add(UiLinkFactory.createUiLink("Reports",
//                "/com/saiton/ccs/res/img-report.png", fxHome -> {
//            fxHome.getPageManager().navigateTo(FxHome.REPORT_PAGE);
//        }));

        links.add(UiLinkFactory.createUiLink("Notifications",
                "/com/saiton/ccs/res/img-leftnav-notifications.png", fxHome
                -> {
            fxHome.getPageManager().navigateTo("notify");
        }));
        links.add(UiLinkFactory.createUiLink("Logout",
                "/com/saiton/ccs/res/img-leftnav-logout.png", fxHome
                -> {
            UserSession.getInstance().logout();
            startLoginUi();
        }));
        home.createUiLinks(links);
//</editor-fold>

        home.start();
    }

    private void createLoginUi() {
        if (ApplicationProperties.getInstance().isFingerAvailable()) {
            login = FxmlUiLauncher.
                    FxmlToNode("/com/saiton/ccs/base/Login.fxml",
                            stage, new Object[]{this});
        } else {
            login = FxmlUiLauncher.FxmlToNode(
                    "/com/saiton/ccs/base/LoginNoFinger.fxml",
                    stage, new Object[]{this});
        }

    }

    @Override
    public void startHomeUi() {
        FxmlUiLauncher.launchContentReplacement(home,
                "CCS: Dashboard", stage, null,
                scene -> {
            scene.lookup("#StageMenu").setVisible(false); // no icon on left
            scene.lookup("#TitleLabel").setVisible(false); // no title                
        });

        stage.centerOnScreen();
    }

    @Override
    public void onLoginSuccess() {
        home.hideAllTiles();
        HashMap<TileDescription, Button> map = home.getTilesMap();

        // during development 
        //comment the "permission based version" and keep "show all"
        //show tiles based on permission
        //--------------------------------------------
        map.keySet().forEach((TileDescription key) -> {
            UserPermission permission = UserSession.getInstance().
                    findPermission(key.getTitle());
            if (permission != null && (permission.canView() || permission.
                    canInsert())) {
                Button btn = map.get(key);
                if (btn != null) {
                    home.addButton(key.getPage(), btn);
                    btn.setVisible(true);
                    btn.setDisable(false);
                }
            }
        });
//        //---------------------------------------

        //-----------------------------------
        //show all 
//        map.keySet().forEach((TileDescription key) -> {
//            Button btn = map.get(key);
//            home.addButton(key.getPage(), btn);
//            btn.setVisible(true);
//            btn.setDisable(false);
//        });
        //-------------------
        NotificationFacade.showNotifications();
    }

    @Override
    public void startLoginUi() {
        FxmlUiLauncher.launchContentReplacement(login, "Login", stage);
        if (loginAccess != null) {
            loginAccess.init();
        }

        stage.centerOnScreen();
    }

    @Override
    public void setLoginAccess(LoginAccess access) {
        loginAccess = access;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
