package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.controller.Result;
import ua.lviv.navpil.coinage.model.Move;
import ua.lviv.navpil.coinage.model.Side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellAPI {

    private final GameImpl game;

    public ShellAPI(GameImpl game) {
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

            if(result.getStatus() == Result.Status.END_OF_GAME) {
                gameLasts = false;

                showEndingInfo();
            } else {
                p(result);
                GameImpl.State state = game.state();

                autoPass();
                autoSlap();

                p(state.getActivePlayer() + ", moves " + state.getAvailableMoves() + ", coins: " + state.getAvailableCoins());
            }
        }
    }

    private void autoSlap() {
        Result result;
        if(game.state().getAvailableMoves().contains(Move.SLAP)) {
            result = game.slap();
            p(result);
        }
    }

    private void autoPass() {
        Result result;
        if(game.state().getAvailableMoves().isEmpty()) {
            result = game.pass();
            p(result);
        }
    }

    private void showEndingInfo() {
        int heads = game.state().getPoints(Side.HEADS);
        int tails = game.state().getPoints(Side.TAILS);

        p("HEADS got " + heads);
        p("TAILS got " + tails);

        if(heads == tails) {
            p("Deuce");
        }
        if(heads > tails) {
            p("HEADS won");
        }else {
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
