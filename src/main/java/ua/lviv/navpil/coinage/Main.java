package ua.lviv.navpil.coinage;

import ua.lviv.navpil.coinage.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        //Command line

        //todo: add CLI class
        //todo: handle different errors and avoid NPEs
        //todo: lowercase everything

        String c;
        boolean gameLasts = true;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Game game = new Game();

        while (gameLasts) {
            shell();
            c = br.readLine();
            Result result = null;
            if (c.equals("exit")) {
                gameLasts = false;
            } else if (c.equals("slap")) {
                result = game.slap();
            } else if (c.equals("coins")) {
                p(game.stats().getAvailableCoins());
            } else if (c.equals("pass")) {
                result = game.pass();
            } else if (c.startsWith("pay")) {
                String coinType = c.split(" ")[1];
                result = game.pay(parseSize(coinType));
            } else if (c.equals("moves")) {
                p(game.stats().getAvailableMoves());
            } else if (c.equals("player")) {
                p(game.stats().getActivePlayer());
            } else if (c.startsWith("place")) {
                String[] split = c.split(" ");
                result = game.place(parseSize(split[1]), split[2]);
            } else if (c.startsWith("move")) {
                String[] split = c.split(" ");
                String from = split[1];
                String to = split[2];
                result = game.move(from, to);
            } else if (c.startsWith("capture")) {
                String[] split = c.split(" ");
                String pos = split[1];
                game.capture(pos);
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

            if(game.endOfGame()) {
                gameLasts = false;
                int heads = game.stats().getPoints(Side.HEAD);
                int tails = game.stats().getPoints(Side.TAILS);

                p("HEADS got " + heads);
                p("TAILS got " + tails);

                if(heads == tails) {
                    p("Deuce");
                } if(heads > tails) {
                    p("HEADS won");
                }else {
                    p("TAILS won");
                }
            } else if(result != null) {
                p(result);
                Game.Stats stats = game.stats();

                if(stats.getAvailableMoves().isEmpty()) {
                    result = game.pass();
                    p(result);
                }

                if(stats.getAvailableMoves().contains(Move.SLAP)) {
                    result = game.slap();
                    p(result);
                }
                p(stats.getActivePlayer() + ", moves " + stats.getAvailableMoves() + ", coins: " + stats.getAvailableCoins());
            }
        }
    }

    private static CoinSize parseSize(String coinType) {
        for (CoinSize coinSize : CoinSize.values()) {
            if(coinSize.name().toLowerCase().startsWith(coinType.toLowerCase())) {
                return coinSize;
            }
        }
        return null;
    }

    private static void shell() {
        System.out.print("coinage>");
    }

    private static void p(Object s) {
        System.out.println(s);
    }
}
