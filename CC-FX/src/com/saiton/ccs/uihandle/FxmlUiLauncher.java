package com.saiton.ccs.uihandle;

import insidefx.undecorator.Undecorator;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * simplify UI launching (to be used with Un-decorator)
 *
 * @author Saitonya Perera
 */
public class FxmlUiLauncher {

    //=========================================================================
    //          Internal Classes,Interfaces
    //=========================================================================
    /**
     * A class that holds return information
     */
    public static class FxmlLaunchInfo {

        private final boolean success;
        private final Stage stage;
        private final Scene scene;

        /**
         * constructor
         *
         * @param success is Ui launch success
         * @param stage related stage
         * @param scene related scene
         */
        public FxmlLaunchInfo(boolean success, Stage stage, Scene scene) {
            this.success = success;
            this.stage = stage;
            this.scene = scene;
        }

        /**
         * is ui launch success
         *
         * @return true if success else false
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * @return stage object or null
         */
        public Stage getStage() {
            return stage;
        }

        /**
         *
         * @return scene object or null
         */
        public Scene getScene() {
            return scene;
        }
    }

    /**
     * Use this to apply a modification to the scene
     */
    public static interface ApplySceneModification {

        /**
         * implement this to modify the scene ex : hide elements.
         *
         * @param scene related scene object
         */
        public void modifyScene(Scene scene);
    }

    //=========================================================================
    //          Fxml based UI launching : Content replacement
    //=========================================================================
    /**
     * A function to launch a FXML based UI, by replacing the content of the
     * current stage if the controller implements the StagePassable then the
     * controller's setStage function will be called
     *
     * @param FXML path to the FXML
     * @param windowTitle UI window title
     * @param stage stage
     * @param parameters parameters
     *
     * @return true if success
     */
    public static boolean launchContentReplacement(String FXML,
            String windowTitle, Stage stage, Object[] parameters) {

        if (FXML == null || windowTitle == null || stage == null) {
            return false;
        }
        try {
            stage.setTitle(windowTitle);
            stage.setResizable(false);

            Region root;
            FXMLLoader loader = new FXMLLoader(FxmlUiLauncher.class.getResource(
                    FXML));
            root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof StagePassable) {
                StagePassable s = (StagePassable) controller;
                s.setStage(stage, parameters);
            }
            Undecorator u = new Undecorator(stage, root);
            u.getStylesheets().add("skin/undecorator.css");

            Scene scene = new Scene(u);
            scene.lookup("#StageMenu").setVisible(false);
            scene.setFill(Color.TRANSPARENT); //transparency for Undecorator

            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * A function to launch a FXML based UI, by replacing the content of the
     * current stage if the controller implements the StagePassable then the
     * controller's setStage function will be called
     *
     * @param FXML path to the FXML
     * @param windowTitle UI window title
     * @param stage stage
     * @return true if success
     */
    public static boolean launchContentReplacement(String FXML,
            String windowTitle, Stage stage) {
        return launchContentReplacement(FXML, windowTitle, stage, null);
    }

    //=========================================================================
    //          Fxml based UI launching : new stage
    //=========================================================================
    /**
     * A function to launch a FXML based UI, by creating a new stage if the
     * controller implements the StagePassable then the controller's setStage
     * function will be called
     *
     * @param FXML path to the FXML
     * @param windowTitle UI window title
     * @param parameters parameters
     * @return true if success
     */
    public static boolean launchOnNewStage(String FXML, String windowTitle,
            Object[] parameters) {
        Stage stage = new Stage();

        stage.initStyle(StageStyle.TRANSPARENT);
        // this is needed for the Undecorator
        boolean ret = launchContentReplacement(FXML, windowTitle, stage,
                parameters);

        if (ret) {
            stage.show();
        }
        return ret;
    }
    public static boolean launchOnNewStageWait(String FXML, String windowTitle,
            Object[] parameters) {
        Stage stage = new Stage();

        stage.initStyle(StageStyle.TRANSPARENT);
        // this is needed for the Undecorator
        boolean ret = launchContentReplacement(FXML, windowTitle, stage,
                parameters);

        if (ret) {
            stage.showAndWait();
        }
        return ret;
    }
    public static FxmlLaunchInfo launchOnNewStage(Node node, String windowTitle,
            Object[] parameters) {
        Stage stage = new Stage();

        stage.initStyle(StageStyle.TRANSPARENT);
        // this is needed for the Undecorator
        FxmlLaunchInfo ret = launchContentReplacement(node, windowTitle, stage);

        if (ret.isSuccess()) {
            stage.show();
        }
        return ret;
    }

  
    /**
     * A function to launch a FXML based UI, by creating a new stage if the
     * controller implements the StagePassable then the controller's setStage
     * function will be called
     *
     * @param FXML path to the FXML
     * @param windowTitle UI window title
     * @return true if success
     */
    public static boolean launchOnNewStage(String FXML, String windowTitle) {
        return launchOnNewStage(FXML, windowTitle, null);
    }

    //=========================================================================
    //          Fxml based UI launching : new stage modal
    //=========================================================================
    /**
     * A function to launch a FXML based UI, by creating a new stage if the
     * controller implements the StagePassable then the controller's setStage
     * function will be called
     *
     * @param parent
     * @param FXML path to the FXML
     * @param windowTitle UI window title
     * @param parameters parameters
     * @return true if success
     */
    public static boolean launchOnNewStageModal(Stage parent, String FXML,
            String windowTitle,
            Object[] parameters) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent);
        // this is needed for the Undecorator
        boolean ret = launchContentReplacement(FXML, windowTitle, stage,
                parameters);

