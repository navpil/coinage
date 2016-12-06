package ua.lviv.navpil.coinage.api.gui.swing;

import ua.lviv.navpil.coinage.api.gui.core.ItemSelectionListener;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Vertex;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SwingBoard extends JPanel {

    private static final int UNIT = 100;

    private final BufferedImage image;
    private final Map<String, XY> vertexLocations = new HashMap<>();
    private ItemSelectionListener listener;
    private Collection<String> selectedItems = Collections.emptySet();
    private Collection<Vertex> vertexes = Collections.emptyList();


    public SwingBoard(String image) {
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
            //todo: handle it somehow differently - probably providing some textual stuff... do we need to handle it?
            throw new RuntimeException(ex);
        }

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                vertexLocations.entrySet().stream()
                        .filter((entry) -> isWithin(x, y, entry.getValue()))
                        .findFirst()
                        .ifPresent((entity) -> listener.positionSelected(entity.getKey()));
            }
        });
    }

    public void setSelectionListener(ItemSelectionListener listener) {
        this.listener = listener;
    }

    public void setSelectedPositions(Collection<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    private boolean isWithin(int x, int y, XY upLeft) {
        //todo: this is a square calculation - the Polygon includesPoint if such function exists
        return x >= upLeft.x && x <= (upLeft.x + UNIT) && y >= upLeft.y && y <= (upLeft.y + UNIT);
    }

    public void updateVertexes(Collection<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        for (Vertex vertex : vertexes) {
            XY point = vertexLocations.get(vertex.getName());
            for (Coin coin : vertex.getCoins()) {
                //todo - should we cache/reuse the SwingCoin for performance? - try it and check with profiler
                new SwingCoin(coin).paintComponent(g.create(point.x, point.y, UNIT, UNIT));
            }
            if (selectedItems.contains(vertex.getName())) {
                g.setColor(Color.CYAN);
                ((Graphics2D) g).setStroke(new BasicStroke(2));
                g.drawRect(point.x, point.y, UNIT, UNIT);
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
