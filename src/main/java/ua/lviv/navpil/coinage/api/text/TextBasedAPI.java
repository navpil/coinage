package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.controller.Result;

public class TextBasedAPI implements TextAPI {

    private final GameImpl game;
    private final GameTextAPI gameTextAPI;

    public TextBasedAPI(GameImpl game) {
        this.game = game;
        gameTextAPI = new GameTextAPI(game);
    }

    public Result evaluate(String c) {
        if (c.equals("exit")) {
            return Result.of(Result.Status.END_OF_GAME, "User forced to exit");
        }

        //GameAPI
        Result result = gameTextAPI.evaluate(c);
        if (result != null) {
            return result;
        }

        Result info = handleInfoCommand(c);
        if (info != null) {
            return info;
        }
        return Result.info("Command " + c + " was not executed");
    }

    private Result handleInfoCommand(String c) {
        Result info = null;
        //Info API
        if (c.equals("coins")) {
            info = Result.info("Available coins: " + game.state().getAvailableCoins());
        } else if (c.equals("moves")) {
            info = Result.info("Available moves: " + game.state().getAvailableMoves());
        } else if (c.equals("active")) {
            info = Result.info("Currently active player: " + game.state().getActivePlayer());
        } else if (c.equals("board")) {
            info = Result.info(new TextualBoard(game.getBoard()).toString());
        } else if (c.equals("players")) {
            GameImpl.State state = game.state();
            info = Result.info("Heads: " + state.getHeadsPlayer() + ": " + state.getHeadsPlayer().coins() + "\n" +
            "Tails: " + state.getTailsPlayer() + ": " + state.getTailsPlayer().coins());
        }
        else if (c.equals("help")) {
            //show some help
        } else if (c.equals("undo")) {
            //todo - this functionality is still not in place. Not the highest priority for now
            //Will be interesting to implement however - using the Memento or stack of reversible Commands
            //undo all players moves after the flip
            //that is: pay, place, move, capture
        }
        return info;
    }


}