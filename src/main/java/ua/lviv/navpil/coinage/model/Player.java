package ua.lviv.navpil.coinage.model;

import java.util.*;
import java.util.stream.Collectors;

public class Player {

    private final Side side;
    private final Map<CoinSize, List<Coin>> coins;

    public Player(Side side) {
        this.side = side;
        coins = new TreeMap<>();

        addCoins(CoinSize.DIME, 4);
        addCoins(CoinSize.PENNY, 3);
        addCoins(CoinSize.NICKEL, 2);
        addCoins(CoinSize.QUARTER, 1);
    }

    private void addCoins(CoinSize type, int count) {
        ArrayList<Coin> c = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            c.add(new Coin(type, side));
        }
        coins.put(type, c);
    }

    public List<Coin> getCoinsForMove() {
        List<Coin> result = coins.values().stream()
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .collect(Collectors.toList());
        if(result.isEmpty()) {
            throw new IllegalStateException("Game should have ended by now");
        }
        return result;
    }

    public Side getSide() {
        return side;
    }

    public List<Coin> coins () {
        return coins.values().stream().reduce(
                new ArrayList<>(),
                (allCoins, c) -> {
                    allCoins.addAll(c);
                    return allCoins;
                }
        );
    }

    public void remove(Coin coin) {
        coins.get(coin.getSize()).remove(0);
    }

    public void add(Coin coin) {
        coins.get(coin.getSize()).add(new Coin(coin.getSize(), getSide()));
    }

    @Override
    public String toString() {
        return "Player{" +
                "side=" + side +
                '}';
    }
}
