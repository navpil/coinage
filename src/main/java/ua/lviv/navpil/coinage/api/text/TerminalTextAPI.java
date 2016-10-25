package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.Result;

public class TerminalTextAPI implements TextAPI {

    public TerminalTextAPI(Game game) {
    }

    @Override
    public Result evaluate(String c) {
        return Result.info("Command " + c + " was not executed");
    }
}
