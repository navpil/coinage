package ua.lviv.navpil.coinage.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Rules implements Iterable<Map.Entry<CoinSize, Integer>>{
    private final Map<CoinSize, Integer> coins = new HashMap<CoinSize, Integer>();

    public Rules coins(CoinSize size, Integer count) {
        coins.put(size, count);
        return this;
    }

    @Override
    public Iterator<Map.Entry<CoinSize, Integer>> iterator() {
        return coins.entrySet().iterator();
    }
}
