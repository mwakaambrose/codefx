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

/**
 * This class is used to generate boiler plate code for projects.
 *
 * @author MWAKA AMBROSE
 * <p>
 * </p><a href="mailto:ambrosemwaka@gmail.com">-Email Me</a>
 * <p>
 * </p><a href="facebook.com/sxambrose">-Add Me On Facebook</a>
 * @version 1.0
 * @since version 1.0
 */
public class TemplateCode {

    /**
     * Boiler plate code for java program, when its created and create main
     * class radio button is checked
     *
     * @param className the name of the class to use in the boiler plate code
     * @return a formatted string of the complete boiler code to be passed into
     * a project being created.
     */
    public static String javaTemplateWithMainClass(String className) {
        return "/*\n"
                + "* Program description here"
                + "\n*/\n"
                + "public class " + className.replace(".java", "") + "{\n"
                + "\tpublic static void main(String[] ams){\n\t}"
                + "\n}";
    }

    /**
     * Boiler plate code for java program, when its created and create without
     * main class radio button is checked
     *
     * @param className the name of the class to use in the boiler plate code
     * @return a formatted string of the complete boiler code to be passed into
     * a project being created.
     */
    public static String javaTemplateWithoutMainClass(String className) {
        return "/*\n"
                + "* Program description here"
                + "\n*/\n"
                + "public class " + className.replace(".java", "") + "{\n"
                + "\n}";
    }

    /**
     * Boiler plate code for javaFX program, doesn't matter wither create main
     * method is checked or not, it just creates it.
     *
     * @param className the name of the class to use in the boiler plate code
     * @return a formatted string of the complete boiler code to be passed into
     * a project being created.
     */
    public static String javaFxTemplate(String className) {
        return "/*\n"
                + "* The application description here\n"
                + "*/\n"
                + "import javafx.application.Application;\n"
                + "import javafx.event.ActionEvent;\n"
                + "import javafx.event.EventHandler;\n"
                + "import javafx.scene.Scene;\n"
                + "import javafx.scene.control.Button;\n"
                + "import javafx.scene.layout.StackPane;\n"
                + "import javafx.stage.Stage;\n"
                + "\n"
                + "public class " + className.replace(".java", "") + " extends Application {\n"
                + "    \n"
                + "    @Override\n"
                + "    public void start(Stage primaryStage) {\n"
                + "        Button btn = new Button();\n"
                + "        btn.setText(\"Say 'Hello World'\");\n"
                + "        btn.setOnAction(new EventHandler<ActionEvent>() {\n"
                + "            \n"
                + "            @Override\n"
                + "            public void handle(ActionEvent event) {\n"
                + "                System.out.println(\"Hello World!\");\n"
                + "            }\n"
                + "        });\n"
                + "        \n"
                + "        StackPane root = new StackPane();\n"
                + "        root.getChildren().add(btn);\n"
                + "        \n"
                + "        Scene scene = new Scene(root, 300, 250);\n"
                + "        \n"
                + "        primaryStage.setTitle(\"Hello World!\");\n"
                + "        primaryStage.setScene(scene);\n"
                + "        primaryStage.show();\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * @param args the command line arguments\n"
                + "     */\n"
                + "    public static void main(String[] args) {\n"
                + "        launch(args);\n"
                + "    }\n"
                + "    \n"
                + "}\n";
    }
}
