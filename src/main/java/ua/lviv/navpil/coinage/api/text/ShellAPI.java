package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.controller.Result;
import ua.lviv.navpil.coinage.model.Move;
import ua.lviv.navpil.coinage.model.Side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static ua.lviv.navpil.coinage.util.CoreUtil.getAvailableCoins;

public class ShellAPI {

    private final Game game;

    public ShellAPI(Game game) {
        this.game = game;
    }

    public void start() throws IOException {
        //Command line
        String c;
        boolean gameLasts = true;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TextBasedAPI textBasedAPI = new TextBasedAPI(game);

        autoSlap();
        while (gameLasts) {
            showPrompt();
            c = br.readLine();
            Result result = textBasedAPI.evaluate(c);

            if (result.getStatus() == Result.Status.END_OF_GAME) {
                gameLasts = false;

                showEndingInfo();
            } else {
                p(result);
                GameState state = game.getState();

                autoPass();
                autoSlap();

                p(state.getActivePlayer() + ", moves " + state.getAvailableMoves() +
                        ", coins: " + getAvailableCoins(state.getSlappedCoins(), state.getActivePlayer()));
            }
        }
    }

    private void autoSlap() {
        Result result;
        if (game.getState().getAvailableMoves().contains(Move.SLAP)) {
            result = game.slap();
            p(result);
        }
    }

    private void autoPass() {
        Result result;
        if (game.getState().getAvailableMoves().isEmpty()) {
            result = game.pass();
            p(result);
        }
    }

    private void showEndingInfo() {
        int heads = game.getState().getPoints(Side.HEADS);
        int tails = game.getState().getPoints(Side.TAILS);

        p("HEADS got " + heads);
        p("TAILS got " + tails);

        if (heads == tails) {
            p("Deuce");
        } else if (heads > tails) {
            p("HEADS won");
        } else {
            p("TAILS won");
        }
    }

    private static void showPrompt() {
        System.out.print("coinage>");
    }

    private static void p(Object s) {
        System.out.println(s);
    }
}
