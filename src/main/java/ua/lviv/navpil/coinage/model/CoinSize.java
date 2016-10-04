package ua.lviv.navpil.coinage.model;

public enum CoinSize implements Comparable<CoinSize> {
    QUARTER(4), NICKEL(3), PENNY(2), DIME(1);

    private final int value;

    CoinSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean gt(CoinSize coinSize) {
        return this.getValue() > coinSize.getValue();
    }
}
