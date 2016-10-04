package ua.lviv.navpil.coinage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Players {
    private final List<Player> players = new ArrayList<Player>();
    private final Map<Side, Player> map = new HashMap<Side, Player>();
    private int currentlyActive = 0;

    public Players() {
        add(new Player(Side.TAILS));
        add(new Player(Side.HEAD));
    }

    private void add(Player player) {
        players.add(player);
        map.put(player.getSide(), player);
    }

    public Player getActive() {
        return players.get(currentlyActive);
    }

    public Player next() {
        currentlyActive++;
        if(currentlyActive == players.size()) {
            currentlyActive = 0;
        }
        return players.get(currentlyActive);
    }

    public Player getPassive() {
        return players.get((currentlyActive + 1) % 2);
    }

    public Player getPlayer(Side side) {
        return map.get(side);
    }

}
