package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Player;
import ua.lviv.navpil.coinage.model.Side;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SwingPlayer extends JPanel {

    private final Player player;
    private final GameImpl.State state;

    public SwingPlayer(Player player, GameImpl.State state) {
        this.player = player;
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
        g.drawString("" + player.getSide(), 10, 25);
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
        g.drawString("P: " + state.getPoints(player.getSide()), 10, 400);


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
        for (Coin coin : player.coins()) {
            m.get(coin.getSize()).add(coin);
        }
        return m;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(130, 400);
    }

    private boolean isActive() {
        return state.getActivePlayer() == player.getSide();
    }

    private Color getPlayerColor() {
        return player.getSide() == Side.HEADS ? Color.WHITE : Color.BLACK;
    }
}
