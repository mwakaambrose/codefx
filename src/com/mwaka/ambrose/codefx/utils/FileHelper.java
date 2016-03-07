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
import com.mwaka.ambrose.codefx.utils.projectconfigs.Configs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * This class is used to assist in file operations, managing project files and
 * workspaces.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class FileHelper {

    /**
     * This method creates a file and writes data to it, and existing data will
     * be over written completely without any checks if the file exists or not,
     * if file exists, data in it will be over written, if the file doesn't
     * exist, it will be created and data written to it.
     *
     * @param projectDir The current project directory path as a string
     * @param fileName The file name of the file to be saved
     * @param data The data to be written to the file
     */
    public static void saveFileToProject(String projectDir, String fileName, String data) {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(projectDir + fileName);
            fileWriter.write(data);
            fileWriter.flush();
            CodeFX.statusLable.setText("Saved successfuly");
        } catch (IOException e) {
            //System.out.println(e);
            CodeFX.statusLable.setText(e.getMessage());
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                CodeFX.statusLable.setText(ex.getMessage());
            }
        }
    }

    /**
     * This method reads a file and displays it to the editor.
     *
     * @param file The file you want to open, a path to it.
     * @param editor The Syntax editor that you want to display the file to.
     */
    public void openFileFromProject(File file, RSyntaxTextArea editor) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                editor.append(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Creates a new directory, its upon the programmer to choose where the
     * directory is created
     *
     * @param dirName directory name
     * @return file object of the directory (Directory itself).
     */
    public static File createNewDir(String dirName) {
        File file = new File(dirName);
        file.mkdir();
        return file;
    }
}
