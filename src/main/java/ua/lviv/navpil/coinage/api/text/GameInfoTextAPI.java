package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.controller.Result;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;
import ua.lviv.navpil.coinage.util.CoreUtil;

import java.util.List;

public class GameInfoTextAPI implements TextAPI {

    private final Game game;

    public GameInfoTextAPI(Game game) {
        this.game = game;
    }

    public Result evaluate(String c) {
        Result info = null;
        //Info API
        if (c.equals("coins")) {
            info = Result.info("Available coins: " + getAvailableCoins());
        } else if (c.equals("moves")) {
            info = Result.info("Available moves: " + game.getState().getAvailableMoves());
        } else if (c.equals("active")) {
            info = Result.info("Currently active player: " + game.getState().getActivePlayer());
        } else if (c.equals("board")) {
            info = Result.info(new TextualBoard(game.getState().getVertexes()).toString());
        } else if (c.equals("players")) {
            GameState state = game.getState();
            info = Result.info("Heads: " + state.getCoins(Side.HEADS) + "\n" +
                    "Tails: " + state.getCoins(Side.TAILS));
        }
        return info;
    }

    private List<Coin> getAvailableCoins() {
        return CoreUtil.getAvailableCoins(game.getState().getSlappedCoins(), game.getState().getActivePlayer());
    }



}
