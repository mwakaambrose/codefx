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

import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.control.TreeView;

/**
 * This is the background Task for the tree view. This refers to this
 * <a href="http://docs.oracle.com/javase/tutorial/essential/io/notification.html">site</a>
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class WatchTask extends Task<Void> {

    private Path path;
    private StringBuilder message = new StringBuilder();
    private Task watchTask;
    private TreeView<PathItem> fileTreeView;
    private Path rootPath;
    private StringProperty messageProp;

    public WatchTask(Path path) {
        this.path = path;
    }

    @Override
    protected Void call() throws Exception {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                break;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == OVERFLOW) {
                    continue;
                }
                Path context = (Path) event.context();
                message.append(context.toAbsolutePath());
                message.append(getKindToMessage(event.kind()));
                message.append(System.getProperty("line.separator"));
                updateMessage(message.toString()); // to bind to the TextArea
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
        return null;
    }

    @Override
    protected void cancelled() {
        updateMessage("Watch task was cancelled"); // to bind to the TextArea
    }

    private String getKindToMessage(WatchEvent.Kind<?> kind) {
        if (kind == ENTRY_CREATE) {
            new CodeFX().refreshTree();
            return " is created";

        } else if (kind == ENTRY_DELETE) {
            new CodeFX().refreshTree();
            return " is deleted";
        }
        new CodeFX().refreshTree();
        return " is updated";
    }
}
