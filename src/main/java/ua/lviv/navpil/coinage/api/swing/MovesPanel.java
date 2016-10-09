package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.Move;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovesPanel extends JPanel {

    private final JButton slap;
    private final JButton capture;
    private final JButton move;
    private final JButton pay;
    private final JButton place;
    private final JButton pass;

    private MoveButtonClickedListener moveButtonClickedListener = new NoOpMoveButtonClickedListener();

    public MovesPanel() {
        pass = createButton("Pass", Move.PASS);
        slap = createButton("Slap", Move.SLAP);
        capture = createButton("Capture", Move.CAPTURE);
        move = createButton("Move", Move.MOVE);
        pay = createButton("Pay", Move.PAY);
        place = createButton("Place", Move.PLACE);

        add(pass);
        add(slap);
        add(capture);
        add(move);
        add(pay);
        add(place);
    }

    private JButton createButton(String label, final Move move) {
        JButton button = new JButton(label);
        button.addActionListener(new MoveButtonClickedActionListener(move));
        return button;
    }

    public void setCorrectStates(List<Move> availableMoves) {
        pass.setEnabled(!availableMoves.contains(Move.NONE));
        slap.setEnabled(availableMoves.contains(Move.SLAP));
        move.setEnabled(availableMoves.contains(Move.MOVE));
        pay.setEnabled(availableMoves.contains(Move.PAY));
        place.setEnabled(availableMoves.contains(Move.PLACE));
        capture.setEnabled(availableMoves.contains(Move.CAPTURE));
    }

    public void setMoveButtonClickedListener(MoveButtonClickedListener moveButtonClickedListener) {
        if(moveButtonClickedListener == null) {
            this.moveButtonClickedListener = new NoOpMoveButtonClickedListener();
        } else {
            this.moveButtonClickedListener = moveButtonClickedListener;
        }
    }

    public static interface MoveButtonClickedListener {
        void buttonClicked(Move move);
    }

    public static class NoOpMoveButtonClickedListener implements MoveButtonClickedListener {
        @Override
        public void buttonClicked(Move move) {
        }
    }

    private class MoveButtonClickedActionListener implements ActionListener {

        private final Move move;

        private MoveButtonClickedActionListener(Move move) {
            this.move = move;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            moveButtonClickedListener.buttonClicked(move);
        }
    }
}
