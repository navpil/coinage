package ua.lviv.navpil.coinage.api.gui.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Side;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class SelectedItemsControllerTest {

    private SelectedItemsController selectedItemsController;

    @Before
    public void setup() {
        selectedItemsController = new SelectedItemsController();
    }


    @Test
    public void addSinglePosition() throws Exception {
        selectedItemsController.addPosition("a1");

        Assert.assertEquals(new HashSet<>(Collections.singletonList("a1")),
                new HashSet<>(selectedItemsController.getPositions()));
    }

    @Test
    public void addTwoPositions() throws Exception {
        selectedItemsController.addPosition("a1");
        selectedItemsController.addPosition("a2");

        Assert.assertEquals(new HashSet<>(Arrays.asList("a1", "a2")),
                new HashSet<>(selectedItemsController.getPositions()));
    }

    @Test
    public void addMultiplePosition() throws Exception {
        selectedItemsController.addPosition("a1");
        selectedItemsController.addPosition("a2");
        selectedItemsController.addPosition("a3");

        Assert.assertEquals(new HashSet<>(Arrays.asList("a2", "a3")),
                new HashSet<>(selectedItemsController.getPositions()));
    }

    @Test
    public void addSingleCoin() throws Exception {
        Coin coin = new Coin(CoinSize.QUARTER, Side.HEADS);
        selectedItemsController.addCoin(coin);
        Assert.assertEquals(coin.getSize(), selectedItemsController.getCoinSize());
    }

    @Test
    public void addSeveralCoins() throws Exception {
        selectedItemsController.addCoin(new Coin(CoinSize.QUARTER, Side.HEADS));
        Coin secondCoin = new Coin(CoinSize.NICKEL, Side.TAILS);
        selectedItemsController.addCoin(secondCoin);

        Assert.assertEquals(secondCoin.getSize(), selectedItemsController.getCoinSize());
    }

    @Test
    public void addPositionsAndCoins() throws Exception {
        selectedItemsController.addPosition("a1");
        selectedItemsController.addPosition("a2");

        selectedItemsController.addCoin(new Coin(CoinSize.QUARTER, Side.HEADS));
        Coin secondCoin = new Coin(CoinSize.NICKEL, Side.TAILS);
        selectedItemsController.addCoin(secondCoin);

        Assert.assertEquals(secondCoin.getSize(), selectedItemsController.getCoinSize());
        Assert.assertEquals(Collections.singletonList("a2"), selectedItemsController.getPositions());

    }

    @Test
    public void addCoinAndPositions() throws Exception {
        selectedItemsController.addCoin(new Coin(CoinSize.QUARTER, Side.HEADS));

        selectedItemsController.addPosition("a1");
        selectedItemsController.addPosition("a2");

        Assert.assertEquals(null, selectedItemsController.getCoinSize());
        Assert.assertEquals(new HashSet<>(Arrays.asList("a1", "a2")),
                new HashSet<>(selectedItemsController.getPositions()));
    }


    @Test
    public void clear() throws Exception {
        selectedItemsController.addPosition("a1");
        selectedItemsController.addPosition("a2");
        selectedItemsController.addCoin(new Coin(CoinSize.QUARTER, Side.HEADS));
        selectedItemsController.clear();

        Assert.assertEquals(null, selectedItemsController.getCoinSize());
        Assert.assertTrue(selectedItemsController.getPositions().isEmpty());

    }

}