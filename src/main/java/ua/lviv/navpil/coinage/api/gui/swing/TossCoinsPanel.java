package ua.lviv.navpil.coinage.api.gui.swing;

import ua.lviv.navpil.coinage.api.gui.core.ItemSelectionListener;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

class TossCoinsPanel extends JPanel {

    private static final int UNIT = 100;

    private CoinSize selectedSize;
    private ItemSelectionListener listener;
    private List<Coin> coins;

    public TossCoinsPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getY() <= 100) {
                    int index = e.getX() / 100;
                    if (index < coins.size()) {
                        listener.coinSelected(coins.get(index));
                    }
                }
            }
        });
    }

    public void setSelectionListener(ItemSelectionListener selectionListener) {
        this.listener = selectionListener;
    }

    public void setSelectedCoinSize(CoinSize coinSize) {
        this.selectedSize = coinSize;
    }

    public void setSlappedCoins(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0;
        for (Coin availableCoin : coins) {
            new SwingCoin(availableCoin).paintComponent(g.create(x, 0, UNIT, UNIT));
            if (availableCoin.getSize() == selectedSize) {
                g.setColor(Color.CYAN);
                ((Graphics2D) g).setStroke(new BasicStroke(2));
                g.drawRect(x, 0, UNIT, UNIT);
            }
            x += UNIT;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 100);
    }
}
