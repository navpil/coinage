package ua.lviv.navpil.coinage.api;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private final BufferedImage image;

    public ImagePanel(String image) {
        try {
            this.image = ImageIO.read(ImagePanel.class.getClassLoader().getResource(image));
        } catch (IOException ex) {
            //todo: handle it somehow differently - probably providing some textual stuff
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
}
