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

import com.mwaka.ambrose.codefx.utils.Dialogs;
import com.mwaka.ambrose.codefx.utils.FileHelper;
import com.mwaka.ambrose.codefx.utils.projectconfigs.Configs;
import java.awt.Color;
import java.awt.Font;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * This class creates the editor and handles all events related to it.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 0.8
 */
public class Editor {

    public RSyntaxTextArea textArea;

    /**
     * Initializes the code editor (that provides syntax highlighting and
     * editing)
     */
    public Editor() {
        textArea = new RSyntaxTextArea(40, 70);
    }

    /**
     * This method sets the font type and font size of the editor please not
     * that the editor font style is plain and you can't change it.
     *
     * @param fontName the font name
     * @param fontSize the font size
     * @return the new font property passed it.
     */
    public Font setEditorFontProperty(String fontName, int fontSize) {
        return new Font(fontName, Font.PLAIN, fontSize);
    }

    /**
     * This method creates the text editor currently its been hard coded to use
     * syntax highlighting for JAVA language only but it support over 20
     * languages syntax highlighting.
     *
     * @return The swing node the will used in the javaFX program since the
     * editor was implemented in Swing.
     */
    private SwingNode createEditor(RSyntaxTextArea textArea, String data) {

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        textArea.setCodeFoldingEnabled(true);

        textArea.setFont(new Font(Configs.getFontStyle("fontstyle"), Font.BOLD, Configs.getFontSize("fontsize")));

        textArea.setBackground(new Color(248, 248, 248));

        textArea.setText(data);

        RTextScrollPane rtScroll = new RTextScrollPane(textArea);

        SwingNode node = new SwingNode();

        CompletionProvider provider = createCompletionProvider();

        AutoCompletion ac = new AutoCompletion(provider);

        ac.install(textArea);

        createSwingNode(node, rtScroll);

        return node;

    }

    /**
     * This method provide basic auto completion for JAVA key words and some
     * other short hand/ shortcuts to help speed up programming
     */
    private CompletionProvider createCompletionProvider() {
        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();
        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.       
        
        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sout", "System.out.println(\"\");", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "serr", "System.err.println(\"\");", "System.err.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "if", "if(condition){}", "if"));
        provider.addCompletion(new ShorthandCompletion(provider, "while", "while(condition){}", "while"));
        provider.addCompletion(new ShorthandCompletion(provider, "try", "try{}catch(Exception e){}", "try"));
        provider.addCompletion(new ShorthandCompletion(provider, "for", "for(int i = 0; i <condition; i++){}", "for"));
        provider.addCompletion(new ShorthandCompletion(provider, "do", "do{/*code to be executed here*/}while(condition);", "do"));
        provider.addCompletion(new ShorthandCompletion(provider, "each", "for(Object name: Object Array){ /*Do something with the object*/}", "each"));
        provider.addCompletion(new ShorthandCompletion(provider, "switch", "switch(status){case condition: /*code to be executed when condition is met;*/ break; }", "switch"));
        
        provider.addCompletion(new ShorthandCompletion(provider, "main",
                "/**\n"
                + "	* @param ams\n"
                + "	*/\n"
                + "	public static void main (String[] ams){\n"
                + "\t\tSystem.out.println(\"Hello world\");\n"
                + "	}",
                "/**\n"
                + "	* @param ams\n"
                + "	*/\n"
                + "	public static void main (String[] ams){\n"
                + "	System.out.println(\"Hello world\");\n"
                + "	}"));

        provider.addCompletion(new ShorthandCompletion(provider, "class",
                "public class ClassNameHere{\n\n}", "public class ClassNameHere{\n\n}"));

        provider.addCompletion(new ShorthandCompletion(provider, "method",
                "/**\n"
                + "	* This is the javadoc comment. describe what the method does in detail here\n"
                + "	*/\n"
                + "	public returnType methodName(parameters){\n"
                + "		//TODO code here\n"
                + "		return value;\n"
                + "	}",
                "/**\n"
                + "	 * This is the javadoc comment. describe what the method does in detail here\n"
                + "	*/\n"
                + "	public returnType methodName(parameters){\n"
                + "		//TODO code here\n"
                + "		return value;\n"
                + "	}"));

        return provider;
    }

    /**
     * This method creates the swing node in its own thread according to the
     * java documentation
     */
    private void createSwingNode(SwingNode swingNode, JComponent jcomponent) {
        SwingUtilities.invokeLater(() -> {
            try {
                String laf = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(laf);
                swingNode.setContent(jcomponent);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {

            }
        });
    }

    public Tab createEditorTab(String fileName, ImageView icon, String initData) {
        Tab tab = new Tab(fileName, createEditor(textArea, initData));
        tab.setGraphic(icon);

        tab.setOnCloseRequest((Event event) -> {
            boolean confirmatioDialogReturnValue = Dialogs.confirmatioDialog();
            if (confirmatioDialogReturnValue) {
                String data = getEditorContents(tab);
                FileHelper.saveFileToProject(Configs.getActiveProjectDir(), tab.getText(), data);
            } else {
                CodeFX.statusLable.setText("Cancelled!");
            }
        });

        return tab;
    }

    /**
     * This method is used to get the contents of the editor. Its created to
     * simplify issues of having to read from a Swing thread and pass its
     * contents to a javaFX thread.
     *
     * @param tab the active tab whose editor contents needs to be returned
     * @return the string contents of the editor.
     */
    public String getEditorContents(Tab tab) {
        String contents;
        SwingNode node = (SwingNode) tab.getContent();
        RTextScrollPane scroll = (RTextScrollPane) node.getContent();
        textArea = (RSyntaxTextArea) scroll.getTextArea();
        contents = textArea.getText();
        return contents;
    }

}
