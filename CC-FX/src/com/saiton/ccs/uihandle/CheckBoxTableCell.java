package com.saiton.ccs.uihandle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;

/**
 * CheckBoxTableCell
 *
 * @author Oracle Sample
 * @param <S> The type of the TableView generic type (i.e. S ==
 * TableView&lt;S&gt;). This should also match with the first generic type in
 * TableColumn.
 */
public class CheckBoxTableCell<S> extends TableCell<S, Boolean> {

    private final CheckBox checkBox;
    private ObservableValue<Boolean> booleanProperty;

    public CheckBoxTableCell() {
        this.checkBox = new CheckBox();
        this.checkBox.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        setGraphic(checkBox);
    }

    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(checkBox);
            
            if (booleanProperty instanceof BooleanProperty) {
                checkBox.selectedProperty().unbindBidirectional(
                        (BooleanProperty) booleanProperty);
            }
            ObservableValue<Boolean> b = getTableColumn().
                    getCellObservableValue(getIndex());
            if (b instanceof BooleanProperty) {
                booleanProperty = b;
                checkBox.selectedProperty().bindBidirectional(
                        (BooleanProperty) booleanProperty);
            }
            //checkBox.setSelected(item);
        }
    }


}
