package com.saiton.ccs.ui.fxhome;

import javafx.scene.layout.AnchorPane;

/**
 * Page Manager Interface, register and navigate to pages
 *
 * @author Saiton
 */
public interface UiPageManager {
    
    public boolean initPageManager(AnchorPane parentNode);

    public boolean registerStartupUiPage(UiPage page);
    
    public boolean registerUiPage(UiPage page);

    public boolean navigateTo(String uiName);
 
    public boolean start();
}
