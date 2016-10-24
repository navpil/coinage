package ua.lviv.navpil.coinage.api.text;

import org.junit.Test;
import ua.lviv.navpil.coinage.api.text.TextualBoard;
import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TextualBoardTest {
    @Test
    public void testToString() throws Exception {
        Board board = new BoardImpl();
        board.place(new Coin(CoinSize.QUARTER, Side.HEADS), "B2");
        board.place(new Coin(CoinSize.QUARTER, Side.TAILS), "C3");
        board.place(new Coin(CoinSize.NICKEL, Side.HEADS), "C3");
        board.place(new Coin(CoinSize.PENNY, Side.TAILS), "C3");

        //Visual test - do you like it how it looks?
        System.out.println(new TextualBoard(mapVertexes(board.getVertexes())));
    }

    private Map<String, Vertex> mapVertexes(Collection<Vertex> vertexes) {
        HashMap<String, Vertex> map = new HashMap<String, Vertex>();
        for (Vertex vertex : vertexes) {
            map.put(vertex.getName(), vertex);
        }
        return map;
    }
}
