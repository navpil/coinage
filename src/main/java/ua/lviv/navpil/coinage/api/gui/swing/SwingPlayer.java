package ua.lviv.navpil.coinage.api.gui.swing;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Player;
import ua.lviv.navpil.coinage.model.Side;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

class SwingPlayer extends JPanel {

    private GameState state;
    private final Side side;

    public SwingPlayer(Side side) {
        this.side = side;
    }

    public void updateState(GameState state) {
        this.state = state;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color oldColor = g.getColor();

        if (isActive()) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 130, 400);
        }

        g.setColor(getPlayerColor());
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("" + side, 10, 25);
        Map<CoinSize, java.util.List<Coin>> coinNumbers = getCoinNumber();

        for (Map.Entry<CoinSize, java.util.List<Coin>> entry : coinNumbers.entrySet()) {
            int x = SwingCoin.radiusFor(entry.getKey()) - 40;
            int y = 25 + getYOffset(entry.getKey());

            for (Coin coin : entry.getValue()) {
                SwingCoin swingCoin = new SwingCoin(coin);
                swingCoin.paintComponent(g.create(x, y, 100, 100));
                x += 10;
            }
        }

        g.setColor(getPlayerColor());
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("P: " + state.getPoints(side), 10, 400);


        g.setColor(oldColor);
    }

    private int getYOffset(CoinSize key) {
        int offset = 0;
        switch (key) {
            case QUARTER:
                offset += (SwingCoin.radiusFor(CoinSize.QUARTER) * 2);
            case NICKEL:
                offset += (SwingCoin.radiusFor(CoinSize.NICKEL) * 2);
            case PENNY:
                offset += (SwingCoin.radiusFor(CoinSize.PENNY) * 2);
        }
        return offset;
    }

    private Map<CoinSize, java.util.List<Coin>> getCoinNumber() {
        Map<CoinSize, java.util.List<Coin>> m = new TreeMap<CoinSize, java.util.List<Coin>>();
        for (CoinSize coinSize : CoinSize.values()) {
            m.put(coinSize, new ArrayList<Coin>());
        }
        for (Coin coin : state.getCoins(side)) {
            m.get(coin.getSize()).add(coin);
        }
        return m;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(130, 400);
    }

    private boolean isActive() {
        return state.getActivePlayer() == side;
    }

    private Color getPlayerColor() {
        return side == Side.HEADS ? Color.WHITE : Color.BLACK;
    }
}
