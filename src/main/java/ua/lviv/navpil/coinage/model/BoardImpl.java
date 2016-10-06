package ua.lviv.navpil.coinage.model;

import java.util.*;

public class BoardImpl implements Board {
    private final Map<String, VertexImpl> verteces = new TreeMap<String, VertexImpl>();
    private final Map<String, Collection<VertexImpl>> regions = new TreeMap<String, Collection<VertexImpl>>();
    private final static Set<String> possiblePositions = new HashSet<String>(Arrays.asList("A1", "A2", "A3", "B1", "B2", "B3", "B4", "C1", "C2", "C3"));

    public BoardImpl() {
        for (String string : possiblePositions) {
            verteces.put(string, new VertexImpl(string));
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

    private void createRegion(String regionCode, String... parts) {
        regions.put(regionCode, new HashSet<VertexImpl>());
        for (String part : parts) {
            VertexImpl vertex = vertex(part);
            vertex.setRegion(regionCode);
            regions.get(regionCode).add(vertex);
        }
    }

    private void join(String a1, String a2) {
        vertex(a1).join(vertex(a2));
    }

    @Override
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

    @Override
    public void place(Coin coin, String position) {
        if(!canPlace(coin, position)) {
            throw new IllegalArgumentException("Can't place a coin " + coin + " at the " + position);
        }
        vertex(position).add(coin);
    }

    @Override
    public boolean canMove(String from, String to) {
        return _canMove(from.toUpperCase(), to.toUpperCase());
    }

    private boolean _canMove(String from, String to) {
        return possiblePositions.contains(from) && possiblePositions.contains(to) &&
                !coinsOn(from).isEmpty() && coinsOn(to).isEmpty() && vertex(from).isAdjacent(to);
    }

    @Override
    public void move(String from, String to) {
        if(!canMove(from, to)) {
            throw new IllegalArgumentException("Can't move coins from " + from + " to " + to);
        }
        for (Coin coin : coinsOn(from)) {
            vertex(from).remove(coin);
            vertex(to).add(coin);
        }
    }

    @Override
    public boolean canCapture(String position) {
        return possiblePositions.contains(position) && !coinsOn(position).isEmpty();
    }

    @Override
    public Coin capture(String position) {
        if(!canCapture(position)) {
            throw new IllegalArgumentException("Can't capture anything on position " + position);
        }

        VertexImpl vertex = vertex(position);
        List<Coin> coins = vertex.getCoins();

        Coin coin = coins.get(coins.size() - 1);
        boolean remove = vertex.remove(coin);
        if (remove) {
            return coin;
        }
        throw new IllegalStateException("This should never happen: We've checked that vertex contains coin we are going to remove");
    }

    private List<Coin> coinsOn(String position) {
        return vertex(position).getCoins();
    }

    /**
     * Inner version of getVertex, which returns the modifiable vertex.
     * Probably need to do this differently - e.g. define method Vertex getVertex() in Board interface
     * but internally return VertexImpl
     *
     * @param position vertex position
     * @return vertex
     */
    private VertexImpl vertex(String position) {
        return verteces.get(position.toUpperCase());
    }

    @Override
    public Vertex getVertex(String position) {
        return vertex(position);
    }

    @Override
    public boolean isFull() {
        for (VertexImpl vertex : verteces.values()) {
            if (vertex.getCoins().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int calculate(Side side) {
        int total = 0;
        for (VertexImpl vertex : verteces.values()) {
            if (vertex.belongsTo(side)) {
                total += vertex.getValue();
            }
        }
        for (Collection<VertexImpl> region : regions.values()) {
            int ownsRegion = 0;
            int regionValue = 0;
            for (VertexImpl vertex : region) {
                if (vertex.belongsTo(side)) {
                    ownsRegion++;
                    regionValue += vertex.getValue();
                } else if (vertex.belongsTo(side.flip())) {
                    ownsRegion--;
                }
            }
            if (ownsRegion > 0) {
                total += regionValue;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Board{" +
                "verteces=" + verteces +
                ", physicalLocation: " + vertexPositions() + '}';
    }

    private String vertexPositions() {
        return
                "\n----------------------------------\n" +
                        "|    A1    |    A2    |    A3    |\n" +
                        "----------------------------------\n" +
                        "|   B1  |   B2   |   B3  |   B4  |\n" +
                        "----------------------------------\n" +
                        "|    C1    |    C2    |    C3    |\n" +
                        "----------------------------------\n";

    }

    @Override
    public Iterator<Vertex> iterator() {
        //todo: Figure out how can I return verteces.values().iterator() without creation of an ArrayList
        //Maybe this version is fine, but how to do it anyway?
        return new ArrayList<Vertex>(verteces.values()).iterator();
    }
}
