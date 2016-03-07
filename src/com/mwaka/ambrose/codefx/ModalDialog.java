/*
 MWAKA AMBROSE LISCENSCE AGREEMENT VERSION 1.0.0.Jan.2016
 (c) 2016 Mwaka Ambrose ambrosemwaka@gmail.com
 =============================================================================
 This program is free software; you can redistribute it and/or
 modify it as long as you respect and stick to the
 agreements stated here in.

 (1) - In every modification, keep this liscensce agreement
 intact so that the user of your software knows thier rights.

 (2) - Its free for learning purposes. As long as you don't
 earn from the code written through it. Commercial use is therefore restricted and
 prohibited without any prior consultation and written permission from the author.
 Contact the author at the email specified above.

 (3) - Under no circumstances shall this software be implicated to
 any harm caused by the modified code or any dirivative of this software. 
 The authors of those versions shall be held accountable for thier own versons 
 of the software. It is also otherwise encouraged to credit the author from which
 any dirivative of your code is based upon, but not in the context of maketing and or 
 any selfish gains.

 (4) - Modification of this liscense agreememt is a direct violation of
 agreement (1) and is highly discouraged, incase of any interest in modification, 
 contact the author of the agreement at the email address specified above.

 (5) - This software is distributed in the hope that it will be useful, 
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR THE PARTICULAR PURPOSE ITS DESIGNED FOR.

 THANK YOU AND HAPPY PROGRAMMING

 Author, Mwaka Ambrose.

 */
package com.mwaka.ambrose.codefx;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This used as a confirmation dialog to some tree cell item actions
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class ModalDialog {

    /**
     * Creates and show a confirmation dialog
     *
     * @param owner dialog owner
     * @param treeItem tree item that the action is going to be performed on
     * @param prop tree item object property.
     */
    public ModalDialog(Stage owner, final TreeItem<PathItem> treeItem, final ObjectProperty<TreeItem<PathItem>> prop) {
        final Stage dialog = new Stage(StageStyle.UTILITY);
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        root.setPadding(new Insets(30));
        root.setHgap(5);
        root.setVgap(10);
        Label label = new Label("Are you sure?");
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            prop.set(treeItem);
            dialog.hide();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            dialog.hide();
        });
        root.add(label, 0, 0, 2, 1);
        root.addRow(1, okButton, cancelButton);
        dialog.setScene(new Scene(root, 300, 100));
        dialog.show();
    }
}
