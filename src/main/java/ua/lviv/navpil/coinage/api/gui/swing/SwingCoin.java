package ua.lviv.navpil.coinage.api.gui.swing;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Side;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SwingCoin extends JPanel {

    private final Coin coin;

    private final Color coinColor;

    private static final Map<CoinSize, Dimensions> DIMENSIONS;

    static {
        HashMap<CoinSize, Dimensions> m = new HashMap<CoinSize, Dimensions>();
        m.put(CoinSize.QUARTER, new Dimensions(48, 45, -15, -25, 15));
        m.put(CoinSize.NICKEL,  new Dimensions(40, 40, -13, -28, 14));
        m.put(CoinSize.PENNY,  new Dimensions(32, 35, -12, -15, 12));
        m.put(CoinSize.DIME,  new Dimensions(26, 30, -9, -6, 10));
        DIMENSIONS = Collections.unmodifiableMap(m);
    }

    SwingCoin(Coin coin) {
        this.coin = coin;
        switch (coin.getSize()) {
            case QUARTER:
                coinColor = Color.GRAY;
                break;
            case NICKEL:
                coinColor = Color.ORANGE;
                break;
            case PENNY:
                coinColor = Color.BLUE;
                break;
            case DIME:
            default:
                coinColor = Color.RED;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = g.getColor();

        int radius = getRadius();

        setBackgroundColor(g);
        g.fillOval(50 - radius, 50 - radius, radius * 2, radius * 2);

        Color sideColor = Color.WHITE;
        if (coin.getSide() == Side.TAILS) {
            sideColor = Color.BLACK;
        }
        g.setColor(sideColor);

        g2d.setStroke(new BasicStroke(2));
        g.drawOval(50 - radius, 50 - radius, radius * 2, radius * 2);
        g2d.setStroke(new BasicStroke(1));

        String coinText = "" + coin.getSize().getValue();
        g.setColor(Color.WHITE);
        if (coin.getSide() == Side.TAILS) {
            coinText = parseToRoman(coin.getSize().getValue());
            g.setColor(Color.BLACK);
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, getCoinFontSize()));

        g.drawString(coinText, 50 + getXOffset(), 50 + getYOffset());

        g.setColor(color);
    }

    private int getYOffset() {
        return DIMENSIONS.get(coin.getSize()).yOffset;
    }

    private int getXOffset() {
        if (coin.getSide() == Side.HEADS) {
            return DIMENSIONS.get(coin.getSize()).xOffset;
        } else {
            return DIMENSIONS.get(coin.getSize()).xRomanOffset;
        }
    }

    private int getCoinFontSize() {
        return DIMENSIONS.get(coin.getSize()).fontSize;
    }

    private Integer getRadius() {
        return radiusFor(coin.getSize());
    }

    private void setBackgroundColor(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        GradientPaint gp = new GradientPaint(
                40, 40, coinColor,
                100, 100, coinColor.darker());
        g2d.setPaint(gp);
    }

    private String parseToRoman(int value) {
        switch (value) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
            default:
                return "IV";
        }
    }

    public static int radiusFor(CoinSize coinSize) {
        return DIMENSIONS.get(coinSize).radius;
    }

    private static class Dimensions {
        final int radius;
        final int fontSize;
        final int xOffset;
        final int xRomanOffset;
        final int yOffset;

        private Dimensions(int radius, int fontSize, int xOffset, int xRomanOffset, int yOffset) {
            this.radius = radius;
            this.fontSize = fontSize;
            this.xOffset = xOffset;
            this.xRomanOffset = xRomanOffset;
            this.yOffset = yOffset;
        }
    }

}
