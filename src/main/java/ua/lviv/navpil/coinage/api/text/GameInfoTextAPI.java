package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.controller.Result;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameInfoTextAPI implements TextAPI {

    private final Game game;

    public GameInfoTextAPI(Game game) {
        this.game = game;
    }

    public Result evaluate(String c) {
        Result info = null;
        //Info API
        if (c.equals("coins")) {
            info = Result.info("Available coins: " + availableCoins());
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

    private ArrayList<Coin> availableCoins() {
        ArrayList<Coin> availableCoins = new ArrayList<Coin>();
        Collection<Coin> slappedCoins = game.getState().getSlappedCoins();
        for (Coin coin : slappedCoins) {
            if (coin.getSide() == game.getState().getActivePlayer()) {
                availableCoins.add(coin);
            }
        }
        Collections.sort(availableCoins);
        return availableCoins;
    }


}
