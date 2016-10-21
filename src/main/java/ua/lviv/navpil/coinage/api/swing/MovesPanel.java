package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Move;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

public class MovesPanel extends JPanel {

    private final GameImpl game;
    private final JButton slap;
    private final JButton capture;
    private final JButton move;
    private final JButton pay;
    private final JButton place;
    private MoveListener moveListener = new NoOpMoveListener();
    private final JButton pass;

    public MovesPanel(final GameImpl game, final SelectedItemsController selectedItemsController) {
        this.game = game;

        pass = new JButton("Pass");
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pass();
                moveListener.moveHappened();
            }
        });
        add(pass);

        slap = new JButton("Slap");
        slap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.slap();
                moveListener.moveHappened();
            }
        });
        add(slap);

        capture = new JButton("Capture");
        capture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> positions = selectedItemsController.getPositions();
                if (!positions.isEmpty()) {
                    game.capture(positions.get(0));
                    moveListener.moveHappened();
                }
            }
        });
        add(capture);

        move = new JButton("Move");
        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> positions = selectedItemsController.getPositions();
                if (positions.size() == 2) {
                    String from = positions.get(0);
                    String to = positions.get(1);
                    if (game.getState().getBoardCoins().get(positions.get(0)).isEmpty()) {
                        from = positions.get(1);
                        to = positions.get(0);
                    }
                    game.move(from, to);
                    moveListener.moveHappened();
                }
            }
        });
        add(move);

        pay = new JButton("Pay");
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoinSize coinSize = selectedItemsController.getCoinSize();
                if (coinSize != null) {
                    game.pay(coinSize);
                    moveListener.moveHappened();
                }
            }
        });
        add(pay);

        place = new JButton("Place");
        place.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoinSize coinSize = selectedItemsController.getCoinSize();
                List<String> positions = selectedItemsController.getPositions();
                if (coinSize != null && !positions.isEmpty()) {
                    game.place(coinSize, positions.get(0));
                    moveListener.moveHappened();
                }
            }
        });
        add(place);

        setCorrectStates();
    }

    public void setCorrectStates() {
        Collection<Move> availableMoves = game.getState().getAvailableMoves();

        pass.setEnabled(!availableMoves.contains(Move.NONE));
        slap.setEnabled(availableMoves.contains(Move.SLAP));
        move.setEnabled(availableMoves.contains(Move.MOVE));
        pay.setEnabled(availableMoves.contains(Move.PAY));
        place.setEnabled(availableMoves.contains(Move.PLACE));
        capture.setEnabled(availableMoves.contains(Move.CAPTURE));
    }

    public void setMoveListener(MoveListener moveListener) {
        if(moveListener == null) {
            this.moveListener = new NoOpMoveListener();
        } else {
            this.moveListener = moveListener;
        }
    }

    public static interface MoveListener {
        void moveHappened();
    }

    public static class NoOpMoveListener implements MoveListener {
        @Override
        public void moveHappened() {
        }
    }
}
