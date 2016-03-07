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
package com.mwaka.ambrose.codefx.utils;

import com.mwaka.ambrose.codefx.CodeFX;
import com.mwaka.ambrose.codefx.Editor;
import com.mwaka.ambrose.codefx.utils.projectconfigs.Configs;
import java.io.File;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the Dialog class, it creates and handles all transactions of the
 * dialogs in the whole program.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class Dialogs {

    Stage window;
    Scene scene1, scene2, scene3;
    VBox listContainer;
    String name = null;
    boolean approval = false;
    AnchorPane root, detail, scene3Root;
    HBox projectRoot;
    int projCat;
    boolean createMainM = true;
    boolean donotCreateMainM = false;
    //public TextArea console;
    static String path = "";
    static String mainclass = "";
    String fileName = "";
    static String wrkSpace = null;

    /**
     * Empty constructor. all its methods are static
     */
    public Dialogs() {

    }

    /**
     * This method creates and displays the new file dialog It handles all the
     * events associated with it.
     *
     * @return The name of the file created.
     */
    public String CreateNewFileDialog() {
        listContainer = new VBox(10);
        listContainer.setPadding(new Insets(15, 15, 15, 15));

        Label label = new Label("File Name:");
        TextField textField = new TextField();
        ChoiceBox<String> options = new ChoiceBox<>();
        options.getItems().add("Java Class");
        options.getItems().add("Java Interface");
        options.getItems().add("Text File");

        options.setValue("Java Class");

        Button okBtn = new Button("Create");
        okBtn.setOnAction((e) -> {
            if (!textField.getText().isEmpty()) {
                switch (options.getValue()) {
                    case "Java Class":
                        name = textField.getText() + ".java";
                        //perform check to see if the class name starts with
                        //an upercase letter
                        window.close();
                        break;
                    case "Text File":
                        name = textField.getText() + ".txt";
                        window.close();
                        break;
                    case "Java Interface":
                        name = textField.getText() + ".javai";
                        window.close();
                        break;
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Please provide a file name.");
                alert.setTitle("User Error.");
                //setting the alert dialog icon
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Icon.setIcon());
                alert.showAndWait();
            }
        });

        //listen for the enter key on the textfiel if its not empty.
        textField.setOnKeyReleased(ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                if (!textField.getText().isEmpty()) {
                    switch (options.getValue()) {
                        case "Java Class":
                            name = textField.getText() + ".java";
                            //perform check to see if the class name starts with
                            //an upercase letter
                            window.close();
                            break;
                        case "Text File":
                            name = textField.getText() + ".txt";
                            window.close();
                            break;
                        case "Java Interface":
                            name = textField.getText() + ".javai";
                            window.close();
                            break;
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setContentText("Please provide a file name.");
                    alert.setTitle("User Error.");
                    //setting the alert dialog icon
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Icon.setIcon());
                    alert.showAndWait();
                }

            }
        });

        listContainer.getChildren().addAll(label, textField, options, okBtn);

        window = new Stage();
        scene1 = new Scene(listContainer, 300, 150);
        window.setScene(scene1);
        window.setTitle("New file");
        window.getIcons().setAll(Icon.setIcon());
        window.setResizable(false);
        window.showAndWait();
        new CodeFX().refreshTree();
        return name;
    }

    /**
     * This method displays a confirmation dialog for saving the text in the
     * editor.
     *
     * @return true if the user chose to save and false if the user canceled or
     * closed the window.
     */
    public static boolean confirmatioDialog() {
        boolean type = false;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        //setting the alert dialog icon
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Icon.setIcon());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText("Do you want to save changes?");
        Optional<ButtonType> showAndWait = alert.showAndWait();

        if (showAndWait.isPresent()) {
            type = true;
        }

        return type;
    }

    /**
     * This method displays a confirmation dialog for saving the text in the
     * editor when the whole program is closing.
     *
     * @return true if the user chose to save and false if the user canceled or
     * closed the window.
     */
    public static boolean confirmationClosingDialog() {
        boolean type = false;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        //setting the alert dialog icon
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Icon.setIcon());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText("Do you want to save changes before\n closing?");
        Optional<ButtonType> showAndWait = alert.showAndWait();

        if (showAndWait.isPresent()) {
            type = true;
        }

        return type;
    }

    /**
     * This method is used to show the about project dialog.
     */
    public static void aboutAppDialog() {
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(new ImageView(new Image("com/mwaka/ambrose/codefx/resources/about.png")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("About CodeFX");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().setAll(Icon.setIcon());
        stage.showAndWait();
    }

    /**
     * This is an abstract method to show an information alert dialog.
     *
     * @param title of the message
     * @param info contents of the message
     */
    public static void infoDialog(String title, String info) {

        Alert alert = new Alert(AlertType.INFORMATION);
        //setting the alert dialog icon
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(Icon.setIcon());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(title);
        //alert.getDialogPane().setContentText(info);
        TextArea root = new TextArea(info);
        root.setEditable(false);
        alert.getDialogPane().setContent(root);
        Optional<ButtonType> showAndWait = alert.showAndWait();

    }

    /**
     * This method is used to create the general setting window and handles all
     * of its transactions
     */
    public static void settingsWindow() {
        //get the current workspace from the prefferances
        //save to string and pass it to the text view.
        String currentWorkDir = Configs.getWorkSpaceDir("workspace");

        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        Label label = new Label("ALL CONFIGURATIONS MADE HERE WILL BE REFLECTED THROUGHOUT THE \nAPPLICATION");
        Label line = new Label("__________________________________________________________________________________________");

        /////////////////////////////////////////////////////////////////////////////////////////////
        Label wrkSpaceDir = new Label("WorkSpace");
        TextField wrkSpaceView = new TextField(currentWorkDir);
        wrkSpaceView.setEditable(false);
        wrkSpaceView.setPrefWidth(360);
        wrkSpaceView.setEditable(false);
        Button wrkSpaceBtn = new Button("Change");
        wrkSpaceBtn.setDisable(true);

        wrkSpaceBtn.setOnAction((e) -> {
            DirectoryChooser dirChooser1 = new DirectoryChooser();
            dirChooser1.setTitle("Choose WorkSpace");
            //this will be the workspace directory.
            //dirChooser1.setInitialDirectory(new File(currentWorkDir));
            File selectedDir = dirChooser1.showDialog(stage);
            if (selectedDir != null) {
                path = selectedDir.getAbsolutePath();
            }
            wrkSpaceView.setText(path);
        });

        /////////////////////////////////////////////////////////////////////////
        String currentClass = Configs.getMainClass("mainclass");
        Label mainClassL = new Label("Main Class");
        TextField mainClassT = new TextField(currentClass);
        mainClassT.setEditable(false);
        mainClassT.setPrefWidth(360);
        mainClassT.setEditable(false);
        Button mainClassBtn = new Button("Choose");
        mainClassBtn.setDisable(true);
        mainClassBtn.setOnAction((e) -> {
            FileChooser dirChooser = new FileChooser();
            dirChooser.setTitle("Choose Main Class");
            dirChooser.getExtensionFilters().add(new ExtensionFilter("Java Files", "*.java"));
            //dirChooser.setInitialDirectory(new File(currentWorkDir));
            File selectedDir = dirChooser.showOpenDialog(stage);
            if (selectedDir != null) {
                mainclass = selectedDir.getName();
            }
            mainClassT.setText(mainclass);
        });

        ////////////////////////////////////////////////////////////////////////////
        Button okBtn = new Button("Save Settings");
        okBtn.setDisable(true);
        okBtn.setOnAction((e) -> {
            //get new configurations and save and close
            //restarting the IDE is required.
            //don't forget to implement the code here.
            Configs.setWorkSpaceDir(path);
            Configs.setMainClass(mainclass);
            stage.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction((e) -> {
            stage.close();
        });

        AnchorPane.setTopAnchor(label, 20.0);
        AnchorPane.setLeftAnchor(label, 30.0);
        AnchorPane.setRightAnchor(label, 30.0);

        AnchorPane.setTopAnchor(line, 40.0);
        AnchorPane.setLeftAnchor(line, 30.0);
        AnchorPane.setRightAnchor(line, 30.0);

        AnchorPane.setTopAnchor(wrkSpaceDir, 70.0);
        AnchorPane.setLeftAnchor(wrkSpaceDir, 30.0);

        AnchorPane.setTopAnchor(wrkSpaceView, 90.0);
        AnchorPane.setLeftAnchor(wrkSpaceView, 30.0);

        AnchorPane.setTopAnchor(wrkSpaceBtn, 90.0);
        AnchorPane.setRightAnchor(wrkSpaceBtn, 30.0);

        AnchorPane.setTopAnchor(mainClassL, 130.0);
        AnchorPane.setLeftAnchor(mainClassL, 30.0);

        AnchorPane.setTopAnchor(mainClassT, 150.0);
        AnchorPane.setLeftAnchor(mainClassT, 30.0);

        AnchorPane.setTopAnchor(mainClassBtn, 150.0);
        AnchorPane.setRightAnchor(mainClassBtn, 30.0);

        AnchorPane.setBottomAnchor(okBtn, 20.0);
        AnchorPane.setRightAnchor(okBtn, 30.0);

        AnchorPane.setBottomAnchor(cancelBtn, 20.0);
        AnchorPane.setRightAnchor(cancelBtn, 130.0);

        root.getChildren().addAll(label, line, wrkSpaceDir, wrkSpaceView, wrkSpaceBtn, mainClassL, mainClassT, mainClassBtn, okBtn, cancelBtn);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().setAll(Icon.setIcon());
        stage.showAndWait();
    }

    /**
     * Used to create fonts window and handles all the fonts settings.
     */
    public static void setFontsAndFontStyle() {
        //get the current workspace from the prefferances
        //save to string and pass it to the text view.
        String currentWorkDir = Configs.getWorkSpaceDir("workspace");

        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        Text label = new Text("Sets the fontstyle and font size of the \neditor window only.");
        label.setFont(new Font("Consolas", 15));
        label.setUnderline(true);

        Label lCurrentFontStyle = new Label("Current font Style: ");
        Label lCurrentFontStyleShow = new Label(Configs.getFontStyle("fontstyle"));
        ComboBox<String> fontlists = new ComboBox();

        String[] fonts = new FontLoader().loadFonts();
        for (String font : fonts) {
            fontlists.getItems().add(font);
        }
        //setting my favourite to be the default that appears
        fontlists.setValue(Configs.getFontStyle("fontstyle"));
        fontlists.setPrefWidth(300);
        fontlists.setEditable(false);

        Label lCurrentFontSize = new Label("Current font Size: ");
        Label lCurrentFontSizeShow = new Label("" + Configs.getFontSize("fontsize"));
        ComboBox<Integer> fontsizelists = new ComboBox();
        //setting the font size from 8 to 30
        for (int i = 8; i <= 30; i++) {
            fontsizelists.getItems().add(i);
        }

        fontsizelists.setPrefWidth(100);
        //fontsizelists.setEditable(false);
        fontsizelists.setValue(Configs.getFontSize("fontsize"));

        Button okBtn = new Button("Save Settings");
        okBtn.setOnAction((e) -> {
            //get the new font add it to the users preferrence setting
            //get the new font size and add to the users preference settings
            //close the configuration window.
            Configs.setFontStyle(fontlists.getValue());
            Configs.setFontSize(fontsizelists.getValue());
            new Editor().textArea.setFont(new java.awt.Font(Configs.getFontStyle("fontstyle"), java.awt.Font.BOLD, Configs.getFontSize("fontsize")));
            stage.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction((e) -> {
            stage.close();
        });

        AnchorPane.setTopAnchor(label, 20.0);
        AnchorPane.setLeftAnchor(label, 30.0);
        AnchorPane.setRightAnchor(label, 30.0);

        AnchorPane.setTopAnchor(lCurrentFontStyle, 70.0);
        AnchorPane.setLeftAnchor(lCurrentFontStyle, 30.0);

        AnchorPane.setTopAnchor(lCurrentFontStyleShow, 70.0);
        AnchorPane.setLeftAnchor(lCurrentFontStyleShow, 130.0);

        AnchorPane.setTopAnchor(fontlists, 90.0);
        AnchorPane.setLeftAnchor(fontlists, 30.0);

        AnchorPane.setTopAnchor(lCurrentFontSize, 130.0);
        AnchorPane.setLeftAnchor(lCurrentFontSize, 30.0);

        AnchorPane.setTopAnchor(lCurrentFontSizeShow, 130.0);
        AnchorPane.setLeftAnchor(lCurrentFontSizeShow, 130.0);

        AnchorPane.setTopAnchor(fontsizelists, 150.0);
        AnchorPane.setLeftAnchor(fontsizelists, 30.0);

        AnchorPane.setBottomAnchor(okBtn, 20.0);
        AnchorPane.setRightAnchor(okBtn, 30.0);

        AnchorPane.setBottomAnchor(cancelBtn, 20.0);
        AnchorPane.setRightAnchor(cancelBtn, 130.0);

        root.getChildren().addAll(label, lCurrentFontStyle, lCurrentFontStyleShow, fontlists, lCurrentFontSize, lCurrentFontSizeShow, fontsizelists, okBtn, cancelBtn);

        Scene scene = new Scene(root, 450, 250);
        stage.setScene(scene);
        stage.setTitle("Fonts and Sizes");
        stage.toFront();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().setAll(Icon.setIcon());
        stage.showAndWait();
    }

    /**
     * This method is used to create a dialog that only shows up on a fresh
     * install of the program, this is when no workspace is detected so it gives
     * an option of setting a workspace.
     *
     * @param stage the owner of the dialog and directory chooser dialog
     * @return the string absolute path to the directory chosen.
     */
    public static String chooseWorkspaceOnStartup(Stage stage) {
        Alert warning = new Alert(AlertType.WARNING);
        warning.setHeaderText("Warning No Workspace Found.");
        VBox root = new VBox(10);
        TextField dispField = new TextField();
        dispField.setPromptText("Workspace directory...");
        Button choose = new Button("Choose..");
        choose.setOnAction((e) -> {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Choose Workspace");
            File dirSelected = dirChooser.showDialog(stage);
            if (dirSelected == null) {
                dispField.setText("Cancelled! please choose a directory");
            } else {
                dispField.setText(dirSelected.getAbsolutePath());
                wrkSpace = dirSelected.getAbsolutePath();
            }
        });
        Label info = new Label("Please choose a directory to be your workspace. \nNote that you can change this later from Settings menu.");
        root.setPadding(new Insets(20));
        root.getChildren().addAll(info, dispField, choose);
        //setting the alert dialog icon
        ((Stage) warning.getDialogPane().getScene().getWindow()).getIcons().add(Icon.setIcon());
        warning.getDialogPane().setContent(root);
        warning.showAndWait();
        return wrkSpace;
    }
}
