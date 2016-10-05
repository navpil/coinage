package ua.lviv.navpil.coinage.model;

import java.util.Random;

public enum Side {
    HEADS, TAILS;

    private static final Random random = new Random();

    public static Side random() {
        return random.nextBoolean() ? Side.HEADS : Side.TAILS;
    }

    public Side flip() {
        return this == HEADS ? TAILS : HEADS;
    }
}
