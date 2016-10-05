package ua.lviv.navpil.coinage.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardImplTest {

    private BoardImpl board;

    @Before
    public void setup() {
        board = new BoardImpl();
    }

    @Test
    public void canMoveTest() {
        Assert.assertFalse(board.canMove("A1", "B1"));

        board.place(new Coin(CoinSize.QUARTER, Side.HEADS), "A1");
        Assert.assertTrue(board.canMove("A1", "B1"));

        board.place(new Coin(CoinSize.NICKEL, Side.HEADS), "A1");
        Assert.assertTrue(board.canMove("A1", "B1"));

        board.place(new Coin(CoinSize.DIME, Side.HEADS), "B1");
        Assert.assertFalse(board.canMove("A1", "B1"));

        board.capture("A1");
        board.capture("A1");
        Assert.assertFalse(board.canMove("A1", "B1"));

        Assert.assertFalse(board.canMove("B1", "C2"));

    }

    @Test
    public void testBoard() {
        //Visual test. Do you like how it looks?
        System.out.println(new BoardImpl());
    }

    @Test
    public void moveTest() {
        board.place(new Coin(CoinSize.QUARTER, Side.HEADS), "A1");
        board.move("A1", "B1");
        Assert.assertTrue(board.getVertex("A1").getCoins().isEmpty());
        Assert.assertEquals(1, board.getVertex("B1").getCoins().size());
        Assert.assertEquals(new Coin(CoinSize.QUARTER, Side.HEADS), board.getVertex("B1").getCoins().get(0));

        board.move("B1", "A1");

        board.place(new Coin(CoinSize.NICKEL, Side.HEADS), "A1");
        board.move("A1", "B1");

        Assert.assertTrue(board.getVertex("A1").getCoins().isEmpty());
        Assert.assertEquals(2, board.getVertex("B1").getCoins().size());
        Assert.assertEquals(new Coin(CoinSize.QUARTER, Side.HEADS), board.getVertex("B1").getCoins().get(0));
        Assert.assertEquals(new Coin(CoinSize.NICKEL, Side.HEADS), board.getVertex("B1").getCoins().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMoveTest_emptySlots() {
        board.move("A1", "A2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMoveTest_occupiedSlot() {
        board.place(new Coin(CoinSize.QUARTER, Side.HEADS), "A1");
        board.place(new Coin(CoinSize.QUARTER, Side.HEADS), "A2");
        board.move("A1", "A2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeCaptureTest_emptySlots() {
        board.capture("A1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePlaceTest() {
        board.place(new Coin(CoinSize.NICKEL, Side.HEADS), "A1");
        board.place(new Coin(CoinSize.NICKEL, Side.TAILS), "A1");
    }

}