        if (ret) {
            stage.showAndWait();
        }
        return ret;
    }

    /**
     * A function to launch a FXML based UI, by creating a new stage if the
     * controller implements the StagePassable then the controller's setStage
     * function will be called
     *
     * @param parent
     * @param FXML path to the FXML
     * @param windowTitle UI window title
     * @return true if success
     */
    public static boolean launchOnNewStageModal(Stage parent, String FXML,
            String windowTitle) {
        return launchOnNewStageModal(parent, FXML, windowTitle, null);
    }

    //=========================================================================
    //          Node based Ui Launching : content replace
    //=========================================================================
    /**
     * Set the scene using a node
     *
     * @param Ui Node object
     * @param windowTitle window title
     * @param stage stage to use
     * @param parameters parameters to pass
     * @param asm {@link ApplySceneModification} object : modify the scene
     * before setting it to the stage
     *
     * @return {@link FxmlLaunchInfo} object
     */
    public static FxmlLaunchInfo launchContentReplacement(Node Ui,
            String windowTitle, Stage stage, Object[] parameters,
            ApplySceneModification asm) {

        if (Ui != null && windowTitle != null && stage != null) {

            try {
                stage.setTitle(windowTitle);
                stage.setResizable(false);
                if (Ui instanceof StagePassable) {
                    StagePassable s = (StagePassable) Ui;
                    s.setStage(stage, parameters);
                }

                Undecorator u = new Undecorator(stage, (Region) Ui);
                u.getStylesheets().add("skin/undecorator.css");
                Scene scene = new Scene(u);
                scene.lookup("#StageMenu").setVisible(false);
                scene.setFill(Color.TRANSPARENT);
//                Ui.setStyle("-fx-background-color: #cccccc ");
                

                if (asm != null) {
                    asm.modifyScene(scene);
                }

                stage.setScene(scene);

                return new FxmlLaunchInfo(true, stage, scene);

            } catch (Exception ex) {
                //failed : returns false for success
            }
        }
        return new FxmlLaunchInfo(false, stage, null);
    }

    /**
     * Set the scene using a node
     *
     * @param Ui Node object
     * @param windowTitle window title
     * @param stage stage to use
     * @return {@link FxmlLaunchInfo} object
     */
    public static FxmlLaunchInfo launchContentReplacement(Node Ui,
            String windowTitle, Stage stage) {

        return launchContentReplacement(Ui, windowTitle, stage, null, null);
    }

    //=========================================================================
    //              Create Node from FXML with stage,parameter passing 
    //=========================================================================
    /**
     * Create a node from an FXML
     *
     * @param FXML FXML path
     * @param stage stage to be passed to the controller (optional,can be null)
     * @param parameters parameters to be passed to the controller (optional,can
     * be null)
     *
     * @return Created node or null
     */
    public static Node FxmlToNode(String FXML, Stage stage, Object[] parameters) {

        if (FXML != null) {

            try {

                Region root;
                FXMLLoader loader = new FXMLLoader(FxmlUiLauncher.class.
                        getResource(
                                FXML));
                root = loader.load();
                Object controller = loader.getController();
                if (controller instanceof StagePassable) {
                    StagePassable s = (StagePassable) controller;
                    s.setStage(stage, parameters);
                }
                return root;

            } catch (IOException ex) {
                //failed : returns null
            }
        }
        return null;
    }

    /**
     * Create a node from an FXML
     *
     * @param FXML FXML path
     * @return Created node or null
     */
    public static Node FxmlToNode(String FXML) {
        return FxmlToNode(FXML, null, null);
    }

    /**
     * Create a node from an FXML
     *
     * @param FXML FXML path
     * @param stage stage to be passed to the controller (optional,can be null)
     *
     * @return Created node or null
     */
    public static Node FxmlToNode(String FXML, Stage stage) {
        return FxmlToNode(FXML, stage, null);
    }

    /**
     * Create a node from an FXML
     *
     * @param FXML FXML path
     * @param parameters parameters to be passed to the controller (optional,can
     * be null)
     * @return Created node or null
     */
    public static Node FxmlToNode(String FXML, Object[] parameters) {
        return FxmlToNode(FXML, null, parameters);
    }
}
