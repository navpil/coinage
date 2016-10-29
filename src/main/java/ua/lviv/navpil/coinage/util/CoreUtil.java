package ua.lviv.navpil.coinage.util;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;
import ua.lviv.navpil.coinage.model.Vertex;

import java.util.*;

public final class CoreUtil {

    private CoreUtil() {
    }

    public static Map<String, Vertex> mapVertexesByName(Collection<Vertex> vertexes) {
        HashMap<String, Vertex> map = new HashMap<String, Vertex>();
        for (Vertex vertex : vertexes) {
            map.put(vertex.getName(), vertex);
        }
        return Collections.unmodifiableMap(map);
    }


    public static List<Coin> getAvailableCoins(Collection<Coin> slappedCoins, Side activePlayer) {
        ArrayList<Coin> availableCoins = new ArrayList<Coin>();
        for (Coin coin : slappedCoins) {
            if (coin.getSide() == activePlayer) {
                availableCoins.add(coin);
            }
        }
        Collections.sort(availableCoins);
        return Collections.unmodifiableList(availableCoins);
    }
}
