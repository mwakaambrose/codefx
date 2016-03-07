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

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class creates the GUI Panel and all its components and handle all the
 * components events. its swing based with all its components
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 */
public class GuiPanel extends JPanel implements ExecProcessor {

    private JLabel statusBar = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTextField jTextField1 = new JTextField();
    private JTextArea jTextArea1 = new JTextArea("EvelyneBuilder [Version 1.0.0.Feb2016]\n(c) 2016 Mwaka Ambrose, JIMTECH Co, Ltd. All rights reserved.\n\n");
    private JScrollPane jScrollPane1 = new JScrollPane();
    private ExecHelper exh;

    //Construct the frame
    public GuiPanel() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {

        this.setLayout(borderLayout1);
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setDebugGraphicsOptions(0);
        statusBar.setDoubleBuffered(true);
        statusBar.setOpaque(false);
        statusBar.setVerifyInputWhenFocusTarget(true);
        statusBar.setText(" Ready...");
        jTextField1.setBackground(UIManager.getColor("control"));
        jTextField1.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(103, 101, 98), new Color(148, 145, 140)), "Input"), BorderFactory.createEmptyBorder(-2, 0, -2, 0)));
        jTextField1.setDoubleBuffered(true);
        jTextField1.setOpaque(false);
        jTextField1.setCaretColor(Color.black);
        jTextField1.setCaretPosition(0);
        jTextField1.setText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputActionPerformed(e);
            }
        });
        this.setBackground(UIManager.getColor("control"));
        this.setDoubleBuffered(true);
        this.setOpaque(true);
        jTextArea1.setBackground(UIManager.getColor("control"));
        jScrollPane1.getViewport().setBackground(UIManager.getColor("control"));
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setBorder(new TitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(103, 101, 98), new Color(148, 145, 140)), "Output"));
        jScrollPane1.setOpaque(false);
        jTextArea1.setDoubleBuffered(true);
        jTextArea1.setOpaque(false);
        jTextArea1.setFont(new Font("Consolas", Font.PLAIN, 14));
        jTextArea1.setWrapStyleWord(true);

        this.add(statusBar, BorderLayout.SOUTH);
        this.add(jScrollPane1, BorderLayout.CENTER);
        this.add(jTextField1, BorderLayout.NORTH);
        jScrollPane1.getViewport().add(jTextArea1, null);
    }

    private void updateTextArea(JTextArea textArea, String line) {
        textArea.append(line);
        textArea.setSelectionStart(textArea.getText().length());
        textArea.setSelectionEnd(textArea.getText().length());
    }

    //File | Exit action performed
    public void jMenuFileExitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    public void processNewInput(String input) {
        updateTextArea(jTextArea1, input);
    }

    public void processNewError(String error) {
        updateTextArea(jTextArea1, error);
    }

    public void processEnded(int exitValue) {
        statusBar.setText("Process ended..");
        if (exitValue == 0) {
            jTextArea1.append("\nBUILD SUCCESSFUL...\n");
        } else if (exitValue == 1) {
            jTextArea1.append("\nBUILD FAILED...\n");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        exh = null;
        statusBar.setText("Ready..");
    }

    void inputActionPerformed(ActionEvent e) {
        if (exh != null) {
            exh.println(jTextField1.getText());
            jTextArea1.append(jTextField1.getText() + "\n");
            jTextField1.setText(null);
        }
    }

    public void compile(String filename) {
        if (exh == null) {
            try {
                exh = ExecHelper.exec(this, filename);
                statusBar.setText("Compiling....");
                jTextArea1.append("Now Compiling please wait....");
            } catch (IOException ex) {
                processNewError(ex.getMessage());
            }
        }
    }

    public void run(String filename) {
        if (exh == null) {
            try {
                exh = ExecHelper.exec(this, filename);
                statusBar.setText("Running....");
            } catch (IOException ex) {
                processNewError(ex.getMessage());
            }
        }
    }
}
