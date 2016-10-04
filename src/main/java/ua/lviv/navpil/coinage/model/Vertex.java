package ua.lviv.navpil.coinage.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex {
    private final String name;
    private final Set<String> adjacent = new HashSet<String>();
    private final List<Coin> coins = new ArrayList<Coin>();
    private String region;

    public Vertex(String name) {
        this.name = name;
    }

    public void join(Vertex other) {
        this.adjacent.add(other.name);
        other.adjacent.add(this.name);
    }

    public boolean isAdjacent(String name) {
        return adjacent.contains(name);
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public boolean belongsTo(Side side) {
        return !coins.isEmpty() && (topCoin().getSide() == side);
    }

    public int getValue() {
        return coins.isEmpty() ? 0 : topCoin().getSize().getValue();
    }

    private Coin topCoin() {
        return coins.get(coins.size() - 1);
    }
}
