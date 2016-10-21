package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Move;
import ua.lviv.navpil.coinage.model.Side;
import ua.lviv.navpil.coinage.model.Vertex;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GameState {

    Collection<Coin> getCoins(Side side);

    Integer getPoints(Side side);

    Side getActivePlayer();

    Collection<Coin> getSlappedCoins();

    List<Coin> getAvailableCoins();

    Map<String, Vertex> getVertexes();

    Collection<Move> getAvailableMoves();

}
