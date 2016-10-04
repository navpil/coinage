package ua.lviv.navpil.coinage;

import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Game;
import ua.lviv.navpil.coinage.model.Result;

public class TextBasedAPI {

    private final Game game;
    private final GameTextAPI gameTextAPI;

    public TextBasedAPI(Game game) {
        this.game = game;
        gameTextAPI = new GameTextAPI(game);
    }

    public Result evaluate(String c) {
        //GameAPI
        Result result = handleGameAPICommand(c);
        if (result != null) {
            return result;
        }

        //Info API
        if (c.equals("coins")) {
            p(game.stats().getAvailableCoins());
        } else if (c.equals("moves")) {
            p(game.stats().getAvailableMoves());
        } else if (c.equals("player")) {
            p(game.stats().getActivePlayer());
        } else if (c.equals("show")) {
            p(new TextualBoard(game.getBoard()));
        } else if (c.equals("stats")) {
            Game.Stats stats = game.stats();
            p("Heads: " + stats.getHeadsPlayer() + ": " + stats.getHeadsPlayer().coins());
            p("Tails: " + stats.getTailsPlayer() + ": " + stats.getTailsPlayer().coins());
        } else if (c.equals("help")) {
            //show some help
        } else if (c.equals("undo")) {
            //undo all players moves after the flip
            //that is: pay, place, move, capture
        }


        //Exit - when extracting it from Main class, it was not clear how to handle that. Previous code just
        //did gameLasts = false and quit the loop. Now I need to think about a better way of handling the
        //exit command
        if (c.equals("exit")) {
            gameLasts = false;
        }
    }

    private Result handleGameAPICommand(String c) {
        return gameTextAPI.handleCommand(c);
    }


}
