package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GameImpl implements Game {

    private final CoinTosser coinTosser;
    private Players players;
    private List<Coin> coinsToUse;
    private List<Coin> unusableCoins;
    private List<Move> availableMoves;
    private Board board;
    private Move startingMove = Move.SLAP;

    public GameImpl(CoinTosser coinTosser) {
        players = new Players();
        coinsToUse = new ArrayList<Coin>();
        availableMoves = Arrays.asList(startingMove);
        board = new BoardImpl();
        this.coinTosser = coinTosser;
    }

    public GameImpl() {
        this(new RandomCoinTosser());
    }

    public Result slap() {
        //done
        if (!availableMoves.contains(Move.SLAP)) {
            return Result.failure("Can't slap");
        } else {

            availableMoves = new ArrayList<Move>();
            List<Coin> coinsForMove = players.getActive().getCoinsForMove();
//            p("Throw coins " + coinsForMove);

            coinsToUse = new ArrayList<Coin>();
            unusableCoins = new ArrayList<Coin>();
            System.out.println("Coins to move: " + coinsForMove);
            for (Coin coin : coinsForMove) {
                Side side = coinTosser.toss(coin);
                System.out.println("Coin " + coin + " fell on side" + side);
                if (side == players.getActive().getSide()) {
                    coinsToUse.add(coin);
//                    p(coin);
                } else {
                    unusableCoins.add(coin.flipped());
                }
            }
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
//            p("You can: " + availableMoves);
        }
    }

    public Result pass() {
        players.next().getSide();
        availableMoves = Arrays.asList(startingMove);
        coinsToUse = new ArrayList<Coin>();
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

    private boolean endOfMove() {
        if (availableMoves.isEmpty()) {
            players.next().getSide();
            availableMoves = Arrays.asList(Move.SLAP);
            coinsToUse = new ArrayList<Coin>();
            return true;
        }
        return false;
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
                return getCorrectResult("Placed");
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
                return getCorrectResult("Moved");
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
                return getCorrectResult("Captured");
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

    private Result getCorrectResult(String message) {
        if (endOfGame()) {
            availableMoves.clear();
            startingMove = Move.NONE;
            return Result.of(Result.Status.END_OF_GAME, message);
        } else if (endOfMove()) {
            return Result.endOfMove(message);
        }
        return Result.success(message);
    }

    @Override
    public GameState getState() {
        return new GameStateImpl(
                players.getPlayer(Side.HEADS).coins(),
                players.getPlayer(Side.TAILS).coins(),
                players.getActive().getSide(),
                combine(coinsToUse, unusableCoins),
                board,
                availableMoves,
                getPoints(Side.HEADS),
                getPoints(Side.TAILS)
        );
    }

    private <T> Collection<T> combine(Collection<T> ... collections) {
        ArrayList<T> result = new ArrayList<T>();
        for (Collection<T> collection : collections) {
            result.addAll(collection);
        }
        return result;
    }

    private int getPoints(Side side) {
        return board.calculate(side) + players.getPlayer(side).coins().size();
    }
}
