package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.controller.Result;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TextBasedAPI implements TextAPI {

    private final List<TextAPI> handlers;

    public TextBasedAPI(Game game) {
        ArrayList<TextAPI> apis = new ArrayList<TextAPI>();

        apis.add(new GameActionsTextAPI(game));
        apis.add(new GameInfoTextAPI(game));
        apis.add(new ApplicationActionsTextAPI(game));
        apis.add(new TerminalTextAPI(game));

        handlers = Collections.unmodifiableList(apis);
    }

    public Result evaluate(String c) {
        for (TextAPI handler : handlers) {
            Result result = handler.evaluate(c);
            if(result != null) {
                return result;
            }
        }
        return Result.failure("Something went wrong while parsing request " + c);
    }
}
