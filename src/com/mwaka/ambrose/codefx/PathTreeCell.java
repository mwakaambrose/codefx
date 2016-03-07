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

import com.mwaka.ambrose.codefx.utils.projectconfigs.Configs;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * This just creates Path tree cell by override the default tree cell
 * configurations adding context menu actions to them.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class PathTreeCell extends TreeCell<PathItem> {

    private TextField textField;
    private Path editingPath;
    private StringProperty messageProp;
    private ContextMenu dirMenu = new ContextMenu();
    private ContextMenu fileMenu = new ContextMenu();

    public PathTreeCell(final Stage owner, final StringProperty messageProp) {
        this.messageProp = messageProp;
        MenuItem expandMenu = new MenuItem("Expand");
        expandMenu.setOnAction((ActionEvent event) -> {
            getTreeItem().setExpanded(true);
        });
        MenuItem expandAllMenu = new MenuItem("Expand All");
        expandAllMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                expandTreeItem(getTreeItem());
            }

            private void expandTreeItem(TreeItem<PathItem> item) {
                if (item.isLeaf()) {
                    return;
                }
                item.setExpanded(true);
                ObservableList<TreeItem<PathItem>> children = item.getChildren();
                children.stream().filter(child -> (!child.isLeaf()))
                        .forEach(child -> expandTreeItem(child));
            }
        });

        MenuItem addMenu = new MenuItem("Add Directory");
        addMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String dirName = AddDialog.AddDir("Directory");
                if (dirName.isEmpty()) {
                    System.out.println("Emty not creating dir");
                } else {
                    Path newDir = createNewDirectory(dirName);
                    if (newDir != null) {
                        TreeItem<PathItem> addItem = PathTreeItem.createNode(new PathItem(newDir));
                        getTreeItem().getChildren().add(addItem);
                    }
                }

            }

            private Path createNewDirectory(String name) {
                Path newDir = null;
                while (true) {
                    Path path = getTreeItem().getValue().getPath();
                    newDir = Paths.get(path.toAbsolutePath().toString(), name);
                    try {
                        Files.createDirectory(newDir);
                        break;
                    } catch (FileAlreadyExistsException ex) {
                        continue;
                    } catch (IOException ex) {
                        cancelEdit();
                        messageProp.setValue(String.format("Creating directory(%s) failed", newDir.getFileName()));
                        break;
                    }
                }
                return newDir;
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
        MenuItem deleteMenu = new MenuItem("Delete");
        deleteMenu.setOnAction((ActionEvent event) -> {
            ObjectProperty<TreeItem<PathItem>> prop = new SimpleObjectProperty<>();
            new ModalDialog(owner, getTreeItem(), prop);
            prop.addListener((ObservableValue<? extends TreeItem<PathItem>> ov, TreeItem<PathItem> oldItem, TreeItem<PathItem> newItem) -> {
                try {
                    Files.walkFileTree(newItem.getValue().getPath(), new VisitorForDelete());
                    if (getTreeItem().getParent() == null) {
                        // when the root is deleted how to clear the TreeView???
                        new CodeFX().refreshTree();
                    } else {
                        getTreeItem().getParent().getChildren().remove(newItem);
                    }
                } catch (IOException ex) {
                    messageProp.setValue(String.format("Deleting %s failed", newItem.getValue().getPath().getFileName()));
                }
            });
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        MenuItem openMenu = new MenuItem("Open");
        openMenu.setOnAction((ActionEvent event) -> {
            Path path = getTreeItem().getValue().getPath();
            System.out.println("Selected Item Path: " + path);
            new CodeFX().openInTab(path);
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
        MenuItem createNewJavaClass = new MenuItem("New Java Class");
        createNewJavaClass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String filename = AddDialog.AddDir("Java Class");
                if (filename.isEmpty()) {
                    System.out.println("Cancelled!");
                } else {
                    Path newDir = createNewFile(filename);
                    if (newDir != null) {
                        TreeItem<PathItem> addItem = PathTreeItem.createNode(new PathItem(newDir));
                        getTreeItem().getChildren().add(addItem);
                    }
                }

            }

            private Path createNewFile(String filename) {
                Path newDir = null;
                while (true) {
                    Path path = getTreeItem().getValue().getPath();
                    if (filename.endsWith(".java")) {
                        filename = filename.replace(".java", "");
                    }
                    newDir = Paths.get(path.toAbsolutePath().toString(), filename + ".java");
                    try {
                        Files.createFile(newDir);
                        break;
                    } catch (FileAlreadyExistsException ex) {
                        continue;
                    } catch (IOException ex) {
                        cancelEdit();
                        messageProp.setValue(String.format("Creating file(%s) failed", newDir.getFileName()));
                        break;
                    }
                }
                return newDir;
            }
        });

        MenuItem createNewTxtFile = new MenuItem("New Text File");
        createNewTxtFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String filename = AddDialog.AddDir("Text File");
                if (filename.isEmpty()) {
                    System.out.println("Cancelled!");
                } else {
                    Path newDir = createNewFile(filename);
                    if (newDir != null) {
                        TreeItem<PathItem> addItem = PathTreeItem.createNode(new PathItem(newDir));
                        getTreeItem().getChildren().add(addItem);
                    }
                }

            }

            private Path createNewFile(String filename) {
                Path newDir = null;
                while (true) {
                    Path path = getTreeItem().getValue().getPath();
                    if (filename.endsWith(".txt")) {
                        filename = filename.replace(".txt", "");
                    }
                    newDir = Paths.get(path.toAbsolutePath().toString(), filename + ".txt");
                    try {
                        Files.createFile(newDir);
                        break;
                    } catch (FileAlreadyExistsException ex) {
                        continue;
                    } catch (IOException ex) {
                        cancelEdit();
                        messageProp.setValue(String.format("Creating file(%s) failed", newDir.getFileName()));
                        break;
                    }
                }
                return newDir;
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        MenuItem setAsActiveProject = new MenuItem("Set As Active Project");
        setAsActiveProject.setOnAction((ActionEvent t) -> {
            Path path = getTreeItem().getValue().getPath();
            if (path.toFile().isDirectory()) {
                //then convert the path to a string and set
                //as the active project directory.
                Configs.setActiveProjectDir(path.toString() + "\\");
                System.out.println("Main Dir: " + Configs.getActiveProjectDir());
                CodeFX.statusLable.setText("Active project directory changed sucessfully...");
            } else {
                CodeFX.statusLable.setText("Failed to set it, its a file not a directory");
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        MenuItem setAsActiveMainClass = new MenuItem("Set As Main Class");
        setAsActiveMainClass.setOnAction((ActionEvent t) -> {
            Path path = getTreeItem().getValue().getPath();
            if (path.toFile().isFile()) {
                //then convert the path to a string and set
                //as the main class.
                Configs.setMainClass(path.toFile().getName());
                System.out.println("Mainclass: " + path.toFile().getName());
                CodeFX.statusLable.setText("Main class changed sucessfully...");
            } else {
                CodeFX.statusLable.setText("Failed to set it, its a directory not a file");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        dirMenu.getItems().addAll(expandMenu, expandAllMenu, createNewJavaClass, createNewTxtFile, setAsActiveProject);
        fileMenu.getItems().addAll(openMenu, deleteMenu, setAsActiveMainClass);
    }

    @Override
    protected void updateItem(PathItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
                if (!getTreeItem().isLeaf()) {
                    setContextMenu(dirMenu);
                } else {
                    setContextMenu(fileMenu);
                }
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
        if (getItem() == null) {
            editingPath = null;
        } else {
            editingPath = getItem().getPath();
        }
    }

    @Override
    public void commitEdit(PathItem pathItem) {
        // rename the file or directory
        if (editingPath != null) {
            try {
                Files.move(editingPath, pathItem.getPath());
            } catch (IOException ex) {
                cancelEdit();
                messageProp.setValue(String.format("Renaming %s filed", editingPath.getFileName()));
            }
        }
        super.commitEdit(pathItem);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
    }

    private String getString() {
        return getItem().toString();
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ENTER) {
                Path path = Paths.get(getItem().getPath().getParent().toAbsolutePath().toString(), textField.getText());
                commitEdit(new PathItem(path));
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
}
