package com.saiton.ccs.util;

import java.util.Optional;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Saitonya
 */
public class InputDialog {

    public static String inputForAddNew(String title) {
        Optional<String> response;

        response = Dialogs.create().title("New " + title.toLowerCase()).
                masthead("Add new " + title.toLowerCase()).message(title).
                showTextInput();

        if (response != null && response.isPresent() && !response.get().trim().isEmpty()) {
            return response.get();
        }
        
        return null;
    }
}
