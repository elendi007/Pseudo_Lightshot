package lightshot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel_2 extends JPanel {
    private BufferedImage bufferedImage;

    ImagePanel_2(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
    }
}
