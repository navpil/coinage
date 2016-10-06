package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.api.ImagePanel;
import ua.lviv.navpil.coinage.model.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

public class SwingAPI {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Coin Age"));
        BoardImpl board = new BoardImpl();

        Random random = new Random();

        for (Vertex vertex : board) {
            if (random.nextBoolean())
                board.place(new Coin(CoinSize.QUARTER, Side.random()), vertex.getName());
            if (random.nextBoolean())
                board.place(new Coin(CoinSize.NICKEL, Side.random()), vertex.getName());
            if (random.nextBoolean())
                board.place(new Coin(CoinSize.PENNY, Side.random()), vertex.getName());
            if (random.nextBoolean())
                board.place(new Coin(CoinSize.DIME, Side.random()), vertex.getName());
        }
        panel.add(new SwingBoard("coinage_map_medium.png", board));

        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
