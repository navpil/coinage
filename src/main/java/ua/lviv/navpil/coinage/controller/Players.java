package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.Player;
import ua.lviv.navpil.coinage.model.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Players {
    private final List<Player> players = new ArrayList<Player>();
    private final Map<Side, Player> map = new HashMap<Side, Player>();
    private int currentlyActive = 0;

    Players() {
        add(new Player(Side.HEADS));
        add(new Player(Side.TAILS));
    }

    private void add(Player player) {
        players.add(player);
        map.put(player.getSide(), player);
    }

    Player getActive() {
        return players.get(currentlyActive);
    }

    Player next() {
        currentlyActive++;
        if(currentlyActive == players.size()) {
            currentlyActive = 0;
        }
        return players.get(currentlyActive);
    }

    Player getPassive() {
        return players.get((currentlyActive + 1) % 2);
    }

    Player getPlayer(Side side) {
        return map.get(side);
    }

}
