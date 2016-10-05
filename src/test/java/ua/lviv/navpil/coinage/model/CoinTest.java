package ua.lviv.navpil.coinage.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CoinTest {

    private List<Coin> coins;

    @Before
    public void setup() {
        Coin dime = new Coin(CoinSize.DIME, Side.HEADS);
        Coin penny = new Coin(CoinSize.PENNY, Side.HEADS);
        Coin nickel = new Coin(CoinSize.NICKEL, Side.HEADS);
        Coin quarter = new Coin(CoinSize.QUARTER, Side.HEADS);

        coins = new ArrayList<Coin>();

        coins.add(quarter);
        coins.add(nickel);
        coins.add(penny);
        coins.add(dime);
    }

    @Test
    public void testGt() throws Exception {
        for(int i = 0; i < coins.size(); i++) {
            for(int j = i + 1; j < coins.size(); j++) {
                Coin large = coins.get(i);
                Coin small = coins.get(j);

                Assert.assertTrue(large.gt(small));
                Assert.assertFalse(small.gt(large));
            }
        }
    }

    @Test
    public void testGtForTheSameCoin() throws Exception {
        for (Coin coin : coins) {
            Assert.assertFalse(coin.gt(coin));
        }
    }
}
