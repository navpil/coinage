package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SwingBoard extends JPanel {

    private final BufferedImage image;
    private final Board board;
    private final Map<String, XY> vertexLocations = new HashMap<String, XY>();

    public SwingBoard(String image, Board board) {
        this.board = board;

        vertexLocations.put("A1", XY.of(30, 10));
        vertexLocations.put("A2", XY.of(195, 10));
        vertexLocations.put("A3", XY.of(360, 10));
        vertexLocations.put("B1", XY.of(0, 130));
        vertexLocations.put("B2", XY.of(120, 130));
        vertexLocations.put("B3", XY.of(270, 130));
        vertexLocations.put("B4", XY.of(410, 130));
        vertexLocations.put("C1", XY.of(30, 250));
        vertexLocations.put("C2", XY.of(195, 250));
        vertexLocations.put("C3", XY.of(360, 250));
        try {
            this.image = ImageIO.read(SwingBoard.class.getClassLoader().getResource(image));
        } catch (IOException ex) {
            //todo: handle it somehow differently - probably providing some textual stuff
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        for (Vertex vertex : board) {
            XY point = vertexLocations.get(vertex.getName());
            for (Coin coin : vertex.getCoins()) {
                new SwingCoin(coin).paintComponent(g.create(point.x, point.y, 100, 100));
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    private static class XY {
        public final int x;
        public final int y;

        private XY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static XY of(int x, int y) {
            return new XY(x, y);
        }
    }
}
