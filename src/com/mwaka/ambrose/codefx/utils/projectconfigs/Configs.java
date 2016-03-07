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
package com.mwaka.ambrose.codefx.utils.projectconfigs;

import java.util.prefs.Preferences;

/**
 * This is the project configuration class, it handles both the Editors
 * configuration and the current project being worked on.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 */
public class Configs {

    static Preferences userPreferences = Preferences.userRoot();

    /**
     * sets the workspace for the IDE
     *
     * @param path path to the workspace in string format
     */
    public static void setWorkSpaceDir(String path) {
        userPreferences.put("workspace", path);
    }

    /**
     * gets the current working directory given the correct directory key please
     * note that it is "workspace"
     *
     * @param key the key to identify the workspace
     * @return a string path of the workspace directory.
     */
    public static String getWorkSpaceDir(String key) {
        return userPreferences.get(key, null);
    }

    /**
     * the string path to the workspace directory directory
     */
    public static String workSpaceDir = getWorkSpaceDir("workspace");

    /**
     * gets the current font size of the editor given the correct font size key
     * please note that it is "fontsize"
     *
     * @param key the key to identify the font in the user configuration
     * preferences.
     * @return the font size and sets it to the editor. if defaults to font size
     * 18 if no use defined size is found.
     */
    public static int getFontSize(String key) {
        return userPreferences.getInt("fontsize", 18);
    }

    /**
     * Sets the font size that will be used by the editor.
     *
     * @param fontSize the font size that will be set to the editor
     */
    public static void setFontSize(int fontSize) {
        userPreferences.putInt("fontsize", fontSize);
    }

    /**
     * Gets the font style that has be chosen by the user using the specified
     * key not that that key is "fontstyle".
     *
     * @param key the key that is used to identify the font style
     * @return the font style to be set in the editor
     */
    public static String getFontStyle(String key) {
        return userPreferences.get(key, "Consolas");
    }

    /**
     * Sets the font style to be used in the editor
     *
     * @param fontStyle the font style of preference.
     */
    public static void setFontStyle(String fontStyle) {
        userPreferences.put("fontstyle", fontStyle);
    }

    /**
     * this method is used to define the active project to make easy for the
     * build tool to find the main class use it for compiling purposes.
     *
     * @param completePathToMainClass the complete path to the main class of the
     * active project.
     */
    public static void setActiveProjectDir(String completePathToMainClass) {
        userPreferences.put("activeproject", completePathToMainClass);
    }

    /**
     * this method is used to get the currently set active project it actually
     * returns a string path to the main class of the active project.
     *
     * @return return the string path to the main class of the currently set
     * active project.
     */
    public static String getActiveProjectDir() {
        return userPreferences.get("activeproject", "none");
    }

    /**
     * This method is used to set the main class which contains the main method
     * to help the build tool compile the project well.
     *
     * @param mainclass main class name
     */
    public static void setMainClass(String mainclass) {
        userPreferences.put("mainclass", mainclass);
    }

    /**
     * This method is used to get the main class
     *
     * @param key to identify the main class setting in the user preference
     * @return the name of the main class
     */
    public static String getMainClass(String key) {
        return userPreferences.get(key, null);
    }
}
