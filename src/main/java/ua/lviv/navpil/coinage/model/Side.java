package ua.lviv.navpil.coinage.model;

import java.util.Random;

public enum Side {
    HEAD, TAILS;

    private static final Random random = new Random();

    public static Side random() {
        return random.nextBoolean() ? Side.HEAD : Side.TAILS;
    }

    public Side flip() {
        return this == HEAD ? TAILS : HEAD;
    }
}
