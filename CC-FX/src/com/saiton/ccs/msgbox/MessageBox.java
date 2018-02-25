package com.saiton.ccs.msgbox;

import javafx.stage.Stage;

/**
 *
 * @author Saiton
 */
public interface MessageBox {
    public static enum MessageOutput{
        MSG_OK,MSG_YES,MSG_NO,MSG_CANCEL
    }
    public static enum MessageIcon{
        MSG_ICON_SUCCESS,MSG_ICON_FAIL,MSG_ICON_NONE
    }
    public static enum MessageType{
        MSG_OK,MSG_OKCANCEL,MSG_YESNO,MSG_YESNOCANCEL
    }
    
    /**
     * show a simple OK MessageBox
     * @param stage JavaFX Stage
     * @param text message
     * @param title title
     */
    void ShowMessage(Stage stage,String text, String title);

    /**
     * show a MessageBox
     * @param stage JavaFX Stage
     * @param text message
     * @param title title
     * @param icon Icon to display (MSG_ICON_SUCCESS,MSG_ICON_FAIL,MSG_ICON_NONE)
     * @param type message type (MSG_OK,MSG_OKCANCEL,MSG_YESNO,MSG_YESNOCANCEL)
     * @return selected option (MSG_OK,MSG_YES,MSG_NO,MSG_CANCEL)
     */
    MessageOutput ShowMessage(Stage stage,String text, String title, MessageIcon icon, MessageType type);
}
