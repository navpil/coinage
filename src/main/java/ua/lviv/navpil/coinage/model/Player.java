package ua.lviv.navpil.coinage.model;

import java.util.*;

public class Player {

    private final Side side;
    private final Map<CoinSize, List<Coin>> coins;

    public Player(Side side) {
        this.side = side;
        coins = new TreeMap<CoinSize, List<Coin>>();

        addCoins(CoinSize.DIME, 4);
        addCoins(CoinSize.PENNY, 3);
        addCoins(CoinSize.NICKEL, 2);
        addCoins(CoinSize.QUARTER, 1);
    }

    private void addCoins(CoinSize type, int count) {
        ArrayList<Coin> c = new ArrayList<Coin>();
        for (int i = 0; i < count; i++) {
            c.add(new Coin(type, side));
        }
        coins.put(type, c);
    }

    public List<Coin> getCoinsForMove() {
        List<Coin> result = new ArrayList<Coin>();
        for (List<Coin> list : coins.values()) {
            if (!list.isEmpty()) {
                result.add(list.get(0));
            }
        }
        if(result.isEmpty()) {
            throw new IllegalStateException("Game should have ended by now");
        }
        return result;
    }

    public Side getSide() {
        return side;
    }

    public List<Coin> coins () {
        List<Coin> allCoins = new ArrayList<Coin>();
        for (List<Coin> coinList : coins.values()) {
            allCoins.addAll(coinList);
        }
        return allCoins;
    }

    @Override
    public String toString() {
        return "Player{" +
                "side=" + side +
                '}';
    }

    public void remove(Coin coin) {
        coins.get(coin.getSize()).remove(0);
    }

    public void add(Coin coin) {
        coin.setSide(getSide());
        coins.get(coin.getSize()).add(coin);
    }
}
