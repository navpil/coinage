package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;

import javax.swing.*;
import java.awt.*;

public class SwingCoin extends JPanel {

    private final Coin coin;

    public SwingCoin(Coin coin) {
        this.coin = coin;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = g.getColor();
        Color coinColor;
        int radius;
        switch (coin.getSize()) {
            case QUARTER:
                coinColor = Color.GRAY;
                radius = 50;
                break;
            case NICKEL:
                coinColor = Color.ORANGE;
                radius = 40;
                break;
            case PENNY:
                coinColor = Color.BLUE;
                radius = 32;
                break;
            case DIME:
            default:
                coinColor = Color.RED;
                radius = 26;
        }
        g.setColor(coinColor);
        g.fillOval(50 - radius, 50 - radius, radius*2, radius*2);

        Color sideColor = Color.WHITE;
        if(coin.getSide() == Side.HEADS) {
            sideColor = Color.BLACK;
        }
        g.setColor(sideColor);

        g.drawOval(50 - radius, 50 - radius, radius*2, radius*2);


        String coinText = "" + coin.getSize().getValue();
        g.setColor(Color.WHITE);
        if(coin.getSide() == Side.HEADS) {
            coinText = parseToRoman(coin.getSize().getValue());
            g.setColor(Color.BLACK);
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString(coinText, 40, 60);

        g.setColor(color);
    }

    private String parseToRoman(int value) {
        switch (value) {
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4:
                default: return "IV";
        }
    }

}
