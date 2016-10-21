package ua.lviv.navpil.coinage.api.gui.swing;

import ua.lviv.navpil.coinage.api.gui.core.ItemSelectionListener;
import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

class TossCoinsPanel extends JPanel {

    private static final int UNIT = 100;

    private final GameState state;
    private CoinSize selectedSize;
    private ItemSelectionListener listener;

    public TossCoinsPanel(final GameState state) {
        this.state = state;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getY() <= 100) {
                    int index = e.getX() / 100;
                    List<Coin> availableCoins = state.getAvailableCoins();
                    if (index < availableCoins.size()) {
                        listener.coinSelected(availableCoins.get(index));
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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0;
        for (Coin availableCoin : state.getAvailableCoins()) {
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
