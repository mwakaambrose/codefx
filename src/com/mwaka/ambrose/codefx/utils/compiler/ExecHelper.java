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

import com.mwaka.ambrose.codefx.utils.projectconfigs.Configs;
import java.io.*;

/**
 * This is an execution helper for the process its based on a project from an
 * article on
 * <a href="www.codeproject.com/Articles/6635/Easier-Execution-Of-External-Proesses-In-Java">CodeProject</a>
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 */
public class ExecHelper implements Runnable {

    // Allocate 1Kb buffers for Input and Error Streams..
    private byte[] inBuffer = new byte[1024];
    private byte[] errBuffer = new byte[1024];

    // Declare internal variables we will need..
    private Process process;
    private InputStream pErrorStream;
    private InputStream pInputStream;
    private OutputStream pOutputStream;
    private PrintWriter outputWriter;
    private Thread processThread;
    private Thread inReadThread;
    private Thread errReadThread;
    private ExecProcessor handler;

    /**
     * Private constructor so that no one can create a new ExecHelper directly..
     */
    private ExecHelper(ExecProcessor ep, Process p) {
        // Save variables..
        handler = ep;
        process = p;
        // Get the streams..
        pErrorStream = process.getErrorStream();
        pInputStream = process.getInputStream();
        pOutputStream = process.getOutputStream();
        // Create a PrintWriter on top of the output stream..
        outputWriter = new PrintWriter(pOutputStream, true);
        // Create the threads and start them..
        processThread = new Thread(this);
        inReadThread = new Thread(this);
        errReadThread = new Thread(this);
        // Start Threads..
        processThread.start();
        inReadThread.start();
        errReadThread.start();
    }

    private void processEnded(int exitValue) {
        // Handle process end..
        handler.processEnded(exitValue);
    }

    private void processNewInput(String input) {
        // Handle process new input..
        handler.processNewInput(input);
    }

    private void processNewError(String error) {
        // Handle process new error..
        handler.processNewError(error);
    }

    /**
     * Run the command and return the ExecHelper wrapper object..
     *
     * @param handler ExecProcessor
     * @param command to be executed.
     * @return the ExecHelper object
     * @throws java.io.IOException
     */
    public static ExecHelper exec(ExecProcessor handler, String command) throws IOException {
        return new ExecHelper(handler, Runtime.getRuntime().exec(command, null, new File(Configs.getActiveProjectDir())));
    }

    /**
     * Print the output string through the print writer..
     *
     * @param output
     */
    public void print(String output) {
        outputWriter.print(output);
    }

    /**
     * Print the output string (and a CRLF pair) through the print writer..
     *
     * @param output thats being written to the underlying print stream / output
     * stream.
     */
    public void println(String output) {
        outputWriter.println(output);
    }

    @Override
    public void run() {
        // Are we on the process Thread?
        if (processThread == Thread.currentThread()) {
            try {
                // This Thread just waits for the process to end and notifies the handler..
                processEnded(process.waitFor());
            } catch (InterruptedException ex) {
                System.out.println("process thread: " + ex.getMessage());
            }
            // Are we on the InputRead Thread?
        } else if (inReadThread == Thread.currentThread()) {
            try {
                // Read the InputStream in a loop until we find no more bytes to read..
                for (int i = 0; i > -1; i = pInputStream.read(inBuffer)) {
                    // We have a new segment of input, so process it as a String..
                    processNewInput(new String(inBuffer, 0, i));
                }
            } catch (IOException ex) {
                System.out.println("inRead thread: " + ex.getMessage());
            }
            // Are we on the ErrorRead Thread?
        } else if (errReadThread == Thread.currentThread()) {
            try {
                // Read the ErrorStream in a loop until we find no more bytes to read..
                for (int i = 0; i > -1; i = pErrorStream.read(errBuffer)) {
                    // We have a new segment of error, so process it as a String..
                    processNewError(new String(errBuffer, 0, i));
                }
            } catch (IOException ex) {
                System.out.println("errRead thread: " + ex.getMessage());
            }
        }
    }
}
