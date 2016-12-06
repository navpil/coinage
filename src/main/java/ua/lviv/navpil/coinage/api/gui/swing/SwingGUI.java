package ua.lviv.navpil.coinage.api.gui.swing;

import ua.lviv.navpil.coinage.api.gui.core.GUI;
import ua.lviv.navpil.coinage.api.gui.core.ItemSelectionListener;
import ua.lviv.navpil.coinage.api.gui.core.MoveAttemptListener;
import ua.lviv.navpil.coinage.controller.GameState;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Side;

import javax.swing.*;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SwingGUI implements GUI {

    private final SwingPlayer headsPlayer;
    private final SwingPlayer tailsPlayer;
    private SwingBoard swingBoard;
    private TossCoinsPanel tossCoinsPanel;
    private MovesPanel movesPanel;
    private JPanel mainPanel;

    private GameState state;

    public SwingGUI() {
        JFrame frame = new JFrame("Coin Age");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

        headsPlayer = new SwingPlayer(Side.HEADS);
        infoPanel.add(headsPlayer);

        swingBoard = new SwingBoard("coinage_map_medium.png");
        infoPanel.add(swingBoard);

        tailsPlayer = new SwingPlayer(Side.TAILS);
        infoPanel.add(tailsPlayer);

        mainPanel.add(infoPanel);

        tossCoinsPanel = new TossCoinsPanel();
        mainPanel.add(tossCoinsPanel);

        movesPanel = new MovesPanel();

        mainPanel.add(movesPanel);

        frame.getContentPane().add(mainPanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void setSelectionListener(ItemSelectionListener selectionListener) {
        swingBoard.setSelectionListener(selectionListener);
        tossCoinsPanel.setSelectionListener(selectionListener);
    }

    @Override
    public void setSelectedPositions(Collection<String> positions) {
        swingBoard.setSelectedPositions(positions);
        swingBoard.repaint();
        tossCoinsPanel.repaint();
    }

    @Override
    public void setSelectedCoinSize(CoinSize coinSize) {
        tossCoinsPanel.setSelectedCoinSize(coinSize);
        swingBoard.repaint();
        tossCoinsPanel.repaint();
    }

    @Override
    public void updateState(GameState state) {
        this.state = state;
        movesPanel.setCorrectStatesForMoves(state.getAvailableMoves());
        swingBoard.updateVertexes(state.getVertexes().values());
        headsPlayer.updateState(state);
        tailsPlayer.updateState(state);
        List<Coin> slappedCoins = new ArrayList<>(state.getSlappedCoins());
        Collections.sort(slappedCoins);
        tossCoinsPanel.setSlappedCoins(slappedCoins);
        mainPanel.repaint();
    }

    @Override
    public void setMoveAttemptListener(MoveAttemptListener listener) {
        movesPanel.setMoveButtonClickedListener(listener);
    }

    @Override
    public void endOfGame() {
        JFrame frame = new JFrame("End of Game");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int headsPoints = state.getPoints(Side.HEADS);
        int tailsPoints = state.getPoints(Side.TAILS);
        if (headsPoints == tailsPoints) {
            panel.add(new JLabel("Deuce: " + headsPoints + " : " + tailsPoints));
        } else if (headsPoints > tailsPoints) {
            panel.add(new JLabel("HEADS wins: " + headsPoints + " : " + tailsPoints));
        } else {
            panel.add(new JLabel("TAILS wins: " + tailsPoints + " : " + headsPoints));
        }

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);

    }
}
