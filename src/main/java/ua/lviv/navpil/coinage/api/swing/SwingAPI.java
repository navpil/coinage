package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Move;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

        final MovesPanel movesPanel = new MovesPanel();
        movesPanel.setCorrectStates(game.state().getAvailableMoves());

        movesPanel.setMoveButtonClickedListener(new MovesPanel.MoveButtonClickedListener() {
            @Override
            public void buttonClicked(Move move) {
                switch (move) {
                    case PASS:
                        game.pass();
                        break;
                    case SLAP:
                        game.slap();
                        break;
                    case CAPTURE:
                        List<String> capturePositions = selectedItemsController.getPositions();
                        if (!capturePositions.isEmpty()) {
                            game.capture(capturePositions.get(0));
                        }
                        break;
                    case MOVE:
                        List<String> positions = selectedItemsController.getPositions();
                        if (positions.size() == 2) {
                            String from = positions.get(0);
                            String to = positions.get(1);
                            if (game.getBoard().getVertex(positions.get(0)).getCoins().isEmpty()) {
                                from = positions.get(1);
                                to = positions.get(0);
                            }
                            game.move(from, to);
                        }
                        break;
                    case PAY:
                        CoinSize coinSize = selectedItemsController.getCoinSize();
                        if (coinSize != null) {
                            game.pay(coinSize);
                        }
                        break;
                    case PLACE:
                        coinSize = selectedItemsController.getCoinSize();
                        positions = selectedItemsController.getPositions();
                        if (coinSize != null && !positions.isEmpty()) {
                            game.place(coinSize, positions.get(0));
                        }
                    break;
                }

                selectedItemsController.clear();
                movesPanel.setCorrectStates(game.state().getAvailableMoves());
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
