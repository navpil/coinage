package ua.lviv.navpil.coinage;

import org.junit.Test;
import ua.lviv.navpil.coinage.model.Board;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Side;

public class TextualBoardTest {
    @Test
    public void testToString() throws Exception {
        Board board = new Board();
        board.place(new Coin(CoinSize.QUARTER, Side.HEAD), "B2");
        board.place(new Coin(CoinSize.QUARTER, Side.TAILS), "C3");
        board.place(new Coin(CoinSize.NICKEL, Side.HEAD), "C3");
        board.place(new Coin(CoinSize.PENNY, Side.TAILS), "C3");
        System.out.println(new TextualBoard(board));

        System.out.println(board.calculate(Side.HEAD));
        System.out.println(board.calculate(Side.TAILS));
    }
}
