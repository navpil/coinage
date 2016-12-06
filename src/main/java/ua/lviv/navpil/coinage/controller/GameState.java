package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Move;
import ua.lviv.navpil.coinage.model.Side;
import ua.lviv.navpil.coinage.model.Vertex;

import java.util.Collection;
import java.util.Map;

public interface GameState {

    /**
     * Returns available coins per player
     *
     * @param side player side
     * @return coins for a player
     */
    Collection<Coin> getCoins(Side side);

    /**
     * Returns number of points for a given player
     *
     * @param side player side
     * @return number of points
     */
    Integer getPoints(Side side);

    /**
     * Which player is currently on move
     *
     * @return active player's side
     */

    Side getActivePlayer();

    /**
     * Returns all slapped coins including coins with the wrong side
     *
     * @return all slapped coins
     */
    Collection<Coin> getSlappedCoins();

    /**
     * Get all boards vertexes
     *
     * @return board vertexes
     */
    Map<String, Vertex> getVertexes();

    /**
     * Returns all moves that a player can execute
     *
     * @return possible moves
     */
    Collection<Move> getAvailableMoves();

}
