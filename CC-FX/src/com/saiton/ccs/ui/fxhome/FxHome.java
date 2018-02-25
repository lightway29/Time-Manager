package com.saiton.ccs.ui.fxhome;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * A home UI for JavaFX (handwritten !)
 *
 * @author Saiton
 */
public class FxHome extends AnchorPane {

    //-----------------------------------------------------------
    public static final double HOME_MIN_WIDTH = 1000.0;
    public static final double HOME_MIN_HEIGHT = 650.0;
    public static final double LEFT_MIN_WIDTH = 200.0;
    public static final double LEFT_MIN_HEIGHT = 650.0;
    public static final double CENTER_PANE_PADDING = 30.0;
    public static final double TILE_BUTTON_SIZE = 128.0;
    public static final double STANDARD_GAP = 5.0;
    public static final Duration SMALL_DURATION = Duration.seconds(0.3);
    public static final String HOME_PAGE = "home";
    public static final String SALES = "sales";
    public static final String SETTINGS_PAGE = "settings";
    public static final String ALACARTE_PAGE = "alacarte";
    public static final String STOCK_PAGE = "stock";
    public static final String NOTIFICATION_PAGE = "notification";
//    public static final String REPORT_PAGE = "report";
    //-----------------------------------------------------------
    private final AnchorPane centerPane;
    private final AnchorPane leftPane;
    private final VBox leftContentPane;
    private final SequentialTransition stTiles;
    private final UiTitle mainTitle;
    private final UiPageManager pageManager;
    private final ArrayList<UiLink> uiLinks;

    private final TilePane homeTilePane;
    private final TilePane salesTilePane;
   // private final TilePane alacarteTilePane;
   // private final TilePane roomTilePane;
    private final TilePane stockTilePane;
private final TilePane settingsTilePane;
    
    private final HashMap<TileDescription, Button> tilesMap;

    public FxHome(UiTitle title) {
        super();

        mainTitle = title; // save title;

        getStylesheets().add("/com/saiton/ccs/res/JMetroLightTheme.css");
        //|---------------|
        //| width 1000    |
        //| height 650    |
        //|               |
        //|_______________|
        setMinSize(HOME_MIN_WIDTH, HOME_MIN_HEIGHT);
        centerPane = new AnchorPane();
        leftContentPane = new VBox();
        homeTilePane = new TilePane();
        salesTilePane = new TilePane();
//        roomTilePane = new TilePane();
//        alacarteTilePane = new TilePane();
        stockTilePane = new TilePane();
        settingsTilePane = new TilePane();
        leftPane = new AnchorPane();
        pageManager = new ConcreteUiPageManager();
        uiLinks = new ArrayList<>();

        leftPane.setMinSize(LEFT_MIN_WIDTH, LEFT_MIN_HEIGHT);
        AnchorPane.setLeftAnchor(leftPane, -7.0);
        AnchorPane.setTopAnchor(leftPane, -7.0);
        AnchorPane.setBottomAnchor(leftPane, -7.0);

        leftContentPane.setMinSize(LEFT_MIN_WIDTH, LEFT_MIN_HEIGHT);
        AnchorPane.setLeftAnchor(leftContentPane, 0.0);
        AnchorPane.setRightAnchor(leftContentPane, 0.0);
        AnchorPane.setTopAnchor(leftContentPane, 0.0);
        AnchorPane.setBottomAnchor(leftContentPane, 0.0);

        //|---|--------
        //| l | top -6  width = 250
        //| f | left -6
        //| t | bot -6
        //|___|________
        leftContentPane.setSpacing(5.0);

        //add title
        leftContentPane.getChildren().add(title.getUiTitle());

        leftPane.getStyleClass().add("dark-grey-background");
        Image img = new Image(this.getClass().getResourceAsStream(
                "/com/saiton/ccs/res/img-left-style-top.png"));
        ImageView imgV = new ImageView(img);
        AnchorPane.setLeftAnchor(imgV, 0.0);
        AnchorPane.setRightAnchor(imgV, 0.0);
        AnchorPane.setTopAnchor(imgV, 0.0);

        leftPane.getChildren().addAll(imgV, leftContentPane);
        //add tile pane to center pane
        AnchorPane.setLeftAnchor(centerPane, LEFT_MIN_WIDTH
                + CENTER_PANE_PADDING);
        AnchorPane.setRightAnchor(centerPane, CENTER_PANE_PADDING);
        AnchorPane.setTopAnchor(centerPane, CENTER_PANE_PADDING);
        AnchorPane.setBottomAnchor(centerPane, CENTER_PANE_PADDING);

        homeTilePane.setHgap(STANDARD_GAP);
        homeTilePane.setVgap(STANDARD_GAP);

        salesTilePane.setHgap(STANDARD_GAP);
        salesTilePane.setVgap(STANDARD_GAP);
//
//        roomTilePane.setHgap(STANDARD_GAP);
//        roomTilePane.setVgap(STANDARD_GAP);
//
//        alacarteTilePane.setHgap(STANDARD_GAP);
//        alacarteTilePane.setVgap(STANDARD_GAP);

        stockTilePane.setHgap(STANDARD_GAP);
        stockTilePane.setVgap(STANDARD_GAP);
        
        settingsTilePane.setHgap(STANDARD_GAP);
        settingsTilePane.setVgap(STANDARD_GAP);

        pageManager.initPageManager(centerPane);

        pageManager.registerStartupUiPage(ConcreteUiPageManager.buildUiPage(salesTilePane, SALES));
        pageManager.registerStartupUiPage(ConcreteUiPageManager.buildUiPage(settingsTilePane, SETTINGS_PAGE));
//        pageManager.registerStartupUiPage(ConcreteUiPageManager.buildUiPage(
//                alacarteTilePane, ALACARTE_PAGE));
        pageManager.registerStartupUiPage(ConcreteUiPageManager.buildUiPage(
                stockTilePane, STOCK_PAGE));
        pageManager.registerStartupUiPage(ConcreteUiPageManager.buildUiPage(
                homeTilePane, HOME_PAGE));
        // add left and center
        this.getChildren().addAll(leftPane, centerPane);

        //animations
        stTiles = new SequentialTransition();
        stTiles.setCycleCount(1);

        tilesMap = new HashMap<>();
    }

