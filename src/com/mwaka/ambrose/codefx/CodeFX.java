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

import com.mwaka.ambrose.cmd.Lyec;
import com.mwaka.ambrose.codefx.utils.Dialogs;
import com.mwaka.ambrose.codefx.utils.FileHelper;
import com.mwaka.ambrose.codefx.utils.Icon;
import com.mwaka.ambrose.codefx.utils.TemplateCode;
import com.mwaka.ambrose.codefx.utils.compiler.Evelyne;
import com.mwaka.ambrose.codefx.utils.projectconfigs.Configs;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;

/**
 * This is the main class, its the main entry point of the program
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class CodeFX extends Application {

    Stage window;
    private AnchorPane root;
    private SplitPane splitPane;
    Button newfile, newproject, save, output, run, compileBtn;
    private AnchorPane editor;
    private AnchorPane explorer;
    public TabPane tabPane;
    public static String fileName;
    Tab tab;
    String initJavaContent;
    private ImageView icon;
    public static Label statusLable;
    TreeView<File> treeView;
    private ExecutorService service;
    private Task watchTask;
    private TreeView<PathItem> fileTreeView;
    private Path rootPath;
    private StringProperty messageProp;
    public Stage stage = null;
    private Label watchText;
    SwingNode consoleWindow, cmdWindow;
    double width, height;

    /**
     * This is the constructor, it does nothing since there is no call this
     * class
     */
    public CodeFX() {
    }

    /**
     * This method is an override from the inherited Application class
     *
     * @param stage the primary stage reference object that will be passed from
     * the inherited class
     */
    @Override
    public void start(Stage stage) {

        width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        window = stage;
        //initialisation of the root nodes and components
        root = new AnchorPane();
        //root.getStylesheets().add(getClass().getResource("com/mwaka/ambrose/codefx/css/darktheme.css").toExternalForm());
        splitPane = new SplitPane();

        if (Configs.getWorkSpaceDir("workspace") == null) {
            Configs.setWorkSpaceDir(Dialogs.chooseWorkspaceOnStartup(window));
        }

        refreshTree();
        createMenu();
        createToolBar();
        createSplitPane();
        createBottomToolbar();
        createSplitPane();
        //statusLable.textProperty().bind(watchTask.messageProperty());
        Scene scene = new Scene(root);
        window.setHeight(height - 50);
        window.setWidth(width);
        window.setTitle("CodeFX 1.0.0.Jan2016 | "+ Configs.getActiveProjectDir());
        window.setScene(scene);
        window.setMaximized(true);
        window.getIcons().setAll(Icon.setIcon());
        window.show();
        if (!Configs.getMainClass("mainclass").isEmpty()) {
            openInTab(Paths.get(Configs.getActiveProjectDir() + Configs.getMainClass("mainclass")));
        }
    }

    /**
     * WHEN PROGRAM IS CLOSING SAVE EVERYTHING BEFORE CLOSING THE PROGRAMME
     * SHUTDOWN THE TREEVIEW BACKGROUN TASK AND KILLS ALL THE ROGUE THREADS THAT
     * ARE NOT IN A POOL
     *
     * @throws java.lang.Exception
     */
    @Override
    public void stop() throws Exception {
        if (watchTask != null && watchTask.isRunning()) {
            watchTask.cancel();
            //statusLable.textProperty().unbind();
        }
        service.shutdownNow();
        System.exit(0);
    }

    /**
     * This is the usual main method of the java programs. it s used to launch
     * the application on desktop and embedded devices
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This Method creates the menu and handles all the menu events
     */
    private void createMenu() {
        //menu bar
        MenuBar menuBar = new MenuBar();
        //menuBar.setStyle("-fx-background-color: linear-gradient(#333333, #000010);");
        Menu fileMenu = new Menu("File");
        Menu editorMenu = new Menu("Editor");
        Menu settingMenu = new Menu("Settings");
        Menu codeMenu = new Menu("Code");
        Menu helpMenu = new Menu("Help");

        //file menu menu items
        MenuItem newFile = new MenuItem("\tNew File");
        //newFile.setStyle("-fx-text-fill: #fff;");
        newFile.setOnAction(e -> {
            createNewTab();
        });

        MenuItem newProject = new MenuItem("\tNew Project");
        newProject.setOnAction(e -> {
            CreateNewProjectDialog();
        });

        MenuItem saveActiveTab = new MenuItem("\tSave");
        saveActiveTab.setOnAction(e -> {
            String data = getActiveTabEditorContents();
            System.out.println(data);
            FileHelper.saveFileToProject(Configs.getActiveProjectDir(), tabPane.getSelectionModel().getSelectedItem().getText(), data);
        });

        MenuItem exit = new MenuItem("Exit | Alt + F4");
        exit.setOnAction((e) -> {
            window.close();
        });

        fileMenu.getItems().addAll(newFile, newProject, saveActiveTab, exit);

        MenuItem gStartedGuide = new MenuItem("\tGetting Started Guide");
        gStartedGuide.setOnAction((e) -> {
            try {
                Desktop.getDesktop().open(new File("G:\\JAVA\\codeFX\\dist\\guide.pdf"));
            } catch (IOException ex) {
                Logger.getLogger(CodeFX.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        MenuItem contrbt = new MenuItem("\tContribute To CodeFX");
        contrbt.setOnAction((e) -> {
            Dialogs.infoDialog("CONTRIBUTE TO OUR DREAM", howToContrbte);
        });
        MenuItem contct = new MenuItem("\tContacts");
        contct.setOnAction((e) -> {
            Dialogs.infoDialog("REACH OUT TO ME", contacts);
        });
        MenuItem developer = new MenuItem("\tDevelopers");
        developer.setOnAction((e) -> {
            Dialogs.infoDialog("Developers and Contributers", developers);
        });
        MenuItem about = new MenuItem("\tAbout CodeFX");
        about.setOnAction((e) -> {
            Dialogs.aboutAppDialog();
        });
        MenuItem liscense = new MenuItem("\tLiscense Statement");
        liscense.setOnAction((e) -> {
            Dialogs.infoDialog("Liscense Statement", liscenseStmt);
        });

        helpMenu.getItems().addAll(gStartedGuide, contrbt, contct, developer, liscense, about);

        MenuItem snippets = new MenuItem("\tCode Samples");
        snippets.setOnAction((e) -> {

            System.out.println(Configs.getActiveProjectDir() + Configs.getMainClass("mainclass"));

            System.out.println(Configs.getActiveProjectDir());
            System.out.println("Main class: " + Configs.getMainClass("mainclass"));

        });

        codeMenu.getItems().add(snippets);

        MenuItem fonts = new MenuItem("\tFonts");
        fonts.setOnAction((e) -> {
            Dialogs.setFontsAndFontStyle();
        });
        editorMenu.getItems().addAll(fonts);

        MenuItem settings = new MenuItem("\tGeneral Settings");
        settings.setOnAction((e) -> {
            Dialogs.settingsWindow();
        });
        settingMenu.getItems().addAll(settings);

        menuBar.getMenus().addAll(fileMenu, editorMenu, settingMenu, codeMenu, helpMenu);

        //anchoring it
        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        //adding the menu bar to the root pane(AnchorPane);
        root.getChildren().add(menuBar);
    }

    /**
     * This Method creates the top tool bar and handles on the tool bar events
     */
    private void createToolBar() {
        newfile = new Button("New File");
        newfile.setTooltip(new Tooltip("This Creates a new file.\nEither a java file or\na plane text file"));
        newproject = new Button("New Project");
        newproject.setTooltip(new Tooltip("This creates a new project\neither java project or javaFX "));
        save = new Button("Save");
        save.setTooltip(new Tooltip("Saves the contents of the currently\nactive tab. select a tab to\nsave its contents."));
        run = new Button("Run");
        run.setTooltip(new Tooltip("Runs the program."));

        compileBtn = new Button("Compile");
        compileBtn.setTooltip(new Tooltip("Compiles the program\nwithout running it.\nAlways compile first before\nrunning because changes made to the\nsource code will not take effect\nuntill recompilation. "));
        compileBtn.setOnAction((e) -> {
            Evelyne.compileProgram("javac " + Configs.getMainClass("mainclass"));
        });
        //creating the toolbar
        ToolBar toolbar = new ToolBar();
        //anchoring it
        AnchorPane.setTopAnchor(toolbar, 26.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);
        //adding the toolbar to the root pane (AnchorPane)
        root.getChildren().add(toolbar);
        //adding the action to new file button on the toolbar.
        newfile.setOnAction(e -> {
            createNewTab();
        });
        //adding the action to new new project button on the toolbar.
        newproject.setOnAction(e -> {
            CreateNewProjectDialog();
        });
        //adding the action to new file button on the toolbar.
        save.setOnAction((e) -> {
            String data = getActiveTabEditorContents();
            FileHelper.saveFileToProject(Configs.getActiveProjectDir(), tabPane.getSelectionModel().getSelectedItem().getText(), data);
        });
        //adding the action to the compile and run button on the toolbar
        run.setOnAction((e) -> {
            String mainclass = null;
            if (Configs.getMainClass("mainclass").isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Fatal Error, Could not compile and run");
                alert.setContentText("PLease go to Settings -> General Settings \nand Choose the main class of your project.");
                alert.showAndWait();
            } else {
                if (Configs.getMainClass("mainclass").endsWith(".java")) {
                    mainclass = Configs.getMainClass("mainclass").replace(".java", "");
                }
                Evelyne.runProgram("java " + mainclass);
            }
        });

        //adding the toolbar items/menus
        toolbar.getItems().addAll(newfile, newproject, save, compileBtn, run);

    }

    /**
     * This Method creates the bottom tool bar and handles on the tool bar
     * events
     */
    private void createBottomToolbar() {

        output = new Button("Output");
        String testData = "";
        output.setOnAction((e) -> {
            System.out.println("WrkSpace: " + Dialogs.chooseWorkspaceOnStartup(window));
        });

        statusLable = new Label();

        //creating the toolbar
        ToolBar toolbar = new ToolBar();
        //adding the toolbar items/menus
        toolbar.getItems().addAll(output, statusLable);
        //anchoring it
        AnchorPane.setBottomAnchor(toolbar, 0.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);
        //adding the toolbar to the root pane (AnchorPane)
        root.getChildren().add(toolbar);
    }

    private TreeItem<PathItem> createNode(PathItem pathItem) {
        return PathTreeItem.createNode(pathItem);
    }

    public void refreshTree() {
        messageProp = new SimpleStringProperty();
        fileTreeView = new TreeView<>();
        fileTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        service = Executors.newFixedThreadPool(3);
        rootPath = Paths.get(Configs.getWorkSpaceDir("workspace"));
        PathItem pathItem = new PathItem(rootPath);
        TreeItem<PathItem> root = createNode(pathItem);
        root.setExpanded(true);
        fileTreeView.setRoot(root);

        fileTreeView.setEditable(true);
        fileTreeView.setCellFactory((TreeView<PathItem> p) -> {
            final PathTreeCell cell = new PathTreeCell(stage, messageProp);
            return cell;
        });

        fileTreeView.setMaxWidth(200);
        watchTask = new WatchTask(rootPath);

        service.submit(watchTask);
    }

    /**
     * This Method creates the split pane a holder for components
     */
    private void createSplitPane() {
        consoleWindow = new SwingNode();
        createConsoleWindow(consoleWindow);

        cmdWindow = new SwingNode();
        createCmdWindow(cmdWindow);

        TabPane consoles = new TabPane();
        AnchorPane consoleRoot = new AnchorPane();
        AnchorPane cmdRoot = new AnchorPane();
        AnchorPane consolesRoot = new AnchorPane();

        AnchorPane.setTopAnchor(consoles, 0.0);
        AnchorPane.setLeftAnchor(consoles, 0.0);
        AnchorPane.setRightAnchor(consoles, 0.0);
        AnchorPane.setBottomAnchor(consoles, 0.0);

        AnchorPane.setTopAnchor(consoleWindow, 0.0);
        AnchorPane.setLeftAnchor(consoleWindow, 0.0);
        AnchorPane.setRightAnchor(consoleWindow, 0.0);
        AnchorPane.setBottomAnchor(consoleWindow, 0.0);

        AnchorPane.setTopAnchor(cmdWindow, 0.0);
        AnchorPane.setLeftAnchor(cmdWindow, 0.0);
        AnchorPane.setRightAnchor(cmdWindow, 0.0);
        AnchorPane.setBottomAnchor(cmdWindow, 0.0);

        consolesRoot.getChildren().add(consoles);
        consoleRoot.getChildren().add(consoleWindow);
        cmdRoot.getChildren().add(cmdWindow);

        Tab consoleTab = new Tab("Console I/O");
        Tab cmdTab = new Tab("Command Prompt");

        consoleTab.setGraphic(new ImageView("com/mwaka/ambrose/codefx/resources/console.png"));
        cmdTab.setGraphic(new ImageView("com/mwaka/ambrose/codefx/resources/cmd.png"));

        consoleTab.setClosable(false);
        cmdTab.setClosable(false);

        consoleTab.setContent(consoleRoot);
        cmdTab.setContent(cmdRoot);

        consoles.getTabs().addAll(consoleTab, cmdTab);

        tabPane = new TabPane();
        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPosition(0, 0.1);

        SplitPane outputPane = new SplitPane();
        outputPane.setOrientation(Orientation.VERTICAL);
        outputPane.setDividerPosition(0, 0.7);

        editor = new AnchorPane();
        editor.setMaxHeight(350);
        editor.setMinHeight(350);
        explorer = new AnchorPane();
        explorer.setMinWidth(300);
        explorer.setMaxWidth(300);

        AnchorPane.setTopAnchor(fileTreeView, 0.0);
        AnchorPane.setLeftAnchor(fileTreeView, 0.0);
        AnchorPane.setRightAnchor(fileTreeView, 0.0);
        AnchorPane.setBottomAnchor(fileTreeView, 0.0);

        explorer.getChildren().add(fileTreeView);

        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        editor.getChildren().add(tabPane);

        outputPane.getItems().addAll(editor, consolesRoot);

        splitPane.getItems().addAll(explorer, outputPane);

        AnchorPane.setTopAnchor(splitPane, 60.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 33.0);
        root.getChildren().add(splitPane);
    }

    /**
     * Creates new tabs
     */
    private void createNewTab() {
        fileName = new Dialogs().CreateNewFileDialog();

        if (fileName != null) {
            if (fileName.endsWith(".java")) {

                //The contents that will be initially available when the java file
                //is created.
                initJavaContent = "/*\n"
                        + " * THE LISCENCE STATEMENT HERE\n"
                        + " * DESCRIBE ANYTHING ELSE HERE.\n"
                        + "*/\n"
                        + "\n"
                        + "/**\n"
                        + " * Describe what the class do here\n"
                        + " * @author Author name\n"
                        + "*/"
                        + "\npublic class " + fileName.replace(".java", "") + "{\n}";
                icon = new ImageView(Icon.setJavaFileIcon());

            } else if (fileName.endsWith(".txt")) {
                initJavaContent = "#THIS IS JUST A PLAIN TEXT FILE SO YOU CAN USE IT FOR WRITING DESCRIPTIONS,"
                        + "\n#LISCENSCE, OR IT CAN BE A README FILE/ INSTRUCTION FILE.";
                icon = new ImageView(Icon.setTextFileIcon());

            } else if (fileName.endsWith(".javai")) {
                fileName = fileName.replace(".javai", ".java");
                initJavaContent = "/*\n"
                        + " * THE LISCENCE STATEMENT HERE\n"
                        + " * DESCRIBE ANYTHING ELSE HERE.\n"
                        + "*/\n"
                        + "\n"
                        + "/**\n"
                        + " * Describe what the interface do here\n"
                        + " * @author Author name\n"
                        + "*/"
                        + "\npublic interface " + fileName.replace(".java", "") + "{\n}";
                icon = new ImageView(Icon.setJavaInterfaceIcon());
            }

            tab = new Editor().createEditorTab(fileName, icon, initJavaContent);
            tabPane.getTabs().add(tab);
            FileHelper.saveFileToProject(Configs.getActiveProjectDir(), fileName, initJavaContent);
            statusLable.setText(fileName + " created!");
        } else {
            statusLable.setText("Cancelled!");
        }
        refreshExplorer();
    }

    /**
     * This method is used to get the contents of the active editor tab.
     *
     * @return returns the string contents of the editor ready to be written to
     * a file.
     */
    private String getActiveTabEditorContents() {
        return new Editor().getEditorContents(tabPane.getSelectionModel().getSelectedItem());
    }

    String liscenseStmt = "\nMWAKA AMBROSE LISCENSCE AGREEMENT VERSION 1.0.0.Jan.2016"
            + " \n(c) 2016 Mwaka Ambrose ambrosemwaka@gmail.com\n"
            + " ===================================================\n"
            + " \nCodeFX - Is an application developed to help begginers in\n"
            + " java programming\n"
            + " master java easily with the built in\n"
            + " code snippets and shot hand completion features.\n"
            + " \nThis program is free software; you can redistribute it and/or\n"
            + " modify it as long as you respect and stick to the\n"
            + " agreements stated here in.\n\n"
            + "(1) - In every modification, keep this liscensce agreement"
            + "\nintact so that the user of your software knows thier rights.\n"
            + "\n"
            + " (2) - Its free for learning purposes. As long as you don't"
            + "\nearn from the code written through it. Commercial use is \ntherefore restricted and"
            + " prohibited \nwithout any prior consultation and written permission from the author.\n"
            + " Contact the author at the email specified above.\n"
            + "\n"
            + " (3) - Under no circumstances shall this software be implicated to"
            + "\nany harm caused by the modified code or any dirivative of \nthis software."
            + "\nThe authors of those versions shall be held accountable for \nthier own versons "
            + " of the software. \nIt is also otherwise encouraged to credit the author from which\n"
            + " any dirivative of your code is based upon, \nbut not in the context of maketing and or \n"
            + " any selfish gains.\n"
            + "\n"
            + " (4) - Modification of this liscense agreememt is a direct violation of\n"
            + " agreement (1) and is highly discouraged, \nincase of any interest in modification, \n"
            + " contact the author of the agreement at \nthe email address specified above.\n"
            + "\n"
            + " (5) - This software is distributed in the hope that it will be useful, \n"
            + " but WITHOUT ANY WARRANTY; without even the implied warranty \nof MERCHANTABILITY"
            + " or FITNESS FOR THE PARTICULAR \nPURPOSE ITS DESIGNED FOR.\n"
            + "\n"
            + " THANK YOU AND HAPPY PROGRAMMING\n"
            + "\n"
            + " Author, Mwaka Ambrose.";

    String developers = "THIS IS A LIST OF ALL THOSE WHO CONTRIBUTED TO\n"
            + "THIS SOFTWARE AND SPECIAL THANKS GOES TO THEM IN THIS\n"
            + "RECOGNITION LIST.\n"
            + "\n(1) - Mwaka Ambrose [Lead project engineer and founder]\n"
            + "(2) - JIMTECH Co, Ltd.\n"
            + "(3) - Opuch George\n\n"
            + "YOU TOO CAN HAVE YOUR NAME HERE.\n"
            + "MAKE A DIFFERENCE IN SOMEONES LIFE, send me an email \n"
            + "at: ambrosemwaka@gmail.com";

    String contacts = "THESE ARE A FEW WAYS YOU CAN REACH ME\n"
            + "===========================================\n\n"
            + "Name: Mwaka Ambrose.\n"
            + "Phone: +256785005918 / +256793270999.\n"
            + "Email: ambrosemwaka@gmail.com / amsxbyte@gmail.com\n"
            + "Google+: https://www.google.com/+mwakaambrose\n"
            + "Facebook: https://www.facebook.com/sxambrose\n"
            + "Twitter: @ambrosemwaka";

    String howToContrbte = "WANT TO CONTIRBUTE? SIMPLE, MAKE A DONATION OR\n"
            + "WRITE SOME CODE. REACH OUT TO ME IN ANY OF THE CANTACT WAYS\n"
            + "LISTED BELOW.\n\n"
            + "Phone: +256785005918 / +256793270999.\n"
            + "Email: ambrosemwaka@gmail.com / amsxbyte@gmail.com\n"
            + "Google+: https://www.google.com/+mwakaambrose\n"
            + "Facebook: https://www.facebook.com/sxambrose\n"
            + "Twitter: @ambrosemwaka";

    AnchorPane scene3Root, detail;
    boolean createMainM = true;
    boolean donotCreateMainM = false;
    HBox projectRoot;
    int projCat;
    Stage window2;
    Scene scene1, scene2, scene3;
    VBox listContainer;

    /**
     * This method creates and displays the new project dialog It handles all
     * the events associated with it.
     */
    public void CreateNewProjectDialog() {
        Button next = new Button("Next....");
        Button cancel = new Button("Cancel");
        Button finishBtn = new Button("Finish");
        Button cancel2 = new Button("Cancel");
        Button back = new Button("Back");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        scene3Root = new VBox(20);
        scene3Root = new AnchorPane();

        Text txt = new Text("PROJECT CONFIGURATIONS");

        TextField projectName = new TextField();
        projectName.setPromptText("Project name...");
        projectName.setPrefWidth(250.0);
        //projectName.setFocused(false);

        TextField className = new TextField();
        className.setPromptText("Class name....");
        className.setPrefWidth(250.0);

        Label descLabel = new Label("This lets the generated code comes with \nmain method to speed up developement.\nCheck the corresponding radio button to \ndisable main method creation.");

        ToggleGroup rBtnToggleGroup = new ToggleGroup();

        RadioButton createMainMethod = new RadioButton("Create Main Method");
        createMainMethod.setToggleGroup(rBtnToggleGroup);
        createMainMethod.setSelected(true);

        RadioButton dontCreateMainMethod = new RadioButton("Don't Create Main Method");
        dontCreateMainMethod.setToggleGroup(rBtnToggleGroup);

        rBtnToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (createMainMethod.isSelected()) {
                    System.out.println("Creating main method...");
                    createMainM = true;
                    donotCreateMainM = false;
                } else if (dontCreateMainMethod.isSelected()) {
                    System.out.println("Not creating the main method.");
                    donotCreateMainM = true;
                    createMainM = false;
                }
            }
        });

        AnchorPane.setTopAnchor(txt, 20.0);
        AnchorPane.setLeftAnchor(txt, 10.0);

        AnchorPane.setTopAnchor(projectName, 50.0);
        AnchorPane.setLeftAnchor(projectName, 10.0);

        AnchorPane.setTopAnchor(className, 90.0);
        AnchorPane.setLeftAnchor(className, 10.0);

        AnchorPane.setTopAnchor(descLabel, 130.0);
        AnchorPane.setLeftAnchor(descLabel, 10.0);

        AnchorPane.setTopAnchor(createMainMethod, 210.0);
        AnchorPane.setLeftAnchor(createMainMethod, 10.0);

        AnchorPane.setTopAnchor(dontCreateMainMethod, 235.0);
        AnchorPane.setLeftAnchor(dontCreateMainMethod, 10.0);

        AnchorPane.setTopAnchor(back, 260.0);
        AnchorPane.setRightAnchor(back, 135.0);

        AnchorPane.setTopAnchor(cancel2, 260.0);
        AnchorPane.setRightAnchor(cancel2, 70.0);

        AnchorPane.setTopAnchor(finishBtn, 260.0);
        AnchorPane.setRightAnchor(finishBtn, 10.0);

        cancel2.setOnAction((ActionEvent event) -> {
            window.close();
        });

        back.setOnAction((e) -> {
            window.setScene(scene2);

        });

        //Handling action for the finish button and creating a templated project
        finishBtn.setOnAction((e) -> {

            File file = new File(Configs.getWorkSpaceDir("workspace") + "\\" + projectName.getText());
            file.mkdir();

            Configs.setMainClass(className.getText().concat(".java"));

            if (className.getText().endsWith(".java")) {
                fileName = className.getText();
            } else {
                fileName = className.getText().concat(".java");
            }

            if (projCat == 1) {
                if (createMainM == true) {
                    FileHelper.saveFileToProject(file.getAbsolutePath() + "\\", fileName, TemplateCode.javaTemplateWithMainClass(fileName));
                    Configs.setActiveProjectDir(file.getAbsolutePath() + "\\");
                    refreshTree();
                    Tab newTab = new Editor().createEditorTab(fileName, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/class.png"))), TemplateCode.javaTemplateWithMainClass(fileName));
                    tabPane.getTabs().add(newTab);
                    refreshExplorer();

                } else {
                    FileHelper.saveFileToProject(file.getAbsolutePath() + "\\", fileName, TemplateCode.javaTemplateWithoutMainClass(fileName));
                    Configs.setActiveProjectDir(file.getAbsolutePath() + "\\");
                    refreshTree();
                    Tab newTab = new Editor().createEditorTab(fileName, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/class.png"))), TemplateCode.javaTemplateWithoutMainClass(fileName));
                    tabPane.getTabs().add(newTab);
                    refreshExplorer();
                }
            } else if (projCat == 2) {
                FileHelper.saveFileToProject(file.getAbsolutePath() + "\\", fileName, TemplateCode.javaFxTemplate(fileName));
                Configs.setActiveProjectDir(file.getAbsolutePath() + "\\");
                refreshTree();
                Tab newTab = new Editor().createEditorTab(fileName, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/class.png"))), TemplateCode.javaFxTemplate(fileName));
                tabPane.getTabs().add(newTab);
                refreshExplorer();
            }

            window.close();
            refreshTree();
        });

        scene3Root.getChildren().addAll(txt, projectName, className, descLabel, createMainMethod, dontCreateMainMethod, back, cancel2, finishBtn);

        scene3 = new Scene(scene3Root, 500, 300);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        root = new AnchorPane();
        projectRoot = new HBox(20);
        projectRoot.setPadding(new Insets(10));

        listContainer = new VBox();
        detail = new AnchorPane();
        Label detailText = new Label("Catergory selection description \nhere."
                + "\nSelect Project Catergory to continue..");

        next.disableProperty().setValue(true);
        cancel.setOnAction((ActionEvent event) -> {
            window.close();
            explorer.getChildren().add(treeView);
        });

        detail.getChildren().addAll(detailText, next, cancel);

        ListView<String> projMenu = new ListView<>();
        projMenu.getItems().addAll("Java", "JavaFX");
        projMenu.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        projMenu.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            int index = projMenu.getSelectionModel().getSelectedIndex();
            if (index == 0) {
                next.disableProperty().setValue(false);
                projCat = 1;
                detailText.setText("Creates a new Java SE application \nin a standard IDE project. \nYou can also generate a main class in \nthe project. \nStandard projects use a custom build \ntool to BUILD and RUN your project.");
            } else if (index == 1) {
                next.disableProperty().setValue(false);
                projCat = 2;
                detailText.setText("Creates a new JavaFX application \nin a standard IDE project. \nYou can also generate a main class in \nthe project. \nStandard projects use a custom build \ntool to BUILD and RUN your project.");
            }
        });

        next.setOnAction((ActionEvent event) -> {
            if (projCat == 1) {
                window.setScene(scene3);
            } else if (projCat == 2) {
                window.setScene(scene3);
            }
        });

        listContainer.getChildren().add(projMenu);
        AnchorPane.setTopAnchor(detail, 20.0);
        AnchorPane.setBottomAnchor(detail, 0.0);
        AnchorPane.setRightAnchor(detail, 0.0);

        AnchorPane.setTopAnchor(listContainer, 20.0);
        AnchorPane.setBottomAnchor(listContainer, 0.0);
        AnchorPane.setLeftAnchor(listContainer, 0.0);

        AnchorPane.setBottomAnchor(next, 3.0);
        AnchorPane.setRightAnchor(next, 70.0);

        AnchorPane.setBottomAnchor(cancel, 3.0);
        AnchorPane.setRightAnchor(cancel, 0.0);

        projectRoot.getChildren().addAll(listContainer, detail);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        window = new Stage();
        scene2 = new Scene(projectRoot, 500, 300);
        window.setScene(scene2);
        window.setTitle("New Project");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().setAll(Icon.setIcon());
        window.showAndWait();
    }

    public void refreshExplorer() {
        explorer.getChildren().remove(0);
        AnchorPane.setTopAnchor(fileTreeView, 0.0);
        AnchorPane.setLeftAnchor(fileTreeView, 0.0);
        AnchorPane.setRightAnchor(fileTreeView, 0.0);
        AnchorPane.setBottomAnchor(fileTreeView, 0.0);
        explorer.getChildren().add(fileTreeView);
    }

    /**
     * Opens the file chosen from the tree view (file explorer into a tab
     *
     * @param filePath path of the file to be opened.
     */
    public synchronized void openInTab(Path filePath) {

        fileName = filePath.getFileName().toString();
        StringBuilder builder = new StringBuilder();

        if (fileName != null) {

            if (fileName.endsWith(".java")) {
                FileReader fileReader = null;
                try {
                    fileReader = new FileReader(new File(filePath.toString()));
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line).append("\n");
                    }
                } catch (IOException e) {
                    System.out.println(e);
                    statusLable.setText("Failed to read and open file. " + e.getMessage());
                } finally {
                    if (fileReader != null) {
                        //close the reader if its not null
                        //i havent found a better way of doing it sinces its giving me 
                        //a bug.
                        //please handle it if you can.
                        //an exception (nullpointerexception) is being thrown here and its uncaught.
                        //when i catch it thats when the bug comes in.
                    }
                    try {
                        fileReader.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                        statusLable.setText("Failed to close file reader...");
                    }
                }
                initJavaContent = builder.toString();

                Image jImage = new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/class.png"));
                icon = new ImageView(jImage);
            } else {
                initJavaContent = builder.toString();
                Image tImage = new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/text.png"));
                icon = new ImageView(tImage);
            }

            //System.out.println("Contents: \n" + builder.toString());
            tab = new Editor().createEditorTab(fileName, icon, initJavaContent);
            //System.out.println("Tab: " + tab.getText());
            //System.out.println("Env V: " + System.getenv());
            try {
                tabPane.getTabs().add(tab);
                statusLable.setText(fileName + " opened successfuly..");
            } catch (Exception e) {
                statusLable.setText(e.getMessage());
            }

        } else {
            statusLable.setText("Failed to open..");
        }

    }

    /**
     * Creates the console node, please note that the console node is a swing
     * component so its being run in its own swing thread and it doesn't
     * synchronize well with the javaFX thread and is slow to respond when the
     * container / window is being resized
     */
    private void createConsoleWindow(SwingNode node) {
        Evelyne.setSystemLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            node.setContent(Evelyne.createConsoleWindow());
        });
    }

    /**
     * Creates the command prompt node, please note that the command prompt node
     * is a swing component so its being run in its own swing thread and it
     * doesn't synchronize well with the javaFX thread and is slow to respond
     * when the container / window is being resized
     */
    private void createCmdWindow(SwingNode node) {
        Lyec.setCmdWorkingDir(Configs.getActiveProjectDir());
        Lyec.setsystemLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            node.setContent(Lyec.getCmdWindowContents());
        });
    }
}
