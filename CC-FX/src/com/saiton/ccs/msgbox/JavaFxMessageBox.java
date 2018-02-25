package com.saiton.ccs.msgbox;



import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
//import static org.controlsfx.dialog.Dialog.Actions.CANCEL;
//import static org.controlsfx.dialog.Dialog.Actions.NO;
//import static org.controlsfx.dialog.Dialog.Actions.OK;
//import static org.controlsfx.dialog.Dialog.Actions.YES;
//import org.controlsfx.dialog.Dialogs;
import static org.controlsfx.dialog.Dialog.ACTION_CANCEL;
import static org.controlsfx.dialog.Dialog.ACTION_NO;
import static org.controlsfx.dialog.Dialog.ACTION_OK;
import static org.controlsfx.dialog.Dialog.ACTION_YES;


import org.controlsfx.dialog.Dialogs;


/**
 *
 * @author Saiton
 */
public class JavaFxMessageBox implements MessageBox {

    @Override
    public void ShowMessage(Stage stage, String text, String title) {
        ShowMessage(stage,text,title,MessageIcon.MSG_ICON_NONE,MessageType.MSG_OK);
    }

    @Override
    public MessageOutput ShowMessage(Stage stage, String text, String title, MessageIcon icon, MessageType type) {
        Action[] actionsArray;

        switch (type) {
            case MSG_OKCANCEL:
                actionsArray = new Action[]{ACTION_OK, ACTION_CANCEL};
                break;
            case MSG_YESNO:
                actionsArray = new Action[]{ACTION_YES, ACTION_NO};
                break;
            case MSG_YESNOCANCEL:
                actionsArray = new Action[]{ACTION_YES, ACTION_NO, ACTION_CANCEL};
                break;
            default:
                actionsArray = new Action[]{ACTION_OK};

        }
        
        
        
         
//        switch (type) {
//            case MSG_OKCANCEL:
//                actionsArray = new Action[]{OK, CANCEL};
//                break;
//            case MSG_YESNO:
//                actionsArray = new Action[]{YES, NO};
//                break;
//            case MSG_YESNOCANCEL:
//                actionsArray = new Action[]{YES, NO, CANCEL};
//                break;
//            default:
//                actionsArray = new Action[]{OK};
//
//        }
        
        Action response;
        Dialogs msg = Dialogs.create()
                .title(title)
                .message(text)
                .actions(actionsArray).owner(stage);

        switch (icon) {
            case MSG_ICON_SUCCESS:
                response = msg.showConfirm();
                break;
            case MSG_ICON_FAIL:
                response = msg.showError();
                break;
            default:
                response = msg.showWarning();
        }

        MessageOutput msgValue;
        
        if (response == ACTION_OK) {
            msgValue = MessageOutput.MSG_OK;
        } else if (response == ACTION_YES) {
            msgValue = MessageOutput.MSG_YES;
        } else if (response == ACTION_NO) {
            msgValue = MessageOutput.MSG_NO;
        } else {
            msgValue = MessageOutput.MSG_CANCEL;
        }
        
        
//          if (response == OK) {
//            msgValue = MessageOutput.MSG_OK;
//        } else if (response == YES) {
//            msgValue = MessageOutput.MSG_YES;
//        } else if (response == NO) {
//            msgValue = MessageOutput.MSG_NO;
//        } else {
//            msgValue = MessageOutput.MSG_CANCEL;
//        }

        return msgValue;

    }
}