    public UiPageManager getPageManager() {
        return pageManager;
    }

//    public void createTileButton(String title, String tileStyleClass, String imgResPath, EventHandler<ActionEvent> action) {
//
//        Button btn = new Button(title);
//        btn.setMinSize(TILE_BUTTON_SIZE, TILE_BUTTON_SIZE);
//        btn.setContentDisplay(ContentDisplay.TOP);
//        //display image position is on top of text
//        btn.setGraphicTextGap(STANDARD_GAP);
//        btn.setTextAlignment(TextAlignment.CENTER);
//
//        Image img = new Image(this.getClass().getResourceAsStream(imgResPath));
//        ImageView imgV = new ImageView(img);
//
//        btn.setGraphic(imgV);
//        btn.setOnAction(action);
//        btn.getStyleClass().add(tileStyleClass);
//        homeTilePane.getChildren().add(btn); //add btn
//
//        stTiles.getChildren().add(createFadeIn(btn)); // add animation
//
//    }
    public void createTileButton(String title, String tileStyleClass,
            String imgResPath, String buttonGroup,
            EventHandler<ActionEvent> action) {

        Button btn = new Button(title);
        btn.setMinSize(TILE_BUTTON_SIZE, TILE_BUTTON_SIZE);
        btn.setContentDisplay(ContentDisplay.TOP);
        //display image position is on top of text
        btn.setGraphicTextGap(STANDARD_GAP);
        btn.setTextAlignment(TextAlignment.CENTER);

        Image img = new Image(this.getClass().getResourceAsStream(imgResPath));
        ImageView imgV = new ImageView(img);

        btn.setGraphic(imgV);
        btn.setOnAction(action);
        btn.getStyleClass().add(tileStyleClass);



        stTiles.getChildren().add(createFadeIn(btn)); // add animation

        btn.setVisible(false);
        btn.setDisable(true);

        String id = title;
        id = id.replace("\n", " ").replace("  ", " ").replace("  ", " ").trim();

        //when new button is added add it to the DB too
        // System.out.println(String.format("INSERT INTO `user_permission_type` (`type`) VALUES ('%s');",id));
        tilesMap.put(new TileDescription(buttonGroup, id), btn);
    }

    public void hideAllTiles() {
        //hide all buttons
        tilesMap.values().forEach((Button btn) -> {
            btn.setDisable(true);
            btn.setVisible(false);
        });
        //clear all tiles
        homeTilePane.getChildren().clear();
        salesTilePane.getChildren().clear();
//        roomTilePane.getChildren().clear();
//        alacarteTilePane.getChildren().clear();
        settingsTilePane.getChildren().clear();
        stockTilePane.getChildren().clear();
    }

    public void addButton(String buttonGroup,Button btn){
        switch (buttonGroup) {
            case HOME_PAGE:
                homeTilePane.getChildren().add(btn);
                break;
            case SALES:
                salesTilePane.getChildren().add(btn);
                break;
            case SETTINGS_PAGE:
                settingsTilePane.getChildren().add(btn);
                break;
//            case ALACARTE_PAGE:
//                alacarteTilePane.getChildren().add(btn);
//                break;
            case STOCK_PAGE:
                stockTilePane.getChildren().add(btn);
                break;
                
        }
    }
    public HashMap<TileDescription, Button> getTilesMap() {
        return tilesMap;
    }

    public void createUiLinks(ArrayList<UiLink> links) {
        uiLinks.addAll(links);
        uiLinks.stream().forEach((UiLink lnk) -> {
            Hyperlink h = new Hyperlink(lnk.getUiName());
            Image img = new Image(this.getClass().getResourceAsStream(lnk.
                    getUiIconResPath()));
            ImageView imgV = new ImageView(img);
            h.setGraphic(imgV);
            h.setGraphicTextGap(STANDARD_GAP);
            h.setOnAction(new EventHandlerImpl(this, lnk));
            h.setMinSize(LEFT_MIN_WIDTH, 45.0);
            h.getStyleClass().add("hyperlink-leftnav");
            h.setContentDisplay(ContentDisplay.LEFT);
            leftContentPane.getChildren().add(h);
        });
    }

    public void start() {
        // stTiles.play(); // start tiles add fade in animations 
        // this is disabled during development period
        hideAllTiles();
       // mainTitle.animate();
        pageManager.start();
    }

    private FadeTransition createFadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition(
                SMALL_DURATION, node);

        fadeTransition.setFromValue(0); // fade in effect
        fadeTransition.setToValue(1);   // 
        fadeTransition.setCycleCount(1); // once
        fadeTransition.setAutoReverse(false); // no repeat (by reversing)

        return fadeTransition;
    }

    private static class EventHandlerImpl implements EventHandler<ActionEvent> {

        private final FxHome home;
        private final UiLink link;

        public EventHandlerImpl(FxHome home, UiLink link) {
            this.home = home;
            this.link = link;
        }

        @Override
        public void handle(ActionEvent event) {
            UiLink.SimpleCallback callback = link.getCallback();
            if (callback != null) {
                callback.callback(home);
            }
        }
    }

}
