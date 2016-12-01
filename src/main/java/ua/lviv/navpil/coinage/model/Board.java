package ua.lviv.navpil.coinage.model;

import java.util.Collection;

public interface Board {
    boolean canPlace(Coin coin, String position);

    void place(Coin coin, String position);

    boolean canMove(String from, String to);

    void move(String from, String to);

    boolean canCapture(String position);

    Coin capture(String position);

    Vertex getVertex(String position);

    Collection<Vertex> getVertexes();

    boolean isFull();

    int calculate(Side side);
}
