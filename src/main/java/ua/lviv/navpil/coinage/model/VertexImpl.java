package ua.lviv.navpil.coinage.model;

import java.util.*;

class VertexImpl implements Vertex {

    private final String name;
    private final Set<String> adjacent = new HashSet<String>();
    private final List<Coin> coins = new ArrayList<Coin>();
    private String region;

    public VertexImpl(String name) {
        this.name = name;
    }

    @Override
    public List<Coin> getCoins() {
        return Collections.unmodifiableList(new ArrayList<Coin>(coins));
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getName() {
        return name;
    }

    void join(VertexImpl other) {
        this.adjacent.add(other.name);
        other.adjacent.add(this.name);
    }

    boolean isAdjacent(String name) {
        return adjacent.contains(name);
    }

    void add(Coin coin) {
        coins.add(coin);
    }

    boolean remove(Coin coin) {
        return coins.remove(coin);
    }

    void setRegion(String region) {
        this.region = region;
    }


    boolean belongsTo(Side side) {
        return !coins.isEmpty() && (topCoin().getSide() == side);
    }

    int getValue() {
        return coins.isEmpty() ? 0 : topCoin().getSize().getValue();
    }

    private Coin topCoin() {
        return coins.get(coins.size() - 1);
    }

    @Override
    public String toString() {
        return "VertexImpl{" +
                "name='" + name + '\'' +
                ", coins=" + coins +
                ", region='" + region + '\'' +
                '}';
    }
}