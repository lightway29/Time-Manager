/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.uihandle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ComponentControl {

    public void activateSearch(TextField textField, Button button) {

        button.setVisible(true);
        button.setMinWidth(36.9998779296875);
        button.setPrefWidth(36.9998779296875);
        textField.setPrefWidth(158);

    }

    public void activateSearch(TextField textField, Button button,
            Double textFieldPreferedSize, Double buttonPreferedSize) {

        button.setVisible(true);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
        textField.setPrefWidth(textFieldPreferedSize);

    }

    public void deactivateSearch(TextField textField, Button button) {

        button.setVisible(false);
        button.setMinWidth(0);
        button.setPrefWidth(0);
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.setPrefWidth(197);

    }

    public void deactivateSearch(Label label, TextField textField, Button button,
            Double textFieldPreferedSize, Double buttonPreferedSize) {

        button.setVisible(false);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.setPrefWidth(textFieldPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, 5));
        textField.setDisable(true);

    }

      public void deactivateSearch(Label label, TextArea textArea, Button button,
            Double textFieldPreferedSize, Double buttonPreferedSize) {

        button.setVisible(false);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
//        textArea.setAlignment(Pos.CENTER_LEFT);
        textArea.setPrefWidth(textFieldPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, 5));
        textArea.setDisable(true);

    }
    
    public void deactivateSearch(CheckBox checkbox, TextField textField, Button button,
            Double textFieldPreferedSize, Double buttonPreferedSize) {

        button.setVisible(false);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.setPrefWidth(textFieldPreferedSize);
        HBox h = new HBox();
        h.setMargin(checkbox, new Insets(0, 0, 0, 5));
        textField.setDisable(true);

    }

    public void deactiveSearchButton(Label label, TextField textField, Button button,
            Double textFieldPreferedSize, Double buttonPreferedSize) {

        button.setVisible(false);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.setPrefWidth(textFieldPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, 5));
        textField.setDisable(false);

    }

    public void controlCComboBox(Label label, ComboBox<?> combo, Button button,
            Double ComboBoxPreferedSize, Double buttonPreferedSize,
            boolean componentState) {

        button.setVisible(componentState);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
        combo.setPrefWidth(ComboBoxPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, 5));
        combo.setDisable(componentState);
    }

    public void deactivateComboBoxWithTwoButtons(Label label, ComboBox<?> combo,
            Button button1, Button button2,
            Double ComboBoxPreferedSize, Double buttonPreferedSize,
            boolean componentState) {

        button1.setVisible(componentState);
        button1.setMinWidth(buttonPreferedSize);
        button1.setPrefWidth(buttonPreferedSize);

        button2.setVisible(componentState);
        button2.setMinWidth(buttonPreferedSize);
        button2.setPrefWidth(buttonPreferedSize);

        combo.setPrefWidth(ComboBoxPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, 5));
        combo.setDisable(componentState);
    }

    
    public void deactivateTextFieldWithTwoButtons(Label label, TextField textField,
            Button button1, Button button2,
            Double TextFieldBoxPreferedSize, Double buttonPreferedSize,Double labelMargin,Double textMargin,
            boolean componentState) {

        button1.setVisible(componentState);
        button1.setMinWidth(buttonPreferedSize);
        button1.setPrefWidth(buttonPreferedSize);

        button2.setVisible(componentState);
        button2.setMinWidth(buttonPreferedSize);
        button2.setPrefWidth(buttonPreferedSize);

        textField.setPrefWidth(TextFieldBoxPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, labelMargin));
        h.setMargin(textField, new Insets(0, 0, 0, textMargin));
        h.setMargin(button1, new Insets(0, 0, 0, 0));
        h.setMargin(button2, new Insets(0, 0, 0, 0));

        textField.setDisable(componentState);
    }

    public void activateSearch(Label label, TextField textField, Button button,
            Double textFieldPreferedSize, Double buttonPreferedSize) {

        button.setVisible(true);
        button.setMinWidth(buttonPreferedSize);
        button.setPrefWidth(buttonPreferedSize);
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.setPrefWidth(textFieldPreferedSize);
        HBox h = new HBox();
        h.setMargin(label, new Insets(0, 0, 0, 0));
        textField.setDisable(false);

    }

}
