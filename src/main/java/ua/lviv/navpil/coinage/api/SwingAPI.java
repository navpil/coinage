package ua.lviv.navpil.coinage.api;

import javax.swing.*;

public class SwingAPI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea textPane = new JTextArea();
        textPane.setText("Hello");

        frame.getContentPane().add(textPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
