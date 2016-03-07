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

import javafx.scene.image.Image;

/**
 * This is the Icon class it loads all the icons that are going to be used in
 * the program.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class Icon {

    /**
     * This method is used to load the image that will be used as the apps icon
     * and dialogs.
     *
     * @return The Image to be used as the icon
     */
    public static Image setIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/appicon.png"));
    }

    /**
     * This method is used to load the image that will be used as the java file
     * icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setJavaFileIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/java.png"));
    }

    /**
     * This method is used to load the image that will be used as the text file
     * icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setTextFileIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/text.png"));
    }

    /**
     * This method is used to load the image that will be used as the project
     * folder icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setProjectIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/folder.png"));
    }

    /**
     * This method is used to load the image that will be used as the new file
     * menu icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setNewFileMenuIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/newfile.png"));
    }

    /**
     * This method is used to load the image that will be used as the new file
     * menu icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setNewProjectMenuIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/newproject.png"));
    }

    /**
     * This method is used to load the image that will be used as the new file
     * menu icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setOpenProjectMenuIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/openproject.png"));
    }

    /**
     * This method is used to load the image that will be used as the java
     * interface icon.
     *
     * @return The Image to be used as the icon
     */
    public static Image setJavaInterfaceIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream("com/mwaka/ambrose/codefx/resources/interface.png"));
    }
}
