package ua.lviv.navpil.coinage.model;

import java.util.Collection;

public interface Board {
    public boolean canPlace(Coin coin, String position);

    public void place(Coin coin, String position);

    public boolean canMove(String from, String to);

    public void move(String from, String to);

    public boolean canCapture(String position);

    public Coin capture(String position);

    public Vertex getVertex(String position);

    public Collection<Vertex> getVertexes();

    public boolean isFull();

    public int calculate(Side side);
}
