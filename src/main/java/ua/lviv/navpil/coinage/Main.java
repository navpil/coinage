package ua.lviv.navpil.coinage;

import ua.lviv.navpil.coinage.api.ShellAPI;
import ua.lviv.navpil.coinage.controller.GameImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new ShellAPI(new GameImpl()).start();
    }
}
