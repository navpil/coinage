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

    private static final Map<CoinSize, Integer> RADIUSES;

    static {
        HashMap<CoinSize, Integer> m = new HashMap<CoinSize, Integer>();
        m.put(CoinSize.QUARTER, 50);
        m.put(CoinSize.NICKEL, 40);
        m.put(CoinSize.PENNY, 32);
        m.put(CoinSize.DIME, 26);
        RADIUSES = Collections.unmodifiableMap(m);
    }

    public SwingCoin(Coin coin) {
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
        Color color = g.getColor();

        int radius = getRadius();

        setBackgroundColor(g);
        g.fillOval(50 - radius, 50 - radius, radius * 2, radius * 2);

        Color sideColor = Color.WHITE;
        if (coin.getSide() == Side.TAILS) {
            sideColor = Color.BLACK;
        }
        g.setColor(sideColor);

        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawOval(50 - radius, 50 - radius, radius * 2, radius * 2);
        ((Graphics2D) g).setStroke(new BasicStroke(1));

        String coinText = "" + coin.getSize().getValue();
        g.setColor(Color.WHITE);
        if (coin.getSide() == Side.TAILS) {
            coinText = parseToRoman(coin.getSize().getValue());
            g.setColor(Color.BLACK);
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString(coinText, 40, 60);

        g.setColor(color);
    }

    private Integer getRadius() {
        return radiusFor(coin.getSize());
    }

    private void setBackgroundColor(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color color1 = coinColor;
        Color color2 = coinColor.darker();
        GradientPaint gp = new GradientPaint(40, 40, color1, 100, 100, color2);
        g2d.setPaint(gp);
//        g.setColor(coinColor);
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
        Integer radius = RADIUSES.get(coinSize);
        return radius == null ? 0 : radius;
    }

}
