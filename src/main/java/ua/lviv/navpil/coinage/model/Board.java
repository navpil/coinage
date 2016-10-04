package ua.lviv.navpil.coinage.model;

import java.util.*;

public class Board {
    private final Map<String, Vertex> verteces = new TreeMap<String, Vertex>();
    private final Map<String, Collection<Vertex>> regions = new TreeMap<String, Collection<Vertex>>();
    private final static Set<String> possiblePositions = new HashSet<String>(Arrays.asList("A1", "A2", "A3", "B1", "B2", "B3", "B4", "C1", "C2", "C3"));

    public Board() {
        for (String string : possiblePositions) {
            verteces.put(string, new Vertex(string));
        }
        join("A1", "A2");
        join("A1", "B1");
        join("A1", "B2");

        join("A2", "A3");
        join("A2", "B2");
        join("A2", "B3");

        join("A3", "B3");
        join("A3", "B4");

        join("B1", "B2");
        join("B2", "B3");
        join("B3", "B4");

        join("C1", "C2");
        join("C1", "B1");
        join("C1", "B2");

        join("C2", "C3");
        join("C2", "B2");
        join("C2", "B3");

        join("C3", "B3");
        join("C3", "B4");

        createRegion("M", "A1", "A2", "A3", "B1");
        createRegion("F", "C1", "B2", "B3");
        createRegion("P", "B4", "C3");
        createRegion("T", "C2");
    }

    private void createRegion(String regionCode, String ... parts) {
        regions.put(regionCode, new HashSet<Vertex>());
        for (String part : parts) {
            Vertex vertex = verteces.get(part);
            vertex.setRegion(regionCode);
            regions.get(regionCode).add(vertex);
        }
    }

    private void join(String a1, String a2) {
        verteces.get(a1).join(verteces.get(a2));
    }

    public boolean canPlace(Coin coin, String position) {
        return _canPlace(coin, position.toUpperCase());
    }

    private boolean _canPlace(Coin coin, String position) {
        return possiblePositions.contains(position) && placementOnPositionIsLegal(coin, position);
    }

    private boolean placementOnPositionIsLegal(Coin coin, String position) {
        List<Coin> coinsAtPosition = coinsOn(position);
        return coinsAtPosition.isEmpty() || coinsAtPosition.get(coinsAtPosition.size() - 1).gt(coin);
    }

    public void place(Coin coin, String position) {
        List<Coin> coinsAtPosition = coinsOn(position);
        coinsAtPosition.add(coin);
    }

    public boolean canMove(String from, String to) {
        return _canMove(from.toUpperCase(), to.toUpperCase());
    }

    private boolean _canMove(String from, String to) {
        return possiblePositions.contains(from) && possiblePositions.contains(to) &&
                !coinsOn(from).isEmpty() && coinsOn(to).isEmpty() && verteces.get(from).isAdjacent(to);
    }

    public void move(String from, String to) {
        Iterator<Coin> iterator = coinsOn(from).iterator();
        List<Coin> coinsTo = coinsOn(to);
        while(iterator.hasNext()) {
            Coin coin = iterator.next();
            iterator.remove();
            coinsTo.add(coin);
        }
    }

    public boolean canCapture(String position) {
        return possiblePositions.contains(position.toUpperCase()) && !coinsOn(position.toUpperCase()).isEmpty();
    }

    public Coin capture(String position) {
        List<Coin> c = coinsOn(position.toUpperCase());
        return c.remove(c.size() - 1);
    }

    private List<Coin> coinsOn(String position) {
        return verteces.get(position.toUpperCase()).getCoins();
    }

    public Vertex getVertex(String position) {
        return verteces.get(position.toUpperCase());
    }

    public boolean isFull() {
        for (Vertex vertex : verteces.values()) {
            if(vertex.getCoins().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int calculate(Side side) {
        int total = 0;
        for (Vertex vertex : verteces.values()) {
            if(vertex.belongsTo(side)) {
                total += vertex.getValue();
            }
        }
        for (Collection<Vertex> region : regions.values()) {
            int ownsRegion = 0;
            //Wrong! Majority counts
            int regionValue = 0;
            for (Vertex vertex : region) {
                if(vertex.belongsTo(side)) {
                    ownsRegion++;
                    regionValue += vertex.getValue();
                } else if (vertex.belongsTo(side.flip())) {
                    ownsRegion--;
                }
            }
            if(ownsRegion > 0) {
                total += regionValue;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        String s = "";
        s = "A1        A2        A3\n";
        Iterator<Vertex> iterator = verteces.values().iterator();

        for(int i = 0; i < 3; i++) {

            s += iterator.next().getCoins();
            s += " ";
        }
        s += "\nB1     B2     B3     B4\n";
        for(int i = 0; i < 4; i++) {
            s += iterator.next().getCoins();
            s += " ";
        }
        s += "\nC1        C2        C3\n";
        for(int i = 0; i < 3; i++) {
            s += iterator.next().getCoins();
            s += " ";
        }
        s+="\n";
        s+= regions();

        return s;
    }

    private String regions() {
        return
                "--------------------------------\n" +
                "|    M    |    M    |     M    |\n" +
                        "--------------------------------\n"+
                        "|    M  |   F   |   F   |  P   |\n" +
                        "--------------------------------\n"+
                        "|    F    |    C    |     P    |\n" +
                        "--------------------------------\n";

    }

}
