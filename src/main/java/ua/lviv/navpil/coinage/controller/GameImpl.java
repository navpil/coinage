package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameImpl implements Game {

    private final CoinTosser coinTosser;
    private Players players;
    private List<Coin> coinsToUse;
    private List<Coin> unusableCoins;
    private List<Move> availableMoves;
    private Board board;

    public GameImpl(CoinTosser coinTosser) {
        players = new Players();
        coinsToUse = new ArrayList<>();
        unusableCoins = new ArrayList<>();
        availableMoves = Collections.singletonList(Move.SLAP);
        board = new BoardImpl();
        this.coinTosser = coinTosser;
    }

    public GameImpl() {
        this(new RandomCoinTosser());
    }

    public Result slap() {
        if (!availableMoves.contains(Move.SLAP)) {
            return Result.failure("Can't slap");
        } else {
            availableMoves = new ArrayList<>();
            List<Coin> coinsForMove = players.getActive().getCoinsForMove();

            coinsToUse = new ArrayList<>();
            unusableCoins = new ArrayList<>();
            System.out.println("Coins to move: " + coinsForMove);
            for (Coin coin : coinsForMove) {
                Side side = coinTosser.toss(coin);
                if (side == players.getActive().getSide()) {
                    coinsToUse.add(coin);
                } else {
                    unusableCoins.add(coin.flipped());
                }
            }
            availableMoves.add(Move.PASS);
            if (coinsToUse.size() == 4) {
                availableMoves.add(Move.PAY);
            }
            if (coinsToUse.size() > 1) {
                availableMoves.add(Move.PLACE);
                availableMoves.add(Move.PLACE);
            } else if (coinsToUse.size() == 1) {
                availableMoves.add(Move.PLACE);
                availableMoves.add(Move.MOVE);
            } else {
                availableMoves.add(Move.CAPTURE);
                availableMoves.add(Move.MOVE);
            }
            return Result.success(coinsToUse.toString());
        }
    }

    public Result pass() {
        if (!availableMoves.contains(Move.PASS)) {
            return Result.failure("Can't pass, game was ended");
        }
        coinsToUse = new ArrayList<>();
        if (endOfGame()) {
            availableMoves.clear();
            return Result.of(Result.Status.END_OF_GAME, "Game ended");
        }
        players.next();
        availableMoves = Collections.singletonList(Move.SLAP);
        return Result.success("Passed");
    }

    public Result pay(CoinSize coinSize) {
        nullCheck(coinSize);
        if (!availableMoves.contains(Move.PAY)) {
            return Result.failure("Can't pay");
        } else {
            Coin coin = getCoin(coinSize);
            if (coin == null) {
                return Result.failure("Don't have the coin " + coinSize);
            }
            players.getActive().remove(coin);
            coinsToUse.remove(coin);
            players.getPassive().add(coin);
            availableMoves.remove(Move.PAY);
            availableMoves.add(Move.PLACE);
            return Result.success("Pay the coin " + coin);
        }
    }

    private void nullCheck(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    public Result place(CoinSize coinSize, String position) {
        if (!availableMoves.contains(Move.PLACE)) {
            return Result.failure("Can't place");
        } else {
            Coin coin = getCoin(coinSize);
            if (coin == null) {
                return Result.failure("Don't have the coin " + coinSize);
            }
            if (board.canPlace(coin, position)) {
                players.getActive().remove(coin);
                coinsToUse.remove(coin);
                board.place(coin, position);
                availableMoves.remove(Move.PLACE);
                return Result.success("Placed");
            } else {
                return Result.failure("Place is not possible");
            }
        }
    }

    public Result move(String from, String to) {
        if(!availableMoves.contains(Move.MOVE)) {
            return Result.failure("Can't move");
        } else {
            if(board.canMove(from, to)) {
                board.move(from, to);
                availableMoves.remove(Move.MOVE);
                return Result.success("Moved");
            } else {
                return Result.failure("Move is not possible");
            }
        }

    }

    public Result capture(String pos) {
        if(!availableMoves.contains(Move.CAPTURE)) {
            return Result.failure("Can't capture");
        } else {
            if(board.canCapture(pos)) {
                Coin captured = board.capture(pos);
                players.getActive().add(captured);
                availableMoves.remove(Move.CAPTURE);
                return Result.success("Captured");
            } else {
                return Result.failure("Capture not possible");
            }
        }
    }

    private Coin getCoin(CoinSize coinSize) {
        for (Coin coin : coinsToUse) {
            if (coin.getSize() == coinSize) {
                return coin;
            }
        }
        return null;
    }

    private boolean endOfGame() {
        return board.isFull() || players.getActive().coins().isEmpty() || players.getPassive().coins().isEmpty();
    }

    @Override
    public GameState getState() {
        return new GameStateImpl(
                players.getPlayer(Side.HEADS).coins(),
                players.getPlayer(Side.TAILS).coins(),
                players.getActive().getSide(),
                Stream.concat(coinsToUse.stream(), unusableCoins.stream()).collect(Collectors.toList()),
                board,
                availableMoves,
                getPoints(Side.HEADS),
                getPoints(Side.TAILS)
        );
    }

    private int getPoints(Side side) {
        return board.calculate(side) + players.getPlayer(side).coins().size();
    }
}
