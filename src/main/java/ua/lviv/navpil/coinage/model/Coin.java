package ua.lviv.navpil.coinage.model;

public class Coin {
    private final CoinSize coinSize;
    private Side side;

    public Coin(CoinSize coinSize, Side side) {
        this.coinSize = coinSize;
        this.side = side;
    }

    public Side slap() {
        return Side.random();
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public CoinSize getSize() {
        return coinSize;
    }

    @Override
    public String toString() {
        String s = "" + coinSize.name().charAt(0);
        return side == Side.HEAD ? s.toUpperCase() : s.toLowerCase();
    }

    public boolean gt(Coin coin) {
        return this.getSize().gt(coin.getSize());
    }
}
