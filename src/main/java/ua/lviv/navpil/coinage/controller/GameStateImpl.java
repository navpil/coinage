package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.*;

import java.util.*;

/**
 * Immutable state
 */
class GameStateImpl implements GameState {

    private final Collection<Coin> headsCoin;
    private final Collection<Coin> tailsCoin;
    private final Side activePlayer;
    private final List<Coin> slappedCoins;
    private final Map<String, Vertex> vertexes;
    private final Collection<Move> availableMoves;
    private final int headsPoints;
    private final int tailsPoints;
    private final List<Coin> availableCoins;

    public GameStateImpl(Collection<Coin> headsCoin, Collection<Coin> tailsCoin, Side activePlayer,
                         Collection<Coin> slappedCoins, Board board,
                         Collection<Move> availableMoves, int headsPoints, int tailsPoints) {
        this.headsCoin = immutableList(headsCoin);
        this.tailsCoin = immutableList(tailsCoin);
        this.activePlayer = activePlayer;
        this.slappedCoins = immutableList(slappedCoins);
        HashMap<String, Vertex> vertexes = new HashMap<String, Vertex>();
        for (Vertex vertex : board.getVertexes()) {
            vertexes.put(vertex.getName(), vertex);
        }
        this.vertexes = Collections.unmodifiableMap(vertexes);
        this.availableMoves = Collections.unmodifiableSet(new HashSet<Move>(availableMoves));
        this.headsPoints = headsPoints;
        this.tailsPoints = tailsPoints;
        List<Coin> availableCoins = new ArrayList<Coin>();
        for (Coin slappedCoin : slappedCoins) {
            if (slappedCoin.getSide() == activePlayer) {
                availableCoins.add(slappedCoin);
            }
        }
        this.availableCoins = Collections.unmodifiableList(availableCoins);

    }

    private List<Coin> immutableList(Collection<Coin> headsCoin) {
        return Collections.unmodifiableList(new ArrayList<Coin>(headsCoin));
    }

    @Override
    public Collection<Coin> getCoins(Side side) {
        switch (side) {
            case HEADS: return headsCoin;
            case TAILS: return tailsCoin;
        }
        return Collections.emptyList();
    }

    @Override
    public Side getActivePlayer() {
        return activePlayer;
    }

    @Override
    public List<Coin> getSlappedCoins() {
        return slappedCoins;
    }

    @Override
    public List<Coin> getAvailableCoins() {
        return availableCoins;
    }

    @Override
    public Map<String, Vertex> getVertexes() {
        return vertexes;
    }

    @Override
    public Collection<Move> getAvailableMoves() {
        return availableMoves;
    }

    @Override
    public Integer getPoints(Side side) {
        switch (side) {
            case HEADS: return headsPoints;
            case TAILS: return tailsPoints;
        }
        return 0;
    }
}
