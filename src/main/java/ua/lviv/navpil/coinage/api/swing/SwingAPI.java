package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.Coin;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;
import java.util.Collections;

public class SwingAPI {

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Coin Age");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

        final GameImpl game = new GameImpl();

        infoPanel.add(new SwingPlayer(game.state().getHeadsPlayer(), game.state()));
        final SwingBoard swingBoard = new SwingBoard("coinage_map_medium.png", game.getBoard());
        final TossCoinsPanel tossCoinsPanel = new TossCoinsPanel(game.state());

        final SelectedItemsController selectedItemsController = new SelectedItemsController();
        swingBoard.setSelectionListener(new SwingBoard.SelectionListener() {
            @Override
            public void itemSelected(String position) {
                selectedItemsController.addPosition(position);
                swingBoard.setSelectedItems(selectedItemsController.getPositions());
                tossCoinsPanel.setSelectedSize(selectedItemsController.getCoinSize());
            }
        });
        infoPanel.add(swingBoard);
        infoPanel.add(new SwingPlayer(game.state().getTailsPlayer(), game.state()));

        panel.add(infoPanel);

        tossCoinsPanel.setSelectionListener(new TossCoinsPanel.SelectionListener() {
            @Override
            public void itemSelected(Coin coin) {
                if (coin.getSide() == game.state().getActivePlayer()) {
                    selectedItemsController.addCoin(coin);
                    swingBoard.setSelectedItems(selectedItemsController.getPositions());
                    tossCoinsPanel.setSelectedSize(selectedItemsController.getCoinSize());
                }
            }
        });
        panel.add(tossCoinsPanel);

        final MovesPanel movesPanel = new MovesPanel(game, selectedItemsController);

        movesPanel.setMoveListener(new MovesPanel.MoveListener() {
            @Override
            public void moveHappened() {
                selectedItemsController.clear();
                movesPanel.setCorrectStates();
                tossCoinsPanel.setSelectedSize(null);
                swingBoard.setSelectedItems(Collections.<String>emptyList());
                infoPanel.repaint();
                movesPanel.repaint();
            }
        });

        panel.add(movesPanel);

        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
