package ua.lviv.navpil.coinage.model;

public class Coin {
    private final CoinSize coinSize;
    private final Side side;

    public Coin(CoinSize coinSize, Side side) {
        this.coinSize = coinSize;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public CoinSize getSize() {
        return coinSize;
    }

    public boolean gt(Coin coin) {
        return this.getSize().gt(coin.getSize());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coin coin = (Coin) o;

        if (coinSize != coin.coinSize) return false;
        if (side != coin.side) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = coinSize != null ? coinSize.hashCode() : 0;
        result = 31 * result + (side != null ? side.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String s = "" + coinSize.name().charAt(0);
        return side == Side.HEADS ? s.toUpperCase() : s.toLowerCase();
    }
}
