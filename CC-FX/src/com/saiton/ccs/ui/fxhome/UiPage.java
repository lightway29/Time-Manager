package com.saiton.ccs.ui.fxhome;

import javafx.scene.Node;

/**
 * An UI Page
 *
 * @author Saiton
 */
public interface UiPage {


    /**
     * 
     * @return get UI for the page 
     */
    public Node getUi();

    /**
     * 
     * @return a unique name for the page
     */
    public String getUiName();
}
