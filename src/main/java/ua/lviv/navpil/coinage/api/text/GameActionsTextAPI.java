package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.controller.Game;
import ua.lviv.navpil.coinage.controller.Result;

public class GameActionsTextAPI implements TextAPI {

    private final Game game;

    public GameActionsTextAPI(Game game) {
        this.game = game;
    }

    public Result evaluate(String c) {
        Result result = null;
        if (c.equals("slap")) {
            result = game.slap();
        } else if (c.equals("pass")) {
            result = game.pass();
        } else if (c.startsWith("pay ")) {
            String coinType = c.split(" ")[1];
            CoinSize coinSize = parseSize(coinType);
            if (coinSize == null) {
                return Result.failure("Can't parse coin from command: " + c);
            }
            result = game.pay(coinSize);
        } else if (c.startsWith("place ")) {
            String[] split = c.split(" ");
            CoinSize coinSize = parseSize(split[1]);
            if (coinSize == null || split.length < 3) {
                return Result.failure("Can't parse command: " + c);
            }
            result = game.place(coinSize, split[2]);
        } else if (c.startsWith("move ")) {
            String[] split = c.split(" ");
            if (split.length < 3) {
                return Result.failure("Can't parse command: " + c);
            }
            String from = split[1];
            String to = split[2];
            result = game.move(from, to);
        } else if (c.startsWith("capture ")) {
            String[] split = c.split(" ");
            String pos = split[1];
            result = game.capture(pos);
        } else if (c.equals("undo")) {
            //todo - this functionality is still not in place. Not the highest priority for now
            //Will be interesting to implement however - using the Memento or stack of reversible Commands
            //undo all players moves after the flip
            //that is: pay, place, move, capture
        }
        return result;
    }

    private static CoinSize parseSize(String coinType) {
        for (CoinSize coinSize : CoinSize.values()) {
            if (coinSize.name().toLowerCase().startsWith(coinType.toLowerCase())) {
                return coinSize;
            }
        }
        return null;
    }
}
