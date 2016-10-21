package ua.lviv.navpil.coinage.api.gui;

import ua.lviv.navpil.coinage.api.gui.core.GUIController;
import ua.lviv.navpil.coinage.api.gui.swing.SwingGUI;
import ua.lviv.navpil.coinage.controller.GameImpl;

import java.io.IOException;

public class SwingAPI {

    public static void main(String[] args) throws IOException {
        SwingGUI gui = new SwingGUI();
        new GUIController(gui, new GameImpl());
    }
}
