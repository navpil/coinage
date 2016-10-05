package ua.lviv.navpil.coinage.model;

import java.util.List;

public interface Vertex {

    List<Coin> getCoins();

    String getName();

    String getRegion();
}
