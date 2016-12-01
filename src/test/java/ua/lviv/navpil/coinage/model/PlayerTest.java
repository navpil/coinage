package ua.lviv.navpil.coinage.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PlayerTest {

    private Player heads;
    private Player tails;

    @Before
    public void setup() {
        heads = new Player(Side.HEADS);
        tails = new Player(Side.TAILS);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCoinsForMoveIllegaly() throws Exception {
        removeAllCoins(heads);
        heads.getCoinsForMove();
    }

    @Test
    public void testGetCoinsForMove() throws Exception {
        removeAllCoins(heads, CoinSize.DIME);
        Assert.assertEquals(3, heads.getCoinsForMove().size());

        removeAllCoins(heads, CoinSize.QUARTER);
        Assert.assertEquals(2, heads.getCoinsForMove().size());

        removeAllCoins(heads, CoinSize.PENNY);
        Assert.assertEquals(1, heads.getCoinsForMove().size());

        heads.add(new Coin(CoinSize.DIME, Side.HEADS));
        Assert.assertEquals(2, heads.getCoinsForMove().size());
    }

    @Test
    public void testGetSide() throws Exception {
        Assert.assertEquals(Side.HEADS, heads.getSide());
        Assert.assertEquals(Side.TAILS, tails.getSide());
    }

    @Test
    public void testCoins() throws Exception {
        assertAllSides(heads.coins(), Side.HEADS);
        assertAllSides(tails.coins(), Side.TAILS);
        Assert.assertEquals(10, heads.coins().size());
        Assert.assertEquals(10, tails.coins().size());

    }

    private void assertAllSides(List<Coin> coins, Side side) {
        for (Coin coin : coins) {
            Assert.assertEquals(side, coin.getSide());
        }
    }

    @Test
    public void testRemove() throws Exception {
        removeAllCoins(heads);
        Assert.assertTrue(heads.coins().isEmpty());
    }

    private void removeAllCoins(Player player) {
        player.coins().forEach(player::remove);
    }

    private void removeAllCoins(Player player, CoinSize size) {
        player.coins().stream()
                .filter(coin -> coin.getSize() == size)
                .forEach(player::remove);
    }

    @Test
    public void testAdd() throws Exception {
        heads.add(new Coin(CoinSize.DIME, Side.TAILS));
        assertAllSides(heads.coins(), Side.HEADS);
        Assert.assertEquals(11, heads.coins().size());

    }
}
