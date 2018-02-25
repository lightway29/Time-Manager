package com.saiton.ccs.ui;

import javafx.stage.Stage;

/**
 *
 * @author Bhathi
 */
public interface StagePassable {
    /**
     * Set the stage and other parameters to the controller
     * @param stage stage to pass
     * @param obj parameter array
     */
    public void setStage(Stage stage,Object[] obj);
}
