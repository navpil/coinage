package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.api.TextBasedAPI;
import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.controller.Result;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SwingAPI {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Coin Age"));

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

//        BoardImpl board = new BoardImpl();
        GameImpl game = new GameImpl();
//        Random random = new Random();

//        for (Vertex vertex : board.getVertexes()) {
//            if (random.nextBoolean())
//                board.place(new Coin(CoinSize.QUARTER, Side.random()), vertex.getName());
//            if (random.nextBoolean())
//                board.place(new Coin(CoinSize.NICKEL, Side.random()), vertex.getName());
//            if (random.nextBoolean())
//                board.place(new Coin(CoinSize.PENNY, Side.random()), vertex.getName());
//            if (random.nextBoolean())
//                board.place(new Coin(CoinSize.DIME, Side.random()), vertex.getName());
//        }
        infoPanel.add(new SwingPlayer(game.state().getHeadsPlayer(), game.state()));
        infoPanel.add(new SwingBoard("coinage_map_medium.png", game.getBoard()));
        infoPanel.add(new SwingPlayer(game.state().getTailsPlayer(), game.state()));

        panel.add(infoPanel);

        final TextBasedAPI gameTextAPI = new TextBasedAPI(game);

        final JLabel infoLabel = new JLabel("Welcome");
        panel.add(infoLabel);
        final JTextField textField = new JTextField();
        panel.add(textField);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    String text = textField.getText();
                    Result result = gameTextAPI.evaluate(text);
                    infoLabel.setText(result.toString());
                    infoPanel.repaint();
                    textField.setText("");
                }
            }
        });
        JButton ok = new JButton("Ok");
        panel.add(ok);
        ok.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                Result result = gameTextAPI.evaluate(text);
                infoLabel.setText(result.toString());
                infoPanel.repaint();
            }
        });



        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
