package com.saiton.ccs.base;

import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Saiton
 */
public class SetCurrencyRateLauncher extends Application {

    private static final String LAUNCHER_FXML = "/com/saiton/ihm/report/SetCurrencyRate.fxml";
    private static final String TITLE = "Set Currency Rate" ;
    @Override
    public void start(Stage stage) throws Exception {
        Region root = FXMLLoader.load(getClass().getResource(LAUNCHER_FXML));
        Undecorator u = new Undecorator(stage, root);
        u.getStylesheets().add("skin/undecorator.css");

        Scene scene = new Scene(u);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

}
