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
package com.mwaka.ambrose.codefx.utils.compiler;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class provides and API to the external compilation process,
 * encapsulating all its underlying works. Users of this package need to only
 * interact with this Class to handle their compiling and run process.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 */
public class Evelyne {

    static GuiPanel testframe = new GuiPanel();

    /**
     * This creates the console panel GUI with all its components ready for
     * transaction.
     *
     * @return panel to set to another layout or frame
     */
    public static JPanel createConsoleWindow() {
        return testframe;
    }

    /**
     * Starts the compiling process and handles all is transaction like
     * outputting error if any exist.
     *
     * @param filename this file to be compiled.
     */
    public static void compileProgram(String filename) {
        testframe.compile(filename);
    }

    /**
     * Runs the specified program and handles all is transaction like getting
     * console inputs, outputs and outputting error if any exist.
     *
     * @param filename this file to be run.
     */
    public static void runProgram(String filename) {
        testframe.run(filename);
    }

    /**
     * Sets the systems look and feel, to make the console panel controls appear
     * native to the native systems looks.
     */
    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Failed to set System Look and Feel\n" + e.getMessage());
        }
    }

}
