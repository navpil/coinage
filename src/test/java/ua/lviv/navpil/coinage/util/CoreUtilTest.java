package ua.lviv.navpil.coinage.util;

import org.junit.Test;
import ua.lviv.navpil.coinage.model.*;

import java.util.*;

import static org.junit.Assert.*;

public class CoreUtilTest {

    @Test
    public void testMapVertexesByName() throws Exception {
        BoardImpl board = new BoardImpl();
        Collection<Vertex> vertexes = board.getVertexes();
        Map<String,Vertex> vertexMap = CoreUtil.mapVertexesByName(vertexes);

        assertEquals("Wrong number of vertexes", 10, vertexMap.size());
        for (Map.Entry<String, Vertex> stringVertexEntry : vertexMap.entrySet()) {
            assertEquals(stringVertexEntry.getKey(), stringVertexEntry.getValue().getName());
        }
    }

    @Test
    public void testReturnedVertexMapIsUnmodifiable() {
        BoardImpl board = new BoardImpl();
        Collection<Vertex> vertexes = board.getVertexes();
        Map<String,Vertex> vertexMap = CoreUtil.mapVertexesByName(vertexes);

        try {
            Vertex anyVertex = vertexMap.values().iterator().next();
            vertexMap.remove(anyVertex.getName());
            fail("Should fail on element removal");
        } catch (UnsupportedOperationException e) {
        }

        try {
            Vertex anyVertex = vertexMap.values().iterator().next();
            vertexMap.put("arbitrary-value", anyVertex);
            fail("Should fail on element addition");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testAvailableCoins() throws Exception {

        Coin onlyHeadsCoin = new Coin(CoinSize.DIME, Side.HEADS);

        Collection<Coin> tailsCoins = new ArrayList<Coin>();
        tailsCoins.add(new Coin(CoinSize.PENNY, Side.TAILS));
        tailsCoins.add(new Coin(CoinSize.NICKEL, Side.TAILS));
        tailsCoins.add(new Coin(CoinSize.QUARTER, Side.TAILS));

        ArrayList<Coin> slappedCoins = new ArrayList<Coin>();
        slappedCoins.add(onlyHeadsCoin);
        slappedCoins.addAll(tailsCoins);

        List<Coin> availableHeadCoins = CoreUtil.getAvailableCoins(Collections.unmodifiableList(slappedCoins), Side.HEADS);

        assertEquals(1, availableHeadCoins.size());
        assertTrue(availableHeadCoins.contains(onlyHeadsCoin));

        List<Coin> availableTailsCoins = CoreUtil.getAvailableCoins(Collections.unmodifiableList(slappedCoins), Side.TAILS);
        assertEquals(3, availableTailsCoins.size());
        assertTrue(availableTailsCoins.containsAll(tailsCoins));

    }

}
