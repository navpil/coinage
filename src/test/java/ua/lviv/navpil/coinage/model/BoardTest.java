package ua.lviv.navpil.coinage.model;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    @Test
    public void canMoveTest() {
        Board board = new Board();
        boolean b = board.canMove("A1", "B3");
        Assert.assertFalse(b);
    }
}
