package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.Result;

public class ApplicationActionsTextAPI implements TextAPI {

    public ApplicationActionsTextAPI(Game game) {

    }

    @Override
    public Result evaluate(String c) {
        if (c.equals("exit")) {
            return Result.of(Result.Status.END_OF_GAME, "User forced to exit");
        } else if (c.equals("help")) {
            //TODO: show some help
        }
        return null;
    }
}
